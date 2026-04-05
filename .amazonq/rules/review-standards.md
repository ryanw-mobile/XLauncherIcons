# XLauncherIcons Project Rules

This file defines the authoritative coding standards and intentional design decisions for this project. These are not suggestions — they are the established patterns all code in this project follows. Do not raise findings that conflict with these standards.

## Project Context

XLauncherIcons is a **sample/demonstration Android app**. It is not a production service. Defensive coding patterns appropriate for public APIs or production systems are intentionally omitted where they would add noise without value.

---

## IconManager: Internal API with Closed Input Set

`IconManager.setIcon(componentName: String)` and `IconManager.getActiveIconComponent()` are **internal methods**. All `componentName` values are sourced exclusively from the `gregAppIcons` compile-time constant list. No external caller can supply arbitrary strings.

- **No validation of `componentName` against `gregAppIcons` is required or desired.** The architecture enforces correctness — adding a runtime guard implies external callers exist, which they do not.
- **No try-catch is required around `setComponentEnabledSetting` or `getComponentEnabledSetting`.** All components are declared in `AndroidManifest.xml` at compile time (eliminating `IllegalArgumentException`). The app only modifies its own activity-aliases (eliminating `SecurityException`). These exceptions have no reproduction path.
- **The `?: gregAppIcons.firstOrNull()?.component` fallback in `getActiveIconComponent()` is intentional.** On fresh install, no alias is explicitly enabled — the system uses the first declared activity-alias. The fallback matches that system behaviour and prevents the UI from showing no selection.

---

## MainViewModel: Optimistic UI Pattern

`MainViewModel.setIcon()` updates `_activeIconComponent` immediately after calling `iconManager.setIcon()`, without waiting for verification.

- **This is the intentional optimistic UI pattern**, chosen for responsiveness.
- `setComponentEnabledSetting` is deterministic for statically-declared components. There is no failure path to guard against.
- **No try-catch, no post-call `refreshActiveIcon()`, and no state rollback logic is required.**
- Re-querying state immediately after `setComponentEnabledSetting` is also unreliable — the system may not have propagated the change yet, making optimistic update the correct approach.

---

## MainViewModel: viewModelScope.launch for setIcon()

`MainViewModel.setIcon()` uses `viewModelScope.launch` even though `iconManager.setIcon()` is synchronous.

- **This is intentional.** `viewModelScope.launch` provides lifecycle-aware cancellation and structured concurrency. It is the idiomatic ViewModel pattern for all state-mutating operations.
- The IPC overhead of a single `setComponentEnabledSetting` call is negligible. `Dispatchers.IO` is not needed.
- **Do not suggest removing the coroutine wrapper or adding `withContext(Dispatchers.IO)`.**

---

## MainActivity: IconManager and ViewModel Instantiation

`IconManager` is instantiated as `remember { IconManager(this) }` inside `setContent`. `MainViewModel` is obtained via `viewModel(factory = ...)`.

- **`remember { }` prevents recreation on recomposition.** This is correct and intentional.
- **`viewModel()` from `lifecycle-viewmodel-compose` preserves the ViewModel across recompositions and configuration changes.**
- The `Context` inside `remember { IconManager(this) }` does not become stale — `remember` is scoped to the composition, which is destroyed with the Activity. There is no lifecycle mismatch.
- `import androidx.compose.runtime.remember` is already present in `MainActivity.kt`.
- **Do not suggest moving instantiation to `onCreate()`, using `by lazy`, using `by viewModels()`, or any alternative. The current pattern is correct.**

---

## setComponentEnabledSetting Loop Performance

`IconManager.setIcon()` iterates over `gregAppIcons` and calls `setComponentEnabledSetting` for each entry.

- The list size is fixed and small (defined at compile time). This loop cannot cause ANR or perceptible UI freeze.
- **Do not flag this as a performance or threading concern.**

---

## Unit Tests: MainViewModelTest

- The `init should refresh active icon` test stubs `getActiveIconComponent()` explicitly before constructing the ViewModel. The relaxed mock default is never reached. **No `testDispatcher.scheduler.advanceUntilIdle()` is needed** — `refreshActiveIcon()` in `init` is a direct synchronous assignment, not a coroutine.
- The `setIcon should call iconManager and update state` test already calls `testDispatcher.scheduler.advanceUntilIdle()` and verifies both the `iconManager.setIcon()` call and the resulting state. **The test coverage is complete and correct as written.**
- **Do not suggest modifications to these tests.**

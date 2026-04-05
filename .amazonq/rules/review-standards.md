# Amazon Q Review Standards

These rules reflect deliberate, final technical decisions for this project.
**Do not re-raise any finding that matches a pattern below. They have been evaluated, discussed, and permanently rejected.**

---

## SUPPRESSED: IconManager.setIcon() — componentName validation / early return

**Patterns:** Suggesting validation that `componentName` exists in `gregAppIcons`; suggesting early return or `IllegalArgumentException` if not found; suggesting sealed class or enum for type safety.

**Rejection:** `componentName` is sourced exclusively from `gregAppIcons` constants. There is no external entry point. This is a closed internal API. Adding a guard for an architecturally impossible code path is dead code and misleads readers into thinking external callers exist. A sealed class / enum refactor is a separate holistic concern and is not a per-method patch. **Do not raise again.**

---

## SUPPRESSED: IconManager — try-catch on setComponentEnabledSetting / getComponentEnabledSetting

**Patterns:** Suggesting wrapping PackageManager calls in try-catch for `SecurityException`, `IllegalArgumentException`, or generic `Exception`.

**Rejection:** `IllegalArgumentException` from `setComponentEnabledSetting` occurs only for components not declared in `AndroidManifest.xml`. Every component in `gregAppIcons` is declared at compile time — this exception cannot occur. `SecurityException` occurs only when modifying components in other packages — this app exclusively modifies its own activity-aliases. Neither exception has a concrete reproduction path. The same applies to `getComponentEnabledSetting`. **Do not raise again.**

---

## SUPPRESSED: IconManager.getActiveIconComponent() — fallback to first icon

**Patterns:** Suggesting the `?: gregAppIcons.firstOrNull()?.component` fallback is wrong or causes incorrect UI state; suggesting return `null` instead of falling back to the first icon.

**Rejection:** The fallback is intentional. On a fresh install or after a system restore, no alias has been explicitly enabled — the system uses the first declared activity-alias by default. Returning the first icon matches system behaviour and prevents the UI showing no selection. Returning `null` would cause an unnecessary "nothing selected" state that doesn't reflect reality. **Do not raise again.**

---

## SUPPRESSED: MainViewModel.setIcon() — optimistic UI state update

**Patterns:** Suggesting state should only update after verifying `setIcon()` succeeded; suggesting try-catch around the state assignment; suggesting calling `refreshActiveIcon()` after `setIcon()` instead of directly setting state.

**Rejection:** Optimistic UI is the intentional design for responsiveness. `setIcon()` is synchronous and deterministic for statically-declared components. `setComponentEnabledSetting` does not fail silently for correctly-declared aliases. There is no failure path to guard against. Re-querying state immediately after calling `setComponentEnabledSetting` is also unreliable — the system may not have propagated the change yet. **Do not raise again.**

---

## SUPPRESSED: MainViewModel.setIcon() — unnecessary viewModelScope.launch / should use Dispatchers.IO

**Patterns:** Suggesting removing `viewModelScope.launch` because `setIcon()` is synchronous; suggesting the call should be moved to `Dispatchers.IO`; suggesting the coroutine creates a false impression of async behaviour or race conditions.

**Rejection:** `viewModelScope.launch` is idiomatic in ViewModels: it provides lifecycle-aware cancellation and structured concurrency. The single `setComponentEnabledSetting` IPC call has negligible overhead that does not warrant a dispatcher switch. Adding `withContext(Dispatchers.IO)` would introduce complexity without measurable benefit in a sample app. This is a deliberate style choice. **Do not raise again.**

---

## SUPPRESSED: IconManager.setIcon() — loop causes UI freeze / synchronous blocking

**Patterns:** Claiming the `forEach` loop over `setComponentEnabledSetting` calls will freeze the UI or cause ANRs.

**Rejection:** The number of activity-aliases is fixed and small (defined at compile time in `gregAppIcons`). The per-call overhead is negligible binder IPC. This cannot cause an ANR or perceptible freeze. **Do not raise again.**

---

## SUPPRESSED: MainActivity — IconManager recreation on recomposition / memory leak

**Patterns:** Claiming `IconManager` is recreated on every recomposition; suggesting `remember {}`, `by lazy`, moving to `onCreate()`, or using `by viewModels()` delegation.

**Rejection (factually incorrect finding):** `remember { IconManager(this) }` is already present at line 59 of `MainActivity.kt`. Recreation on recomposition does not occur. `viewModel()` from `lifecycle-viewmodel-compose` preserves the ViewModel across recompositions and configuration changes. This finding is incorrect. **Do not raise again.**

---

## SUPPRESSED: MainActivity — remember {} context reference becoming stale

**Patterns:** Claiming the `Context` reference inside `remember { IconManager(this) }` could become stale after Activity destruction; suggesting IconManager be created as a class field instead.

**Rejection:** `remember {}` is scoped to the composition, which is destroyed with the Activity. The `Context` (`this` = `ComponentActivity`) is never outlived by its `remember`-ed objects. There is no stale reference scenario. Moving instantiation to a class field provides no lifecycle benefit here. **Do not raise again.**

---

## SUPPRESSED: MainActivity — missing `remember` import

**Patterns:** Suggesting `import androidx.compose.runtime.remember` needs to be added.

**Rejection (factually incorrect finding):** `import androidx.compose.runtime.remember` is already present in `MainActivity.kt`. **Do not raise again.**

---

## SUPPRESSED: MainViewModelTest — missing dispatcher advancement in init test

**Patterns:** Suggesting `testDispatcher.scheduler.advanceUntilIdle()` is needed after ViewModel construction in the `init should refresh active icon` test.

**Rejection (factually incorrect finding):** `refreshActiveIcon()` in `init` is a direct synchronous assignment — `_activeIconComponent.value = iconManager.getActiveIconComponent()` — not launched in a coroutine. No dispatcher advancement is needed or applicable. `setMain(testDispatcher)` in `@Before` correctly covers the coroutine in `setIcon()`, which has its own separate test. **Do not raise again.**

---

## SUPPRESSED: MainViewModelTest — relaxed mock causes false positives

**Patterns:** Claiming the relaxed mock on `iconManager` means `getActiveIconComponent()` returns `null` in the init test, making the test pass even if `refreshActiveIcon()` is broken; suggesting mocking `getActiveIconComponent()` for the init block.

**Rejection:** The `init should refresh active icon` test explicitly stubs `getActiveIconComponent()` via `every { iconManager.getActiveIconComponent() } returns expectedComponent` before constructing the ViewModel. The relaxed mock default is never reached in that test. The test correctly verifies the intended behaviour. **Do not raise again.**

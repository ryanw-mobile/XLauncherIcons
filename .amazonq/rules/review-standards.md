# Amazon Q Review Standards

These rules reflect deliberate, final technical decisions. **Do not re-raise findings that match these patterns.** They have been discussed, evaluated, and permanently rejected with full technical justification.

---

## PERMANENTLY SUPPRESSED: IconManager.setIcon() — componentName validation

**Finding pattern:** Suggesting validation that `componentName` exists in `gregAppIcons`, or suggesting `IllegalArgumentException` / early return when not found.

**Why this is rejected (final):**
- `componentName` values are sourced exclusively from `gregAppIcons` constants. There is no external entry point.
- This is a closed internal API. No caller in this codebase can pass an arbitrary string.
- Adding a guard for a code path that is architecturally impossible is dead code.
- A sealed class / enum refactor is a separate holistic concern, not a per-method patch.

**Do not raise again.**

---

## PERMANENTLY SUPPRESSED: IconManager — try-catch for PackageManager operations

**Finding pattern:** Suggesting wrapping `setComponentEnabledSetting` or `getComponentEnabledSetting` in try-catch for `SecurityException`, `IllegalArgumentException`, or generic `Exception`.

**Why this is rejected (final):**
- `IllegalArgumentException` is thrown only for components not declared in `AndroidManifest.xml`. Every component in `gregAppIcons` is declared at compile time. This exception cannot occur.
- `SecurityException` is thrown only when modifying components in other packages. This app exclusively modifies its own activity-aliases. This exception cannot occur.
- There is no concrete reproduction path for either exception in this codebase.

**Do not raise again.**

---

## PERMANENTLY SUPPRESSED: MainViewModel.setIcon() — optimistic UI state update

**Finding pattern:** Suggesting state should only be updated after verifying `iconManager.setIcon()` succeeded, or adding try-catch around the state assignment.

**Why this is rejected (final):**
- Optimistic UI update is the intentional design for responsiveness.
- `setIcon()` is synchronous and deterministic for statically-declared components.
- `setComponentEnabledSetting` does not fail silently for correctly-declared components (see above).
- Pessimistic updates would introduce unnecessary UI lag in a sample app where no failure path exists.

**Do not raise again.**

---

## PERMANENTLY SUPPRESSED: MainViewModel.setIcon() — unnecessary viewModelScope.launch

**Finding pattern:** Suggesting removing `viewModelScope.launch` because `iconManager.setIcon()` is synchronous.

**Why this is rejected (final):**
- Using `viewModelScope.launch` is idiomatic for ViewModels: it provides lifecycle-aware cancellation and structured concurrency even for synchronous calls.
- This is a deliberate style choice, not a bug or performance issue.

**Do not raise again.**

---

## PERMANENTLY SUPPRESSED: MainActivity — IconManager recreation on recomposition

**Finding pattern:** Claiming `IconManager` is recreated on every recomposition, suggesting `remember {}`, moving instantiation to `onCreate()`, or using `by lazy`.

**Why this is rejected (final, and factually wrong):**
- `remember { IconManager(this) }` is already present at line 59 of `MainActivity.kt`. This prevents recreation on recomposition.
- `viewModel()` from `lifecycle-viewmodel-compose` already preserves the ViewModel across recompositions and configuration changes.
- This finding has been raised and dismissed as factually incorrect. The code is correct as written.

**Do not raise again.**

---

## PERMANENTLY SUPPRESSED: MainViewModelTest — missing dispatcher advancement in init test

**Finding pattern:** Suggesting `testDispatcher.scheduler.advanceUntilIdle()` after ViewModel construction in the `init should refresh active icon` test.

**Why this is rejected (final, and factually wrong):**
- `refreshActiveIcon()` called in `init` is a **direct synchronous assignment**: `_activeIconComponent.value = iconManager.getActiveIconComponent()`.
- It is NOT launched in a coroutine. No dispatcher advancement is needed or applicable.
- This finding is factually incorrect about the implementation.
- The `setMain(testDispatcher)` in `@Before` correctly covers the coroutine in `setIcon()`, which has its own test.

**Do not raise again.**

# Amazon Q Review Standards

These rules reflect deliberate technical decisions for this project. Findings matching these patterns should not be re-raised.

## Suppressed Finding Classes

### 1. ComponentName validation in IconManager.setIcon()
**Pattern:** Suggesting validation that `componentName` exists in `gregAppIcons` before calling `setComponentEnabledSetting`.

**Reason:** `componentName` values are internal constants from `gregAppIcons`. There is no external entry point that can supply an arbitrary string. Adding a guard for a code path that cannot exist by construction adds noise without value. If type-safety is ever required in future, a sealed class or enum refactor would be done holistically — not patched per-method.

### 2. try-catch around PackageManager operations in IconManager
**Pattern:** Suggesting wrapping `setComponentEnabledSetting` or `getComponentEnabledSetting` in try-catch for `SecurityException` or `IllegalArgumentException`.

**Reason:** `IllegalArgumentException` is thrown only for components not declared in `AndroidManifest.xml`. All components in `gregAppIcons` are declared there at compile time. `SecurityException` is thrown only when attempting to modify components in other packages. This app only modifies its own aliases. These are theoretical concerns with no concrete reproduction path.

### 3. Optimistic UI state update in MainViewModel.setIcon()
**Pattern:** Suggesting state should only be updated after `iconManager.setIcon()` succeeds, or that try-catch should be added around the state update.

**Reason:** Optimistic UI is the intentional design. `setIcon()` is a synchronous, deterministic operation on declared activity-aliases. Pessimistic state updates would introduce unnecessary UI lag. If `setIcon()` were to throw (which it cannot given the constraints above), `refreshActiveIcon()` would be called on resume to self-correct.

### 4. viewModelScope.launch for synchronous setIcon()
**Pattern:** Suggesting removing `viewModelScope.launch` because `iconManager.setIcon()` is synchronous.

**Reason:** Using `viewModelScope.launch` is idiomatic in ViewModels for ensuring structured concurrency and lifecycle-aware cancellation, even for synchronous calls. It also makes the function non-blocking from the call site. This is a deliberate style choice, not a bug.

### 5. IconManager / ViewModel recreation on recomposition in MainActivity
**Pattern:** Claiming `IconManager` is recreated on every recomposition and suggesting `remember {}` or moving instantiation outside `setContent`.

**Reason:** `remember { IconManager(this) }` is already present at line 59 of `MainActivity.kt`. This finding is factually incorrect. `viewModel()` from `lifecycle-viewmodel-compose` also prevents recreation. No code change is needed.

### 6. Test dispatcher advancement for init block in MainViewModelTest
**Pattern:** Suggesting `testDispatcher.scheduler.advanceUntilIdle()` is needed after ViewModel construction in the `init should refresh active icon` test.

**Reason:** `refreshActiveIcon()` called in `init` is a direct synchronous assignment (`_activeIconComponent.value = iconManager.getActiveIconComponent()`), not launched in a coroutine. No dispatcher advancement is needed. The `setMain(testDispatcher)` in `@Before` is sufficient for the `setIcon` test which does use a coroutine.

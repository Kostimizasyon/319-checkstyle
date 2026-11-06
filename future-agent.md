# Instructions for Future Agent: Fixing `Group_Name_Proj1_Report.pdf`

## 1. What Went Wrong in `Group_Name_Proj1_Report.pdf`

1. **Wrong Table Classification (Ignored Folder Structure):**
   - Previously, the report split the 16 files inside `lesson-vm-out/design smells/` into 4 "Anti-Patterns" (Part I table) and 12 "Code Smells" (Part II table) based on general software engineering theory, and **completely ignored** the 33 files inside `lesson-vm-out/code smells/`.
   - **Rule:** Disregard theoretical classifications or internal sub-smell breakdowns when building the tables. Classify smells **strictly based on the folder they are located in**:
     - **Part I Table (`1. Anti-Pattern Types (Design Smells) Detected by PTIDEJ`):** Must list **all 16 files** inside `lesson-vm-out/design smells/`.
     - **Part II Table (`1. Code Smells Detected by PTIDEJ`):** Must list **all 33 files** inside `lesson-vm-out/code smells/`.

2. **Unwanted Metric Calculation Details (`NMD+NAD`, `LCOM5`, `NOC`, `IR`, etc.):**
   - The current PDF includes internal PTIDEJ metric formulas, bounds, and calculations (such as `NMD+NAD = 59.0`, `NMD+NAD = 57.0`, `NMD+NAD = 68.0`, `NMD+NAD = 15.0`, `NMD+NAD = 41 > 40.5`, `LCOM5 = 1.5 > 1.3257`, `LCOM5_MaxBound = 1.3257575861432334`, `NOC = 1`, `IR`, etc.).
   - **Rule:** Remove all metric calculation formulas, thresholds, and raw metric numbers from both the tables and the prose/justifications across the entire PDF. Explain the smells and refactorings purely in terms of code structure, design, maintainability, and runtime behavior.

---

## 2. Exact Data for Part I Table (`lesson-vm-out/design smells/`)

Read all 16 files in `lesson-vm-out/design smells/` (each ends with `#----> Total:N`). Do not include theoretical descriptions or metric formulas in the table:

| Anti-Pattern / Design Smell Type | Instances | Source File (`lesson-vm-out/design smells/`) |
| :--- | :---: | :--- |
| `RefusedParentBequest` | 40 | `RefusedParentBequest.ini` |
| `ComplexClass` | 31 | `ComplexClass.ini` |
| `LongParameterList` | 19 | `LongParameterList.ini` |
| `LazyClass` | 13 | `LazyClass.ini` |
| `Blob` | 7 | `Blob.ini` |
| `ClassDataShouldBePrivate` | 7 | `ClassDataShouldBePrivate.ini` |
| `AntiSingleton` | 3 | `AntiSingleton.ini` |
| `BaseClassShouldBeAbstract` | 2 | `BaseClassShouldBeAbstract.ini` |
| `SpeculativeGenerality` | 2 | `SpeculativeGenerality.ini` |
| `BaseClassKnowsDerivedClass` | 0 | `BaseClassKnowsDerivedClass.txt` |
| `FunctionalDecomposition` | 0 | `FunctionalDecomposition.txt` |
| `LargeClass` | 0 | `LargeClass.txt` |
| `ManyFieldAttributesButNotComplex` | 0 | `ManyFieldAttributesButNotComplex.ini` |
| `MessageChains` | 0 | `MessageChains.ini` |
| `SwissArmyKnife` | 0 | `SwissArmyKnife.ini` |
| `TraditionBreaker` | 0 | `TraditionBreaker.ini` |
| **Total Design Smells (Anti-Patterns)** | **124** | **Across 16 files in `lesson-vm-out/design smells/`** |

---

## 3. Exact Data for Part II Table (`lesson-vm-out/code smells/`)

Read all 33 files in `lesson-vm-out/code smells/` (each has `total = N` in its `[Summary]` section). List all 33 in the Part II table:

| Code Smell Type | Instances | Source File (`lesson-vm-out/code smells/`) |
| :--- | :---: | :--- |
| `ParentClassProvidesProtectedDetection` | 434 | `ParentClassProvidesProtectedDetection.ini` |
| `MethodNoParameterDetection` | 415 | `MethodNoParameterDetection.ini` |
| `NotAbstract` | 415 | `NotAbstract.ini` |
| `NoPolymorphism` | 401 | `NoPolymorphism.ini` |
| `TwoInheritanceDetection` | 327 | `TwoInheritanceDetection.ini` |
| `NotComplexClassDetection` | 197 | `NotComplexClassDetection.ini` |
| `RareOverridingDetection` | 163 | `RareOverridingDetection.ini` |
| `LongMethodClassDetection` | 135 | `LongMethodClassDetection.ini` |
| `ChildClassDetection` | 131 | `ChildClassDetection.ini` |
| `FieldPrivate` | 128 | `FieldPrivate.ini` |
| `NoInheritanceDetection` | 107 | `NoInheritanceDetection.ini` |
| `NotComplexDetection` | 94 | `NotComplexDetection.ini` |
| `ComplexClassOnlyDetection` | 49 | `ComplexClassOnlyDetection.ini` |
| `LowCohesionDetection` | 42 | `LowCohesionDetection.ini` |
| `LowCohesionOnlyDetection` | 42 | `LowCohesionOnlyDetection.ini` |
| `DataClass` | 36 | `DataClass.ini` |
| `LargeClassDetection` | 31 | `LargeClassDetection.ini` |
| `LargeClassOnlyDetection` | 31 | `LargeClassOnlyDetection.ini` |
| `ManyAttributesDetection` | 30 | `ManyAttributesDetection.ini` |
| `ClassOneMethod` | 25 | `ClassOneMethod.ini` |
| `HasChildrenDetection` | 22 | `HasChildrenDetection.ini` |
| `AbstractClass` | 19 | `AbstractClass.ini` |
| `LongParameterListClassDetection` | 19 | `LongParameterListClassDetection.ini` |
| `LongMethodDetection` | 16 | `LongMethodDetection.ini` |
| `FewMethodsDetection` | 13 | `FewMethodsDetection.ini` |
| `ControllerClassDetection` | 7 | `ControllerClassDetection.ini` |
| `FieldPublic` | 7 | `FieldPublic.ini` |
| `OneChildClassDetection` | 5 | `OneChildClassDetection.ini` |
| `ClassGlobalVariable` | 3 | `ClassGlobalVariable.ini` |
| `FunctionClassDetection` | 3 | `FunctionClassDetection.ini` |
| `NotClassGlobalVariable` | 3 | `NotClassGlobalVariable.ini` |
| `MessageChainsClassDetection` | 0 | `MessageChainsClassDetection.ini` |
| `MultipleInterface` | 0 | `MultipleInterface.ini` |
| **Total Code Smells** | **3350** | **Across 33 files in `lesson-vm-out/code smells/`** |

---

## 4. Text & Section Updates Required in the PDF

1. **Part I, Section 2 (`Most Impactful Anti-Pattern on CheckStyle Quality & Justification`):**
   - Remove `with NMD+NAD = 59.0, Checker with NMD+NAD = 57.0, JavadocMethodCheck with NMD+NAD = 68.0, and AuditEventDefaultFormatter with LowCohesion LCOM5 = 1.5 > 1.3257`.
   - Remove `(flagged by PTIDEJ as NotClassGlobalVariable)`.
   - Also mention `ClassDataShouldBePrivate` and `LongParameterList` alongside `Blob` and `AntiSingleton` (matching `lesson-answer-draft.txt`, since all of them are in `lesson-vm-out/design smells/`).

2. **Part I, Section 3 (`Refactoring Two Anti-Pattern Instances`):**
   - **Instance 1 (`AntiSingleton` in `JavaLanguageParser`):**
     - Remove `(NotClassGlobalVariable)` and `PTIDEJ's NotClassGlobalVariable check` — refer only to `AntiSingleton` (`AntiSingleton.ini`).
   - **Instance 2 (`Blob` #4 in `AuditEventDefaultFormatter` & `AuditEvent`):**
     - In `a. Reason for Choosing the Anti-Pattern Instance`: Remove `flagged for LowCohesion with LCOM5 = 1.5 > 1.3257` and `flagged as DataClass with NMD+NAD = 15.0`.
     - In `c. Steps to Refactor`: Change `Step 3 (Restore Instance Cohesion / Reduce LCOM5 below 1.3257)` and remove `the Henderson-Sellers LCOM5 metric drops below 1.0 (well under LCOM5_MaxBound = 1.3257575861432334)`. State simply that sharing cohesive instance fields (`separatorLength` and `checkSuffix`) across the constructor and `format()` restores class cohesion.
     - In `d. Refactored Code Snippets & PTIDEJ Verification Result`: Fix the mid-word line wrapping of `// Anti-Pattern Refactor` in the `AuditEvent.java` snippet, and remove `LCOM5 drops from 1.5 to <= 1.0 (below the 1.3257 threshold)`.

3. **Part II, Section 2 & Section 3 (`Selected Code Smells and Justification` & `Refactoring`):**
   - Reference the actual Code Smell files from `lesson-vm-out/code smells/` (and `lesson-answer-draft.txt`):
     - **Smell A:** `OneChildClassDetection` (`OneChildClass`, 5 instances in `lesson-vm-out/code smells/OneChildClassDetection.ini`), specifically `OneChildClass.2` (`AbstractRootNode` -> `RootNode`) and `OneChildClass.5` (`AbstractElementNode` -> `ElementNode`). Remove references to `SpeculativeGenerality.ini` and `NOC = 1`.
     - **Smell B:** `LongMethodClassDetection` (`LongMethodClass`, 135 instances in `lesson-vm-out/code smells/LongMethodClassDetection.ini`), specifically `LongMethodClass.93` (`ImportControlLoader.startElement`). Remove references to `ComplexClass #2`, `NMD+NAD = 41 > 40.5`, and the note about `the ComplexClass metric counts methods plus attributes`.
   - Also mention `FieldPublic` (7 instances) and `ClassOneMethod` (25 instances) in Part II Section 2 as discussed in `lesson-answer-draft.txt`.
CHANGE GROUPNAME TO TOZARING

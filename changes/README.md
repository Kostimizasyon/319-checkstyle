# Code Changes & Refactoring Review Directory

This directory contains the original files, refactored files, and git diffs for comparing all changes made for **Part I (Anti-Patterns)** and **Part II (Code Smells)**.

---

## 📁 Directory Structure

```text
changes/
├── diffs/
│   ├── all_changes.diff
│   ├── 1_AntiSingleton_JavaLanguageParser.diff
│   ├── 2_Blob_AuditEvent_Formatter.diff
│   ├── 3_CodeSmell_SpeculativeGenerality_xpath.diff
│   └── 4_CodeSmell_ComplexClass_ImportControlLoader.diff
│
├── original_files/       (Unmodified versions prior to refactoring)
│   ├── JavaLanguageParser.g4
│   ├── AuditEventDefaultFormatter.java
│   ├── AuditEvent.java
│   ├── AbstractRootNode.java      [DELETED in refactoring]
│   ├── RootNode.java
│   ├── AbstractElementNode.java   [DELETED in refactoring]
│   ├── ElementNode.java
│   └── ImportControlLoader.java
│
└── refactored_files/     (Active refactored versions with explanation comments)
    ├── JavaLanguageParser.g4
    ├── AuditEventDefaultFormatter.java
    ├── AuditEvent.java
    ├── RootNode.java              [Inherits directly from AbstractNode]
    ├── ElementNode.java           [Inherits directly from AbstractNode]
    └── ImportControlLoader.java
```

---

## 🔍 Change Breakdown & Summary

### 1. AntiSingleton
- **Diff:** `diffs/1_AntiSingleton_JavaLanguageParser.diff`
- **Original File:** `original_files/JavaLanguageParser.g4`
- **Refactored File:** `refactored_files/JavaLanguageParser.g4`
- **Summary:** Encapsulated the raw mutable `static int fileCounter` inside a `public static final class DfaClearCounter` helper with synchronized thread-safe increments (`tick()`), eliminating shared mutable static state.

### 2. Blob
- **Diff:** `diffs/2_Blob_AuditEvent_Formatter.diff`
- **Original Files:** `original_files/AuditEventDefaultFormatter.java`, `original_files/AuditEvent.java`
- **Refactored Files:** `refactored_files/AuditEventDefaultFormatter.java`, `refactored_files/AuditEvent.java`
- **Summary:** 
  - Transferred domain logic (`getCheckShortName`, `getSourceIdentifier`) to `AuditEvent.java` to cure Feature Envy / DataClass smell.
  - Removed disconnected static helper in `AuditEventDefaultFormatter.java` and added cohesive instance variables (`separatorLength`, `checkSuffix`) to lower LCOM5 below threshold.

### 3. Speculative Generality / OneChildClass
- **Diff:** `diffs/3_CodeSmell_SpeculativeGenerality_xpath.diff`
- **Original Files:** `original_files/AbstractRootNode.java`, `original_files/RootNode.java`, `original_files/AbstractElementNode.java`, `original_files/ElementNode.java`
- **Refactored Files:** `refactored_files/RootNode.java`, `refactored_files/ElementNode.java`
- **Deleted Files:** `AbstractRootNode.java`, `AbstractElementNode.java`
- **Summary:** Applied *Collapse Hierarchy*. Folded single-child abstract classes (`AbstractRootNode` and `AbstractElementNode`) directly into their only concrete subclasses (`RootNode` and `ElementNode`). Eliminated speculative abstractions while keeping behavior identical.

### 4. ComplexClass / Long Method
- **Diff:** `diffs/4_CodeSmell_ComplexClass_ImportControlLoader.diff`
- **Original File:** `original_files/ImportControlLoader.java`
- **Refactored File:** `refactored_files/ImportControlLoader.java`
- **Summary:** Extracted sub-logic inside the bloated `startElement()` method into three dedicated helper methods (`startImportControlElement`, `startSubpackageElement`, `startFileElement`), converting `startElement()` into a readable dispatcher.

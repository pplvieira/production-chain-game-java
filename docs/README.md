# Documentation Index

Welcome to the Medieval Production Chain Game documentation. This directory contains comprehensive documentation for developers, maintainers, and end-users.

The idea with this game is to implement a super advanced micromanagement production and trading game in a medieval setting. Implementing super realistic mechanics like 
- a realistic, detailed, and historically accurate resource production trees, 
- a labor system where workers spend their working hours executing tasks like recipes in production buildings, tending to animals and buildings maintenance, household chores, and transporting of goods
- a free market governed by free trade between player lords and NPCs, where players can exchange goods at monetary or other goods prices set by the every-shifting market
- a very technical, statistical and analytical approach to solving problems and taking decisions
- a weather system and realistic interactions where real world events are simulated and influence mechanics in the gameplay


## 📁 Documentation Structure

### `/architecture`
Architectural documentation, design decisions, and codebase analysis.

**Contents:**
- `CODEBASE_ANALYSIS.md` - Comprehensive analysis of the entire codebase structure, design patterns, and Maven best practices review
- `QUICK_REFERENCE.md` - Quick lookup guide with architecture overview, key metrics, and critical issues

### `/development`
Developer documentation, implementation plans, and coding guides.

**Contents:**
- `JAVA_FILES_INVENTORY.txt` - Detailed inventory of all 33 Java files with class descriptions and purposes
- `v1.x.x-implementation-plan.md` - Template for version implementation planning

### `/deployment`
Deployment guides, configuration, and operations documentation.

**Contents:**
- *Coming soon* - Deployment procedures, environment setup, and production configuration

### `/user`
End-user documentation, game guides, and tutorials.

**Contents:**
- *Coming soon* - User manuals, gameplay guides, and tutorials

---

## 🚀 Quick Start for Developers

1. **New to the project?** Start with:
   - [`architecture/QUICK_REFERENCE.md`](architecture/QUICK_REFERENCE.md) - 15-minute overview
   - [`architecture/CODEBASE_ANALYSIS.md`](architecture/CODEBASE_ANALYSIS.md) - Deep dive

2. **Looking for specific classes?** See:
   - [`development/JAVA_FILES_INVENTORY.txt`](development/JAVA_FILES_INVENTORY.txt) - Every file explained

3. **Planning a new version?** Use:
   - [`development/v1.x.x-implementation-plan.md`](development/v1.x.x-implementation-plan.md) - Implementation plan template

---

## 📝 Contributing to Documentation

When adding new documentation:
- Place architectural docs in `/architecture`
- Place developer guides in `/development`
- Place deployment docs in `/deployment`
- Place user-facing docs in `/user`
- Update this README with links to new documents

---

## 🔀 Version Control

### Semantic Versioning

#### Format: `v1.1.X`
- **First digit**: Always **1** (major version)
- **Second digit**: Always **1** (for current development cycle v1.1.x)
- **Third digit**: **Increments with EVERY commit** (v1.1.4, v1.1.5, v1.1.6...)

#### Phase Completion Markers
- **Phase notation** is added ONLY when a phase is manually marked complete
- **Format**: `v1.1.X-phaseN` where X is the current commit number at completion
- **Examples**:
  - Working commits: v1.1.4 → v1.1.5 → v1.1.6 → v1.1.7
  - Phase 1 complete: `v1.1.7-phase1` (manually marked)
  - Continue working: v1.1.8 → v1.1.9 → ... → v1.1.22
  - Phase 2 complete: `v1.1.22-phase2` (manually marked)

**Key Rule**: The third digit ALWAYS increments with each commit, regardless of phase. Phase markers are added at completion milestones.

### Commit Conventions
Use conventional commit prefixes for clarity:
- `feat:` - New feature implementation
- `fix:` - Bug fixes
- `refactor:` - Code restructuring without changing functionality
- `test:` - Adding or modifying tests
- `docs:` - Documentation changes
- `chore:` - Build tasks, dependency updates, maintenance

**Format:** `<type>: v1.1.X - <description>`

**Examples of regular commits:**
```
feat: v1.1.5 - Add ResourceLoader utility class
fix: v1.1.6 - Correct path resolution in BuildingInstance
refactor: v1.1.8 - Extract constants to ItemConstants class
test: v1.1.12 - Add unit tests for RecipeHandler
docs: v1.1.3 - Update version numbering documentation
```

**Examples of phase completion commits:**
```
feat: v1.1.7-phase1 - Complete Phase 1: Cleanup & Refactoring
feat: v1.1.22-phase2 - Complete Phase 2: Core Development
feat: v1.1.45-phase3 - Complete Phase 3: Testing & Quality Assurance
feat: v1.1.50-phase4 - Complete Phase 4: Documentation & Polish
```

### Branch Strategy
```
feature/phase-N-description → develop → main
```

- **Feature branches**: `feature/phase-1-cleanup`, `feature/phase-2-core-dev`
- **Development branch**: `develop` - Integration and testing
- **Main branch**: `main` - Production-ready code only
- **Hotfix branches**: `hotfix/critical-bug-description` → `main` + `develop`

### Phase Targets (v1.1.x Development Cycle)
Phases are workflow milestones, not version ranges:
- **Phase 1** (Cleanup & Refactoring): Complete when all refactoring done
- **Phase 2** (Core Development): Complete when all features implemented
- **Phase 3** (Testing & QA): Complete when all tests pass
- **Phase 4** (Documentation & Polish): Complete when documentation finalized

Each phase completion is marked with a special commit (e.g., `v1.1.X-phase1`).

### Git Workflow Example
```bash
# Create feature branch
git checkout -b feature/phase-1-cleanup

# Regular work commits (third digit increments each time)
git add .
git commit -m "refactor: v1.1.4 - Make datatypesold package package-private"

git commit -m "feat: v1.1.5 - Add ResourceLoader utility class"

git commit -m "fix: v1.1.6 - Correct import statements after deprecation"

git commit -m "refactor: v1.1.7 - Add SLF4J logging framework"

# Phase 1 complete - manually mark completion
git commit -m "feat: v1.1.7-phase1 - Complete Phase 1: Cleanup & Refactoring"

# Continue with Phase 2 (version continues incrementing)
git commit -m "feat: v1.1.8 - Add RegistryManager class"

git commit -m "feat: v1.1.9 - Implement enhanced AdvancedStorage"

# ... many more commits ...

git commit -m "feat: v1.1.22 - Complete Player ownership system"

# Phase 2 complete - manually mark completion
git commit -m "feat: v1.1.22-phase2 - Complete Phase 2: Core Development"

# Merge to develop
git checkout develop
git merge feature/phase-1-cleanup

# After testing, merge to main and tag major milestones
git checkout main
git merge develop
git tag v1.1.22-phase2
```

---

## 🧪 Testing Before Deployment

### Pre-Deployment Testing Protocol

Before pushing code to GitHub or merging to `develop`/`main`, run this comprehensive test suite to ensure code quality and functionality.

---

#### **Phase 1: Static Code Analysis**

**1.1 Compilation Check**
```bash
# Navigate to Maven project
cd productionchainid

# Clean and compile
mvn clean compile

# Expected: BUILD SUCCESS with 0 errors
```
**Pass Criteria:**
- ✅ Zero compilation errors
- ✅ No missing dependencies
- ✅ All imports resolved

**1.2 Code Style & Syntax**
```bash
# Check for common issues
grep -r "System.out.println" src/main/java/
# Expected: Minimal or no debug statements

# Check for TODO comments
grep -r "TODO" src/main/java/
# Review and track in implementation plan
```
**Pass Criteria:**
- ✅ No debug println statements (or documented exceptions)
- ✅ All TODOs tracked in implementation plan
- ✅ No commented-out code blocks (>10 lines)

**1.3 Dependency Verification**
```bash
# Check dependency tree
mvn dependency:tree

# Check for vulnerabilities (if using plugins)
mvn dependency:analyze
```
**Pass Criteria:**
- ✅ No conflicting dependencies
- ✅ All dependencies at latest stable versions
- ✅ No unused dependencies

---

#### **Phase 2: Unit & Integration Tests**

**2.1 Run All Tests**
```bash
# Execute test suite
mvn test

# Expected: All tests pass
```
**Pass Criteria:**
- ✅ 100% of tests passing
- ✅ No test failures or errors
- ✅ Code coverage ≥ 70% (when tests exist)

**2.2 Manual Class Testing**
For classes with `main()` test methods (until proper unit tests exist):

```bash
# Test ItemRegistry
java -cp target/classes com.productionchain.enginedatatypes.ItemRegistry

# Test RecipeRegistry
java -cp target/classes com.productionchain.enginedatatypes.RecipeRegistry

# Test BuildingTypeList
java -cp target/classes com.productionchain.buildings.BuildingTypeList

# Test ProductionBuildingInstance
java -cp target/classes com.productionchain.buildings.ProductionBuildingInstance
```
**Pass Criteria:**
- ✅ No runtime exceptions
- ✅ Expected output displayed
- ✅ JSON files load correctly

**2.3 Integration Testing Checklist**

Run through critical game workflows:

- [ ] **Item Creation & Registry**
  - Load items from JSON
  - Lookup items by name
  - Verify item properties (weight, durability, etc.)

- [ ] **Recipe Execution**
  - Load recipes from JSON
  - Filter recipes by category
  - Execute recipe with valid ingredients
  - Verify ingredient consumption
  - Verify output creation

- [ ] **Building System**
  - Load building types from JSON
  - Create building instances
  - Set active recipe
  - Execute production cycle
  - Verify storage updates

- [ ] **Storage System**
  - Add items to AdvancedStorage
  - Remove items (FIFO)
  - Degrade items (durability)
  - Remove spoiled items
  - Check capacity limits

- [ ] **Animal System** (if implemented)
  - Load animal types
  - Create animal instances
  - Update animal stats
  - Check housing capacity

**Pass Criteria:**
- ✅ All workflows complete without exceptions
- ✅ Data persists correctly to JSON
- ✅ Data loads correctly from JSON

---

#### **Phase 3: File & Resource Validation**

**3.1 JSON File Integrity**
```bash
# Validate JSON syntax (requires jq or python)
for file in productionchainid/src/main/resources/*.json; do
    echo "Validating $file"
    python3 -m json.tool "$file" > /dev/null && echo "✅ Valid" || echo "❌ Invalid"
done
```
**Pass Criteria:**
- ✅ All JSON files valid
- ✅ No syntax errors
- ✅ All required fields present

**3.2 File Path Verification**
```bash
# Check for hardcoded absolute paths
grep -r "C:\\\\" src/main/java/
grep -r "/Users/" src/main/java/
grep -r "/home/" src/main/java/
```
**Pass Criteria:**
- ✅ Zero hardcoded absolute paths
- ✅ All paths use relative references or resource loaders
- ✅ Code portable across OS (Windows/Mac/Linux)

**3.3 Resource Loading Test**
```bash
# Verify resources are in correct location
ls -la productionchainid/src/main/resources/
ls -la productionchainid/src/main/resources/buildingtypes/
```
**Pass Criteria:**
- ✅ All referenced JSON files exist
- ✅ Files in correct directory structure
- ✅ No broken resource references

---

#### **Phase 4: Packaging & Build**

**4.1 Package Creation**
```bash
# Create JAR package
mvn package

# Verify JAR created
ls -lh productionchainid/target/*.jar
```
**Pass Criteria:**
- ✅ BUILD SUCCESS
- ✅ JAR file created in target/
- ✅ No packaging warnings

**4.2 JAR Execution Test**
```bash
# Test JAR execution (if main class configured)
java -jar productionchainid/target/productionchainid-*.jar

# Or test main class directly
java -cp productionchainid/target/classes com.productionchain.Main
```
**Pass Criteria:**
- ✅ Program runs without errors
- ✅ Resources load correctly from JAR
- ✅ Expected output produced

---

#### **Phase 5: Git & Version Control Checks**

**5.1 Branch Hygiene**
```bash
# Check current branch
git branch --show-current

# Verify on correct feature branch (not main/develop)
git status

# Check for uncommitted changes
git diff
```
**Pass Criteria:**
- ✅ On correct feature branch
- ✅ No uncommitted debug code
- ✅ No untracked files (except intentional)

**5.2 Commit Quality Check**
```bash
# Review recent commits
git log --oneline -5

# Check commit message format
git log -1 --pretty=%B
```
**Pass Criteria:**
- ✅ Commit messages follow convention
- ✅ Version numbers correct
- ✅ Descriptive commit messages

**5.3 Merge Readiness**
```bash
# Update from develop
git fetch origin develop
git merge origin/develop

# Check for conflicts
git status
```
**Pass Criteria:**
- ✅ No merge conflicts
- ✅ Up to date with develop branch
- ✅ All tests still pass after merge

---

#### **Phase 6: Documentation & Code Review**

**6.1 Documentation Check**
- [ ] Implementation plan updated
- [ ] New classes added to JAVA_FILES_INVENTORY.txt
- [ ] Architecture changes documented in CODEBASE_ANALYSIS.md
- [ ] README updated if necessary
- [ ] Release notes updated

**6.2 Code Quality Review**
- [ ] No duplicate code (DRY principle)
- [ ] Proper naming conventions (camelCase, descriptive)
- [ ] No magic numbers/strings (use constants)
- [ ] Appropriate comments for complex logic
- [ ] No security vulnerabilities (SQL injection, XSS, etc.)

**6.3 Refactoring Checklist**
- [ ] Legacy code removed (datatypesold package)
- [ ] No commented-out code blocks
- [ ] Imports cleaned up (no unused imports)
- [ ] Proper exception handling
- [ ] Resource cleanup (files, streams closed)

---

### 🎯 Pre-Deployment Final Checklist

Before pushing to GitHub:

- [ ] ✅ All Phase 1-6 tests completed
- [ ] ✅ `mvn clean test` passes with 0 failures
- [ ] ✅ `mvn package` builds successfully
- [ ] ✅ Manual integration tests completed
- [ ] ✅ JSON files validated
- [ ] ✅ No hardcoded paths
- [ ] ✅ Git commit messages formatted correctly
- [ ] ✅ Branch is up to date with develop
- [ ] ✅ Documentation updated
- [ ] ✅ Code review completed

**If all items checked:**
```bash
# Push to GitHub
git push -u origin <branch-name>

# Create pull request
gh pr create --title "feat: v1.X.Y - Description" --body "See implementation plan"
```

**If any item fails:**
1. Document the failure
2. Fix the issue
3. Re-run affected test phases
4. Increment patch version (v1.X.Y+1)
5. Commit fix with proper message
6. Repeat checklist

---

### 📊 Test Result Documentation

After each test run, document results in implementation plan:

```markdown
## Test Results - v1.X.Y

**Date:** YYYY-MM-DD
**Phase:** Phase N
**Tester:** [Your name]

| Test Phase | Status | Notes |
|------------|--------|-------|
| Static Code Analysis | ✅ PASS | 0 compilation errors |
| Unit Tests | ✅ PASS | 24/24 tests passing |
| Integration Tests | ✅ PASS | All workflows functional |
| Resource Validation | ✅ PASS | All JSON valid |
| Packaging | ✅ PASS | JAR builds successfully |
| Git Checks | ✅ PASS | Ready to merge |
| Documentation | ✅ PASS | All docs updated |

**Overall:** READY FOR DEPLOYMENT ✅
```

---

## 🔗 External Resources

- [GitHub Repository](https://github.com/pplvieira/production-chain-game-java)
- [Project README](../README.md)
- [Release Notes](../Release%20Notes.md)

---

Last Updated: 2025-11-18

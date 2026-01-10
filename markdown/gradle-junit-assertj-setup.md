# Manual Setup: JUnit 5 + AssertJ + Gradle

## Prerequisites

- Java JDK 11+ installed
- Gradle 7.0+ installed (or use Gradle Wrapper)

---

## Step 1: Initialize Gradle Project

```powershell
gradle init --type java-application --dsl kotlin
```

This creates the basic project structure with `build.gradle.kts`.

---

## Step 2: Configure Version Catalog

Create or edit `gradle/libs.versions.toml`:

```toml
[versions]
junit-jupiter = "5.12.1"
assertj = "3.26.3"

[libraries]
junit-jupiter = { module = "org.junit.jupiter:junit-jupiter", version.ref = "junit-jupiter" }
assertj-core = { module = "org.assertj:assertj-core", version.ref = "assertj" }
```

### Version Catalog Explanation

| Section       | Purpose                                                |
| ------------- | ------------------------------------------------------ |
| `[versions]`  | Centralized version definitions for dependency reuse   |
| `[libraries]` | Library aliases mapping to Maven coordinates + version |

---

## Step 3: Configure build.gradle.kts

```kotlin
plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // JUnit 5 for test framework
    testImplementation(libs.junit.jupiter)
    
    // AssertJ for fluent assertions
    testImplementation(libs.assertj.core)
    
    // Required for JUnit Platform to discover tests
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
```

### Dependency Explanation

| Dependency                          | Scope               | Purpose                                      |
| ----------------------------------- | ------------------- | -------------------------------------------- |
| `junit-jupiter`                     | `testImplementation`| JUnit 5 test engine + API                    |
| `assertj-core`                      | `testImplementation`| Fluent assertion library                     |
| `junit-platform-launcher`           | `testRuntimeOnly`   | Discovers and runs tests at runtime          |

---

## Step 4: Create Source Directories

```powershell
New-Item -ItemType Directory -Path "app/src/main/java" -Force
New-Item -ItemType Directory -Path "app/src/test/java" -Force
```

---

## Step 5: Refresh Gradle Dependencies

```powershell
./gradlew build --refresh-dependencies
```

Or in VS Code: `Ctrl+Shift+P` → "Java: Clean Java Language Server Workspace"

---

## Step 6: Write a Test Using AssertJ

```java
package data_structures;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ExampleTest {

    @Test
    void shouldDemonstrateAssertJFluency() {
        String actual = "hello";
        
        assertThat(actual)
            .isNotNull()
            .isNotEmpty()
            .hasSize(5)
            .startsWith("hel")
            .endsWith("lo")
            .containsOnlyOnce("ell");
    }
}
```

---

## Step 7: Run Tests

```powershell
./gradlew test
```

View test report at: `app/build/reports/tests/test/index.html`

---

## Common AssertJ Assertions

### Collections

```java
assertThat(list)
    .hasSize(3)
    .contains("a", "b")
    .containsExactly("a", "b", "c")
    .containsExactlyInAnyOrder("c", "a", "b")
    .doesNotContain("z")
    .isEmpty()
    .isNotEmpty();
```

### Objects

```java
assertThat(object)
    .isNull()
    .isNotNull()
    .isEqualTo(expected)
    .isNotEqualTo(other)
    .isSameAs(sameReference)
    .isInstanceOf(MyClass.class);
```

### Numbers

```java
assertThat(number)
    .isEqualTo(42)
    .isGreaterThan(40)
    .isLessThanOrEqualTo(50)
    .isBetween(40, 50)
    .isPositive()
    .isNegative()
    .isZero();
```

### Exceptions

```java
assertThatThrownBy(() -> methodThatThrows())
    .isInstanceOf(IllegalArgumentException.class)
    .hasMessage("expected message")
    .hasMessageContaining("partial");

assertThatCode(() -> methodThatDoesNotThrow())
    .doesNotThrowAnyException();
```

### Strings

```java
assertThat(string)
    .isEqualTo("expected")
    .isEqualToIgnoringCase("EXPECTED")
    .contains("sub")
    .startsWith("pre")
    .endsWith("suf")
    .matches("regex.*pattern")
    .isBlank()
    .isNotBlank();
```

---

## Troubleshooting

| Issue                                    | Solution                                                      |
| ---------------------------------------- | ------------------------------------------------------------- |
| `libs.assertj.core` not recognized       | Run `./gradlew --refresh-dependencies` or sync Gradle         |
| Tests not discovered                     | Ensure `useJUnitPlatform()` is in `build.gradle.kts`          |
| Source folder missing error              | Create `src/main/java` and `src/test/java` directories        |
| AssertJ imports not resolving            | Verify `testImplementation(libs.assertj.core)` in build file  |

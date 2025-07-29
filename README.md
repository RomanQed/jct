# jct 

[![jct](https://img.shields.io/maven-central/v/com.github.romanqed/jct?strategy=releaseProperty&style=for-the-badge&label=jct&color=blue)](https://repo1.maven.org/maven2/com/github/romanqed/jct)

Lightweight Java implementation of an operation cancellation mechanism, similar to C#'s CancellationToken.
Designed to be minimal, composable, and runtime-agnostic — works well with standard Java concurrency tools and third-party frameworks.

## Getting Started

To use this library, you will need:

* Java 11 or higher
* Maven or Gradle

## Features

* Immutable, thread-safe `CancelToken` abstraction
* Simple and lightweight cancellation source (`CancelSource`)
* Composable tokens (linked or combined)
* Integration with `CompletableFuture` and any asynchronous workflows
* Memory-friendly: `CancelSource` supports reuse via `reset()`

## Installing

### Gradle dependency

```groovy
dependencies {
    implementation group: 'com.github.romanqed', name: 'jct', version: '1.1.0'
}
```

### Maven dependency

```xml
<dependencies>
    <dependency>
        <groupId>com.github.romanqed</groupId>
        <artifactId>jct</artifactId>
        <version>1.1.0</version>
    </dependency>
</dependencies>
```

## Usage Examples

### Creating a cancelable operation

```java
CancelSource source = Cancellation.source();
CancelToken token = source.token();

CompletableFuture<Void> future = token.onCancelled().thenRun(() -> {
    System.out.println("Cancelled!");
});

// Later:
source.cancel();
```

### Combining tokens

```java
CancelToken token1 = Cancellation.source().token();
CancelToken token2 = Cancellation.source().token();

CancelToken combined = Cancellation.combinedToken(token1, token2);

combined.onCancelled().thenRun(() -> {
    System.out.println("Any of the tokens was cancelled");
});
```

### Reusing a source

```java
CancelSource source = Cancellation.source();

// Use source.token() in one task
runCancellableTask(source.token());

// Cancel and reset
source.cancel();
source.reset();

// Reuse for another task
runCancellableTask(source.token());
```

### Empty token (never cancels)

```java
CancelToken empty = Cancellation.emptyToken();

empty.onCancelled().thenRun(() -> {
    // Will never be invoked
});
```

## Built With

* [Gradle](https://gradle.org) - Dependency management

## Authors

* **[RomanQed](https://github.com/RomanQed)** - *Main work*

See also the list of [contributors](https://github.com/RomanQed/jfunc/contributors)
who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

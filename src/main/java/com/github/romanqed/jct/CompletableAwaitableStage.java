package com.github.romanqed.jct;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * {@link AwaitableStage} implementation wrapping a {@link CompletableFuture}.
 * <p>
 * Provides blocking wait methods with optional timeouts,
 * along with full {@link CompletionStage} functionality.
 *
 * @param <T> the result type returned by this stage
 */
public final class CompletableAwaitableStage<T> implements AwaitableStage<T> {
    private final CompletableFuture<T> future;

    /**
     * Wraps the given {@link CompletableFuture} as an {@link AwaitableStage}.
     *
     * @param future the underlying future to wrap
     */
    public CompletableAwaitableStage(CompletableFuture<T> future) {
        this.future = future;
    }

    @Override
    public T await() throws InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T await(long timeout) throws InterruptedException, TimeoutException {
        try {
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T await(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        try {
            return future.get(timeout, unit);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T awaitUnchecked() {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Await interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T awaitUnchecked(long timeout) throws TimeoutException {
        try {
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Await interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T awaitUnchecked(long timeout, TimeUnit unit) throws TimeoutException {
        try {
            return future.get(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Await interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <U> AwaitableStage<U> thenApply(Function<? super T, ? extends U> fn) {
        return new CompletableAwaitableStage<>(future.thenApply(fn));
    }

    @Override
    public <U> AwaitableStage<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return new CompletableAwaitableStage<>(future.thenApplyAsync(fn));
    }

    @Override
    public <U> AwaitableStage<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor) {
        return new CompletableAwaitableStage<>(future.thenApplyAsync(fn, executor));
    }

    @Override
    public AwaitableStage<Void> thenAccept(Consumer<? super T> action) {
        return new CompletableAwaitableStage<>(future.thenAccept(action));
    }

    @Override
    public AwaitableStage<Void> thenAcceptAsync(Consumer<? super T> action) {
        return new CompletableAwaitableStage<>(future.thenAcceptAsync(action));
    }

    @Override
    public AwaitableStage<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor) {
        return new CompletableAwaitableStage<>(future.thenAcceptAsync(action, executor));
    }

    @Override
    public AwaitableStage<Void> thenRun(Runnable action) {
        return new CompletableAwaitableStage<>(future.thenRun(action));
    }

    @Override
    public AwaitableStage<Void> thenRunAsync(Runnable action) {
        return new CompletableAwaitableStage<>(future.thenRunAsync(action));
    }

    @Override
    public AwaitableStage<Void> thenRunAsync(Runnable action, Executor executor) {
        return new CompletableAwaitableStage<>(future.thenRunAsync(action, executor));
    }

    @Override
    public <U, V> AwaitableStage<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return new CompletableAwaitableStage<>(future.thenCombine(other, fn));
    }

    @Override
    public <U, V> AwaitableStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return new CompletableAwaitableStage<>(future.thenCombineAsync(other, fn));
    }

    @Override
    public <U, V> AwaitableStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
        return new CompletableAwaitableStage<>(future.thenCombineAsync(other, fn, executor));
    }

    @Override
    public <U> AwaitableStage<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return new CompletableAwaitableStage<>(future.thenAcceptBoth(other, action));
    }

    @Override
    public <U> AwaitableStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return new CompletableAwaitableStage<>(future.thenAcceptBothAsync(other, action));
    }

    @Override
    public <U> AwaitableStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor) {
        return new CompletableAwaitableStage<>(future.thenAcceptBothAsync(other, action, executor));
    }

    @Override
    public AwaitableStage<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return new CompletableAwaitableStage<>(future.runAfterBoth(other, action));
    }

    @Override
    public AwaitableStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return new CompletableAwaitableStage<>(future.runAfterBothAsync(other, action));
    }

    @Override
    public AwaitableStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return new CompletableAwaitableStage<>(future.runAfterBothAsync(other, action, executor));
    }

    @Override
    public <U> AwaitableStage<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return new CompletableAwaitableStage<>(future.applyToEither(other, fn));
    }

    @Override
    public <U> AwaitableStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return new CompletableAwaitableStage<>(future.applyToEitherAsync(other, fn));
    }

    @Override
    public <U> AwaitableStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor) {
        return new CompletableAwaitableStage<>(future.applyToEitherAsync(other, fn, executor));
    }

    @Override
    public AwaitableStage<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return new CompletableAwaitableStage<>(future.acceptEither(other, action));
    }

    @Override
    public AwaitableStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return new CompletableAwaitableStage<>(future.acceptEitherAsync(other, action));
    }

    @Override
    public AwaitableStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor) {
        return new CompletableAwaitableStage<>(future.acceptEitherAsync(other, action, executor));
    }

    @Override
    public AwaitableStage<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return new CompletableAwaitableStage<>(future.runAfterEither(other, action));
    }

    @Override
    public AwaitableStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return new CompletableAwaitableStage<>(future.runAfterEitherAsync(other, action));
    }

    @Override
    public AwaitableStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return new CompletableAwaitableStage<>(future.runAfterEitherAsync(other, action, executor));
    }

    @Override
    public <U> AwaitableStage<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        return new CompletableAwaitableStage<>(future.thenCompose(fn));
    }

    @Override
    public <U> AwaitableStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
        return new CompletableAwaitableStage<>(future.thenComposeAsync(fn));
    }

    @Override
    public <U> AwaitableStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
        return new CompletableAwaitableStage<>(future.thenComposeAsync(fn, executor));
    }

    @Override
    public <U> AwaitableStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
        return new CompletableAwaitableStage<>(future.handle(fn));
    }

    @Override
    public <U> AwaitableStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
        return new CompletableAwaitableStage<>(future.handleAsync(fn));
    }

    @Override
    public <U> AwaitableStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
        return new CompletableAwaitableStage<>(future.handleAsync(fn, executor));
    }

    @Override
    public AwaitableStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
        return new CompletableAwaitableStage<>(future.whenComplete(action));
    }

    @Override
    public AwaitableStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action) {
        return new CompletableAwaitableStage<>(future.whenCompleteAsync(action));
    }

    @Override
    public AwaitableStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor) {
        return new CompletableAwaitableStage<>(future.whenCompleteAsync(action, executor));
    }

    @Override
    public AwaitableStage<T> exceptionally(Function<Throwable, ? extends T> fn) {
        return new CompletableAwaitableStage<>(future.exceptionally(fn));
    }
}

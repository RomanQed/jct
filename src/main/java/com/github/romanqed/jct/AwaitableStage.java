package com.github.romanqed.jct;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents a {@link CompletionStage} that supports blocking wait
 * for completion with optional timeout and unchecked interruption.
 *
 * @param <T> the result type returned by this stage
 */
public interface AwaitableStage<T> extends CompletionStage<T> {

    /**
     * Waits if necessary for this stage to complete, and then returns its result.
     *
     * @return the computed result
     * @throws InterruptedException if the current thread was interrupted while waiting
     */
    T await() throws InterruptedException;

    /**
     * Waits if necessary for at most the given timeout in milliseconds for this stage to complete,
     * and then returns its result.
     *
     * @param timeout the maximum time to wait, in milliseconds
     * @return the computed result
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @throws TimeoutException if the wait timed out before completion
     */
    T await(long timeout) throws InterruptedException, TimeoutException;

    /**
     * Waits if necessary for at most the given timeout with specified {@link TimeUnit} for this stage to complete,
     * and then returns its result.
     *
     * @param timeout the maximum time to wait
     * @param unit the time unit of the {@code timeout} argument
     * @return the computed result
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @throws TimeoutException if the wait timed out before completion
     */
    T await(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;

    /**
     * Waits uninterruptibly for this stage to complete and returns its result.
     * If interrupted, the interruption status is preserved but no {@link InterruptedException} is thrown.
     *
     * @return the computed result
     */
    T awaitUnchecked();

    /**
     * Waits uninterruptibly for at most the given timeout in milliseconds for this stage to complete,
     * and returns its result.
     * If interrupted, the interruption status is preserved but no {@link InterruptedException} is thrown.
     *
     * @param timeout the maximum time to wait, in milliseconds
     * @return the computed result
     * @throws TimeoutException if the wait timed out before completion
     */
    T awaitUnchecked(long timeout) throws TimeoutException;

    /**
     * Waits uninterruptibly for at most the given timeout with specified {@link TimeUnit} for this stage to complete,
     * and returns its result.
     * If interrupted, the interruption status is preserved but no {@link InterruptedException} is thrown.
     *
     * @param timeout the maximum time to wait
     * @param unit the time unit of the {@code timeout} argument
     * @return the computed result
     * @throws TimeoutException if the wait timed out before completion
     */
    T awaitUnchecked(long timeout, TimeUnit unit) throws TimeoutException;

    @Override
    <U> AwaitableStage<U> thenApply(Function<? super T, ? extends U> fn);

    @Override
    <U> AwaitableStage<U> thenApplyAsync(Function<? super T, ? extends U> fn);

    @Override
    <U> AwaitableStage<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor);

    @Override
    AwaitableStage<Void> thenAccept(Consumer<? super T> action);

    @Override
    AwaitableStage<Void> thenAcceptAsync(Consumer<? super T> action);

    @Override
    AwaitableStage<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor);

    @Override
    AwaitableStage<Void> thenRun(Runnable action);

    @Override
    AwaitableStage<Void> thenRunAsync(Runnable action);

    @Override
    AwaitableStage<Void> thenRunAsync(Runnable action, Executor executor);

    @Override
    <U, V> AwaitableStage<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn);

    @Override
    <U, V> AwaitableStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn);

    @Override
    <U, V> AwaitableStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor);

    @Override
    <U> AwaitableStage<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action);

    @Override
    <U> AwaitableStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action);

    @Override
    <U> AwaitableStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor);

    @Override
    AwaitableStage<Void> runAfterBoth(CompletionStage<?> other, Runnable action);

    @Override
    AwaitableStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action);

    @Override
    AwaitableStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor);

    @Override
    <U> AwaitableStage<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn);

    @Override
    <U> AwaitableStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn);

    @Override
    <U> AwaitableStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor);

    @Override
    AwaitableStage<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action);

    @Override
    AwaitableStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action);

    @Override
    AwaitableStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor);

    @Override
    AwaitableStage<Void> runAfterEither(CompletionStage<?> other, Runnable action);

    @Override
    AwaitableStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action);

    @Override
    AwaitableStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor);

    @Override
    <U> AwaitableStage<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn);

    @Override
    <U> AwaitableStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn);

    @Override
    <U> AwaitableStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor);

    @Override
    <U> AwaitableStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn);

    @Override
    <U> AwaitableStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn);

    @Override
    <U> AwaitableStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor);

    @Override
    AwaitableStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action);

    @Override
    AwaitableStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action);

    @Override
    AwaitableStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor);

    @Override
    AwaitableStage<T> exceptionally(Function<Throwable, ? extends T> fn);

    /**
     * This implementation throws {@link UnsupportedOperationException}.
     * To obtain a {@link CompletableFuture} use the appropriate method provided by implementation (if accessible).
     *
     * @throws UnsupportedOperationException always
     */
    @Override
    default CompletableFuture<T> toCompletableFuture() {
        throw new UnsupportedOperationException("Cannot use awaitable stage as completable future");
    }
}

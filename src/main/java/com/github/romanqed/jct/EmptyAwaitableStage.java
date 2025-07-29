package com.github.romanqed.jct;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An {@link AwaitableStage} that represents a completed empty stage
 * which cannot be awaited or completed.
 * <p>
 * All methods return itself or throw {@link IllegalStateException} where appropriate.
 */
public final class EmptyAwaitableStage implements AwaitableStage<Void> {
    public static final EmptyAwaitableStage STAGE = new EmptyAwaitableStage();

    @Override
    public Void await() {
        throw new IllegalStateException("Empty stage cannot be awaited");
    }

    @Override
    public Void await(long timeout) {
        throw new IllegalStateException("Empty stage cannot be awaited");
    }

    @Override
    public Void await(long timeout, TimeUnit unit) {
        throw new IllegalStateException("Empty stage cannot be awaited");
    }

    @Override
    public Void awaitUnchecked() {
        throw new IllegalStateException("Empty stage cannot be awaited");
    }

    @Override
    public Void awaitUnchecked(long timeout) {
        throw new IllegalStateException("Empty stage cannot be awaited");
    }

    @Override
    public Void awaitUnchecked(long timeout, TimeUnit unit) {
        throw new IllegalStateException("Empty stage cannot be awaited");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> thenApply(Function<? super Void, ? extends U> fn) {
        return (AwaitableStage<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> thenApplyAsync(Function<? super Void, ? extends U> fn) {
        return (AwaitableStage<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> thenApplyAsync(Function<? super Void, ? extends U> fn, Executor executor) {
        return (AwaitableStage<U>) this;
    }

    @Override
    public AwaitableStage<Void> thenAccept(Consumer<? super Void> action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> thenAcceptAsync(Consumer<? super Void> action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> thenAcceptAsync(Consumer<? super Void> action, Executor executor) {
        return this;
    }

    @Override
    public AwaitableStage<Void> thenRun(Runnable action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> thenRunAsync(Runnable action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> thenRunAsync(Runnable action, Executor executor) {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U, V> AwaitableStage<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super Void, ? super U, ? extends V> fn) {
        return (AwaitableStage<V>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U, V> AwaitableStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super Void, ? super U, ? extends V> fn) {
        return (AwaitableStage<V>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U, V> AwaitableStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super Void, ? super U, ? extends V> fn, Executor executor) {
        return (AwaitableStage<V>) this;
    }

    @Override
    public <U> AwaitableStage<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super Void, ? super U> action) {
        return this;
    }

    @Override
    public <U> AwaitableStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super Void, ? super U> action) {
        return this;
    }

    @Override
    public <U> AwaitableStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super Void, ? super U> action, Executor executor) {
        return this;
    }

    @Override
    public AwaitableStage<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> applyToEither(CompletionStage<? extends Void> other, Function<? super Void, U> fn) {
        return (AwaitableStage<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> applyToEitherAsync(CompletionStage<? extends Void> other, Function<? super Void, U> fn) {
        return (AwaitableStage<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> applyToEitherAsync(CompletionStage<? extends Void> other, Function<? super Void, U> fn, Executor executor) {
        return (AwaitableStage<U>) this;
    }

    @Override
    public AwaitableStage<Void> acceptEither(CompletionStage<? extends Void> other, Consumer<? super Void> action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> acceptEitherAsync(CompletionStage<? extends Void> other, Consumer<? super Void> action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> acceptEitherAsync(CompletionStage<? extends Void> other, Consumer<? super Void> action, Executor executor) {
        return this;
    }

    @Override
    public AwaitableStage<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> thenCompose(Function<? super Void, ? extends CompletionStage<U>> fn) {
        return (AwaitableStage<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> thenComposeAsync(Function<? super Void, ? extends CompletionStage<U>> fn) {
        return (AwaitableStage<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> thenComposeAsync(Function<? super Void, ? extends CompletionStage<U>> fn, Executor executor) {
        return (AwaitableStage<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> handle(BiFunction<? super Void, Throwable, ? extends U> fn) {
        return (AwaitableStage<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> handleAsync(BiFunction<? super Void, Throwable, ? extends U> fn) {
        return (AwaitableStage<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> AwaitableStage<U> handleAsync(BiFunction<? super Void, Throwable, ? extends U> fn, Executor executor) {
        return (AwaitableStage<U>) this;
    }

    @Override
    public AwaitableStage<Void> whenComplete(BiConsumer<? super Void, ? super Throwable> action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> whenCompleteAsync(BiConsumer<? super Void, ? super Throwable> action) {
        return this;
    }

    @Override
    public AwaitableStage<Void> whenCompleteAsync(BiConsumer<? super Void, ? super Throwable> action, Executor executor) {
        return this;
    }

    @Override
    public AwaitableStage<Void> exceptionally(Function<Throwable, ? extends Void> fn) {
        return this;
    }
}

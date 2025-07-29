package com.github.romanqed.jct;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * {@link CancelSource} implementation based on {@link CompletableFuture}.
 * <p>
 * Supports cancellation, resetting, and cancellation with timeout.
 */
public final class CompletableCancelSource implements CancelSource {
    private final Supplier<CompletableFuture<Void>> supplier;
    private volatile State state;

    /**
     * Creates a new {@code CompletableCancelSource} with the given
     * supplier for {@link CompletableFuture} instances.
     *
     * @param supplier provides new {@link CompletableFuture} instances for cancellation state
     */
    public CompletableCancelSource(Supplier<CompletableFuture<Void>> supplier) {
        this.supplier = supplier;
        this.state = new State(supplier.get());
    }

    @Override
    public CancelToken token() {
        return state.token;
    }

    @Override
    public void reset() {
        state = new State(supplier.get());
    }

    @Override
    public void cancel() {
        state.future.complete(null);
    }

    @Override
    public void cancelAfter(long timeout) {
        state.future.completeOnTimeout(null, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void cancelAfter(long timeout, TimeUnit unit) {
        state.future.completeOnTimeout(null, timeout, unit);
    }

    @Override
    public boolean canceled() {
        return state.future.isDone();
    }

    private static final class State {
        final CompletableFuture<Void> future;
        final CompletableCancelToken token;

        private State(CompletableFuture<Void> future) {
            this.future = future;
            this.token = new CompletableCancelToken(future);
        }
    }
}

package com.github.romanqed.jct;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

/**
 * {@link CancelToken} implementation based on {@link CompletableFuture}.
 * <p>
 * Supports checking cancellation and awaiting cancellation completion.
 */
public final class CompletableCancelToken implements CancelToken {
    private final CompletableFuture<Void> future;
    private final CompletableAwaitableStage<Void> stage;

    /**
     * Creates a new token backed by the given {@link CompletableFuture}.
     *
     * @param future the future representing cancellation completion
     */
    public CompletableCancelToken(CompletableFuture<Void> future) {
        this.future = future;
        this.stage = new CompletableAwaitableStage<>(future);
    }

    @Override
    public boolean canceled() {
        return future.isDone();
    }

    @Override
    public boolean cancellable() {
        return true;
    }

    @Override
    public void checkCanceled() {
        if (future.isDone()) {
            throw new CancellationException("Token is cancelled");
        }
    }

    @Override
    public AwaitableStage<Void> onCancelled() {
        return stage;
    }
}

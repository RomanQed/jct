package com.github.romanqed.jct;

/**
 * Combines two {@link CancelToken} instances into a single token.
 * <p>
 * Cancellation status and cancellation notifications reflect the combined state
 * of underlying tokens.
 */
public final class CombinedPairCancelToken implements CancelToken {
    private final CancelToken first;
    private final CancelToken second;
    private final AwaitableStage<Void> stage;

    /**
     * Creates a combined token from two tokens and a cancellation stage.
     *
     * @param first the first token to combine
     * @param second the second token to combine
     * @param stage the {@link AwaitableStage} that completes when any token is cancelled
     */
    public CombinedPairCancelToken(CancelToken first, CancelToken second, AwaitableStage<Void> stage) {
        this.first = first;
        this.second = second;
        this.stage = stage;
    }

    @Override
    public boolean canceled() {
        return first.canceled() || second.canceled();
    }

    @Override
    public boolean cancellable() {
        return first.cancellable() || second.cancellable();
    }

    @Override
    public void checkCanceled() {
        first.checkCanceled();
        second.checkCanceled();
    }

    @Override
    public AwaitableStage<Void> onCancelled() {
        return stage;
    }
}

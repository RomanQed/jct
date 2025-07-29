package com.github.romanqed.jct;

/**
 * Combines multiple {@link CancelToken} instances from an array into a single token.
 * <p>
 * Cancellation status and cancellation notifications reflect the combined state
 * of all underlying tokens.
 */
public class CombinedArrayCancelToken implements CancelToken {
    private final CancelToken[] tokens;
    private final AwaitableStage<Void> stage;

    /**
     * Creates a combined token from an array of tokens and a cancellation stage.
     *
     * @param tokens the array of tokens to combine
     * @param stage the {@link AwaitableStage} that completes when any token is cancelled
     */
    public CombinedArrayCancelToken(CancelToken[] tokens, AwaitableStage<Void> stage) {
        this.tokens = tokens;
        this.stage = stage;
    }

    @Override
    public boolean canceled() {
        for (var token : tokens) {
            if (token.canceled()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cancellable() {
        for (var token : tokens) {
            if (token.cancellable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void checkCanceled() {
        for (var token : tokens) {
            token.checkCanceled();
        }
    }

    @Override
    public AwaitableStage<Void> onCancelled() {
        return stage;
    }
}

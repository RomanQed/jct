package com.github.romanqed.jct;

/**
 * Combines multiple {@link CancelToken} instances from an {@link Iterable} into a single token.
 * <p>
 * Cancellation status and notifications reflect the combined state
 * of all underlying tokens.
 */
public final class CombinedCancelToken implements CancelToken {
    private final Iterable<CancelToken> tokens;
    private final AwaitableStage<Void> stage;

    /**
     * Creates a combined token from an iterable collection of tokens and a cancellation stage.
     *
     * @param tokens the iterable of tokens to combine
     * @param stage the {@link AwaitableStage} that completes when any token is cancelled
     */
    public CombinedCancelToken(Iterable<CancelToken> tokens, AwaitableStage<Void> stage) {
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

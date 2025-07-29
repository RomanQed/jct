package com.github.romanqed.jct;

/**
 * An immutable, non-cancellable {@link CancelToken} that never signals cancellation.
 * <p>
 * Useful as a default or placeholder token.
 */
public final class EmptyCancelToken implements CancelToken {
    public static final EmptyCancelToken TOKEN = new EmptyCancelToken();

    @Override
    public boolean cancellable() {
        return false;
    }

    @Override
    public void checkCanceled() {
        // Do nothing
    }

    @Override
    public AwaitableStage<Void> onCancelled() {
        return EmptyAwaitableStage.STAGE;
    }

    @Override
    public boolean canceled() {
        return false;
    }
}

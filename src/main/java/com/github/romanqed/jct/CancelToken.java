package com.github.romanqed.jct;

import java.util.concurrent.CancellationException;

/**
 * Represents a token that tracks cancellation state and
 * provides notification when cancellation occurs.
 * <p>
 * Extends {@link CancelState} for cancellation status querying.
 */
public interface CancelToken extends CancelState {

    /**
     * Checks whether this token supports cancellation.
     *
     * @return {@code true} if cancellation is possible, {@code false} otherwise
     */
    boolean cancellable();

    /**
     * Checks the cancellation status and throws an unchecked exception
     * if cancellation has been requested.
     *
     * @throws CancellationException if the token has been cancelled
     */
    void checkCanceled();

    /**
     * Returns a stage that completes when cancellation is requested.
     *
     * @return an {@link AwaitableStage} completed upon cancellation
     */
    AwaitableStage<Void> onCancelled();
}

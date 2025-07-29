package com.github.romanqed.jct;

import java.util.concurrent.TimeUnit;

/**
 * Represents a source that can issue {@link CancelToken}s,
 * manage cancellation, and control cancellation lifecycle.
 * <p>
 * Extends {@link CancelState} for cancellation status querying.
 */
public interface CancelSource extends CancelState {

    /**
     * Returns the associated {@link CancelToken}.
     * <p>
     * Implementations may return the same token instance on repeated calls.
     *
     * @return the current {@link CancelToken}
     */
    CancelToken token();

    /**
     * Resets the cancellation source, severing ties to previously issued tokens.
     * <p>
     * This allows reuse of the source to issue fresh tokens for new operations.
     */
    void reset();

    /**
     * Cancels immediately, signaling all issued tokens.
     */
    void cancel();

    /**
     * Schedules cancellation after the given timeout in milliseconds.
     *
     * @param timeout the delay before cancellation, in milliseconds
     */
    void cancelAfter(long timeout);

    /**
     * Schedules cancellation after the given timeout with specified time unit.
     *
     * @param timeout the delay before cancellation
     * @param unit the unit of the timeout parameter
     */
    void cancelAfter(long timeout, TimeUnit unit);
}

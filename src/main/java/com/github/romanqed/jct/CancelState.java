package com.github.romanqed.jct;

/**
 * Represents a cancellation state that can be queried
 * to check if cancellation has been requested.
 */
public interface CancelState {

    /**
     * Checks whether cancellation has been requested.
     *
     * @return {@code true} if cancelled, {@code false} otherwise
     */
    boolean canceled();
}

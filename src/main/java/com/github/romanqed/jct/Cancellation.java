package com.github.romanqed.jct;

import java.util.concurrent.CompletableFuture;

/**
 * Utility class providing factory methods and combinators for cancellation tokens and sources.
 *
 * <p>This class serves as the primary entry point to create and combine {@link CancelSource}
 * and {@link CancelToken} instances, facilitating cancellation management in asynchronous workflows.
 *
 * <p>Features include:
 * <ul>
 *     <li>Creating new {@link CancelSource} instances</li>
 *     <li>Obtaining a shared empty (non-cancellable) {@link CancelToken}</li>
 *     <li>Combining multiple cancellation tokens into a single source or token</li>
 * </ul>
 *
 * <p>All methods are static and the class is not instantiable.
 */
public final class Cancellation {
    private Cancellation() {
    }

    /**
     * Returns a shared empty {@link CancelToken} which is never cancellable or cancelled.
     *
     * @return a singleton empty cancel token
     */
    public static CancelToken emptyToken() {
        return EmptyCancelToken.TOKEN;
    }

    /**
     * Returns a {@link CancelToken} instance that is already canceled.
     * <p>
     * This token reports {@code true} from {@link CancelToken#canceled()} immediately.
     * It can be used to simulate cancellation or test cancellation behavior without
     * waiting or external triggers.
     *
     * @return a {@link CancelToken} that is already canceled
     */
    public static CancelToken canceledToken() {
        return new CompletableCancelToken(CompletableFuture.completedFuture(null));
    }

    /**
     * Creates a combined {@link CancelToken} from multiple tokens, which is considered cancelled
     * when any of the constituent tokens is cancelled.
     *
     * @param tokens the tokens to combine
     * @return a combined cancel token representing cancellation of any input token
     */
    public static CancelToken combinedToken(CancelToken... tokens) {
        var future = new CompletableFuture<Void>();
        for (var token : tokens) {
            token.onCancelled().thenRun(() -> future.complete(null));
        }
        return new CombinedArrayCancelToken(tokens, new CompletableAwaitableStage<>(future));
    }

    /**
     * Creates a combined {@link CancelToken} from an iterable of tokens, which is considered cancelled
     * when any of the constituent tokens is cancelled.
     *
     * @param tokens the tokens to combine
     * @return a combined cancel token representing cancellation of any input token
     */
    public static CancelToken combinedToken(Iterable<CancelToken> tokens) {
        var future = new CompletableFuture<Void>();
        for (var token : tokens) {
            token.onCancelled().thenRun(() -> future.complete(null));
        }
        return new CombinedCancelToken(tokens, new CompletableAwaitableStage<>(future));
    }

    /**
     * Creates a combined {@link CancelToken} from two tokens, which is considered cancelled
     * when any of the constituent tokens is cancelled.
     *
     * @param first  the first token to combine
     * @param second the second token to combine
     * @return a combined cancel token representing cancellation of any input token
     */
    public static CancelToken combinedToken(CancelToken first, CancelToken second) {
        var future = new CompletableFuture<Void>();
        first.onCancelled().thenRun(() -> future.complete(null));
        second.onCancelled().thenRun(() -> future.complete(null));
        return new CombinedPairCancelToken(first, second, new CompletableAwaitableStage<>(future));
    }

    /**
     * Creates a new {@link CancelSource} backed by a {@link CompletableFuture}.
     *
     * @return a new cancellable source instance
     */
    public static CancelSource source() {
        return new CompletableCancelSource(CompletableFuture::new);
    }

    /**
     * Combines cancellation signals from multiple {@link CancelToken} instances into the given {@link CancelSource}.
     * When any token is cancelled, the source is cancelled as well.
     *
     * @param source the cancel source to be cancelled on any token cancellation
     * @param tokens the tokens to observe for cancellation
     */
    public static void combine(CancelSource source, CancelToken... tokens) {
        for (var token : tokens) {
            token.onCancelled().thenRun(source::cancel);
        }
    }

    /**
     * Combines cancellation signals from multiple {@link CancelToken} instances into a new {@link CancelSource}.
     * When any token is cancelled, the returned source is cancelled as well.
     *
     * @param tokens tokens to observe for cancellation
     * @return a new {@link CancelSource} that cancels when any token cancels
     */
    public static CancelSource combined(CancelToken... tokens) {
        var ret = new CompletableCancelSource(CompletableFuture::new);
        combine(ret, tokens);
        return ret;
    }

    /**
     * Combines cancellation signals from an iterable of {@link CancelToken} into the given {@link CancelSource}.
     * When any token is cancelled, the source is cancelled as well.
     *
     * @param source the cancel source to be cancelled on any token cancellation
     * @param tokens the iterable of tokens to observe for cancellation
     */
    public static void combine(CancelSource source, Iterable<CancelToken> tokens) {
        for (var token : tokens) {
            token.onCancelled().thenRun(source::cancel);
        }
    }

    /**
     * Combines cancellation signals from an iterable of {@link CancelToken} into a new {@link CancelSource}.
     * When any token is cancelled, the returned source is cancelled as well.
     *
     * @param tokens tokens to observe for cancellation
     * @return a new {@link CancelSource} that cancels when any token cancels
     */
    public static CancelSource combined(Iterable<CancelToken> tokens) {
        var ret = new CompletableCancelSource(CompletableFuture::new);
        combine(ret, tokens);
        return ret;
    }
}

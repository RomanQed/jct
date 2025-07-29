package com.github.romanqed.jct;

import java.util.concurrent.CompletableFuture;

/**
 *
 */
public final class Cancellation {
    private Cancellation() {
    }

    /**
     *
     * @return
     */
    public static CancelSource source() {
        return new CompletableCancelSource(CompletableFuture::new);
    }

    /**
     *
     * @return
     */
    public static CancelToken emptyToken() {
        return EmptyCancelToken.TOKEN;
    }

    /**
     *
     * @param source
     * @param tokens
     */
    public static void combine(CancelSource source, CancelToken... tokens) {
        for (var token : tokens) {
            token.onCancelled().thenRun(source::cancel);
        }
    }

    /**
     *
     * @param tokens
     * @return
     */
    public static CancelSource combined(CancelToken... tokens) {
        var ret = new CompletableCancelSource(CompletableFuture::new);
        combine(ret, tokens);
        return ret;
    }

    /**
     *
     * @param source
     * @param tokens
     */
    public static void combine(CancelSource source, Iterable<CancelToken> tokens) {
        for (var token : tokens) {
            token.onCancelled().thenRun(source::cancel);
        }
    }

    /**
     *
     * @param tokens
     * @return
     */
    public static CancelSource combined(Iterable<CancelToken> tokens) {
        var ret = new CompletableCancelSource(CompletableFuture::new);
        combine(ret, tokens);
        return ret;
    }

    /**
     *
     * @param tokens
     * @return
     */
    public static CancelToken combinedToken(CancelToken... tokens) {
        var future = new CompletableFuture<Void>();
        for (var token : tokens) {
            token.onCancelled().thenRun(() -> future.complete(null));
        }
        return new CombinedArrayCancelToken(tokens, new CompletableAwaitableStage<>(future));
    }

    /**
     *
     * @param tokens
     * @return
     */
    public static CancelToken combinedToken(Iterable<CancelToken> tokens) {
        var future = new CompletableFuture<Void>();
        for (var token : tokens) {
            token.onCancelled().thenRun(() -> future.complete(null));
        }
        return new CombinedCancelToken(tokens, new CompletableAwaitableStage<>(future));
    }
}

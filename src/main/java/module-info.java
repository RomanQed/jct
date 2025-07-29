/**
 * Cancellation token library providing composable, awaitable, and cancellable stages.
 *
 * <p>This module offers interfaces and implementations for cancellation tokens and sources,
 * allowing fine-grained cancellation control for asynchronous tasks.
 *
 * <p>Key features include:
 * <ul>
 *     <li>{@link com.github.romanqed.jct.CancelToken} and {@link com.github.romanqed.jct.CancelSource} abstractions</li>
 *     <li>Composable cancellation tokens combining multiple sources</li>
 *     <li>Awaitable stages wrapping {@link java.util.concurrent.CompletableFuture} with blocking wait support</li>
 * </ul>
 */
module com.github.romanqed.jct {
    exports com.github.romanqed.jct;
}

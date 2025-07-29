package com.github.romanqed.jct;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public final class CancellationTest {

    @Test
    void testEmptyCancelToken() {
        var token = EmptyCancelToken.TOKEN;

        assertFalse(token.canceled());
        assertFalse(token.cancellable());
        assertDoesNotThrow(token::checkCanceled);
        assertNotNull(token.onCancelled());

        assertThrows(IllegalStateException.class, token.onCancelled()::await);
    }

    @Test
    void testCompletableCancelToken() {
        var future = new CompletableFuture<Void>();
        var token = new CompletableCancelToken(future);

        assertTrue(token.cancellable());
        assertFalse(token.canceled());

        // Not cancelled yet: checkCanceled should not throw
        assertDoesNotThrow(token::checkCanceled);

        future.complete(null);

        assertTrue(token.canceled());
        CancellationException ex = assertThrows(CancellationException.class, token::checkCanceled);
        assertTrue(ex.getMessage().contains("Token is cancelled"));

        // onCancelled stage should complete after future is done
        assertDoesNotThrow(() -> token.onCancelled().awaitUnchecked());
    }

    @Test
    void testCompletableCancelSourceCancel() {
        var source = new CompletableCancelSource(CompletableFuture::new);

        CancelToken token = source.token();
        assertFalse(token.canceled());

        source.cancel();

        assertTrue(token.canceled());
        assertTrue(source.canceled());

        // reset works: tokens and future replaced
        source.reset();
        var token2 = source.token();
        assertNotSame(token2, token);
        assertFalse(token2.canceled());
        assertFalse(source.canceled());
    }

    @Test
    void testCompletableCancelSourceCancelAfter() throws InterruptedException {
        var source = new CompletableCancelSource(CompletableFuture::new);

        source.cancelAfter(50, TimeUnit.MILLISECONDS);
        assertFalse(source.canceled());

        // Wait to expire
        Thread.sleep(70);

        assertTrue(source.canceled());
    }

    @Test
    void testCombinedArrayCancelToken() {
        var source1 = Cancellation.source();
        var source2 = Cancellation.source();

        var tokens = new CancelToken[]{source1.token(), source2.token()};

        var combinedFuture = new CompletableFuture<Void>();
        var combinedToken = new CombinedArrayCancelToken(tokens, new CompletableAwaitableStage<>(combinedFuture));

        assertFalse(combinedToken.canceled());
        assertTrue(combinedToken.cancellable());

        // Cancel one token should reflect in combined token
        source1.cancel();
        assertTrue(combinedToken.canceled());

        // checkCanceled on combined token calls checkCanceled on each underlying token
        assertThrows(CancellationException.class, combinedToken::checkCanceled);
    }

    @Test
    void testCombinedCancelToken() {
        var source1 = Cancellation.source();
        var source2 = Cancellation.source();

        var tokens = List.of(source1.token(), source2.token());

        var combinedFuture = new CompletableFuture<Void>();
        var combinedToken = new CombinedCancelToken(tokens, new CompletableAwaitableStage<>(combinedFuture));

        assertFalse(combinedToken.canceled());
        assertTrue(combinedToken.cancellable());

        source2.cancel();
        assertTrue(combinedToken.canceled());

        assertThrows(CancellationException.class, combinedToken::checkCanceled);
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void testEmptyAwaitableStage() {
        AwaitableStage stage = EmptyAwaitableStage.STAGE;

        assertThrows(IllegalStateException.class, stage::await);

        assertSame(stage, stage.thenRun(() -> {}));
        assertSame(stage, stage.thenApply(v -> "test"));
    }

    @Test
    void testCancellationCombineAndCombinedMethods() {
        // Комбинируем два токена в один CancelSource
        var s1 = Cancellation.source();
        var s2 = Cancellation.source();
        var target = Cancellation.source();

        // Пока никто не отменён
        assertFalse(s1.canceled());
        assertFalse(s2.canceled());
        assertFalse(target.canceled());

        // Привязываем s1 и s2 к target
        Cancellation.combine(target, s1.token(), s2.token());

        // Отменяем один из них
        s1.cancel();
        assertTrue(target.canceled());

        // Проверим, что новый source от combined вызывает отмену при отмене любого токена
        var s3 = Cancellation.source();
        var s4 = Cancellation.source();
        var combined = Cancellation.combined(s3.token(), s4.token());

        assertFalse(combined.canceled());

        s4.cancel();
        assertTrue(combined.canceled());

        // Проверим работу combinedToken с varargs
        var s5 = Cancellation.source();
        var s6 = Cancellation.source();
        var token = Cancellation.combinedToken(s5.token(), s6.token());

        assertFalse(token.canceled());
        s6.cancel();
        assertTrue(token.canceled());

        // Проверим работу combinedToken с Iterable
        var s7 = Cancellation.source();
        var s8 = Cancellation.source();
        var token2 = Cancellation.combinedToken(List.of(s7.token(), s8.token()));

        assertFalse(token2.canceled());
        s7.cancel();
        assertTrue(token2.canceled());
    }
}

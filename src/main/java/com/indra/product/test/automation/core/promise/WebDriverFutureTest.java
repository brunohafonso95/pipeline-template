
package com.indra.product.test.automation.core.promise;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author msergio
 * @since Setembro de 2018
 * @version {version}
 */
public class WebDriverFutureTest {
    private static final ExecutorService service = Executors.newCachedThreadPool();
    private List<String> results = new ArrayList<>();
    private static final Logger log = Logger.getLogger(WebDriverFutureTest.class.getName());

    @Test
    public void testCompletedFuture() {
        String expectedValue = "the expected value";
        CompletableFuture<String> alreadyCompleted = CompletableFuture.completedFuture(expectedValue);
        Object ret;
        try {
            ret = alreadyCompleted.get();
            assertThat(ret, is(expectedValue));
        } catch (InterruptedException | ExecutionException e) {

        }
    }

    @Test
    public void testRunAsync() {
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> log.info("running async task"), service);
        assertThat(runAsync.isDone(), is(true));
    }

    // Consuming results of CompletableFutures

    @Test
    public void testAcceptResult() {
        CompletableFuture<String> task = CompletableFuture.supplyAsync(simulatedTask("add when done"), service);
        CompletableFuture<Void> acceptingTask = task.thenAccept(results::add);

        assertThat(acceptingTask.isDone(), is(true));
        assertThat(results.size(), is(1));
        assertThat(results.contains("add when done"), is(true));
    }

    private Supplier<String> simulatedTask(String taskResult) {
        return () -> {
            return taskResult;
        };
    }

}
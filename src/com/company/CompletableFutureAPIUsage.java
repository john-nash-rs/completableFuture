package com.company;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureAPIUsage {
    public static void main(String args[]) throws ExecutionException, InterruptedException {
        demoCompletableFutureComplete();

        demoCompletableFuture();

        demoSupplyAsync();

        demoThenAccept();

        demoThenRun();

        demoThenCombine();

        demoCompleteExceptionally();

        demoCancel();

    }

    private static void demoCancel() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFutureFirst = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task Running inside completable Future");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result";
        });

        completableFutureFirst.cancel(true);

        boolean isCancelled = completableFutureFirst.isCancelled();
        System.out.println("Completable Future is cancelled :: "+isCancelled);

        String result = completableFutureFirst.get();

        System.out.println("Result :: "+result);
    }

    private static void demoCompleteExceptionally() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFutureFirst = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task Running inside completable Future");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "oops";
        });

        completableFutureFirst.completeExceptionally(new RuntimeException("Task in CompletableFuture Failed"));

        String result = completableFutureFirst.get();

        System.out.println("Result :: "+result);
    }

    private static void demoThenCombine() throws InterruptedException, ExecutionException {
        long timeStampStart = System.currentTimeMillis();
        CompletableFuture<String> completableFutureFirst = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task Running inside completable Future");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "one plus ";
        });

        CompletableFuture<String> completableFutureSecond = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task Running inside completable Future");
            return "two";
        });

        CompletableFuture<String> completableFutureThird = completableFutureFirst.thenCombine(completableFutureSecond, (resultOne, resultTwo) -> {
            return (resultOne + resultTwo + " is three");
        });

        String result = completableFutureThird.get();
        long timeStampEnd = System.currentTimeMillis();
        System.out.println("Time taken in the Completable Future operation is " + (timeStampEnd - timeStampStart));
        System.out.println("Result is :: "+result);
    }

    private static void demoThenRun() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task Running inside completable Future");
            return "returned result";
        });

        CompletableFuture<Void> secondCompletableFuture = completableFuture.thenRun(() -> {
            System.out.println("I don't get any input from the previous completableFuture. I am just triggered after it.");
        });
    }

    private static void demoThenAccept() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task Running inside completable Future");
            return "returned result";
        });

        CompletableFuture<Void> secondCompletableFuture = completableFuture.thenAccept(result -> {
            System.out.println("I am the "+result);
        });
    }

    private static void demoSupplyAsync() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task Running inside completable Future");
            return "I am the returned result";
        });

        String result = completableFuture.get();
        System.out.println(result);
    }

    private static void demoCompletableFuture() {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("Task Running inside completable Future");
        });
    }

    private static void demoCompletableFutureComplete() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        //String value = completableFuture.get();
        //The above statement will cause program to run forever.
        // Because there is nothing for the completable future to do.

        completableFuture.complete("Completing the completable future with this default text");
        String value = completableFuture.get();
        System.out.println("Value After Complete: "+value);
    }
}

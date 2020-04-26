package com.company;

import java.util.concurrent.*;

public class Main {

    /**
     * To book a parking, we need customer details and parking details.
     * And we don't need any booking return object for the client, in other word,
     * we can make booking process async/
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws Exception {

        //Simple Iterative Approach
        showIterativeProcess();

        //Using Future
        showFutureProcess();

        //Using Completable Future
        showCompletableFuture();


    }

    private static void showCompletableFuture() throws InterruptedException, ExecutionException {
        System.out.println("===============Completable Future process starts================");
        long timeStampStart = System.currentTimeMillis();
        CompletableFuture<String> booking = CompletableFuture.supplyAsync(() -> findCustomerDetails())
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> findParkingDetails()), (customer, parking) -> book(customer, parking));
        System.out.println(booking.get());
        long timeStampEnd = System.currentTimeMillis();
        System.out.println("Time taken in the Completable Future operation is " + (timeStampEnd - timeStampStart));
        System.out.println("==========================================");
        System.out.println();
        System.out.println();
    }

    private static void showFutureProcess() throws InterruptedException, java.util.concurrent.ExecutionException {
        System.out.println("===============Future process starts================");
        long timeStampStart = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Callable customer = () -> {
            return findCustomerDetails();
        };

        Callable parking = () -> {
            return findParkingDetails();
        };

        Future<String> customerFuture = executorService.submit(customer);
        Future<String> parkingFuture = executorService.submit(parking);

        String customerStr = customerFuture.get();
        String parkingStr = parkingFuture.get();
        Callable booking = () -> {
            return book(customerStr, parkingStr);
        };

        Future<String> bookingFuture = executorService.submit(booking);
        System.out.println(bookingFuture.get());
        long timeStampEnd = System.currentTimeMillis();
        System.out.println("Time taken in the Future operation is " + (timeStampEnd - timeStampStart));
        System.out.println("==========================================");
        System.out.println();
        System.out.println();
    }

    private static void showIterativeProcess() {
        System.out.println("===============Iterative process starts================");
        long timeStampStart = System.currentTimeMillis();
        String customerName = findCustomerDetails();
        String parkingName = findParkingDetails();

        String booking = book(customerName, parkingName);

        long timeStampEnd = System.currentTimeMillis();
        System.out.println(booking);
        System.out.println("Time taken in the iterative operation is " + (timeStampEnd - timeStampStart));
        System.out.println("==========================================");
        System.out.println();
        System.out.println();
    }

    private static String findCustomerDetails() {
        System.out.println("****** Finding customer Details ******* Sleeping for 500 ms");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("****** Found customer Details *******");
        return "John";
    }

    private static String findParkingDetails() {
        System.out.println("****** Finding parking Details ******* Sleeping for 400 ms");
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("****** Found parking Details *******");
        return "Mall Road Parking";
    }

    private static String book(String customer, String parking) {
        System.out.println("****** booking parking ******* Sleeping for 700 ms");
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("****** Parking booked *******");

        return (customer + " has booked a parking at " + parking);
    }
}

package me.lars;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    final static String UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/118.0";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("target url: ");
        String target = scanner.nextLine();
        System.out.println("requests: ");
        int requests = scanner.nextInt();
        System.out.println("threads: ");
        int threads = scanner.nextInt();

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < requests; i++) {
            executorService.execute(() -> sendRequest(target));
        }

        executorService.shutdown();
    }

    private static void sendRequest(String url) {
        try {
            URL target = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) target.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", UserAgent);

            int responseCode = connection.getResponseCode();
            System.out.println("requested " + url + " response code: " + responseCode);

            connection.disconnect();
        } catch (IOException e) {
            System.err.println("failed to send request: " + e.getMessage());
        }
    }
}
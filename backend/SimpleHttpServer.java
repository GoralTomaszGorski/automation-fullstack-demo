import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.awt.Desktop;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;

public class SimpleHttpServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Serwowanie index.html
        server.createContext("/", exchange -> {
            File file = new File("frontend/index.html"); // <- tu jest Twój index.html
            byte[] response = Files.readAllBytes(file.toPath());
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });

        // Proxy do Reqres API (logowanie)
        server.createContext("/api/login", new ProxyHandler("https://reqres.in/api/login"));

        server.setExecutor(null);
        server.start();
        System.out.println("Server running on http://localhost:" + port);

        // Automatycznie otwiera stronę w przeglądarce
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI("http://localhost:" + port));
        }
    }

    static class ProxyHandler implements HttpHandler {
        private final String targetUrl;

        ProxyHandler(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Odczytaj dane z requestu
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes());

                // Forward do Reqres
                HttpURLConnection conn = (HttpURLConnection) new URL(targetUrl).openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.getOutputStream().write(body.getBytes());

                // Odbierz odpowiedź
                String response = new String(conn.getInputStream().readAllBytes());

                // Wyślij do przeglądarki
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
}

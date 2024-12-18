package dev.ufo.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dev.ufo.console.Console;
import dev.ufo.data.Collection;
import dev.ufo.data.Date;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RestApi {

    private static final Map<String, Long> IP_COOLDOWN = new ConcurrentHashMap<>();
    private static final long COOLDOWN_TIME = TimeUnit.SECONDS.toMillis(5);

    public static void init() {
        Console.log("Start initializing the webserver...");
        new Thread(RestApi::startServer).start();
        Console.log("Webserver is running!");

    }

    private static void startServer() {
        try {

            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/", exchange -> {
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

                String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();
                long currentTime = System.currentTimeMillis();

                Long lastRequestTime = IP_COOLDOWN.get(clientIp);

                //if (lastRequestTime != null && currentTime - lastRequestTime < COOLDOWN_TIME) {
                //    exchange.sendResponseHeaders(429, 0);
                //    OutputStream os = exchange.getResponseBody();
                //    os.close();
                //    return;
                //}

                IP_COOLDOWN.put(clientIp, currentTime);

                String response = getResponse(exchange);

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            });

            server.start();

        } catch (Exception e){

            Console.warning("The web server could not be started! Please check on it!");
            Console.emptyConsoleSpace(5);
            Console.error(e);
            Console.emptyConsoleSpace(5);

        }

    }

    private static String getResponse(HttpExchange exchange) {

        String path = exchange.getRequestURI().getPath().replace("/", "");

        try {
            int i = Integer.parseInt(path);
            return Collection.search(i);
        } catch (Exception e){
            return Collection.search(Date.of(path)) + "";
        }

    }

}

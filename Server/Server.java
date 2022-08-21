import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
	server.createContext("/", new MyHandler());
        server.createContext("/topsecret", new MyHandler2());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "<body style='background-color:#666666; color:red;'><h1 style='font-size:80px;'>Yes!<br>Mike is the man!</h1><script>alert('[+] Before proceeding, please confirm that Mike is indeed the fucking man with the boss ass master plan.');</script></body>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class MyHandler2 implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "<body style='background-color:#0A0A0A; color:blue;'><h1 style='font-size:80px;'>Yes!<br>the secret phrase is ABRACADABRA!</h1><script>alert('[+] Before proceeding, please confirm that you can keep a secret.');</script></body>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

    }
}

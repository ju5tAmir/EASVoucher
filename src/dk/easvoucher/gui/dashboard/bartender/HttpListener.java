import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpListener {
    public static void main(String[] args) throws IOException {
        // Specify the IP address and port to listen on
        String ipAddress = "192.168.1.100"; // Change this to your desired IP address
        int port = 8080;

        HttpServer server = HttpServer.create(new InetSocketAddress(ipAddress, port), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server is listening on " + ipAddress + ":" + port + "...");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream requestBody = exchange.getRequestBody();
            byte[] buffer = new byte[requestBody.available()];
            requestBody.read(buffer);
            String requestBodyString = new String(buffer);

            System.out.println("Received request body:");
            System.out.println(requestBodyString);

            String response = "Request body received successfully!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

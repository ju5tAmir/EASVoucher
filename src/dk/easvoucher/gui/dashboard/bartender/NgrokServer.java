package dk.easvoucher.gui.dashboard.coordinator.ticket.print;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Tunnel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main2 {
    private static String publicUrl;

    public static void main(String[] args) throws Exception {
        // Initialize Ngrok Client
        final NgrokClient ngrokClient = new NgrokClient.Builder().build();
        // Set the log level of Ngrok client to only log severe messages
        Logger ngrokLogger = Logger.getLogger("com.github.alexdlaird.ngrok");
        ngrokLogger.setLevel(Level.SEVERE);

        final CreateTunnel createTunnel = new CreateTunnel.Builder()
                .withAddr(8080)
                .build();
        // Open a HTTP tunnel on port 8080
        final Tunnel httpTunnel = ngrokClient.connect(createTunnel);
        publicUrl = httpTunnel.getPublicUrl().toString();

    }

    public static String getPublicUrl() {
        return publicUrl;
    }
}

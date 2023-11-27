package connect;

import java.io.IOException;
import java.net.Socket;

public class PortScanner {

    public static void scanPorts(String host, int startPort, int endPort) {
        System.out.println("Scanning ports on " + host + " from " + startPort + " to " + endPort + "...");

        for (int port = startPort; port <= endPort; port++) {
            if (isPortAvailable(host, port)) {
                System.out.println("Port " + port + " is available");
            }
        }
    }

    private static boolean isPortAvailable(String host, int port) {
        try (Socket ignored = new Socket(host, port)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}



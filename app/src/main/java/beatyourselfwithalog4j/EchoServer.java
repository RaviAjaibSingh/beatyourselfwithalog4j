package beatyourselfwithalog4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class EchoServer implements Runnable {
    @Override
    public void run() {
        while (true) {
            // Avoid using log4j in this class so that we don't
            // accidentally start a compromise loop (e.g. if the sender
            // sends a compromised string that we print out)
            int port = 9999;
            InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
            System.out.println("Loopback address is " + loopbackAddress);

            try (ServerSocket listener = new ServerSocket(port, 50, loopbackAddress)) {
                try (Socket socket = listener.accept()) {
                    System.out.println("Log4j2 has established a network connection.  Below is what log4j transmitted over the network:" + port);
                    socket.setSoTimeout(1000);
                    try (InputStream inputStream = socket.getInputStream()) {
                        while (true) {
                            int character = inputStream.read();
                            System.out.print((char) character);
                            if (character == -1) {
                                System.out.println();
                                System.out.println("Connection terminated, beating over.");
                                break;
                            }
                        }
                    }
                }

            } catch (SocketTimeoutException e) {
                // do nothing
                System.out.println();
                System.out.println("Connection terminated, beating over.");
            } catch (IOException e) {
                System.out.println();
                System.out.println("Received IOException " + e.getMessage());
            }
        }
    }
}

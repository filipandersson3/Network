import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This is a class
 * Created 2021-03-16
 *
 * @author Magnus Silverdal
 */
public class ConnectionListener implements Runnable{
    private ServerSocket serverSocket;
    private ArrayList<Socket> connections = new ArrayList<>();

    public ConnectionListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        String msg = null;
        while (true) {
            try {
                connections.add(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop()  {

    }

    public ArrayList<Socket> getConnections() {
        return connections;
    }
}
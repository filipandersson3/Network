import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Model {
    String ip;
    int port;
    Socket socket;
    ServerSocket serverSocket;
    ArrayList<PrintWriter> out = new ArrayList<>();

    public Model(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void ClientStart() {
        try {
            socket = new Socket(ip,port);
        } catch (IOException e) {
            System.out.println("Client failed to connect");
            System.exit(0);
        }
        //Connected
        try {
            out.add(new PrintWriter(socket.getOutputStream(),true));
            ListenerThread in = new ListenerThread(new BufferedReader(new InputStreamReader(socket.getInputStream()
            )));
            Thread listener = new Thread(in);
            listener.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ServerStart() {
        System.out.println("Server started.");
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for connections!");
            // Go
            ConnectionListener connectionListener = new ConnectionListener(serverSocket);
            Thread connectionThread = new Thread(connectionListener);
            connectionThread.start();
            int connectionCount = 1;
            while (true) {
                System.out.println(connectionListener.getConnections().size());
                if (connectionListener.getConnections().size() >= connectionCount) {
                    System.out.println("connected");
                    Socket connection = connectionListener.getConnections().get(connectionCount-1);
                    ListenerThread in =
                            new ListenerThread(new BufferedReader(new InputStreamReader(connection.getInputStream())));
                    Thread listener = new Thread(in);
                    listener.start();
                    out.add(new PrintWriter(connection.getOutputStream(), true));
                    out.get(connectionCount-1).println("Welcome idiot");
                    out.get(connectionCount-1).println("Your ID: " + (connectionCount-1));
                    connectionCount++;
                }
            }

                //Protocol
        } catch (IOException e) {
            System.out.println("Server fail");
        }
    }

    public void send(String msg) {
        for (PrintWriter pw:
             out) {
            pw.println(msg);
        }
    }

    public void stop() {
        for (PrintWriter pw:
             out) {
            pw.close();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }
}

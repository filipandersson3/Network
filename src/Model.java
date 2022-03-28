import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Model {
    String ip;
    int port;
    Socket socket;
    ServerSocket serverSocket;
    PrintWriter out;

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
            out = new PrintWriter(socket.getOutputStream(),true);
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
                socket = serverSocket.accept();
                // Go
                out = new PrintWriter(socket.getOutputStream(), true);
                //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                ListenerThread in =
                        new ListenerThread(new BufferedReader(new InputStreamReader(socket.getInputStream())));
                Thread listener = new Thread(in);
                listener.start();
                System.out.println("Client connected!");
                //Protocol
                out.println("Welcome idiot");
        } catch (IOException e) {
            System.out.println("Server fail");
        }
    }

    public void send(String msg) {
        out.println(msg);
    }

    public void stop() {
        out.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }
}

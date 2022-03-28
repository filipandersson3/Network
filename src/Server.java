import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author magnus
 */
public class Server {
    public static void main(String[] args){
        int port = Integer.parseInt(JOptionPane.showInputDialog(null,"Port?","Connect to..",JOptionPane.QUESTION_MESSAGE));
        boolean run = true;
        ServerSocket serverSocket;
        Socket socket;
        System.out.println("Server started.");

        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for connections!");
                socket = serverSocket.accept();
                // Go
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                ListenerThread in =
                        new ListenerThread(new BufferedReader(new InputStreamReader(socket.getInputStream())));
                Thread listener = new Thread(in);
                listener.start();
                System.out.println("Client connected!");
                Scanner tgb = new Scanner(System.in);
                //Protocol
                out.println("Welcome idiot");
                while (run) {
                    out.println(tgb.nextLine());
                }
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Server fail");
        }
    }
}
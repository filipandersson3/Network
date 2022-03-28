import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author magnus
 */
public class Client {

    public static void main(String[] args) {
        String ip = (String) JOptionPane.showInputDialog(null,"IP?","Connect to..",JOptionPane.QUESTION_MESSAGE);
        int port = Integer.parseInt(JOptionPane.showInputDialog(null,"Port?","Connect to..",JOptionPane.QUESTION_MESSAGE));       ;
        Socket socket = null;

        try {
            socket = new Socket(ip,port);
        } catch (IOException e) {
            System.out.println("Client failed to connect");
            System.exit(0);
        }

        // GO
        try {
            Scanner tgb = new Scanner(System.in);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ListenerThread in = new ListenerThread(new BufferedReader(new InputStreamReader(socket.getInputStream()
             )));
            Thread listener = new Thread(in);
            listener.start();
            boolean run = true;
            while (run) {
                out.println(tgb.nextLine());
            }

            out.close();
            socket.close();
            System.out.println("Done!");
        } catch (Exception e) {
            System.out.println("Client failed to communicate");
        }
    }
}
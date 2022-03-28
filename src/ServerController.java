import javax.swing.*;
import java.util.Scanner;

public class ServerController {
    private String ip;
    private int port;
    private Model server;

    public static void main(String[] args) {
        ServerController clientController = new ServerController();
    }

    public ServerController() {
        ip = (String) JOptionPane.showInputDialog(null,"IP?","Connect to..",JOptionPane.QUESTION_MESSAGE);
        port = Integer.parseInt(JOptionPane.showInputDialog(null,"Port?","Connect to..",JOptionPane.QUESTION_MESSAGE));
        Scanner tgb = new Scanner(System.in);
        server = new Model(ip,port);
        server.ServerStart();
        while(tgb.hasNext()) {
            server.send(tgb.nextLine());
        }

    }
}

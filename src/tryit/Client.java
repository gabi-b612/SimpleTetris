package tryit;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    public static void main(String[] argv) throws IOException {

        JFrame window = new JFrame("Simple Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Add GamePanel to the window
        GamePanel gamePanel = new GamePanel();

        String serverAddress = "127.0.0.1"; // Adresse IP locale du serveur
        int serverPort = 8070; // Numéro de port sur lequel le serveur écoute

        try {
            // Création du socket client
            Socket socket = new Socket(serverAddress, serverPort);

            System.out.println("Connexion établie avec le serveur " + serverAddress + ":" + serverPort);

            // Obtention des flux d'entrée/sortie pour la communication avec le serveur
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
        } catch (Exception e) {
            return;
        }

        toWindow(window, gamePanel);
    }

    public static void toWindow(JFrame window, GamePanel gamePanel) {
        GamePanel gamePanel1 = new GamePanel();
        GamePanel gamePanel2 = new GamePanel();

        window.setLayout(new GridLayout(1, 3));

        window.add(gamePanel);
        window.add(gamePanel1);
        window.add(gamePanel2);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.launchGame(); // 1:20:47
        gamePanel1.launchGame(); // 1:20:47
        gamePanel2.launchGame(); // 1:20:47
    }
}

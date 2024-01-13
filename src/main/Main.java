package main;

import tryit.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] argv) throws IOException {

        JFrame window = new JFrame("Simple Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Add GamePanel to the window
        GamePanel gamePanel = new GamePanel();

        int portNumber = 8070;
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Serveur en attente de connexions sur le port " + portNumber);
            int nb_client = 2;
            int i = 0;

            // Attente de connexions
            while (i != nb_client) {
                // Bloque jusqu'à ce qu'une connexion soit établie
                Socket clientSocket = serverSocket.accept();
                i++;
                System.out.println("Connexion établie avec " + clientSocket.getInetAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Client.toWindow(window, gamePanel);
    }
}

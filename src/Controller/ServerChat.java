package Controller;

import static view.ChatFrame.clientWriters;
import static view.ChatFrame.chatArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ServerChat {
//    private static Set<PrintWriter> clientWriters = new HashSet<>();
//    private static JTextArea serverTextArea;

    public ServerChat() {
//        JFrame serverFrame = new JFrame("Chat Server");
//        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        serverFrame.setSize(400, 300);
//
//        serverTextArea = new JTextArea();
//        serverTextArea.setEditable(false);
//        serverFrame.add(new JScrollPane(serverTextArea), BorderLayout.CENTER);
//
//        JTextField serverMessageField = new JTextField();
//        serverFrame.add(serverMessageField, BorderLayout.SOUTH);
//
//        serverMessageField.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String message = serverMessageField.getText();
//                if (!message.isEmpty()) {
//                    broadcastMessage("Server: " + message);
//                    serverTextArea.append("You: " + message + "\n");
//                    serverMessageField.setText("");
//                }
//            }
//        });
//
//        serverFrame.setVisible(true);
System.out.println("server working");
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
//            chatArea.append("Server started...\n");

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
                synchronized (clientWriters) {
                    clientWriters.add(writer);
                }

                String message;
                while ((message = reader.readLine()) != null) {
                    chatArea.append(message + "\n");

                    fileHandler fh = new fileHandler("chat.txt") ;
                    fh.fileWrite(message+ "\n");

                    broadcastMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clientWriters) {
                    clientWriters.remove(writer);
                }
            }
        }
    }

    public static void broadcastMessage(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);

            }
        }
    }
}
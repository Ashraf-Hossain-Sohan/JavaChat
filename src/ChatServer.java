import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static JTextArea chatArea;
    private static BufferedWriter fileWriter;

    public static void main(String[] args) {
        JFrame chatFrame = new JFrame("Chat Server");
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setSize(400, 300);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatFrame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JTextField messageField = new JTextField();
        chatFrame.add(messageField, BorderLayout.SOUTH);

        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    broadcastMessage("Server: " + message);
                    chatArea.append("You: " + message + "\n");
                    saveMessageToFile("Server: " + message);
                    messageField.setText("");
                }
            }
        });

        chatFrame.setVisible(true);

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            fileWriter = new BufferedWriter(new FileWriter("chat.txt", true));
            chatArea.append("Server started...\n");

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    broadcastMessage(message);
                    saveMessageToFile(message);
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

    private static void broadcastMessage(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }

    private static synchronized void saveMessageToFile(String message) {
        try {
            fileWriter.write(message);
            fileWriter.newLine();
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

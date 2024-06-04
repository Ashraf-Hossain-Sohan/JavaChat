import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ChatClient {
    private static JTextArea chatArea;
    private static PrintWriter writer;

    public static void main(String[] args) {
        JFrame clientFrame = new JFrame("Chat Client");
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setSize(400, 300);
        clientFrame.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        clientFrame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JTextField messageField = new JTextField();
        clientFrame.add(messageField, BorderLayout.SOUTH);

        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (writer != null && !message.isEmpty()) {
                    writer.println("Client: " + message);
                    chatArea.append("You: " + message + "\n");
                    messageField.setText("");
                }
            }
        });

        clientFrame.setVisible(true);

        try {
            Socket socket = new Socket("localhost", 12345);
            writer = new PrintWriter(socket.getOutputStream(), true);

            new Thread(new ReaderThread(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ReaderThread implements Runnable {
        private Socket socket;

        public ReaderThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    chatArea.append(message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

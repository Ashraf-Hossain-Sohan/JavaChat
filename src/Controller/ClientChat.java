package Controller;

//import ClientChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ClientChat {
    public static JTextArea chatArea;
    private static PrintWriter writer;

    public ClientChat() {
        JFrame clientFrame = new JFrame("Chat Client");
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setSize(400, 300);
        //clientFrame.setLocationRelativeTo(null);
        clientFrame.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        clientFrame.add(chatScrollPane, BorderLayout.CENTER);

        //! Create a panel for message input and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        JTextField messageField = new JTextField();
        inputPanel.add(messageField, BorderLayout.CENTER);
       // clientFrame.add(messageField, BorderLayout.SOUTH);

        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (writer != null && !message.isEmpty()) {
                    writer.println("Client: " + message);
//                    chatArea.append("You: " + message + "\n");
                    messageField.setText("");
                }
            }
        });

        //! Create a panel for buttons
        JPanel buttonPanel = new JPanel();

        //! Create a button to send messages
        JButton sendButton = new JButton("Send");
        buttonPanel.add(sendButton);

        //! Create a button to attach files
        JButton fileButton = new JButton("Attach File");
        buttonPanel.add(fileButton);

        //! Create a button to add emojis
        JButton emojiButton = new JButton("Emoji");
        buttonPanel.add(emojiButton);

        inputPanel.add(buttonPanel, BorderLayout.EAST);

        //! Add components to the chat frame
        clientFrame.add(chatScrollPane, BorderLayout.CENTER);
        clientFrame.add(inputPanel, BorderLayout.SOUTH);

        //! Add ActionListener to the send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    chatArea.append("You: " + message + "\n");
                    messageField.setText("");
                }
            }
        });

        //! Add ActionListener to the file button
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(clientFrame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    chatArea.append("File attached: " + file.getName() + "\n");
                }
            }
        });

        //! Add ActionListener to the emoji button
        emojiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] emojis = {"😀", "😂", "😍", "😎", "😭", "😡"};
                String emoji = (String) JOptionPane.showInputDialog(clientFrame, "Select an emoji:", "Emoji", JOptionPane.PLAIN_MESSAGE, null, emojis, emojis[0]);
                if (emoji != null) {
                    messageField.setText(messageField.getText() + emoji);
                }
            }
        });



        try {
            Socket socket = new Socket("localhost", 12345);
            writer = new PrintWriter(socket.getOutputStream(), true);

            new Thread(new ReaderThread(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }



        clientFrame.setVisible(true);
    }


//    public static void main(String[] args) {
//        new ClientChat();
//    }
}
class ReaderThread implements Runnable {
    private Socket socket;

    public ReaderThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while ((message = reader.readLine()) != null) {
                ClientChat.chatArea.append(message + "\n");
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

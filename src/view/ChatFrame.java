package view;

import static Controller.ServerChat.broadcastMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.BorderLayout;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import Controller.ClientChat;
import Controller.ServerChat;

public class ChatFrame {

    static ServerChat server;
    static ClientChat c1;
    public static Set<PrintWriter> clientWriters = new HashSet<>();
    public static JTextArea chatArea;
    JPanel inputPanel;
    public JButton emojiButton;
    public JTextField messageField;
    JFrame chatFrame;

    boolean gg;

    public void setVisible(){
        chatFrame.setVisible(gg);
    }

    public ChatFrame(boolean wow) {

            gg = wow;
        // Initialize the frame and its components on the Event Dispatch Thread

            //! Create the chat frame
            chatFrame = new JFrame("Server");
            chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chatFrame.setSize(400, 300);
            chatFrame.setLocationRelativeTo(null);
            chatFrame.setLayout(new BorderLayout());

            //! Initialize the class-level chatArea
            chatArea = new JTextArea();
            chatArea.setEditable(false);
            JScrollPane chatScrollPane = new JScrollPane(chatArea);

            //! Create a panel for message input and buttons
            inputPanel = new JPanel();
            inputPanel.setLayout(new BorderLayout());

            //! Create a text field for entering messages
            messageField = new JTextField();
            inputPanel.add(messageField, BorderLayout.CENTER);

            messageField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String message = messageField.getText();
                    if (!message.isEmpty()) {
                        broadcastMessage("Server: " + message);
                        chatArea.append("You: " + message + "\n");
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
            emojiButton = new JButton("Emoji");
            buttonPanel.add(emojiButton);

            inputPanel.add(buttonPanel, BorderLayout.EAST);

            //! Add components to the chat frame
            chatFrame.add(chatScrollPane, BorderLayout.CENTER);
            chatFrame.add(inputPanel, BorderLayout.SOUTH);

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
                    int option = fileChooser.showOpenDialog(chatFrame);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        chatArea.append("File attached: " + file.getName() + "\n");
                    }
                }
            });

          //  ! Add ActionListener to the emoji button
            emojiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] emojis = {"😀", "😂", "😍", "😎", "😭", "😡"};
                    String emoji = (String) JOptionPane.showInputDialog(chatFrame, "Select an emoji:", "Emoji", JOptionPane.PLAIN_MESSAGE, null, emojis, emojis[0]);
                    if (emoji != null) {
                        messageField.setText(messageField.getText() + emoji);
                    }
                }
            });

            chatFrame.setVisible(wow);


//            server = new ServerChat();
           // c1 = new ClientChat ();
    }

    public static void main(String[] args) {
        new ChatFrame(true);
    }
}

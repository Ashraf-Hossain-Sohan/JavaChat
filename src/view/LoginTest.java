package view;

import Controller.ServerChat;
import view.ChatFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Controller.ServerChat.broadcastMessage;

public class LoginTest {
    JFrame chatFrame = new JFrame("value");
    JButton b1 = new JButton("chatFrame chas");
    JPanel panelMain = new JPanel();
    JPanel page1 = new JPanel();
    JPanel page2 = new JPanel();
    ChatFrame qwe = new ChatFrame(false);

    public LoginTest(){

        CardLayout ash = new CardLayout();
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setSize(1050,500);
        chatFrame.setLocationRelativeTo(null);
        chatFrame.setLayout(new BorderLayout());

        panelMain.setLayout(ash);
        page1.add(b1);
        page2.setLayout(new BorderLayout());
        page2.add(qwe.inputPanel,BorderLayout.SOUTH);

        panelMain.add(page1 , "page1");

        panelMain.add(page2 , "page2");

        chatFrame.add(panelMain, BorderLayout.CENTER);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ash.show(panelMain , "page2");

            }
        });

        qwe.emojiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] emojis = {"😀", "😂", "😍", "😎", "😭", "😡"};
                String emoji = (String) JOptionPane.showInputDialog(chatFrame, "Select an emoji:", "Emoji", JOptionPane.PLAIN_MESSAGE, null, emojis, emojis[0]);
                if (emoji != null) {
                    qwe.messageField.setText(qwe.messageField.getText() + emoji);
                }
            }
        });

        qwe.messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = qwe.messageField.getText();
                if (!message.isEmpty()) {
                    broadcastMessage("Server: " + message);
                    qwe.chatArea.append("You: " + message + "\n");
                    qwe.messageField.setText("");
                }
            }
        });

        chatFrame.setVisible(true);
    }
}

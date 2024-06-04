package view;

import static view.ChatFrame.chatArea;

import java.io.IOException;

import Controller.fileHandler;

public class chatReader implements Runnable {
    String Chats;

    @Override
    public void run() {

        fileHandler fh = new fileHandler("chat.txt");


        try {
            Chats = String.join("\n", fh.readFile());
            chatArea.setText(Chats);
            System.out.println(Chats);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}

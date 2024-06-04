import Controller.ClientChat;
import Controller.ServerChat;
import view.ChatFrame;
import view.LoginTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;


public class chat {

    public static ChatFrame obj;
    static ServerChat server;

    public static void main(String[] args) {
      Login l = new Login();
        server = new ServerChat();
//         obj = new ChatFrame();
  //      ChatFrame cf = new ChatFrame();

       // LoginTest t1 = new LoginTest();


    }
}
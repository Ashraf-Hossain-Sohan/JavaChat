import Controller.ClientChat;
import Controller.ServerChat;
import view.ChatFrame;


public class chat {

    public static ChatFrame obj;
    static ServerChat server;
    static ClientChat c1;


    public static void main(String[] args) {
      Login l = new Login();
        c1 = new ClientChat();
        server = new ServerChat();
//         obj = new ChatFrame();
  //      ChatFrame cf = new ChatFrame();

       // LoginTest t1 = new LoginTest();


    }
}
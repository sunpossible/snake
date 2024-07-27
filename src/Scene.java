import javax.swing.*;
import java.awt.*;

public class Scene extends JFrame {
    private Snake snake;



    public void initUI(){
        System.out.println("init UI");
        //  todo 初始化遊戲畫面

        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();  //獲取package中的系統外觀樣式

        try{
            UIManager.setLookAndFeel(lookAndFeel); //設置系統外觀樣式
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        Image img = Toolkit.getDefaultToolkit().getImage("image//title.png"); //獲取遊戲的logo icon
    }

    public void run(){
        System.out.println("game running");
        //  todo 遊戲執行細節

        snake = new Snake();  //初始化貪吃蛇物件
    }

    public static void main (String[] args){
        // 遊戲程式的起始點
        System.out.println("Application starting...");
        Scene game = new Scene();  //建立遊戲畫面的物件
        game.initUI();  //初始化遊戲畫面
        game.run();     //開始遊戲
        System.out.println("Game starting...");
    }
}
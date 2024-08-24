import java.awt.*;
import javax.swing.*;

public class Help extends JDialog {

    public Help(){
        setTitle("Game Instructions");   //設置窗數標題
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  //設置關閉操作
        setModal(true);  //設定為模態窗口
        setSize(410,380);  //設定窗口大小
        setLocationRelativeTo(null);  //設置窗口顯示於螢幕中央
        JPanel contentPane = new JPanel();  //創建內容面板
        contentPane.setLayout(new BorderLayout(0,0));  //設置內容面板布局
        setContentPane(contentPane);  //將內容面板設置為窗口的內容面板

        JTextArea J1 = new JTextArea("遊戲中貪吃蛇的頭是一個紅方塊 貪吃蛇的身體是漸變色的方塊" +
                "食物是綠色的圖形 可以通過鍵盤上的方向鍵 or WASD 操控" +
                "在遊戲介面按ESC可以重啟遊戲 按space可暫停 or 繼續"+
                "Menu 中的設置可調網格和邊框是否可見 遊戲介面右側會顯示目前蛇的長度和花費時間\n");
        J1.setFocusable(false);
        Font f = new Font("微軟雅黑", Font.PLAIN,15);
        J1.setFont(f);
        J1.setEditable(false);
        J1.setOpaque(false);
        J1.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(J1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        contentPane.add(scroll,BorderLayout.CENTER);

        setVisible(true);   //設置窗口可見
    }
}

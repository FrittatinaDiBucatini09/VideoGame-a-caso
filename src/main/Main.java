package src.main;
 
import javax.swing.*;

 public class Main{
   
   public static void main(String[] args) {
       JFrame window = new JFrame();
       window.setResizable(false);
       window.setTitle("2D Adventure");
    
       GamePanel gamePanel = new GamePanel();
       window.add(gamePanel);

       window.pack();

       window.setLocationRelativeTo(null);
       window.setVisible(true);

       gamePanel.startGameThread();
 }
}

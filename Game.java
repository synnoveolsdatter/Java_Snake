import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game {
      public static JFrame f = new JFrame();
      
      public static void main(String[] args) {
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
            f.setSize(600, 600);

      }

      public static void bindControls() {
            f.addKeyListener(new KeyListener() {
                  // required functions to satisfy KeyListener constructor
                  @Override
                  public void keyTyped(KeyEvent e) {
                  }
                  @Override
                  public void keyReleased(KeyEvent e) {
                  }
                  // ok here's the good stuff
                  @Override
                  public void keyPressed(KeyEvent e) {
                        System.out.println(e.getKeyCode() + " doesn't do anything yet lol");
                  }
            });
      }
}

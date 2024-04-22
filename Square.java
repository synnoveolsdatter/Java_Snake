import javax.swing.*;
import java.awt.*;

public class Square extends JPanel {
      public int[] pos = {0, 0};

      public Square(int[] pos) {
            this.pos = pos;
      }

      public void paint() {
            repaint();
      }

      public void paintComponent(Graphics g) {
            super.paintComponent(g);// Graphics class really getting the attention it deserves lol
            g.fillRect(pos[0], pos[1], Consts.size, Consts.size);
      }
}
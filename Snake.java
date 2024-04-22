import javax.swing.*;
import java.awt.*;

public class Snake extends JPanel {
      public int[] pos = {0, 0};
      public int[] size = {20, 20};
      public Color c = Color.red;
      public Square[] snak = {};

      public Snake(int[] pos, int[] size, Color color) {
            this.pos = pos;
            this.size = size;
            this.c = color;
            for (int i = 0; i < 3; i++) {
                  this.snak[i] = new Square(pos);
            }
      }

      public void paint() {
            repaint();
      }

      public void paintComponent(Graphics g) {
            super.paintComponent(g);// Graphics class really getting the attention it deserves lol
            g.setColor(Consts.SNAKE_COLOR);
            
      }
}

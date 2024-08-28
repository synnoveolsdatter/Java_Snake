package com.synnoveolsdatter.snake;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final int REAL_WIDTH = 800;
	public static final int REAL_HEIGHT = 600;
	public static final int BLOCKSIZE = 20;
	public static final int WIDTH = (int)REAL_WIDTH / BLOCKSIZE - 1;
	public static final int HEIGHT = (int)REAL_HEIGHT / BLOCKSIZE - 1;
	public static int TICKDELAY = 70;
    public static Direction direction;

	public Snake snake;
	public short[] apple;
	
	private GameDrawable actualPanel;
	private Random rand = new Random();
	private boolean alive = true;
	private static boolean running = false;
	private static Direction nextDir = Direction.NOWHERE;
	
	public static void main(String[] args) {
		if (args.length > 1) {
			TICKDELAY = Integer.parseInt(args[1]);
		} else {
			System.out.println("You can optionally add a number to delay between frames if you choose.");
			System.out.println("(usage: java -jar Game.jar {milliseconds between frames}");
			System.out.println("(default is 70)");
		}
		EventQueue.invokeLater(() -> {
			Game game = new Game("Snake Clone - Synnove Lemon");
			game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			game.setBackground(Color.BLACK);
			game.addKeyListener(new KeyAdapter() {
			    @Override
			    public void keyPressed(KeyEvent key) {
			    	// System.out.println("Key Pressed: " + key.getKeyCode());
			        int k = key.getKeyCode();
			        switch (k) {
			            case 119:
			            case 87:
			            case KeyEvent.VK_UP:
			            	if (direction == Direction.DOWN)
			            		break;
			                game.snake.dir[0] = 0;
			                game.snake.dir[1] = -1;
			                nextDir = Direction.UP;
			                running = true;
			                break;
			            case 115:
			            case 83:
			            case KeyEvent.VK_DOWN:
			            	if (direction == Direction.UP)
			            		break;
			            	game.snake.dir[0] = 0;
			            	game.snake.dir[1] = 1;
			            	nextDir = Direction.DOWN;
			            	running = true;
			                break;
			            case 97:
			            case 65:
			            case KeyEvent.VK_LEFT:
			            	if (direction == Direction.RIGHT)
			            		break;
			            	game.snake.dir[0] = -1;
			            	game.snake.dir[1] = 0;
			            	nextDir = Direction.LEFT;
			            	running = true;
			                break;
			            case 100:
			            case 68:
			            case KeyEvent.VK_RIGHT:
			            	if (direction == Direction.LEFT)
			            		break;
			            	game.snake.dir[0] = 1;
			            	game.snake.dir[1] = 0;
			            	nextDir = Direction.RIGHT;
			            	running = true;
			                break;
			        }
			    }
			});
			game.setVisible(true);
			game.init();
			// System.out.println("Snake Pos: {" + game.snake.headPos()[0] + ", " + game.snake.headPos()[1] + "}");
			// System.out.println("Apple Pos: {" + game.apple[0] + ", " + game.apple[1] + "}");
		});
	}

	public Game(String title) {
		snake = new Snake(randPos());
		randomiseApplePos();
		this.setTitle(title);
		actualPanel = new GameDrawable();
		this.add(actualPanel);
	}

	public void init() {
		setBackground(Color.BLACK);
		setFocusable(true);
		setPreferredSize(new Dimension(REAL_WIDTH, REAL_HEIGHT));
		setSize(new Dimension(REAL_WIDTH, REAL_HEIGHT));
		setResizable(false);
		go();
	}

	public void go() {
		Timer t = new Timer(TICKDELAY, this);
		t.start();
	}

	public void actionPerformed(ActionEvent e) {
		if (alive) {
			if (appleEaten()) {
				snake.extendSnake();
				randomiseApplePos();
			}
			direction = nextDir;
			snake.update();
			alive = !checkAlive();
			if (running) {
			    short[] headPos = snake.headPos();
				ArrayList<short[]> sneik = snake.withoutHead();
				for (short[] s : sneik) {
					if (s[0] == headPos[0] && s[1] == headPos[1]) {
						alive = false;
						// System.out.println("Collision");
						break;
					}
				}
			}
		}
		actualPanel.repaint();
/*		System.out.println("Head: "  + snake.headPos());
		System.out.println("Apple: " + apple);*/
	}
	
	public boolean checkAlive() {
		short x = snake.headPos()[0];
		short y = snake.headPos()[1];
		return
				((x < 0 || x > WIDTH) ||
				(y < 0 || y >= HEIGHT));
	}
	
	public boolean appleEaten() {
		return apple[0] == snake.headPos()[0] && apple[1] == snake.headPos()[1];
	}

	private class GameDrawable extends JPanel {
		private static final long serialVersionUID = 1L;
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			draw(g);
		}
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			Stroke s = g2.getStroke();
			
			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(0, 0, REAL_WIDTH, REAL_HEIGHT);
			g2.setColor(Color.RED);
			g2.fillRect(apple[0] * BLOCKSIZE, apple[1] * BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
			
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(3));
			g2.drawRect(apple[0] * BLOCKSIZE, apple[1] * BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
			
			for (Short[] block : snake) {
				short[] b = new short[] {(short)block[0], (short)block[1]};
				g2.setColor(Color.GREEN);
				g2.setStroke(s);
				g2.fillRect(b[0] * BLOCKSIZE, b[1] * BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);

				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(3));
				g2.drawRect(b[0] * BLOCKSIZE, b[1] * BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
			}
		}
	}
	
	private void randomiseApplePos() {
		short[] newApple = randPos();
		if (snake.inSnake(newApple)) {
			randomiseApplePos();
		} else {
			apple = newApple;
		}
	}
	
	private short[] randPos() {
		return new short[] {
				(short) rand.nextInt(WIDTH - 1),
				(short) rand.nextInt(HEIGHT - 1)
		};
	}
}


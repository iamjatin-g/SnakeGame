// package snakegame;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFrame;

public class Main extends MainGame {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake Game");
		frame.setBounds(10,10,920,740);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainGame obj = new MainGame();
		obj.setBackground(Color.BLACK);
		frame.add(obj);
		
		frame.setVisible(true);
	}
}

class MainGame extends JPanel implements ActionListener, KeyListener{
	
	public int[] snakeXlength = new int[750];
	public int[] snakeYlength = new int[750];
	public int snakeLength = 3, moves = 0, delay = 100, score = 0;
	public boolean left = false, right = true, up = false, down = false, gameOver = false;
	
	public int[] xPos = {50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825};
	public int[] yPos = {125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
	
	public Random random = new Random();
	public int foodX, foodY;
	
	public ImageIcon snakeTitle = new ImageIcon(getClass().getResource("gametitle.png"));
	public ImageIcon leftMouth = new ImageIcon(getClass().getResource("leftmouth.png"));
	public ImageIcon rightMouth = new ImageIcon(getClass().getResource("rightmouth.png"));
	public ImageIcon upMouth = new ImageIcon(getClass().getResource("upmouth.png"));
	public ImageIcon downMouth = new ImageIcon(getClass().getResource("downmouth.png"));
	public ImageIcon snakeImage = new ImageIcon(getClass().getResource("snakeimage.png"));
	public ImageIcon food = new ImageIcon(getClass().getResource("food.png"));
	
	public Timer timer;
	
	MainGame(){
		addKeyListener(this);
		setFocusable(true);
		timer = new Timer(delay, this);
		timer.start();
		
		Food();
	}
	
	// @Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.WHITE);
		g.drawRect(24, 24, 852, 52);
		g.drawRect(24, 100, 852, 576);
		snakeTitle.paintIcon(this, g, 25, 25);
		g.setColor(Color.BLACK);
		g.fillRect(25, 101, 851, 575);
		
		if(moves == 0) {
			snakeXlength[0] = 100;
			snakeXlength[1] = 75;
			snakeXlength[2] = 50;
			
			snakeYlength[0] = 125;
			snakeYlength[1] = 125;
			snakeYlength[2] = 125;
			
		}
		
		if(left) {
			leftMouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
		}
		else if(right) {
			rightMouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
		}
		else if(up) {
			upMouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
		}
		else if(down) {
			downMouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
		}
		
		for(int i = 1; i < snakeLength ; i++) {
			snakeImage.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
		}
		
		food.paintIcon(this, g, foodX, foodY);
		
		if(gameOver) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial",Font.BOLD,50));
			g.drawString("Game Over!", 300, 300);
			
			g.setFont(new Font("Arial",Font.PLAIN,20));
			g.drawString("Press SPACE to restart.", 330, 350);
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.PLAIN,14));
		g.drawString("Score : "+score, 750, 43);
		g.drawString("Length : "+snakeLength, 750, 63);
		
		g.dispose();
	}
	
	// @Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i = snakeLength-1; i > 0; i--) {
			snakeXlength[i] = snakeXlength[i-1];
			snakeYlength[i] = snakeYlength[i-1];
		}
		
		if(left) {
			snakeXlength[0] = snakeXlength[0] - 25;
		}
		else if(right) {
			snakeXlength[0] = snakeXlength[0] + 25;
		}
		else if(up) {
			snakeYlength[0] = snakeYlength[0] - 25;
		}
		else if(down) {
			snakeYlength[0] = snakeYlength[0] + 25;
		}
		
		if(snakeXlength[0] > 850) {
			timer.stop();
			gameOver = true;
		}
		else if(snakeXlength[0] < 25) {
			timer.stop();
			gameOver = true;
		}
		
		if(snakeYlength[0] > 650) {
			timer.stop();
			gameOver = true;
		}
		else if(snakeYlength[0] < 100) {
			timer.stop();
			gameOver = true;
		}

		eatFood();
		eatBody();
		repaint();
	}


	public void eatBody() {
		for(int i = snakeLength-1; i > 0; i--) {
			if(snakeXlength[i] == snakeXlength[0] && snakeYlength[i] == snakeYlength[0]) {
				timer.stop();
				gameOver = true;
			}
		}
		
	}

	public void Food() {
		foodX = xPos[random.nextInt(32)];
		foodY = yPos[random.nextInt(21)];
		
		for(int i = snakeLength-1; i > 0; i--) {
			if(snakeXlength[i] == foodX && snakeYlength[i] == foodY) {
				Food();
			}
		}
	}
	public void eatFood() {
		if(snakeXlength[0] == foodX && snakeYlength[0] == foodY) {
			Food();
			snakeLength++;
			score++;
		}
		
	}
	public void restartGame() {
		gameOver = false;
		moves = 0;
		score = 0;
		snakeLength = 3;
		left = false;
		right = true;
		up = false;
		down = false;
		timer.start();
		repaint();
	}
	// @Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			restartGame();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT && (!right)) {
			left = true;
			right = false;
			up = false;
			down = false;
			moves++;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT && (!left)) {
			left = false;
			right = true;
			up = false;
			down = false;
			moves++;
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP && (!down)) {
			left = false;
			right = false;
			up = true;
			down = false;
			moves++;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN && (!up)) {
			left = false;
			right = false;
			up = false;
			down = true;
			moves++;
		}
	}


	// @Override
	public void keyReleased(KeyEvent e) {
		// 
		
	}
	// @Override
	public void keyTyped(KeyEvent e) {
		// 
		
	}
}

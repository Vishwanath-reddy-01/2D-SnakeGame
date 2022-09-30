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

public class GamePannel extends JPanel implements ActionListener, KeyListener{

	private int[] snakexLength =new int[750];
	private int[] snakeyLength =new int[750];
	private int snakeLength =3;
	
	
	private int[] xPos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
	private int[] yPos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375,400,425,450,475,500,525,550,575,600,625};
	
	private Random random = new Random();
	private int enemyX, enemyY;
	
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private int moves =0;
	private int score =0;
	private boolean gameOver=false;
	
	private ImageIcon snakeTitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
	private ImageIcon leftMouth = new ImageIcon(getClass().getResource("leftmouth.png"));
	private ImageIcon rightMouth = new ImageIcon(getClass().getResource("rightmouth.png"));
	private ImageIcon upMouth = new ImageIcon(getClass().getResource("upmouth.png"));
	private ImageIcon downMouth = new ImageIcon(getClass().getResource("downmouth.png"));
	private ImageIcon snakeImage= new ImageIcon(getClass().getResource("snakeimage.png"));
	private ImageIcon enemy= new ImageIcon(getClass().getResource("enemy.png"));
	
	private Timer timer;
	private int delay = 150;
	
	GamePannel(){
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		
		timer = new Timer(delay,this);
		timer.start();
		newEnemy();
		
	}
	
	private void newEnemy() {
		// TODO Auto-generated method stub
		enemyX = xPos[random.nextInt(34)];
		enemyY = yPos[random.nextInt(23)];
		
		for(int i=snakeLength-1; i>=0; i--) {
			if(snakexLength[i]==enemyX && snakeyLength[i] == enemyY) {
				newEnemy();
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		g.setColor(Color.WHITE);
		g.drawRect(24, 10, 851, 55);
		g.drawRect(24, 74, 851, 576);
		
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 850, 575);
		snakeTitle.paintIcon(this, g, 25, 11);
		
		if(moves==0) {
			snakexLength[0] = 100;
			snakexLength[1] = 75;
			snakexLength[2] = 50;
			
			snakeyLength[0] = 100;
			snakeyLength[1] = 100;
			snakeyLength[2] = 100;
			
		}
		
		if(left) {
			leftMouth.paintIcon(this, g, snakexLength[0], snakeyLength[0]);
		}
		if(right) {
			rightMouth.paintIcon(this, g, snakexLength[0], snakeyLength[0]);
		}
		if(up) {
			upMouth.paintIcon(this, g, snakexLength[0], snakeyLength[0]);
		}
		if(down) {
			downMouth.paintIcon(this, g, snakexLength[0], snakeyLength[0]);
		}
		for(int i=1; i<snakeLength; i++) {
			snakeImage.paintIcon(this, g, snakexLength[i], snakeyLength[i]);
			
		}
		enemy.paintIcon(this, g, enemyX, enemyY);
		
		if(gameOver) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString("GAME OVER", 300, 300);
			
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString("Press SPACE to restrat", 320, 350);
			
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.drawString("Score : "+score, 680, 47);
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		for(int i =snakeLength-1; i>0; i--) {
			snakexLength[i]=snakexLength[i-1];
			snakeyLength[i]=snakeyLength[i-1];
		}
		if(left) {
			snakexLength[0] = snakexLength[0]-25;
		}
		if(right) {
			snakexLength[0] = snakexLength[0]+25;
		}
		if(up) {
			snakeyLength[0] = snakeyLength[0]-25;
		}
		if(down) {
			snakeyLength[0] = snakeyLength[0]+25;
		}
		if(snakexLength[0] > 850) snakexLength[0]=25;
		if(snakexLength[0] < 25) snakexLength[0]=850;
		
		if(snakeyLength[0] > 625) snakeyLength[0]=75;
		if(snakeyLength[0] < 75) snakeyLength[0]=625;
		
		eat();
		collidesWithBody();
		repaint();
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)) {
			left =true;
			right = false;
			up = false;
			down = false;
			moves++;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)) {
			left =false;
			right = true;
			up = false;
			down = false;
			moves++;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP && (!down)) {
			left =false;
			right = false;
			up = true;
			down = false;
			moves++;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)) {
			left = false;
			right = false;
			up = false;
			down = true;
			moves++;
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE&& (!up)) {
			restart();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	private void eat() {
		if(snakexLength[0]==enemyX && snakeyLength[0] == enemyY) {
			newEnemy();
			snakeLength++;
			score++;
		}
	}
	private void collidesWithBody() {
		for(int i= snakeLength-1; i>0; i--) {
			if(snakexLength[i]==snakexLength[0] && snakeyLength[i]==snakeyLength[0]) {
				timer.stop();
				gameOver=true;
			}
		}
	}
	private void restart() {
		gameOver=false;
		moves = 0;
		snakeLength=3;
		score =0;
		left = false;
		right =true;
		up = false;
		down = false;
		timer.start();
		repaint();
	}
}


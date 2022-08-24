import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

final public class Test {
	JFrame frame;
	DrawPanel drawPanel;
	Random random = new Random();
	class player_A {
		double x = (double) random.nextInt(1400);
		double y = (double) random.nextInt(500);
		boolean x_inc = true;
		boolean y_inc = true;
		void update() {
			if (x < 0 | x > 1400) {x_inc = !x_inc;}
			if (y < 0 | y > 570) {y_inc = !y_inc;}
			if (x_inc == true){x++;} else {x--;}
			if (y_inc == true){y++;} else {y--;}		
		}
	}
	
	class player_C {
		double attackDistance = 1200;
		double x = (double) random.nextInt(1400);
		double y = (double) random.nextInt(500);
		double speed = 1.5; //(double) (random.nextInt(1000)/1000;		
		boolean away = true;

		void update(double Ax, double Ay) {
			double speed_hold = speed;
			double theta;
			double y1; double x1;
			if (Ax > x) {x1 = Ax - x;} else {x1 = x - Ax;} 
			if (Ay > y) {y1 = Ay - y;} else {y1 = y - Ay;}
			theta = Math.atan(y1 / x1);

//			PointerInfo pi = MouseInfo.getPointerInfo();
//			Point p = pi.getLocation();
//			double distance_limit = p.getX();
			attackDistance = Math.sqrt(Math.pow(Ax-x,2) + Math.pow(Ay-y,2));
			if (attackDistance < 15) {
				speed = -10;
			}		

			if (Ax > x) {x += Math.cos(theta)*speed;} else {x -= Math.cos(theta)*speed;} 
			if (Ay > y) {y += Math.sin(theta)*speed;} else {y -= Math.sin(theta)*speed;} 
			speed = speed_hold;

			if (attackDistance < 0	) {System.exit(0);}
		}
	}

	player_A A = new player_A();
	player_C[] B = new player_C[72];
	
	public static void main(String... args) {
		new Test().go();
	}


    private void go()
    {
        frame = new JFrame("Random Blips of Death");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawPanel = new DrawPanel();
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setResizable(false);
        frame.setSize(1400, 600);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
	Image icon = Toolkit.getDefaultToolkit().getImage("Project_A/happy.png"); frame.setIconImage(icon);
        moveIt();
    }

	class DrawPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		Color myColor = new Color(0,0,0,125);
		public void paintComponent(Graphics g) {
			g.setColor(Color.red);
			g.fillRect(0, 0, this.getWidth() - 6, this.getHeight() - 6);
			g.setColor(Color.GREEN);
			g.fillOval((int) A.x, (int) A.y, 16, 16);
			g.setColor(myColor);
			for (int k=0; k<B.length; k++) {
				g.fillOval((int) B[k].x, (int) B[k].y, (int) (B[k].speed*16), (int) (B[k].speed*16));
			}
		}
	}

	private void moveIt() {
		for (int q=0; q<B.length; q++) {
			B[q] = new player_C();
		}
		while (true) {
			A.update();
			for (int j=0; j<B.length; j++) {
				if (j < B.length-1) {B[j].update(B[j+1].x, B[j+1].y);}
				else {B[j].update(A.x, A.y);}
			} 
			try {Thread.sleep(4);} catch (Exception e) {e.printStackTrace();}
			frame.repaint();
		}
	}
}

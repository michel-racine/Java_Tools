// package layout;
import java.awt.*;
import java.awt.image.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SimpleAudioPlayer {

	Clip clip;
	AudioInputStream audioInputStream;

	private SimpleAudioPlayer(String fname) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
            try {
		audioInputStream = AudioSystem.getAudioInputStream(new File(fname).getAbsoluteFile());
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
                clip.start();
		while (clip.getMicrosecondLength() != clip.getMicrosecondPosition()){}
            } catch (Exception ex) {System.out.println("error with sound..."); ex.printStackTrace();}
	}

	public static void addComponentsToPane(Container pane) {

                JButton button0;
                JButton button1;
                JButton button2;
		JButton button3;

                ImageIcon myIcon = new ImageIcon("Data/green_button3.png");
                ImageIcon myIcon_pressed = new ImageIcon("Data/red_button3.png");
		pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
////////////////////////////////////////////////////////////////////////////////////////
		button0 = new JButton("", myIcon);
		button0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e4) {
				try {
					SimpleAudioPlayer audioPlayer0 = new SimpleAudioPlayer("Data/a.wav");
				} catch (Exception ex) { System.out.println("Error with playing sound."); ex.printStackTrace();}
			}
		});

		button0.setPressedIcon(myIcon_pressed);
		button0.setBorder(null);
		c.gridx = 0; c.gridy = 0;
		pane.add(button0, c);
//////////////////////////////////////////////////////////////////////////////////////////
		button1 = new JButton("",myIcon);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				try {
					SimpleAudioPlayer audioPlayer1 = new SimpleAudioPlayer("Data/b.wav");
				} catch (Exception ex) { System.out.println("Error with playing sound."); ex.printStackTrace();}
			}
		});
                button1.setPressedIcon(myIcon_pressed);
		button1.setBorder(null);
		c.gridx = 1; c.gridy = 0;
		pane.add(button1, c);
///////////////////////////////////////////////////////////////////////////////////////////
		button2 = new JButton("", myIcon);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e3) {
				try {
					SimpleAudioPlayer audioPlayer2 = new SimpleAudioPlayer("Data/c.wav");
				} catch (Exception ex) { System.out.println("Error with playing sound."); ex.printStackTrace();}
                		sweet_action();
			}
		});
                button2.setPressedIcon(myIcon_pressed);
		button2.setBorder(null);
                c.gridx = 2; c.gridy = 0;
		pane.add(button2, c);
///////////////////////////////////////////////////////////////////////////////////////////
		button3 = new JButton("", myIcon);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e3) {
				try {
					SimpleAudioPlayer audioPlayer3 = new SimpleAudioPlayer("Data/d.wav");
				} catch (Exception ex) { System.out.println("Error with playing sound."); ex.printStackTrace();}
                		sweet_action();
			}
		});
                button3.setPressedIcon(myIcon_pressed);
		button3.setBorder(null);
                c.gridx = 3; c.gridy = 0;
		pane.add(button3, c);
///////////////////////////////////////////////////////////////////////////////////////////
	}


	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Player of Death");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Color customColor = new Color(90,90,90); frame.getContentPane().setBackground(customColor);
		addComponentsToPane(frame.getContentPane()); frame.pack(); frame.setVisible(true);
		Image icon = Toolkit.getDefaultToolkit().getImage("Data/death.png"); frame.setIconImage(icon);
	}


	public static void main(String[] args) {
		createAndShowGUI();
	}


	public static void sweet_action() {
		System.out.println("Sound was played with sweet actions!");
	}
}



















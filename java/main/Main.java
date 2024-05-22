package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("My Terra Game");

		GamePanel gamePanel = new GamePanel();

		window.add(gamePanel);
		window.pack();


		//while gamePanel.getTh


		window.setLocation(0, 0);
		window.setVisible(true);
		window.setResizable(true);

		gamePanel.startGameThread();
	}

}
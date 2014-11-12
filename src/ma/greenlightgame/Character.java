package ma.greenlightgame;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Scanner;

import javax.swing.JPanel;

public class Character extends JPanel {
	static Scanner scnr = new Scanner(System.in);
	public Character(){
		String base;
		
		base = scnr.nextLine();
		System.out.print("base");
	}
	
	public void DrawCharacter(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawString("Hello World",20,100);
		System.out.print("Hello World");
		
		
	}
}

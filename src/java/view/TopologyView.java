package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TopologyView extends JFrame {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private ViewPanel jp;
	
	
	public TopologyView() {
		super("Graphic simulation");
		setBounds(10, 10, WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jp = new ViewPanel();
		add(jp);
		setVisible(true);
	}
	
	public void updateAll() {
		jp.update();
	}
	
}

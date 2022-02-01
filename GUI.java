package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame {
	public GUI() throws IOException {
		super("C++ Parser");
		criarInput();
	}
	private void criarInput() throws IOException {
		setLayout(new BorderLayout());
		Color azul = new Color(19,54,124);
		
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setLayout(new FlowLayout());
		panelTitulo.setBackground(azul);
		
		JLabel titulo = new JLabel ("PARSER");
		titulo.setForeground(Color.white);
		titulo.setFont(new Font("Verdana", Font.PLAIN, 16));
		
		panelTitulo.add(titulo);
		
		JPanel panelInput = new JPanel();
		panelInput.setLayout(new FlowLayout());
		panelInput.setBackground(azul);
		
		JLabel var = new JLabel("Variável");
		var.setForeground(Color.white);
		JTextField varField = new JTextField(40);
		
		
		JLabel diretorio = new JLabel("Diretório");
		diretorio.setForeground(Color.white);
		JTextField dirField = new JTextField(40);
		
		String localDir = System.getProperty("user.dir");
		BufferedImage logo = ImageIO.read(new File(localDir + "/resources/Logo-Stellantis.png"));
		JLabel logolabel = new JLabel(new ImageIcon(logo));	
		
		panelInput.add(var);
		panelInput.add(varField);
		panelInput.add(diretorio);
		panelInput.add(dirField);
		panelInput.add(logolabel);
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setLayout(new FlowLayout());
		panelBotoes.setBackground(azul);
		
		JButton botaoBuscar = new JButton("Buscar");
		
		
		panelBotoes.add(botaoBuscar);
		
		add(panelTitulo, BorderLayout.NORTH);
		add(panelInput, BorderLayout.CENTER);
		add(panelBotoes, BorderLayout.SOUTH);	
	}
	
}

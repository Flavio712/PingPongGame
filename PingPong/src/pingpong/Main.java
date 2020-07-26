package pingpong;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main{
	
	final static int ANCHO = 1000;
	final static int ALTO = 500;

	static String player1Name;
	static String player2Name;
	
	public Main() {//constructor
		
		//Pantalla del juego
		JFrame ventana = new JFrame(); //creamos una ventana
		ventana.setSize(ANCHO,ALTO); //determina al tamaño de la ventana
		ventana.setBounds(0, 0, ANCHO, ALTO); //evita que en pc de bajo recursos cause problemas
		ventana.setVisible(true); //muestra la ventana
		ventana.setResizable(false); //no esta permitido redimencionar la ventana
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //el juego se cierra cuando se presiona la X
		ventana.setLocationRelativeTo(null); //ventana al medio de la pantalla
		ventana.setTitle("PING PONG"); //titulo de la ventana
				
		Gameplay gameplay = new Gameplay();//creamos un objeto de la clase gameplay
		ventana.add(gameplay);//añadimos el objeto gameplay a la ventana
				
		//jugadores
		JFrame jugadores= new JFrame ("Bienvenidos al PING PONG");//ventana para dar la bienvenida a los jugadores
		jugadores.setSize(350, 120);
		jugadores.setVisible(true);
		jugadores.setResizable(false);
		jugadores.setLocationRelativeTo(null);
		jugadores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel= new JPanel();
		
		JButton botonJugadores = new JButton("Ingresar jugadores");//boton para registrar jugadores
		panel.add(botonJugadores);
		jugadores.add(panel);
		
		JButton botonListos = new JButton("Listos");//boton para empezar a jugar
		panel.add(botonListos);
		jugadores.add(panel);
		
		jugadores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		botonJugadores.addActionListener(new ActionListener() {//este metodo guarda los valores ingresados por teclado en 2 string que son los nombres
		
			@Override
			public void actionPerformed(ActionEvent e) {
				String captura = JOptionPane.showInputDialog(null,"Ingrese Nombre de Jugador 1");
				player1Name = captura;
				String captura2 = JOptionPane.showInputDialog(null,"Ingrese Nombre de Jugador 2");
				player2Name = captura2;			
			}
		});
		
		botonListos.addActionListener(new ActionListener() {//este metodo hace que el boton "Listos" cierre la ventana para empezar a jugar
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jugadores.dispatchEvent(new WindowEvent(jugadores, WindowEvent.WINDOW_CLOSING));	
			}		
		});	
			
	}
	
	public static void main(String[] args) {
			new Main();
	}

}

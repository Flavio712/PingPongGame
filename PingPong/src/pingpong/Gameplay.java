package pingpong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{//JPanel nos permite dibujar en la pantalla
	
	private static final long serialVersionUID = 1L; //implementacion de java para quitar un error
	
	private boolean juego = false;
	private boolean gameOver = false;
	
	private Timer timer;//cada cuantos milissegundos repinta la pantalla
	private int tiempo = 7;//se repinta cada 7 milisegundos

	private int jugador1X = 5;//creamos posicione x del jugador1
	private int jugador1Y = Main.ALTO/2-75;//creamos posicione y del jugador1
	private int jugador2X = Main.ANCHO-76;//creamos posicione x del jugador2
	private int jugador2Y = Main.ALTO/2-75;//creamos posicione y del jugador2
	
	private int score1 = 0;
	private int score2 = 0;

	private int pelotaX = 480;
	private int pelotaY = 220;
	private double dirPelotaX = 2;
	private double dirPelotaY = 1;
	
	private boolean up1 = false;
	private boolean down1 = false;
	private boolean up2 = false;
	private boolean down2 = false;
	
	private int numeroRandom = (int)(Math.random()*3); //numero aleatorio para saber en que direccion sale la pelota al comienzo del saque
	private int dirRandom;

	static int puntoSetPlayer1;
	static int puntoSetPlayer2;

	private int powerUp2 = 0;
	private int powerUp1 = 0;

	public Gameplay() {//constructor
		timer = new Timer(tiempo,this);//creamos el timer
		timer.start();
		setFocusable(true);//toma la ventana del JPanel como la ventana que va a pintar
		addKeyListener(this);//agregamos el keylistener
	}
	
	public void paint(Graphics g) {//metodo que nos permite dibujar, Graphics, clase que nos permite dibujar
		
		ImageIcon ImgFondo = new ImageIcon(getClass().getResource("/Imagenes/mesa.png"));//se crea un objeto tipo imagen para utlizar de fondo
		g.drawImage(ImgFondo.getImage(), 0, 0, Main.ANCHO, Main.ALTO, null);//se dibuja la imagen en el fondo
		
		ImageIcon ImgPaleta1 = new ImageIcon(getClass().getResource("/Imagenes/paleta1.png"));//se crea un objeto tipo imagen para utlizar de paleta1
		g.drawImage(ImgPaleta1.getImage(), jugador1X, jugador1Y, 55, 100, null);//se dibuja la imagen en la paleta1
		
		ImageIcon ImgPaleta2 = new ImageIcon(getClass().getResource("/Imagenes/paleta2.png"));//se crea un objeto tipo imagen para utlizar de paleta2
		g.drawImage(ImgPaleta2.getImage(), jugador2X, jugador2Y, 55, 100, null);//se dibuja la imagen en la paleta2
		
		ImageIcon ImgPelota = new ImageIcon(getClass().getResource("/Imagenes/pelota.png"));//se crea un objeto tipo imagen para utlizar de pelota
		g.drawImage(ImgPelota.getImage(), pelotaX, pelotaY, 20, 20, null);//se dibuja la imagen en la pelota
		
		ImageIcon ImgPower = new ImageIcon(getClass().getResource("/Imagenes/power.png"));//se crea un objeto tipo imagen para utlizar de power
		g.drawImage(ImgPower.getImage(), 130, 420, 30, 30, null);//se dibuja la imagen en la power
		
		ImageIcon ImgPower2 = new ImageIcon(getClass().getResource("/Imagenes/power.png"));//se crea un objeto tipo imagen para utlizar de power2
		g.drawImage(ImgPower2.getImage(), 820, 420, 30, 30, null);//se dibuja la imagen en la power2
		
		moverPelota();
		moverJugadores();
		coliciones();
		
		//Puntuacion
		g.setColor(Color.WHITE);//color de las letras a dibujar
		g.setFont(new Font("Consolas",Font.PLAIN,22));//tipo de letra,fuente y tamaño
		if(Main.player1Name!=null) {
			g.drawString("Score de "+Main.player1Name+":"+score1, 20, 20);//score del jugador1
		}
		else {
			g.drawString("Score del jugador 1:"+score1, 20, 20);
		}
		if(Main.player2Name!=null) {
			g.drawString("Score de "+Main.player2Name+":"+score2, 715, 20);//score del jugador2
		}
		else {
			g.drawString("Score del jugador 2:"+score2, 715, 20);
		}
		g.drawString("SET", 470, 20);
		g.drawString(""+puntoSetPlayer1+"-"+puntoSetPlayer2, 470, 40);
		
		//Mensaje de inicio
		if(!juego && !gameOver) {//primer mensaje antes de empezar a jugar
			g.drawString("PRESIONE ENTER PARA JUGAR", 350, 150);
		}
		if(gameOver) {
			score1 = 0;//limpio pantalla
			score2 = 0;//limpio pantalla
			powerUp1=0;//limpio pantalla
			powerUp2=0;//limpio pantalla
			if(puntoSetPlayer1>puntoSetPlayer2){
				if(Main.player1Name!=null) {
					g.drawString("GANADOR: "+Main.player1Name, 360, 150);
				}
				else {
					g.drawString("GANADOR: JUGADOR 1", 360, 150);
				}
			}
			else{
				if(Main.player2Name!=null) {
					g.drawString("GANADOR "+Main.player2Name, 360, 150);
				}
				else {
					g.drawString("GANADOR: JUGADOR 2", 360, 150);
				}
			}
			g.drawString("PRESIONE ENTER PARA REINICIAR", 300, 170);
		}
		
		//powerUP
		if(powerUp1==0) {//dibujo cuanto de poder tiene el jugador 1
			g.drawString("- - - - -", 15, 450);
		}
		if(powerUp1==1) {
			g.drawString("X - - - -", 15, 450);
		}
		if(powerUp1==2) {
			g.drawString("X X - - -", 15, 450);
		}
		if(powerUp1==3) {
			g.drawString("X X X - -", 15, 450);
		}
		if(powerUp1==4) {
			g.drawString("X X X X -", 15, 450);
		}
		if(powerUp1==5) {
			g.drawString("X X X X X", 15, 450);
		}
		
		if(powerUp2==0) {//dibujo cuanto de poder tiene el jugador 2
			g.drawString("- - - - -", 860, 450);
		}
		if(powerUp2==1) {
			g.drawString("X - - - -", 860, 450);
		}
		if(powerUp2==2) {
			g.drawString("X X - - -", 860, 450);
		}
		if(powerUp2==3) {
			g.drawString("X X X - -", 860, 450);
		}
		if(powerUp2==4) {
			g.drawString("X X X X -", 860, 450);
		}
		if(powerUp2==5) {
			g.drawString("X X X X X", 860, 450);
		}
	}
	
	private void direccionRandomGenerator() {//metodo usado para generar un numero aleatorio para luego usar en el angulo con el que se mueve la pelota
		int direccionRandom = (int)(Math.random()*3); //numero aleatorio para saber con que direccion sale la pelota
		dirRandom= direccionRandom;
	}
	
	private void coliciones() {
		if(new Rectangle(pelotaX,pelotaY,20,20).intersects(jugador1X, jugador1Y, 15, 100)) {//nos dibuja un rectangulo en la posicion
			direccionRandomGenerator();													// de la pelota y otra en la posicion del jugador 1	
			if(dirPelotaX>7) {
				dirPelotaX=3;
			}
			if(powerUp1 == 5) {															//si hay colision entre ellos cambia direccion de la pelota
				dirPelotaX = -dirPelotaX*2;												//dependiendo en que if entre y aumenta su velocidad
				dirPelotaY = dirPelotaY*1.0;
				powerUp1=0;
			}			
			else if(dirRandom==1) {															
				dirPelotaX = -dirPelotaX*1.1;													
				dirPelotaY = dirPelotaY*1.2;
				powerUp1++;
			}
			else {
				dirPelotaX = -dirPelotaX*1.1;															
				dirPelotaY = dirPelotaY*1.1;
				powerUp1++;
			}
		}																					
		if(new Rectangle(pelotaX,pelotaY,20,20).intersects(jugador2X, jugador2Y, 15, 100)) {
			direccionRandomGenerator();
			if(dirPelotaX>7) {
				dirPelotaX=3;
			}
			if(powerUp2 == 5) {
				dirPelotaX = -dirPelotaX*2;												
				dirPelotaY = dirPelotaY*1.0;
				powerUp2=0;
			}	
			else if(dirRandom==1) {
				dirPelotaX = -dirPelotaX*1.1;														
				dirPelotaY = dirPelotaY*1.2;
				powerUp2++;
			}
			else {
				dirPelotaX = -dirPelotaX*1.1;														
				dirPelotaY = dirPelotaY*1.1;
				powerUp2++;
			}
		}
	}

	private void moverJugadores() {
		if(juego) {
			if(up1) {//si up1 es true el jugador se mueve hacia arriba hasta el limite establecido
				if(jugador1Y<0) {
					jugador1Y=0;
				}
				else {
					jugador1Y -=5;
				}
			}
			if(down1) {//si down1 es true el jugador se mueve hacia abajo hasta el limite establecido
				if(jugador1Y>360) {
					jugador1Y=360;
				}
				else {
					jugador1Y +=5;
				}
			}
			if(up2) {//si up2 es true el jugador se mueve hacia arriba hasta el limite establecido
				if(jugador2Y<0) {
					jugador2Y=0;
				}
				else {
					jugador2Y -=5;
				}
			}
			if(down2) {//si down2 es true el jugador se mueve hacia abajo hasta el limite establecido
				if(jugador2Y>360) {
					jugador2Y=360;
				}
				else {
					jugador2Y +=5;
				}
			}
		}
	}

	private void moverPelota() {
		if(juego) {	
				pelotaX += dirPelotaX;//ponemos la pelota en movimiento
				pelotaY += dirPelotaY;//ponemos la pelota en movimiento
				if(pelotaX < 0) {
					score2++;//si la pelota pasa de esa posicion se suma 1 a score2
					reiniciarJugada();
					if(score2==3) {//si el puntaje de un jugador llega a 3 gana un set
						score2=0;
						setGamePlayer2();
						reiniciarJugada();
						if(puntoSetPlayer2==3) {//si un jugador gana 3 set gana el juego
							gameOver();
						}
					}
				}
				if(pelotaX > 1000-40) {
					score1++;//si la pelota pasa de esa posicion se suma 1 a score1
					reiniciarJugada();
					if(score1==3) {
						score1=0;
						setGamePlayer1();
						reiniciarJugada();
						if(puntoSetPlayer1==3) {
							gameOver();
						}
					}
				}
				if(pelotaY < 0) {
					dirPelotaY = -dirPelotaY;//si golpea un borde cambiamos direccion
				}
				if(pelotaY > 500-60) {
					dirPelotaY = -dirPelotaY;//si golpea un borde cambiamos direccion
				}
		}
	}
	
	private void setGamePlayer1() {//metodo que suma un punto al set del jugador 1
		puntoSetPlayer1++;
		
	}

	private void setGamePlayer2() {//metodo que suma un punto al set del jugador 2
		puntoSetPlayer2++;
	}

	private void gameOver() {//cambia estado de juego y lo termina
		gameOver=true;
		juego=false;
		timer.stop();
		Historial.main(null);
	}

	private void reiniciar() {//seteamos todas las variables a su origen
		juego = true;
		gameOver = false;	
		jugador1X = 5;//creamos posicione x del jugador2
		jugador1Y = Main.ALTO/2-75;//creamos posicione y del jugador2
		jugador2X = Main.ANCHO-76;//creamos posicione x del jugador1
		jugador2Y = Main.ALTO/2-75;//creamos posicione y del jugador1
		score1 = 0;
		score2 = 0;
		pelotaX = 480;
		pelotaY = 220;
		puntoSetPlayer1 = 0;
		puntoSetPlayer2 = 0;
		powerUp1=0;
		powerUp2=0;
		numeroRandom =0;
		numeroRandom = (int)(Math.random()*5);//depende el numero que salga es la posicion en la que sale la pelota
		if(numeroRandom==1) {
			dirPelotaX = 2;
			dirPelotaY = 1;
		}
		else if(numeroRandom==2) {
			dirPelotaX = -2;
			dirPelotaY = 1;
		}
		else if(numeroRandom==3) {
			dirPelotaX = 2;
			dirPelotaY = -1;
		}
		else {
			dirPelotaX = -2;
			dirPelotaY = -1;
		}
		up1 = false;
		down1 = false;
		up2 = false;
		down2 = false;
		
		timer.start();
	}
	
	private void reiniciarJugada() {//seteamos todas las variables a su origen
	
		jugador1X = 5;//creamos posicione x del jugador2
		jugador1Y = Main.ALTO/2-75;//creamos posicione y del jugador2
		jugador2X = Main.ANCHO-76;//creamos posicione x del jugador1
		jugador2Y = Main.ALTO/2-75;//creamos posicione y del jugador1
		pelotaX = 480;
		pelotaY = 220;
		numeroRandom = 0;
		numeroRandom = (int)(Math.random()*5);//depende el numero que salga es la posicion en la que sale la pelota
		if(numeroRandom==1) {
			dirPelotaX = 2;
			dirPelotaY = 1;
		}
		else if(numeroRandom==2) {
			dirPelotaX = -2;
			dirPelotaY = 1;
		}
		else if(numeroRandom==3) {
			dirPelotaX = 2;
			dirPelotaY = -1;
		}
		else {
			dirPelotaX = -2;
			dirPelotaY = -1;
		}
		up1 = false;
		down1 = false;
		up2 = false;
		down2 = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {//inicializo el juego
		repaint();
		timer.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {//si la tecla que se pulsa es la tecla de up cambiamos up a true
			up2=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {//si la tecla que se pulsa es la tecla de down cambiamos down a true
			down2=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {//si la tecla que se pulsa es la tecla de w cambiamos w a true
			up1=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {//si la tecla que se pulsa es la tecla de s cambiamos s a true
			down1=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {//inicia juego
			juego=true;
			if(gameOver) {
				reiniciar();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {//pausa del juego
			if(juego) {
				timer.stop();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_V) {//vuelve de la pausa al juego
				timer.start();
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {//si la tecla que se pulsa es la tecla de up cambiamos up a false
			up2=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {//si la tecla que se pulsa es la tecla de down cambiamos down a false
			down2=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {//si la tecla que se pulsa es la tecla de w cambiamos w a false
			up1=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {//si la tecla que se pulsa es la tecla de s cambiamos s a false
			down1=false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
		
}

package pingpong;

import java.io.File;
import java.io.FileWriter;

public class Historial {
	
	public static void main(String []args)//guardamos los nombres de los gandores en un txt
	{
		try {
			File archivo=new File("C:\\Users\\Duplex PC\\Desktop\\historial.txt");//Creamos un objeto File se encarga de crear o abrir un archivo txt(PONGAN UNA DIRECCION VALIDA EN SUS PC)
			FileWriter Historial=new FileWriter(archivo,true);//Creamos un objeto FileWriter que se encarga de escribir el archivo
			if(Gameplay.puntoSetPlayer1>Gameplay.puntoSetPlayer2){//depende de quien gane ,y si puso o no su nombre, escribe en el txt el ganador
				if(Main.player1Name!=null) {
					Historial.write("Ganador: " + Main.player1Name + "\n");
				}
				else {
					Historial.write("Ganador jugador 1 \n");
				}
			}
			else{
				if(Main.player2Name!=null) {
					Historial.write("Ganador: " + Main.player2Name + "\n");
				}
				else {
					Historial.write("Ganador jugador 2 \n");
				}
			}
		Historial.close();//Cerramos el archivo
		}
		catch(Exception e){//Si existe un problema al escribir cae aqui
			System.out.println("Error al escribir");
		}
	}
}

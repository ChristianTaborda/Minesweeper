/********************************************
 * Christian Camilo Taborda Campiño         *
 * Código: 1632081-3743                     *
 * Fecha de creación: 11/04/2017            *
 * Fecha de última modificación: 13/04/2017 *
 * ****************************************** 
 */

package buscaminas;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class Controlador{
	
	//Atributos:
	private static final int LADO = 8;
	private Casilla[][] botones;
	private ImageIcon imagenBomba, imagenBandera, bombaGanadora, bombaPerdedora;
	private Cronometro tiempoActual, tiempoRecord;
	private Archivador registrador;
	
	//Distribuye aleatoriamente 10 minas el el tablero:
	public void distribuirBombas(){
		
		//Creación del random y variables a ultilizar:
		Random aleatorio = new Random();
		int fila, columna;
		
		//Iteración de 0 hasta 10:
		for(int x=0; x<10; x++){
			//Generación aleatoria mientras no haya bomba en una casilla:
			do{
				fila = aleatorio.nextInt(LADO);
				columna = aleatorio.nextInt(LADO);
			}while(botones[fila][columna].getBomba());
			
			//Asignación de la bomba a la casilla:
			botones[fila][columna].setBomba(true);
		}
		
	}
	
	//Edita la fuente y color de fondo de los JOptionPane:
	public void editarJOptionPane(){
		
		//Cambio de color de fondo para los JOptionPane:
		UIManager.put("OptionPane.background",new ColorUIResource(255,255,255));
		UIManager.put("Panel.background",new ColorUIResource(255,255,255));
		
		//Inicialización de fuentes para los JOptionPane:
		Font label = new Font("Arial Black",Font.BOLD,22);
		Font button = new Font("Arial Black",Font.BOLD,15);
		
		//Cambio de fuente para los JOptionPane:
		UIManager.put("OptionPane.messageFont", label);
		UIManager.put("OptionPane.buttonFont", button);
		
	}
	
	//Carga las imágenes para el juego:
	public void initImagenes(){
		
		imagenBomba = new ImageIcon(getClass().getResource("/imagenes/bomba.png"));
		imagenBandera = new ImageIcon(getClass().getResource("/imagenes/bandera.png"));
		bombaGanadora = new ImageIcon(getClass().getResource("/imagenes/bombaG.png"));
		bombaPerdedora = new ImageIcon(getClass().getResource("/imagenes/bombaP.png"));
		
	}
	
	//Retorna el nombre de una pista de audio:
	public String seleccionarAudio(int audio){
		
		//Creación de la variable a utilizar:
		String archivo = null;
		
		//Validación de la pista deseada:
		switch(audio){
			case 1:
				archivo = "/click.wav";
				break;
			case 2:
				archivo = "/bandera.wav";
				break;
			case 3:
				archivo = "/explosion.wav";
				break;
			case 4:
				archivo = "/notificacion.wav";
				break;
			case 5:
				archivo = "/victoria.wav";
				break;
			case 6:
				archivo = "/menu.wav";
				break;
			case 7:
				archivo = "/derrota.wav";
				break;
		}
		
		//Retorno de la pista seleccionada:
		return archivo;
		
	}
	
	//Reproduce una pista de audio:
	public void reproducirAudio(int pista){
		
		try{	
		
			//Importación y reproducción del sonido:
			URL url = getClass().getResource("/sonido" + seleccionarAudio(pista));
			AudioInputStream entrada = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(entrada);
			clip.start();
			
		}catch(IOException E){
			E.printStackTrace();
		}
		catch(LineUnavailableException E){
			E.printStackTrace();
		}
		catch(UnsupportedAudioFileException E){
			E.printStackTrace();
		}
		
		
	}
	
	//Constructor:
	public Controlador(){
		
		//Edición de los JOptionPane:
		editarJOptionPane();
		
		//Importación de las imágenes:
		initImagenes();
		
		//Creación de la matriz de casillas:
		botones = new Casilla[LADO][LADO];
		
		for(int x=0; x<LADO; x++){
			for(int y=0; y<LADO; y++){
				//Creación de cada casilla:
				botones[x][y] = new Casilla(x,y);
				botones[x][y].setBackground(new Color(190,190,190));
				botones[x][y].setBorder(BorderFactory.createRaisedBevelBorder());
				botones[x][y].setFont(new Font("Arial Black", Font.BOLD, 22));
			}
		}
		
		//Distribución aleatoria de las minas:
		distribuirBombas();
		
		//Creación del manejador de archivos:
		registrador = new Archivador();
		
		//Creación del tiempo de juego inicial:
		tiempoActual = new Cronometro(0, "Actual", false);
		
		//Inicialización del mejor tiempo registrado:
		tiempoRecord = new Cronometro(registrador.leer(), "Récord", true);
		
		//Inicio del conteo del tiempo de juego:
		tiempoActual.iniciar();
			
	}
	
	/**************
	 * MÉTODOS GET*
	 **************/
	
	//Retorna una casilla de la matriz de casillas:
	public Casilla getBoton(int fila, int columna){
		return botones[fila][columna];
	}
	
	//Retorna el Panel con el tiempo actual:
	public Cronometro getTiempoActual(){
		return tiempoActual;
	}
	
	//Retorna el panel con el mejor tiempo:
	public Cronometro getTiempoRecord(){
		return tiempoRecord;
	}
	
	//Escribe sobre una casilla la cantidad de minas a su alrededor:
	public void pintarCantidad(int cantidad, int fila, int columna){
		
		//Preparación del botón para escribir en él:
		//botones[fila][columna].setIcon(null);
		botones[fila][columna].setBackground(new Color(160,160,160));
		botones[fila][columna].setBorder(BorderFactory.createLoweredBevelBorder());
		
		//Cambio de color dependiendo el número:
		switch(cantidad){
			case 1:
				botones[fila][columna].setForeground(Color.BLUE);
				botones[fila][columna].setText("1");
				break;
			case 2:
				botones[fila][columna].setForeground(Color.GREEN);
				botones[fila][columna].setText("2");
				break;
			case 3:
				botones[fila][columna].setForeground(Color.RED);
				botones[fila][columna].setText("3");
				break;
			case 4:
				botones[fila][columna].setForeground(Color.ORANGE);
				botones[fila][columna].setText("4");
				break;
			case 5:
				botones[fila][columna].setForeground(Color.BLACK);
				botones[fila][columna].setText("5");
				break;
			case 6:
				botones[fila][columna].setForeground(Color.YELLOW);
				botones[fila][columna].setText("6");
				break;
			case 7:
				botones[fila][columna].setForeground(Color.CYAN);
				botones[fila][columna].setText("7");
				break;
			case 8:
				botones[fila][columna].setForeground(Color.PINK);
				botones[fila][columna].setText("8");
				break;
		}
		
	}
	
	//Revela las casillas aledañas a una con 0 minas alrededor:
	public void despejarZona(int fila, int columna){
		
		/*******************
		 *CASILLAS ALEDAÑAS*
		 *******************/
		
		//arriba
		if(fila>0){
			if(botones[fila-1][columna].getIcon() == null){
				jugar(fila-1, columna);
			}			
		}
		
		//superior derecha
		if(fila>0 && columna<LADO-1){
			if(botones[fila-1][columna+1].getIcon() == null){
				jugar(fila-1, columna+1);
			}	
		}
		
		//derecha
		if(columna<LADO-1){
			if(botones[fila][columna+1].getIcon() == null){
				jugar(fila, columna+1);
			}	
		}
		
		//inferior derecha
		if(fila<LADO-1 && columna<LADO-1){
			if(botones[fila+1][columna+1].getIcon() == null){
				jugar(fila+1, columna+1);
			}	
		}
		
		//abajo
		if(fila<LADO-1){
			if(botones[fila+1][columna].getIcon() == null){
				jugar(fila+1, columna);
			}	
		}
		
		//inferior izquierda
		if(fila<LADO-1 && columna>0){
			if(botones[fila+1][columna-1].getIcon() == null){
				jugar(fila+1, columna-1);
			}	
		}
		
		//izquierda
		if(columna>0){
			if(botones[fila][columna-1].getIcon() == null){
				jugar(fila, columna-1);
			}	
		}
		
		//superior izquierda
		if(fila>0 && columna>0){
			if(botones[fila-1][columna-1].getIcon() == null){
				jugar(fila-1, columna-1);
			}	
		}
		
	}
	
	//Método en caso de no haber mina en una casilla:
	public void sinMina(int fila, int columna){
		
		//Inicializamos la variable a utilizar:
		int cantidad = 0;
		
		/****************************
		 * CONTEO CASILLAS ALEDAÑAS *
		 ****************************/
		
		//arriba
		if(fila>0){
			if(botones[fila-1][columna].getBomba()){
				cantidad++;
			}
		}
		
		//superior derecha
		if(fila>0 && columna<LADO-1){
			if(botones[fila-1][columna+1].getBomba()){
				cantidad++;
			}
		}
		
		//derecha
		if(columna<LADO-1){
			if(botones[fila][columna+1].getBomba()){
				cantidad++;
			}
		}
		
		//inferior derecha
		if(fila<LADO-1 && columna<LADO-1){
			if(botones[fila+1][columna+1].getBomba()){
				cantidad++;
			}
		}
		
		//abajo
		if(fila<LADO-1){
			if(botones[fila+1][columna].getBomba()){
				cantidad++;
			}
		}
		
		//inferior izquierda
		if(fila<LADO-1 && columna>0){
			if(botones[fila+1][columna-1].getBomba()){
				cantidad++;
			}
		}
		
		//izquierda
		if(columna>0){
			if(botones[fila][columna-1].getBomba()){
				cantidad++;
			}
		}
		
		//superior izquierda
		if(fila>0 && columna>0){
			if(botones[fila-1][columna-1].getBomba()){
				cantidad++;
			}
		}
		
		//Escritura sobre el botón de la cantidad de minas alrededor:
		pintarCantidad(cantidad, fila, columna);
		
		//Validación de la cantidad de minas:
		if(cantidad != 0){
			//Reproducción del sonido al oprimir una casilla:
			reproducirAudio(1);
		}
		else{
			//Revelación de las casillas aledañas:
			despejarZona(fila, columna);
		}
		
	}
	
	//Quita todas las minas del tablero:
	public void quitarBombas(){
		
		for(int x=0; x<LADO; x++){
			for(int y=0; y<LADO; y++){
				//Eliminación de la mina de cada botón:
				botones[x][y].setBomba(false);
			}
		}
		
	}
	
	//Reinicializa los valores del juego para una nueva partida:
	public void reiniciar(){
		
		//Restaura los valores iniciales de las casillas:
		repetir();
		
		//Elimina las bombas del tablero:
		quitarBombas();
		
		//Distribuye nuevamente las bombas de forma aleatoria:
		distribuirBombas();
		
	}
	
	//Restaura los valores iniciales de una casilla:
	public void rebobinar(Casilla C){
		
		C.setBackground(new Color(190,190,190));
		C.setBorder(BorderFactory.createRaisedBevelBorder());
		C.setText(null);
		C.setIcon(null);
		C.setRevisada(false);
		C.setUltima(false);
		
	}
	
	//Reinicia los valores del juego con el mismo tablero:
	public void repetir(){
		
		//Pausa del tiempo de juego:
		tiempoActual.detener();
		
		//Creación del hilo para el efecto de la restauración:
		Thread hilo = new Thread(){
			public synchronized void run(){
				try{
					
					//Reinicialización de los tiempos:
					tiempoActual.limpiar();
					tiempoRecord.setTiempo(registrador.leer());
					
					for(int x=0; x<LADO; x++){
						for(int y=0; y<LADO; y++){
							
							//Validación de los botones a restaurar:
							if(botones[x][y].getRevisada() || botones[x][y].getIcon() != null){
								
								//Restauración de cada botón:
								rebobinar(botones[x][y]);
								
								//Tiempo de espera entre cada restauración:
								sleep(60);
								
							}							
						}
					}
					
					//Reinicio del conteo del tiempo de juego:
					tiempoActual.iniciar();
					
				}catch(InterruptedException E){
					return;
				}
			}
		};
		
		//Ejecución del hilo:
		hilo.start();
		
	}
	
	//Finaliza el juego:
	public void fin(boolean tipo){	
		
		//Creación de las variables a utilizar:
		String mensaje, icono;
		
		//Validación del tipo de finalización del juego:
		if(tipo){
			
			//Inicialización de las variables anteriores:
			mensaje = "¡Has Ganado!";
			icono = "/ganador.gif";
			
			//Validación de la existencia de un tiempo récord:
			if(tiempoRecord.getTiempo() != 0){
				
				//Validación del tiempo actual respecto al récord:
				if(tiempoActual.getTiempo() < tiempoRecord.getTiempo()){
					
					//Sobreescritura del nuevo récord:
					registrador.escribir(String.valueOf(tiempoActual.getTiempo()));
					
					//Reproducción del sonido de nuevo récord:
					reproducirAudio(4);
					
					//Notificación al jugador del nuevo récord:
					ImageIcon record = new ImageIcon(getClass().getResource("/imagenes/record.gif"));
					JOptionPane.showMessageDialog(null, "¡Nuevo Récord!", "Final", JOptionPane.DEFAULT_OPTION, record);
					
				}	
				
				//Reproducción del sonido de victoria:
				reproducirAudio(5);
				
			}
			else{
				
				//Asignación del tiempo actual como primer récord:
				registrador.escribir(String.valueOf(tiempoActual.getTiempo()));
				
				//Reproducción del sonido de nuevo récord:
				reproducirAudio(5);
				
			}
		}
		else{
			
			//Inicialización de las variables anteriores:
			mensaje = "¡Has Perdido!";
			icono = "/perdedor.gif";
			
			//Reproducción del sonido de derrota:
			reproducirAudio(7);
		}
		
		//Notificación al jugador de su resultado final:
		ImageIcon juicio = new ImageIcon(getClass().getResource("/imagenes" + icono));
		JOptionPane.showMessageDialog(null, mensaje, "Final", JOptionPane.DEFAULT_OPTION, juicio);
		
		//Reproducción del sonido de menú:
		reproducirAudio(6);
		
		//Validación de un nuevo juego:
		ImageIcon reinicio = new ImageIcon(getClass().getResource("/imagenes/decidir.gif"));
		int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres volver a jugar?", "Reinicio", JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, reinicio);
		
		//Validación de la decisión del jugador:
		if(opcion == 0){
			
			//Reproducción del sonido de menú:
			reproducirAudio(6);
			
			//Validación de un nuevo tablero:
			ImageIcon nuevo = new ImageIcon(getClass().getResource("/imagenes/repetir.gif"));
			int decision = JOptionPane.showConfirmDialog(null, "Quieres jugar con el mismo tablero?", "Reinicio", JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, nuevo);
			
			//Reproducción del sonido de menú:
			reproducirAudio(6);
			
			//Validación de la decisión del jugador:
			if(decision == 0){
				
				//Reinicio con el mismo tablero:
				repetir();
				
			}
			else{
				
				//Reinicio con un nuevo tablero:
				if(decision == 1){
					reiniciar();
				}
				else{
					
					//Cerrar la aplicación:
					System.exit(-1);
					
				}
			}			
		}
		else{
			
			//Cerrar la aplicación:
			System.exit(-1);
			
		}
	}
	
	//Revela la ubicación de las minas en el tablero:
	public void buscarBombas(boolean tipo){
		
		//Pausa del tiempo de juego:
		tiempoActual.detener();
		
		//Creación del hilo para el efecto de revelación:
		Thread hilo = new Thread(){
			public synchronized void run(){
				try{
					
					//Tiempo antes de la revelación:
					sleep(200);
					
					for(int x=0; x<LADO; x++){
						for(int y=0; y<LADO; y++){
							if(botones[x][y].getBomba()){
								
								//Revelación de cada botón:
								botones[x][y].setRevisada(true);
								botones[x][y].setBorder(BorderFactory.createLoweredBevelBorder());
								
								//Validación del tipo de revelación:
								if(tipo){
									
									//Revelación en la victoria:
									botones[x][y].setBackground(Color.BLUE);
									botones[x][y].setIcon(bombaGanadora);
									
								}
								else{
									
									//Revelación en la derrota:
									if(!botones[x][y].getUltima()){
										botones[x][y].setIcon(imagenBomba);
									}	
									
								}
								
								//Tiempo entre cada revelación:
								sleep(170);
								
							}
						}
					}
					
					//Finalización del juego:
					fin(tipo);
					
				}catch(InterruptedException E){
					return;
				}
			}
		};
		
		//Ejecución del hilo:
		hilo.start();
		
	}
	
	//Método en caso de haber mina en una casilla:
	public void conMina(int fila, int columna){
		
		//Revelación de la casilla:
		botones[fila][columna].setBorder(BorderFactory.createLoweredBevelBorder());
		botones[fila][columna].setIcon(bombaPerdedora);
		
		//Indicación de la casilla perdedora:
		botones[fila][columna].setUltima(true);
		botones[fila][columna].setBackground(Color.RED);
		
		//Reproducción del sonido de explosión:
		reproducirAudio(3);
		
		//Revelación de la ubicación de las demás minas:
		buscarBombas(false);
		
	}
	
	//Cuenta las casillas revisadas:
	public int contarRevisadas(){
		
		//Creación de la variable a utilizar:
		int cantidad = 0;
		
		for(int x=0; x<LADO; x++){
			for(int y=0; y<LADO; y++){
				
				//Conteo de las casillas revisadas:
				if(botones[x][y].getRevisada()){
					cantidad++;
				}
				
			}
		}
		
		//Retorno de la cantidad de casillas revisadas:
		return cantidad;
		
	}
	
	//Validación de la victoria:
	public void juzgar(){
		
		//Validación del total de casillas revisadas:
		if(contarRevisadas() == 54){
			
			//Revelación de la ubicación de las minas:
			buscarBombas(true);
			
		}
	}
	
	//Método al oprimir una casilla:
	public void jugar(int fila, int columna){
		
		//Validación al oprimir la casilla:
		if(!botones[fila][columna].getRevisada() && botones[fila][columna].getIcon() == null){
			
			//Revisión de la casilla oprimida:
			botones[fila][columna].setRevisada(true);
			
			//Validación de la mina en la casilla:
			if(botones[fila][columna].getBomba()){
				
				//Ejecución del método si hay mina:
				conMina(fila, columna);
				
			}
			else{
				
				//Ejecución del método si no hay mina:
				sinMina(fila, columna);
				
				//Validación de la continuidad del juego:
				juzgar();
				
			}
		}		
	}
	
	//Asigna una bandera a una casilla:
	public void ponerBandera(int fila, int columna){
		
		//Validación de la casilla a asignar la bandera:
		if(!botones[fila][columna].getRevisada()){
			
			//Validación de el estado de la casilla:
			if(botones[fila][columna].getIcon() == null){
				
				//Asignación de la bandera a la casilla:
				botones[fila][columna].setIcon(imagenBandera);
				
				//Reproducción del sonido de bandera asignada:
				reproducirAudio(2);
				
			}
			else{
				
				//Eliminación de la bandera de la casilla:
				botones[fila][columna].setIcon(null);
				
			}
			
		}	
	}
	
	//Muestra un mensaje de ayuda al jugador:
	public void ayuda(){
		
		//Creación del mensaje:
		String mensaje = "Instrucciones:\n\n";
		mensaje += "*Pulsa las casillas para descubrir si hay mina o no.\n";
		mensaje += "*Usa el click derecho para marcar una bandera\n donde crees que hay una mina.\n";
		mensaje += "*Clickea el logo del juego para reiniciar la partida\n con un nuevo tablero.\n";
		mensaje += "-Intenta descubrir todas las casillas sin descubrir\n ninguna mina.\n\n";		
		mensaje += "Suerte! :)\n\n";
		
		//Pausa del tiempo:
		tiempoActual.detener();
		
		//Importación del icono:
		ImageIcon ayuda = new ImageIcon(getClass().getResource("/imagenes/ayuda.gif"));
		
		//Notificación al usuario:
		JOptionPane.showMessageDialog(null, mensaje, "Ayuda", JOptionPane.DEFAULT_OPTION, ayuda);
		
		//Continuación del tiempo:
		tiempoActual.iniciar();
		
	}

}

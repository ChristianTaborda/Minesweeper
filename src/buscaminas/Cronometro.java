/********************************************
 * Christian Camilo Taborda Campiño         *
 * Código: 1632081-3743                     *
 * Fecha de creación: 11/04/2017            *
 * Fecha de última modificación: 13/04/2017 *
 * ****************************************** 
 */

package buscaminas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public class Cronometro extends JPanel implements Runnable{
	
	//Atributos:
	private int alto = 24, ancho = 150;
	private String cadenaTiempo;
	private long tiempo;
	private String titulo, tipo;
	private Thread hilo;
	private Clip pista;
	private boolean record;
	
	//Dibuja el panel:
	public void paintComponent(Graphics G){
		
		super.paintComponent(G);
		
		//Dibuja el título en el panel:
		G.setColor(Color.BLACK);
		G.drawString(titulo, ancho/8, alto);
		
		//Dibuja el tipo de tiempo en el panel:
		G.setColor(Color.BLACK);
		G.drawString(tipo, ancho/8, alto*2);
		
		//Validación de si el tiempo es cero:
		if(tiempo == 0){
			
			//Validación de si es el tiempo récord:
			if(!record){
				
				//Dibuja el tiempo en el panel:
				G.setColor(Color.RED);
				G.drawString(cadenaTiempo, ancho/8 + 12, alto*3);
				
			}			
			
		}
		else{
			
			//Dibuja el tiempo en el panel:
			G.setColor(Color.RED);
			G.drawString(cadenaTiempo, ancho/8 + 12, alto*3);
			
		}
		
	}
	
	/**************
	 * MÉTODOS GET*
	 **************/
	
	//Retorna el tamaño predeterminado para el panel:
	public Dimension getPreferredSize(){
		Dimension salida = new Dimension(ancho+(alto*2)+2, alto*3);
		return salida;
	}
	
	//Retorna el tiempo en segundos:
	public long getTiempo(){
		return tiempo;
	}
	
	/**************
	 * MÉTODOS SET*
	 **************/
	
	//Cambia el tiempo del panel:
	public void setTiempo(long tiempo){
		
		//Cambio del valor del tiempo:
		this.tiempo = tiempo;
		
		//Extracción los minutos del tiempo:
		long m = tiempo/60;
		
		//Extracción los segundos del tiempo:
		long s = tiempo - m*60;
		
		//Formateo del tiempo para su dibujo:
		this.cadenaTiempo = String.format("%02d:%02d", m, s);
		
		//Asignación de la fuente al tiempo:
		Font fuente = new Font("Arial Black", Font.BOLD, 25);
		this.setFont(fuente);
		
		//Cambio de valor de las dimensiones del tiempo:
		alto = fuente.getSize();
		FontMetrics FM = getFontMetrics(fuente);
		ancho = FM.stringWidth(cadenaTiempo);
		
		//Actualiza el dibujo:
		this.repaint();
		
	}
	
	//Retorna el nombre de una pista de audio:
	public String seleccionarAudio(int audio){
		
		//Creación de la variable a utilizar:
		String archivo = null;
		
		//Validación de la pista deseada:
		switch(audio){
			case 1:
				archivo = "/pista.wav";
				break;
			case 2:
				archivo = "/tic.wav";
				break;
			case 3:
				archivo = "/tac.wav";
				break;
			
		}
		
		//Retorno de la pista seleccionada:
		return archivo;
		
	}

	//Inicialización de las pistas:
	public void reproducirAudio(int audio){
		
		try{	
			
			//Importación de la pista:
			URL url = getClass().getResource("/sonido" + seleccionarAudio(audio));
			AudioInputStream entrada = AudioSystem.getAudioInputStream(url);
			
			//Validación del tipo de audio:
			if(audio == 1){
					
				//Inicialización y reproducción de la pista de fondo:
				pista = AudioSystem.getClip();
				pista.open(entrada);
				
			}
			else{
				
				//Inicialización y reproducción del audio:
				Clip clip = AudioSystem.getClip();
				clip.open(entrada);
				clip.start();
				
			}
			
		}
		catch(LineUnavailableException E){
			E.printStackTrace();			
		}
		catch(IOException E){
			E.printStackTrace();
		}
		catch(UnsupportedAudioFileException E){
			E.printStackTrace();
		}
		
	}
	
	//Constructor:
	public Cronometro(long tiempo, String tipo, boolean record){
		
		//Inicialización de los atributos:
		this.tipo = tipo;
		this.record = record;
		this.setTiempo(tiempo);
		titulo = "Tiempo";
		
		//Asignación del color de fondo:
		setBackground(Color.YELLOW);
		
		//Inicialización de la pista de fondo:
		reproducirAudio(1);
		
	}
	
	//Inicia el conteo del tiempo:
	public void iniciar(){
		
		//Eliminación del hilo en caso de que exista:
		this.detener();
		
		//Reproducción de la pista de fondo:
		pista.start();
		
		//Creación del nuevo hilo:
		hilo = new Thread(this);
		
		//Ejecución del hilo:
		hilo.start();
		
	}
	
	//Pausa el conteo del tiempo:
	public void detener(){
		
		//Pausa de la pista de fondo:
		pista.stop();
		
		//Validación de la existencia del hilo:
		if(hilo != null){
			
			//Pausa del hilo:
			hilo.interrupt();
			
			//Eliminación del hilo:
			hilo = null;
			
		}	
		
	}
	
	//Reinicia el tiempo en cero:
	public void limpiar(){
		this.setTiempo(0);
	}
	
	//Método principal del hilo:
	public void run(){
		
		//Ejecución del método hasta que se detenga el cronómetro:
		while(true){
			
			//Conteo de los segundos:
			tiempo++;
			this.setTiempo(tiempo);
			
			//Validación para aplicar el sonido al cronómetro:
			if(tiempo > 1){
				
				//Validación de los segundos:
				if(tiempo%2 == 0){
					
					//Reproducción del sonido del tac:
					reproducirAudio(3);
					
				}
				else{
					
					//Reproducción del sonido del tic:
					reproducirAudio(2);
					
				}
				
			}
			
			//Validación de si la pista de fondo terminó:
			if(!pista.isRunning()){
				
				//Reinicialización de la pista de fondo:
				reproducirAudio(1);
				pista.start();
				
			}
			
			try{
				
				//Tiempo de espera entre el conteo:
				Thread.sleep(1000);
				
			}
			catch(InterruptedException E){
				return;
			}
			
		}	
		
	}
	
}


/********************************************
 * Christian Camilo Taborda Campi�o         *
 * C�digo: 1632081-3743                     *
 * Fecha de creaci�n: 11/04/2017            *
 * Fecha de �ltima modificaci�n: 13/04/2017 *
 * ****************************************** 
 */

package buscaminas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuscaminasGUI extends JFrame{
	
	//Atributos:	
	private static final int LADO = 8;
	private Controlador juego;
	private Container contenedor;
	private JPanel malla, centro, cabecera;
	private JLabel titulo, logo;
	private JButton ayuda;
	
	//Inicializa los componentes gr�ficos:
	public void initGUI(){
		
		//Contenedor:
		contenedor = this.getContentPane();
		contenedor.setLayout(new BorderLayout());
		
		//Controlador:
		juego = new Controlador();
		
		//Escucha del mouse:
		EscuchaMouse escucha = new EscuchaMouse();
		
		//Panel Central:
		centro = new JPanel(new FlowLayout());
		centro.setBackground(Color.YELLOW);
		
		//Logo del juego:
		logo = new JLabel();
		logo.setBackground(Color.YELLOW);
		Dimension D = new Dimension(60,100);
		logo.setPreferredSize(D);
		logo.setOpaque(true);
		ImageIcon I = new ImageIcon(getClass().getResource("/imagenes/logo.gif"));
		logo.setIcon(I);
		logo.addMouseListener(escucha);
		
		//Inclusi�n de los componentes al panel central:
		centro.add(juego.getTiempoRecord());
		centro.add(logo);
		centro.add(juego.getTiempoActual());
		
		//Panel de los botones:
		malla = new JPanel(new GridLayout(LADO,LADO));
		
		//Inclusi�n de la escucha a los botones y los botones al panel:
		for(int x=0; x<LADO; x++){
			for(int y=0; y<LADO; y++){
				juego.getBoton(x, y).addMouseListener(escucha);
				malla.add(juego.getBoton(x, y));
			}
		}
		
		//Panel de la cabecera:
		cabecera = new JPanel(new FlowLayout());
		cabecera.setBackground(new Color(0,0,50));
		
		//T�tulo del juego:
		titulo = new JLabel("BUSCAMINAS");
		titulo.setFont(new Font("Arial Black", Font.BOLD, 36));
		titulo.setForeground(Color.CYAN);
		titulo.setBackground(new Color(0,0,50));
		titulo.setHorizontalAlignment(JLabel.CENTER);
		titulo.setOpaque(true);
		
		//Bot�n de ayuda:
		ayuda = new JButton("?");
		ayuda.setFont(new Font("Arial Black", Font.BOLD, 36));
		ayuda.setForeground(new Color(0,0,50));
		ayuda.setBackground(Color.CYAN);
		ayuda.setOpaque(true);
		ayuda.addMouseListener(escucha);
		
		//Inclusi�n del t�tulo y el bot�n de ayuda en la cabecera:
		cabecera.add(titulo);
		cabecera.add(ayuda);
		
		//Inclusi�n de los paneles al contenedor:
		contenedor.add(cabecera, BorderLayout.NORTH);
		contenedor.add(malla, BorderLayout.SOUTH);
		contenedor.add(centro, BorderLayout.CENTER);
		
	}
	
	//Contructor
	public BuscaminasGUI(){
		
		//Inicializaci�n de los componentes gr�ficos:
		initGUI();
		
		//Asignaci�n del icono al JFrame:
		ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/icono.png"));
		setIconImage(icono.getImage());
		
		//Caracterizaci�n de la ventana:
		setTitle("Buscaminas");
		setResizable(false);
		pack();
		setVisible(true);
		
	}
	
	//Escucha de los botones:
	private class EscuchaMouse extends MouseAdapter{
		
		//M�todo para los clicks del mouse:
		public void mouseClicked(MouseEvent E){
			
			//Validaci�n del bot�n de ayuda:
			if(E.getSource() == ayuda){
				
				//Env�o de ayuda al jugador:
				juego.ayuda();
				
			}
			else{
				
				//Validaci�n del logo del juego:
				if(E.getSource() == logo){
					
					//Reinicio del juego:
					juego.reiniciar();
					
				}
				else{
					
					//Creaci�n de la casilla auxiliar:
					Casilla C = (Casilla)E.getSource();
					
					//Validaci�n del bot�n del mouse:
					if((E.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK){
						//Si es el bot�n derecho:
						juego.jugar(C.getFila(), C.getColumna());
					}
					else{				
						//Si es el bot�n derecho:
						juego.ponerBandera(C.getFila(), C.getColumna());				
					}
					
				}	
				
			}	
			
		}
		
	}

}

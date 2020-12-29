/********************************************
 * Christian Camilo Taborda Campiño         *
 * Código: 1632081-3743                     *
 * Fecha de creación: 11/04/2017            *
 * Fecha de última modificación: 13/04/2017 *
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
	
	//Inicializa los componentes gráficos:
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
		
		//Inclusión de los componentes al panel central:
		centro.add(juego.getTiempoRecord());
		centro.add(logo);
		centro.add(juego.getTiempoActual());
		
		//Panel de los botones:
		malla = new JPanel(new GridLayout(LADO,LADO));
		
		//Inclusión de la escucha a los botones y los botones al panel:
		for(int x=0; x<LADO; x++){
			for(int y=0; y<LADO; y++){
				juego.getBoton(x, y).addMouseListener(escucha);
				malla.add(juego.getBoton(x, y));
			}
		}
		
		//Panel de la cabecera:
		cabecera = new JPanel(new FlowLayout());
		cabecera.setBackground(new Color(0,0,50));
		
		//Título del juego:
		titulo = new JLabel("BUSCAMINAS");
		titulo.setFont(new Font("Arial Black", Font.BOLD, 36));
		titulo.setForeground(Color.CYAN);
		titulo.setBackground(new Color(0,0,50));
		titulo.setHorizontalAlignment(JLabel.CENTER);
		titulo.setOpaque(true);
		
		//Botón de ayuda:
		ayuda = new JButton("?");
		ayuda.setFont(new Font("Arial Black", Font.BOLD, 36));
		ayuda.setForeground(new Color(0,0,50));
		ayuda.setBackground(Color.CYAN);
		ayuda.setOpaque(true);
		ayuda.addMouseListener(escucha);
		
		//Inclusión del título y el botón de ayuda en la cabecera:
		cabecera.add(titulo);
		cabecera.add(ayuda);
		
		//Inclusión de los paneles al contenedor:
		contenedor.add(cabecera, BorderLayout.NORTH);
		contenedor.add(malla, BorderLayout.SOUTH);
		contenedor.add(centro, BorderLayout.CENTER);
		
	}
	
	//Contructor
	public BuscaminasGUI(){
		
		//Inicialización de los componentes gráficos:
		initGUI();
		
		//Asignación del icono al JFrame:
		ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/icono.png"));
		setIconImage(icono.getImage());
		
		//Caracterización de la ventana:
		setTitle("Buscaminas");
		setResizable(false);
		pack();
		setVisible(true);
		
	}
	
	//Escucha de los botones:
	private class EscuchaMouse extends MouseAdapter{
		
		//Método para los clicks del mouse:
		public void mouseClicked(MouseEvent E){
			
			//Validación del botón de ayuda:
			if(E.getSource() == ayuda){
				
				//Envío de ayuda al jugador:
				juego.ayuda();
				
			}
			else{
				
				//Validación del logo del juego:
				if(E.getSource() == logo){
					
					//Reinicio del juego:
					juego.reiniciar();
					
				}
				else{
					
					//Creación de la casilla auxiliar:
					Casilla C = (Casilla)E.getSource();
					
					//Validación del botón del mouse:
					if((E.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK){
						//Si es el botón derecho:
						juego.jugar(C.getFila(), C.getColumna());
					}
					else{				
						//Si es el botón derecho:
						juego.ponerBandera(C.getFila(), C.getColumna());				
					}
					
				}	
				
			}	
			
		}
		
	}

}

/********************************************
 * Christian Camilo Taborda Campiño         *
 * Código: 1632081-3743                     *
 * Fecha de creación: 11/04/2017            *
 * Fecha de última modificación: 13/04/2017 *
 * ****************************************** 
 */

package buscaminas;

import java.awt.Dimension;

import javax.swing.JButton;

public class Casilla extends JButton{
	
	//Atributos:
	private static final int DIMENSION = 50;
	private int fila, columna;
	private boolean bomba, revisada, ultima;
	
	//Constructor:
	public Casilla(int fila, int columna){
		
		//Inicialización de los atributos:
		this.fila = fila;
		this.columna = columna;
		bomba = false;
		revisada = false;
		ultima = false;
		
		//Asignación del tamaño predeterminado:
		Dimension D = new Dimension(DIMENSION, DIMENSION);
		setPreferredSize(D);
		
	}
	
	/**************
	 * MÉTODOS GET*
	 **************/
	
	//Retorna el valor del entero -fila-:
	public int getFila(){
		return fila;
	}
	
	//Retorna el valor del entero -columna-:
	public int getColumna(){
		return columna;
	}
	
	//Retorna el valor del booleano -ultima-:
	public boolean getUltima(){
		return ultima;
	}
	
	//Retorna el valor del booleano -bomba-:
	public boolean getBomba(){
		return bomba;
	}
	
	//Retorna el valor del booleano -revisada-:
	public boolean getRevisada(){
		return revisada;
	}
	
	/**************
	 * MÉTODOS SET*
	 **************/
	
	//Cambia el valor del booleano -ultima-:
	public void setUltima(boolean ultima){
		this.ultima = ultima;
	}	
	
	//Cambia el valor del booleano -bomba-:
	public void setBomba(boolean bomba){
		this.bomba = bomba;
	}	
	
	//Cambia el valor del booleano -revisada-:
	public void setRevisada(boolean revisada){
		this.revisada = revisada;
	}
	
}

/********************************************
 * Christian Camilo Taborda Campi�o         *
 * C�digo: 1632081-3743                     *
 * Fecha de creaci�n: 11/04/2017            *
 * Fecha de �ltima modificaci�n: 13/04/2017 *
 * ****************************************** 
 */

package buscaminas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Archivador{
	
	//Atributos:
	private FileReader lector;
	private BufferedReader entrada;
	private FileWriter escritor;
	private BufferedWriter salida;
	
	//Retorna el tiempo le�do de un archivo de texto:
	public long leer(){
		
		//Creaci�n de la variable de salida:
		String texto = null;
		
		try{
			
			//Inicializaci�n y lectura del archivo:
			lector = new FileReader("src/registro/registro.txt");
			entrada = new BufferedReader(lector);
			texto = entrada.readLine();
			
		}catch(FileNotFoundException E){
			
			//Si el archivo no existe se tomar� como cero:
			texto = "0";
			
		}catch(IOException E){
			
			E.printStackTrace();
			
		}finally{
			
			try{
				
				//Si el archivo existe se cierra debidamente:
				if(!texto.equals("0")){
					entrada.close();
				}	
				
			}catch(IOException E){
				E.printStackTrace();
			}
			
		}
		
		//Inicializaci�n de la variable a retornar:
		long salida = Long.valueOf(texto);
		
		//Retorno del tiempo le�do:
		return salida;
		
	}
	
	//Crea o sobreescribe un archivo de texto con el mejor tiempo:
	public void escribir(String texto){
		
		try{
			
			//Inicializaci�n y escritura del archivo:
			escritor = new FileWriter("src/registro/registro.txt");
			salida = new BufferedWriter(escritor);
			salida.write(texto);
			
		}catch(IOException E){
			
			E.printStackTrace();
			
		}finally{
			
			try{
				salida.close();
			}catch(IOException E){
				E.printStackTrace();
			}
		}
		
	}

}

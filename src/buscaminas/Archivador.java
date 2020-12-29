/********************************************
 * Christian Camilo Taborda Campiño         *
 * Código: 1632081-3743                     *
 * Fecha de creación: 11/04/2017            *
 * Fecha de última modificación: 13/04/2017 *
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
	
	//Retorna el tiempo leído de un archivo de texto:
	public long leer(){
		
		//Creación de la variable de salida:
		String texto = null;
		
		try{
			
			//Inicialización y lectura del archivo:
			lector = new FileReader("src/registro/registro.txt");
			entrada = new BufferedReader(lector);
			texto = entrada.readLine();
			
		}catch(FileNotFoundException E){
			
			//Si el archivo no existe se tomará como cero:
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
		
		//Inicialización de la variable a retornar:
		long salida = Long.valueOf(texto);
		
		//Retorno del tiempo leído:
		return salida;
		
	}
	
	//Crea o sobreescribe un archivo de texto con el mejor tiempo:
	public void escribir(String texto){
		
		try{
			
			//Inicialización y escritura del archivo:
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

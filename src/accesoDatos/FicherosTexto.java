package accesoDatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {

	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public FicherosTexto() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();

		String nombre;
		int valor;
		int cantidad;

		Deposito miDepos = new Deposito();

		File file = new File("Ficheros/datos/depositos.txt");
		String str;
		int i = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((str = br.readLine()) != null) {
				i++;
				String[] arrString = str.split(";");
				int castValor = (int) Integer.parseInt(arrString[1]);
				int castCantidad = (int) Integer.parseInt(arrString[2]);
				nombre = arrString[0];
				valor = castValor;
				cantidad = castCantidad;

				miDepos = new Deposito(nombre, valor, cantidad);

				depositosCreados.put(valor, miDepos);
			}
		} catch (Exception e) {
		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();

		String clave;
		String nombre2;
		int precio;
		int cantidad;

		Dispensador miDispensador = new Dispensador();

		File file = new File("Ficheros/datos/dispensadores.txt");
		String str;
		int i = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((str = br.readLine()) != null) {
				i++;
				String[] arrString = str.split(";");

				clave = arrString[0];
				nombre2 = arrString[1];
				int castValor = (int) Integer.parseInt(arrString[2]);

				int castCantidad = (int) Integer.parseInt(arrString[3]);

				precio = castValor;
				cantidad = castCantidad;

				miDispensador = new Dispensador(clave, nombre2, precio, cantidad);

				dispensadoresCreados.put(clave, miDispensador);
			}
		} catch (Exception e) {
		}

		return dispensadoresCreados;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		
		try {
			FileWriter fw = new FileWriter("Ficheros/datos/depositos.txt", false);
			BufferedWriter bw = new BufferedWriter(fw);
			// 2.
			fw.write("");
			
			
			bw.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
			int k = entry.getKey();
			Deposito v = entry.getValue();
			try {
				FileWriter fw = new FileWriter("Ficheros/datos/depositos.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);

				fw.write(depositos.get(k).getNombreMoneda() + ";");
				fw.write(depositos.get(k).getValor() + ";");
				fw.write(depositos.get(k).getCantidad() + ";");
				fw.write("\n");

				
				
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = true;
		try {
			FileWriter fw = new FileWriter("Ficheros/datos/dispensadores.txt", false);
			BufferedWriter bw = new BufferedWriter(fw);
			fw.write("");
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			String k = entry.getKey();
			
			
			
			Dispensador v = entry.getValue();
			try {
				FileWriter fw = new FileWriter("Ficheros/datos/dispensadores.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				fw.write(dispensadores.get(k).getClave() + ";");
				fw.write(dispensadores.get(k).getNombreProducto() + ";");
				fw.write(dispensadores.get(k).getPrecio() + ";");
				fw.write(dispensadores.get(k).getCantidad() + ";");
				
				fw.write("\n");

				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return todoOK;
	}

} // Fin de la clase
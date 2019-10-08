package accesoDatos;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");

		try {
			HashMap<String, String> datosConexion;

			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();

			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;

			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			}

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			// e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			// e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> obtenerDepositos = new HashMap<Integer, Deposito>();
		Deposito miDeposito = new Deposito();
		try {
			Statement stmt = conn1.createStatement();
			String query = "SELECT * from depositos ;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				int valor = rs.getInt("valor");
				int cantidad = rs.getInt("cantidad");

				miDeposito = new Deposito(nombre, valor, cantidad);
				obtenerDepositos.put(valor, miDeposito);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return obtenerDepositos;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> obtenerDispensadores = new HashMap<String, Dispensador>();
		Dispensador miDispensador = new Dispensador();
		try {
			Statement stmt = conn1.createStatement();
			String query = "SELECT * from dispensadores ;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String clave = rs.getString("clave");
				String nombre = rs.getString("nombre");
				int precio = rs.getInt("precio");
				int cantidad = rs.getInt("cantidad");

				miDispensador = new Dispensador(clave, nombre, precio, cantidad);
				obtenerDispensadores.put(clave, miDispensador);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return obtenerDispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = false;

		try {
			String query = "DELETE FROM depositos";
			PreparedStatement stmt = conn1.prepareStatement(query);
			
			stmt.executeUpdate(query);
			stmt.close();
			
			String nombre;
			int valor;
			int cantidad;
			Iterator iterator = depositos.entrySet().iterator();
			
			while (iterator.hasNext()) {
				
				Map.Entry mapita = (Map.Entry) iterator.next();
				
				Deposito miDeposito = (Deposito) mapita.getValue();
				
				nombre = miDeposito.getNombreMoneda();
				valor  = miDeposito.getValor();
				cantidad = miDeposito.getCantidad();
				
				Statement stmt2 = conn1.createStatement();
				String query2 = "INSERT INTO depositos (nombre, valor, cantidad) VALUES  ('" + nombre + "','" + valor + "','" + cantidad + "')";
				stmt2.executeUpdate(query2);
				iterator.remove();
			}
			todoOK = true;
		} catch (Exception e) {
		}
		

		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = false;
		try {
			String query = "DELETE FROM dispensadores";
			PreparedStatement stmt = conn1.prepareStatement(query);
			stmt.executeUpdate(query);
			stmt.close();
			
			String clave;
			String nombre;
			int precio;
			int cantidad;
			Iterator iterator = dispensadores.entrySet().iterator();
			
			while (iterator.hasNext()) {
				
				Map.Entry mapita = (Map.Entry) iterator.next();
				
				Dispensador miDispensador = (Dispensador) mapita.getValue();
				
				 clave = miDispensador.getClave();
				 nombre = miDispensador.getNombreProducto();
				 precio = miDispensador.getPrecio();
				 cantidad = miDispensador.getCantidad();
				
				Statement stmt2 = conn1.createStatement();
				String query2 = "INSERT INTO dispensadores (clave, nombre, precio, cantidad) VALUES  ('" + clave + "','" + nombre + "','" + precio + "', '" + cantidad + "')";
				stmt2.executeUpdate(query2);
				iterator.remove();
			}
			todoOK = true;
		} catch (Exception e) {
		}
		
		
		
		

		return todoOK;
	}

} // Fin de la clase
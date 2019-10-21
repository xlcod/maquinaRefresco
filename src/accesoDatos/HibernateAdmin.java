package accesoDatos;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import HibernateXml.HibernateUtil;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class HibernateAdmin implements I_Acceso_Datos {

	Session session;

	public HibernateAdmin() {

		HibernateUtil util = new HibernateUtil();

		session = util.getSessionFactory().openSession();

	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> obtenerDepositos = new HashMap<Integer, Deposito>();

		System.out.println("Entra");

		Query q = session.createQuery("select e from Deposito e");
		List results = q.list();

		Iterator equiposIterator = results.iterator();

		while (equiposIterator.hasNext()) {
			Deposito miDeposito = (Deposito) equiposIterator.next();
			int valor = miDeposito.getValor();
			obtenerDepositos.put(valor, miDeposito);

		}

		return obtenerDepositos;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> obtenerDispensadores = new HashMap<String, Dispensador>();

		System.out.println("Entra");

		Query q = session.createQuery("select e from Dispensador e");
		List results = q.list();

		Iterator equiposIterator = results.iterator();

		while (equiposIterator.hasNext()) {
			Dispensador miDispensador = (Dispensador) equiposIterator.next();
			String clavecita = miDispensador.getClave();
			obtenerDispensadores.put(clavecita, miDispensador);

		}

		return obtenerDispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		Boolean todoOK = false;
		Transaction transaction = null;
		try {
			Iterator iterator = depositos.entrySet().iterator();

			while (iterator.hasNext()) {

				Map.Entry mapita = (Map.Entry) iterator.next();

				Deposito miDeposito = (Deposito) mapita.getValue();

				String nombre = miDeposito.getNombreMoneda();
				int valor = miDeposito.getValor();
				int cantidad = miDeposito.getCantidad();

				transaction = session.beginTransaction();

				miDeposito.setNombreMoneda(nombre);
				miDeposito.setValor(valor);
				miDeposito.setCantidad(cantidad);

				session.update(miDeposito);

				transaction.commit();

				iterator.remove();
			}
			todoOK = true;
		} catch (Exception e) {
			System.out.println("Algo ha ido mal");
		}

		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		Boolean todoOK = false;
		Transaction transaction = null;
		try {
			Iterator iterator = dispensadores.entrySet().iterator();

			while (iterator.hasNext()) {

				Map.Entry mapita = (Map.Entry) iterator.next();

				Dispensador miDispensador = (Dispensador) mapita.getValue();

				String clave = miDispensador.getClave();
				String nombre = miDispensador.getNombreProducto();
				int precio = miDispensador.getPrecio();
				int cantidad = miDispensador.getCantidad();

				transaction = session.beginTransaction();

				miDispensador.setClave(clave);
				miDispensador.setNombreProducto(nombre);
				miDispensador.setPrecio(precio);
				miDispensador.setCantidad(cantidad);

				session.update(miDispensador);

				transaction.commit();

				iterator.remove();
			}
			todoOK = true;
		} catch (Exception e) {
			System.out.println("Algo ha ido mal");
		}

		return todoOK;
	}

}

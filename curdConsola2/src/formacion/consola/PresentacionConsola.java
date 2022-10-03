package formacion.consola;

import static formacion.bibliotecas.Consola.*;

import formacion.accesodatos.DaoEmpleado;
import formacion.accesodatos.DaoEmpleadoMemoria;
import formacion.entidades.Empleado;
import formacion.entidades.EntidadesException;

public class PresentacionConsola {

	// SINGLETON
	private static final DaoEmpleado DAO = DaoEmpleadoMemoria.getInstancia();

//	private static final Logger LOGGER = Logger.getLogger(PresentacionConsola.class.getName());

	public static void main(String[] args) {
//		LOGGER.severe("Se ha iniciado el programa"); // Level.SEVERE
//		LOGGER.log(Level.SEVERE, "Fallo del programa", new RuntimeException("Fallo"));

		int opcion;

		do {
			mostrarMenu();
			opcion = pedirOpcion();
			procesarOpcion(opcion);
		} while (opcion != 0);
	}

	private static void mostrarMenu() {
		pl("1. Mostrar todos");
		pl("2. Buscar por Id");
		pl("3. Insertar");
		pl("4. Modificar");
		pl("5. Borrar");
		pl("0. Salir");
	}

	private static int pedirOpcion() {
		return pedirInt("Selecciona una de las opciones del menú");
	}

	private static void procesarOpcion(int opcion) {
		switch (opcion) {
		case 1:
			mostrarTodos();
			break;
		case 2:
			buscarPorId();
			break;
		case 3:
			insertar();
			break;
		case 4:
			modificar();
			break;
		case 5:
			borrar();
			break;
		case 0:
			pl("Gracias por utilizar la aplicación");
			break;
		default:
			errorPl("Por favor, elija una de las opciones existentes");
		}
	}

	private static void mostrarTodos() {
		pl();
		
		cabeceraListado();
		
		for (Empleado e : DAO.obtenerTodos()) {
			mostrarLinea(e);
		}
		
		pl();
	}

	private static void buscarPorId() {
		long id = pedirLong("Dime el id a buscar");
		Empleado empleado = DAO.obtenerPorId(id);
		mostrarFicha(empleado);
	}

	private static void insertar() {
		Empleado empleado = new Empleado();

		boolean repetir = true;

		do {
			try {
				empleado.setNombre(pedirString("Nombre"));
				repetir = false;
			} catch (EntidadesException e) {
				pl(e.getMessage());
			}
		} while (repetir);

		repetir = true;

		do {
			try {
				empleado.setNif(pedirString("NIF"));
				repetir = false;
			} catch (EntidadesException e) {
				pl(e.getMessage());
			}
		} while (repetir);

		repetir = true;

		do {
			try {
				empleado.setFechaNacimiento(pedirLocalDate("Fecha de nacimiento"));
				repetir = false;
			} catch (EntidadesException e) {
				pl(e.getMessage());
			}
		} while (repetir);

		repetir = true;

		do {
			try {
				empleado.setSueldo(pedirBigDecimal("Sueldo"));
				repetir = false;
			} catch (EntidadesException e) {
				pl(e.getMessage());
			}
		} while (repetir);

		// Empleado empleado = new Empleado(null, nif, nombre, fechaNacimiento, sueldo);

		DAO.insertar(empleado);
	}


	private static void validarPeticion(Runnable pd) {
		boolean repetir = true;

		do {
			try {
				pd.run();
				repetir = false;
			} catch (EntidadesException e) {
				pl(e.getMessage());
			}
		} while (repetir);
	}

	private static void modificar() {
		Empleado empleado = new Empleado();

		validarPeticion(() -> empleado.setId(pedirLong("Id")));
		validarPeticion(() -> empleado.setNombre(pedirString("Nombre")));
		validarPeticion(() -> empleado.setNif(pedirString("NIF")));
		validarPeticion(() -> empleado.setFechaNacimiento(pedirLocalDate("Fecha de nacimiento")));
		validarPeticion(() -> empleado.setSueldo(pedirBigDecimal("Sueldo")));

		DAO.modificar(empleado);
	}

	private static void borrar() {
		long id = pedirLong("Dime el id a borrar");
		DAO.borrar(id);
	}

	private static void mostrarFicha(Empleado empleado) {
		pl();
		
		if (empleado == null) {
			pl("No existe ese empleado");
			pl();
			return;
		}

		pf("Id                  %,d\n", empleado.getId());
		pf("NIF                 %9s\n", empleado.getNif());
		pf("Nombre              %s\n", empleado.getNombre());
		pf("Fecha de nacimiento %1$te de %1$tB de %1$tY\n", empleado.getFechaNacimiento());
		pf("Sueldo              %,.2f €\n", empleado.getSueldo());
		pl();
	}

	private static void cabeceraListado() {
		pl(" Id\tNIF      \tNombre                        \tFecha     \tSueldo     ");
	}
	
	private static void mostrarLinea(Empleado empleado) {
		pf("%3s\t%9s\t%-30s\t%4$td/%4$tm/%4$tY\t%5$,9.2f €\n", empleado.getId(), empleado.getNif(), empleado.getNombre(),
				empleado.getFechaNacimiento(), empleado.getSueldo());
	}


}

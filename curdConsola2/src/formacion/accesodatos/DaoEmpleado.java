package formacion.accesodatos;

import formacion.entidades.Empleado;

// Añadimos a las operaciones básicas de todos los tipos generales de DAO
// las operaciones específicas de un Empleado concreto
// Habrá uno por entidad
public interface DaoEmpleado extends Dao<Empleado> {

}

package formacion.accesodatos;

// Data Access Object
// CRUD: Create, Retrieve, Update, Delete 
// (Altas, bajas, modificaciones y consultas)

// Operaciones básicas necesarias para cualquier tipo de datos
interface Dao<T> {
	Iterable<T> obtenerTodos();
	T obtenerPorId(Long id);
	
	void insertar(T objeto);
	void modificar(T objeto);
	void borrar(Long id);
}

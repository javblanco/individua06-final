package es.cic.bootcamp.individual06final.enumeration;

public enum Categoria {

	
	TECNOLOGIA("Tecnología"), CIENCIAS("Ciencias"), FILOSOFIA("Filosofía"), COCINA("Cocina"), OTROS("Otros");

	private final String nombre;

	Categoria(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	} 
	
	
}

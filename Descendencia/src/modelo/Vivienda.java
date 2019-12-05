package modelo;

public class Vivienda {
	private String direccion;
	private String ciudad;			//String, Enum, u Objeto?
	private boolean disponible; //boolean o int?
	private Persona propietario;
	public Vivienda(String direccion, String ciudad, boolean disponibilidad) {
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.disponible = disponibilidad;
	}
	public Vivienda(String direccion, String ciudad) {
		this.direccion = direccion;
		this.ciudad = ciudad;
		disponible = true;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public boolean isDisponible() {
		return disponible;
	}

	public Persona getPropietario(){
		return propietario;
	}
	public void setPropietario(Persona propietario) {
		this.propietario = propietario;
		disponible = false;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
		
	}
	
	
}

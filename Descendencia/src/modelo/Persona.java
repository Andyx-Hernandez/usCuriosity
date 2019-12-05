package modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Auxiliar.Sexo;
import MetodosOrdenamiento.OrdenarPor;

public class Persona implements Comparable,Serializable{
	private String cIdentidad;
	private String nombre;
	private String apellidos;
	private Sexo sexo;			//char, boolean, enum?
	public Persona(String cIdentidad, String nombre, String apellidos ,Sexo sexo) {
		this.cIdentidad = cIdentidad;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.sexo = sexo;
		
		
	}
	public String getCIdentidad() {
		return cIdentidad;
	}
	public void setCIdentidad(String identidad) {
		cIdentidad = identidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	@Override
	public boolean equals(Object arg0) {
		boolean iguales = false;
		if(arg0 instanceof Persona){
			Persona persona0 = (Persona) arg0;
			iguales = ( (cIdentidad.equalsIgnoreCase(persona0.getCIdentidad())) &&
					(nombre.equalsIgnoreCase(persona0.getNombre())) && (apellidos.equalsIgnoreCase(persona0.getApellidos())) &&
					(sexo.ordinal() == persona0.getSexo().ordinal()));
		}
		return iguales;
	}	
	
	
	public int compareTo(Object o) {

		int result = Integer.MAX_VALUE;
		
		if(o instanceof Persona){
        Persona p = (Persona) o;
		GregorianCalendar fecha = OrdenarPor.CovierteAFecha(p.getCIdentidad());
		result = OrdenarPor.CovierteAFecha(cIdentidad).compareTo(fecha);
		}
		return result;
		
	}
	
	public int obtenerEdad(){
		Calendar cal = Calendar.getInstance();
		
		return cal.get(Calendar.YEAR) - OrdenarPor.CovierteAFecha(cIdentidad).get(GregorianCalendar.YEAR);
	}
}

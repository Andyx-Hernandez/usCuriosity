package dJisktra;

import java.util.Comparator;
import java.util.LinkedList;

import cu.edu.cujae.ceis.graph.vertex.Vertex;
//Clase que representa en una lista el camino mas corto de un extremo a otro

public class Camino implements Comparable<Camino>{
          
	private LinkedList<Vertex>  Caminosvertex; // Vertices recorridos
	private double pesoMinimo; //Peso minimo de extremo a extremo
	
	
	//Gets and sets
	public Camino(double peso) {
		 
		pesoMinimo = peso;
		Caminosvertex = new LinkedList<Vertex>();
	}



	public LinkedList<Vertex> getCaminosvertex() {
		return Caminosvertex;
	}



	public double getPesoMinimo() {
		return pesoMinimo;
	}

	public int compareTo(Camino o) {
		return ((Double)pesoMinimo).compareTo(o.getPesoMinimo());
	}

}


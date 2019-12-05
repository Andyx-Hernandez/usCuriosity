package dJisktra;

import java.util.Comparator;

import cu.edu.cujae.ceis.graph.edge.WeightedEdge;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

//Clase que representa la informacion del vertice en el momento en que se procesa
//representa una distancia minima para llegar a el y por que vertices se pasa como si fuera una lista enlazada

public class Etiqueta{

	private Vertex myVertex;
	private double distancia;
	private Etiqueta toMe;
	
	
	public Etiqueta(Vertex myVertex, double distancia, Etiqueta toMe) {
		this.myVertex = myVertex;
		this.distancia = distancia;
		this.toMe = toMe;
	}
	
	public Vertex getMyVertex() {
		return myVertex;
	}
	
	public void setMyVertex(Vertex myVertex) {
		this.myVertex = myVertex;
	}
	
	public double getDistancia() {
		return distancia;
	}
	
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
	public Etiqueta getToMe() {
		return toMe;
	}
	
	public void setToMe(Etiqueta toMe) {
		this.toMe = toMe;
	}

}

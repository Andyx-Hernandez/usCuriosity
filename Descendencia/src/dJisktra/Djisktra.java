package dJisktra;

import java.util.Iterator;
import java.util.LinkedList;

import modelo.Controladora;
import cu.edu.cujae.ceis.graph.Graph;
import cu.edu.cujae.ceis.graph.edge.Edge;
import cu.edu.cujae.ceis.graph.edge.WeightedEdge;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedEdgeNotDirectedGraph;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedVertexNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

public class Djisktra {

	static LinkedList<Etiqueta> listaEtiquetas = new LinkedList<Etiqueta>(); //Conjunto cuya distacia a inicio no ha sido determinada

	//Recibe un grafo no dirigido ponderado por los vertices....el vertice inicio y fin
	public static Camino caminoMasCorto(ILinkedWeightedEdgeNotDirectedGraph graph,Vertex inicio, Vertex fin)
	{
		boolean caminoEncontrado = false; // camino no encontrado
		Camino camino = null;
		//////*********************Inicializar las Etiquetas******************//////////////////////



		listaEtiquetas.add(new Etiqueta(inicio, 0, null)); // inicia la primera etiqueta con el vertice inicio
                                                           // distancia recorrida 0
		Iterator<Vertex> it = graph.getVerticesList().iterator();

		while(it.hasNext())
		{
			Vertex v = it.next();

			if(!v.equals(inicio))
				listaEtiquetas.add(new Etiqueta( v, Double.MAX_VALUE, null)); // Max value es como el infinito del djkstra de MC
		}

		/////*****************************//////////////////////////////////////////////////////////////

		while(!caminoEncontrado)
		{
			Etiqueta e = devolverMenorEtiqueta(); // devuelve la menor etiqueta y la elimina de la lista esta significa que su distancia 
			// ya a sido calculada

			Iterator<Etiqueta> iteratorEtiquetas = listaEtiquetas.iterator();

			while(iteratorEtiquetas.hasNext()) // iterar etiquetas buscando adyacentes
			{
				Etiqueta etiquetaAdyacente = iteratorEtiquetas.next();
				Edge bridge = existeUnPuente(e.getMyVertex(), etiquetaAdyacente.getMyVertex());

				if(bridge != null)//significa que son adayacentes
				{
					double distanciaBridge = e.getDistancia() +  (Double)((WeightedEdge)bridge).getWeight();

					if(etiquetaAdyacente.getDistancia() > distanciaBridge)
					{
						etiquetaAdyacente.setDistancia(distanciaBridge);
						etiquetaAdyacente.setToMe(e);

					}

				}	
			}

			if(e.getMyVertex().equals(fin)){
				caminoEncontrado = true;

				////Para construir un camino////

				camino = construirUnCamino(e);
			}
		}

		return camino;

	}

	private static Edge existeUnPuente(Vertex a , Vertex b){ 

		Edge returnEdge = null;

		Iterator<Edge> it = a.getEdgeList().iterator(); // recorrer lista de arista del vertice A

		while(it.hasNext())
		{
			Edge e = it.next();

			if(e.getVertex().equals(b)){

				if(returnEdge == null)
					returnEdge = e;
				else                           //Como no existe validacion para no insertar mas de 2 aristas de un vertice a otro...entonce se devuelve la de menor peso...el objeto que represente el peso debe implementar la interfaz comparable
				{                                  
                      Object wreturned = ((WeightedEdge) returnEdge).getWeight();
                      Object we = ((WeightedEdge) e).getWeight();
                      
                      if(implementsComparable(wreturned) && implementsComparable(we))//comprueba si el objeto implementa la interfaz comparable
                      {
                    	  
                    	  if(((Comparable)wreturned).compareTo(we) > 0 ){
                    		  
                    		  returnEdge = e;
                    	  }
                      }
                      
				}
			}

		}

		return returnEdge;
	} 

	private static Etiqueta devolverMenorEtiqueta(){ // devuelve la menor etiqueta y la elimina

		Etiqueta e = null;

		Iterator<Etiqueta> it = listaEtiquetas.iterator();

		while(it.hasNext())
		{
			Etiqueta eIt = it.next();

			if(e == null)
				e = eIt;
			else if(e.getDistancia() > eIt.getDistancia())
				e = eIt;

		}

		listaEtiquetas.remove(e);
		return e;
	}

	//construye el camino a partir de las etiquetas

	private static Camino construirUnCamino(Etiqueta e){ 

		Camino result = new Camino(e.getDistancia()); // tomo la distancia recorrida hasta ese momento
		Etiqueta aux = e;

		while(aux != null){
			result.getCaminosvertex().addFirst(aux.getMyVertex()); //construye el camino cambiando hacia la
			aux = aux.getToMe();                                   //referencia anterior contenida en el getToMe
		}                  

		return result;
	}
	
	// devuelve en runtime si el objeto implementa la interfaz comparable
	private static boolean implementsComparable(Object object){
		boolean doesImplements = false;

		Class[] classes = object.getClass().getInterfaces(); // devuelve en runtime las interfaces que implementa dicha clase

		int pos = 0;

		while(pos < classes.length && !doesImplements){
			if(classes[pos].getCanonicalName().equals("java.lang.Comparable"))
				doesImplements = true;

			pos++;
		}			

		return doesImplements;
	}
}

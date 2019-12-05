package Auxiliar;

import modelo.Controladora;
import modelo.Vivienda;
import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedEdgeNotDirectedGraph;

public class Initialize {

	
     
     public static void initializateGraph()
     {
    	 ILinkedWeightedEdgeNotDirectedGraph grafo = new LinkedGraph();
         {
         	Vivienda a = new Vivienda("Calle Amor 2","Amor");
         	Vivienda b = new Vivienda("Calle Ojalata","Oz");
         	Vivienda c = new Vivienda("Calle Cordura","Amor");
         	Vivienda d = new Vivienda("Calle Freeze","Invernalia");
         	Vivienda e = new Vivienda("Calle Cuervo","Invernalia");
         	Vivienda f = new Vivienda("Calle Dream","NoneDream");
         	Vivienda g = new Vivienda("Calle None","NoneDream");
         	Vivienda h = new Vivienda("Calle Pixel","Pixabey");
         	Vivienda i = new Vivienda("Calle Render","Pixabey");
         	Vivienda j = new Vivienda("Calle Palmera","Oasis");
         	
         	grafo.insertVertex(a);
         	grafo.insertVertex(b);
         	grafo.insertVertex(c);
         	grafo.insertVertex(d);
         	grafo.insertVertex(e);
         	grafo.insertVertex(f);
         	grafo.insertVertex(g);
         	grafo.insertVertex(h);
         	grafo.insertVertex(i);
         	grafo.insertVertex(j);
         	
         	grafo.insertWEdgeNDG(1, 0, 1.0);
         	grafo.insertWEdgeNDG(2, 0, 1.0);
         	grafo.insertWEdgeNDG(3, 0, 1.0);
         	
         	grafo.insertWEdgeNDG(4, 1, 1.8);
         	grafo.insertWEdgeNDG(5, 1, 1.7);
         	
         	grafo.insertWEdgeNDG(6, 2, 1.2);
         	grafo.insertWEdgeNDG(7, 2, 1.6);
         	grafo.insertWEdgeNDG(8, 2, 1.4);
         	grafo.insertWEdgeNDG(9, 2, 1.5);
         	grafo.insertWEdgeNDG(3, 6, 2.5);
         }
    	 Controladora.getInstance().setGrafoVivienda(grafo);
     }
}

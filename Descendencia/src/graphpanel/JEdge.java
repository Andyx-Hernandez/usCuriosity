package graphpanel;

public class JEdge {
	// Objeto visual Arista, contiene referencias al v�rtice origen y el v�rtice
	// destino, adem�s de un double que indica el peso de la arista
	JVertex start, end;
	double weight;
	
	public JEdge(JVertex start, JVertex end, double weight){
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	
	public boolean pointsTo(JVertex vertex){
		// Indica si la arista apunta al v�rtice dado
		return (start.equals(vertex) || end.equals(vertex));
	}
}

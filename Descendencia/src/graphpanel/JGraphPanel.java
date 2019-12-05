package graphpanel;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import cu.edu.cujae.ceis.graph.edge.Edge;
import cu.edu.cujae.ceis.graph.edge.WeightedEdge;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import cu.edu.cujae.ceis.tree.general.GeneralTree;
import cu.edu.cujae.ceis.tree.iterators.general.InBreadthIterator;

public class JGraphPanel extends CirclePanel
{
	private static final long serialVersionUID = 6916725142143854275L;
	
	private GeneralTree<Vertex> tree;
    private LinkedList<JVertex> nodes;
    private LinkedList<JEdge> edges;
    private JVertex selectedNode = null;
    private int maxNodeWidth;
    private int nextNodeX;

    public JGraphPanel(GeneralTree<Vertex> tree)
    {
    	super();
        this.tree = tree;
        nodes = new LinkedList<JVertex>();
        edges = new LinkedList<JEdge>();
        update();
        
       addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if(selectedNode!=null)
					selectedNode.setSelected(false);
				selectedNode = null;
				JGraphPanel.this.repaint();
			}
		});
       
      
    }
    
    public JGraphPanel(ILinkedNotDirectedGraph graph, int vertexIndex)
    {
    	super();
        expansionTree(graph, vertexIndex);
        nodes = new LinkedList<JVertex>();
        edges = new LinkedList<JEdge>();
        update();
        
       addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if(selectedNode!=null)
					selectedNode.setSelected(false);
				selectedNode = null;
				JGraphPanel.this.repaint();
			}
		});
       
       setBackground(Color.white);
    }
    
    public JGraphPanel(ILinkedNotDirectedGraph graph)
    {
    	super();
        expansionTree(graph, 0);
        nodes = new LinkedList<JVertex>();
        edges = new LinkedList<JEdge>();
        update();
        
       addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if(selectedNode!=null)
					selectedNode.setSelected(false);
				selectedNode = null;
				JGraphPanel.this.repaint();
			}
		});
       
       setBackground(Color.white);
    }
    
    public void expansionTree(ILinkedNotDirectedGraph graph, int vertexIndex){
    	LinkedList<Vertex> addedVertices = new LinkedList<Vertex>();
    	Queue<Vertex> queue = new ArrayDeque<Vertex>();
    	tree = new GeneralTree<Vertex>();
    	
    	Vertex root = graph.getVerticesList().get(vertexIndex);
    	addedVertices.addFirst(root);
    	queue.offer(root);
    	tree.setRoot(new BinaryTreeNode<Vertex>(root));
    	
    	while(!queue.isEmpty()){
    		Vertex current = queue.poll();
			BinaryTreeNode<Vertex> father = searchNode(current);
    		Iterator<Vertex> iterator = current.getAdjacents().iterator();
    		while(iterator.hasNext()){
    			Vertex adjacent = iterator.next();
    			if(!addedVertices.contains(adjacent) && !queue.contains(adjacent)){
    				queue.offer(adjacent);
    				addedVertices.addFirst(adjacent);
    				tree.insertNode(new BinaryTreeNode<Vertex>(adjacent), father);    				
    			}
    		}
    	}
    	
    }
    
    private BinaryTreeNode<Vertex> searchNode(Vertex info) {
		BinaryTreeNode<Vertex> node = null;
		InBreadthIterator<Vertex> iterator = tree.inBreadthIterator();
		boolean found = false;
		while(iterator.hasNext() && !found){
			BinaryTreeNode<Vertex> current = iterator.nextNode();
			if(current.getInfo().equals(info)){
				found = true;
				node = current;
			}
		}
		return node;
	}

	public void update(){
    	this.removeAll();
		nodes.clear();
		edges.clear();
		selectedNode = null;
		maxNodeWidth = 0;       	
    	nextNodeX = 0;
    	
    	setCrowns(tree.treeHeight());
    	setSectors(tree.getLeaves().size());
    	
		if(!tree.isEmpty())
        	addNode((BinaryTreeNode<Vertex>) tree.getRoot(), null, 0, 0);
		this.validate();
		Iterator<JVertex> iter = nodes.iterator();
		this.setPreferredSize(null);
    	while(iter.hasNext()){
    		final JVertex tn = iter.next();
    		tn.addMouseListener(new java.awt.event.MouseAdapter() {
    			public void mousePressed(java.awt.event.MouseEvent e) {
    				if(selectedNode!=null)
    					selectedNode.setSelected(false);
    				tn.setSelected(true);
    				selectedNode = tn;
    				JGraphPanel.this.repaint();
    			}
    		});
    		tn.setPreferredSize(new Dimension(maxNodeWidth, tn.getPreferredSize().height));
    		for(JVertex atn: findAdjacents(tn)){
    			Iterator<JEdge> edgeIter = edges.iterator();
    			boolean exists = false;
    			while(!exists && edgeIter.hasNext()){
    				JEdge edge = edgeIter.next();
    				if(edge.pointsTo(atn) && edge.pointsTo(tn))
    					exists = true;
    			}
    			if(!exists){
    				boolean found = false;
    				Iterator<Edge> edgeIter2 = tn.getInfo().getEdgeList().iterator();
    				while(iter.hasNext() && !found){
    					Edge edge = edgeIter2.next();
    					if(edge.getVertex().equals(atn.getInfo())){
    						found = true;
    						double weight = (Double)((WeightedEdge) edge).getWeight();
    	    				edges.add(new JEdge(tn, atn, weight));
    					}
    				}
    			}
    		}
    	}
    	this.setPreferredSize(new Dimension(maxNodeWidth*(nextNodeX+2),getPreferredSize().height*3));
    }
    
    public Vertex getSelectedInfo(){
    	Vertex info = null;
    	if(selectedNode!=null)
    		info = selectedNode.getInfo();
    	return info;
    }

    private void addNode(BinaryTreeNode<Vertex> node, JVertex parent, int crown, int sector) {
    	JVertex treeNode = new JVertex(node, parent);
    	int width=1;		
    	nodes.addFirst(treeNode);
    			
		List<BinaryTreeNode<Vertex>> hijos = tree.getSons(node);
		Iterator<BinaryTreeNode<Vertex>> iter = hijos.iterator();
		boolean first = true;
		while(iter.hasNext()){
			if(!first){
				nextNodeX++;
				width++;
			}
			else
				first=false;
			addNode(iter.next(), treeNode, crown+1, nextNodeX);
		}

		this.addInPosition(treeNode, crown, sector, width);
		if(maxNodeWidth<treeNode.getPreferredSize().width)
			maxNodeWidth=treeNode.getPreferredSize().width;
		
	}


	public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D)g);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	// Selected node highlighting
        if(selectedNode!=null){
        	int x = selectedNode.getX()-3;
        	int y = selectedNode.getY()-3;
        	int width = selectedNode.getWidth()+6;
        	int height = selectedNode.getHeight()+6;
        	GradientPaint gp = new GradientPaint(0f, 0f, selectedNode.color().brighter(),
                    0f, 10f, Color.white,true); 
        	
        	g2d.setPaint(gp);
        	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.7f); 
        	g2d.setComposite(ac);
        	g2d.fillRoundRect(x, y, width, height, 9, 9);
        	
        	g2d.setColor(selectedNode.color());
        	g2d.drawRoundRect(x, y, width, height, 9, 9);   
        	
        	x-=2;
        	y-=2;
        	ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.2f); 
        	g2d.setComposite(ac);
        	g2d.fillRoundRect(x, y, width, height, 9, 9);
        	
        	g2d.setColor(selectedNode.color());
        	g2d.drawRoundRect(x, y, width, height, 9, 9);   
        	
        	
        	g2d.setComposite(AlphaComposite.SrcOver);
        }
    	
        // Edges
        Iterator<JEdge> iter = edges.iterator();
    	while(iter.hasNext()){
    		JEdge edge = iter.next();
    		JVertex tn = edge.start;
    		JVertex etn = edge.end;
    		
    		int x1 = tn.getX()+tn.getWidth()/2;
    		int y1 = tn.getY()+tn.getHeight()/2;
    		int x2 = etn.getX()+etn.getWidth()/2;
    		int y2 = etn.getY()+etn.getHeight()/2;

    		GradientPaint gp = new GradientPaint(x1, y1, tn.color().darker().darker(),
    				x2, y2, etn.color().darker().darker()); 
    		g2d.setPaint(gp);
    		g2d.drawLine(x1, y1, x2, y2);    		
    		
    		if(edge.pointsTo(selectedNode)){
    			String w = String.valueOf(edge.weight);
    			int width=0;
    			for(int i=0; i<w.length(); i++)
    				width += g2d.getFontMetrics().charWidth(w.charAt(i));
    			int height = g2d.getFontMetrics().getHeight();
    			
    			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f); 
            	g2d.setComposite(ac);
            	g2d.setColor(Color.white);
            	g2d.fillRect((x1+x2-width)/2-2, (y1+y2)/2-height+2, width+4, height+2);
            	g2d.drawRect((x1+x2-width)/2-2, (y1+y2)/2-height+2, width+4, height+2);
            	g2d.setComposite(AlphaComposite.SrcOver);
            	
            	g2d.setPaint(gp);
    			g2d.drawString(w, (x1+x2-width)/2, (y1+y2)/2);
    		}
    	}  
    }
	
	private JVertex[] findAdjacents(JVertex tn){
		LinkedList<Vertex> adj = new LinkedList<Vertex>();
		adj.addAll(tn.getInfo().getAdjacents());
		JVertex[] adjacentNodes = new JVertex[adj.size()];
		int i=0;
		Iterator<JVertex> iter = nodes.iterator();
		while(iter.hasNext() && i<adjacentNodes.length){
			JVertex ctn = iter.next();
			ListIterator<Vertex> iter2 = adj.listIterator();
			boolean found = false;
			while(iter2.hasNext() && !found){
				Vertex info = iter2.next();
				if(ctn.getInfo().equals(info)){
					found = true;
					adjacentNodes[i] = ctn;
					i++;
					iter2.remove();
				}
			}
		}
		return adjacentNodes;
	}

}

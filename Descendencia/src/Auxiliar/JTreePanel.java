package Auxiliar;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import modelo.Persona;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import cu.edu.cujae.ceis.tree.general.GeneralTree;

// Esta clase se utiliza para graficar un �rbol general
public class JTreePanel extends JPanel
{
	private static final long serialVersionUID = 6916725142143854275L;
	
	private GeneralTree<Persona> tree;		// El objeto �rbol original
    private LinkedList<JTreeNode> nodes;	// Lista de nodos a dibujar
    private JTreeNode selectedNode = null;	// Apunta al nodo seleccionado
    private int maxNodeWidth;				// Ancho de los nodos    
    // El entero nextNodeX se utiliza para decirle al panel en que posici�n colocar
    // cada nodo en el m�todo addNode()
    private int nextNodeX;
    
    // Men� y elementos del men� desplegado al hacer clic derecho en un nodo 
    private JPopupMenu popupMenu = null;
    private JMenuItem insertMI = null;
    private JMenuItem modifyMI = null;
    private JMenuItem removeMI = null;

    public JTreePanel(GeneralTree<Persona> tree)
    {
        this.tree = tree;
        // GridBagLayout permitir� posicionar los nodos dentro del Panel sin tener
        // que calcular su posici�n manualmente. Las reglas seguidas ser�n:
        // - Los nodos quedar�n posicionados como si fuera en una tabla.
        // - Cada nodo hoja ocupa una columna distinta, nunca habr� dos hojas en
        // la misma columna.
        // - Cada nivel del �rbol ser� una fila distinta, de manera que la ra�z quede
        // en la fila superior y las hojas en las filas inferiores.
        // - Un padre siempre aparecer� encima del centro de toda su descendencia.
        setLayout(new GridBagLayout());	
        nodes = new LinkedList<JTreeNode>();
        update();
        
        // Limpia la selecci�n al presionar con el mouse fuera de todos los nodos
       addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				clearSelection();
			}
		});
       
       setBackground(Color.white);
    }    

	public void update(){
		
    	this.removeAll(); // Elimina todos los componentes
		nodes.clear(); // Vac�a la lista de nodos
		selectedNode = null; // Deselecciona
		maxNodeWidth = 0;    // Reinicia el ancho de nodo   	
    	nextNodeX = 0;		// Reinicia la posici�n. El primer nodo ser� colocado
    						// con x=0
    	
    	// Agregar la ra�z del �rbol, y a partir de ah� agregar el resto del �rbol
    	// recursivamente
		if(!tree.isEmpty())
        	addNode((BinaryTreeNode<Persona>) tree.getRoot(), null);
		
		this.validate();	// Necesario para refrescar la visual
		this.setPreferredSize(null);	// Reinicia el tama�o del panel
	
		// Recorrer todos los nodos del panel agreg�ndoles un listener para
		// poder seleccionarlos, y otro para desplegar un men� al hacerles clic
		// derecho.
		Iterator<JTreeNode> iter = nodes.iterator();
    	while(iter.hasNext()){
    		final JTreeNode tn = iter.next();
    		tn.addMouseListener(new java.awt.event.MouseAdapter() {
    			public void mousePressed(java.awt.event.MouseEvent e) {
    				if(selectedNode!=null)
    					selectedNode.setSelected(false);
    				
    				tn.setSelected(true);
    				selectedNode = tn;
    				JTreePanel.this.repaint();
    				if(e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3)
						getPopupMenu().show(selectedNode, e.getX(), e.getY());
    				
    				// The next lines are for debug purpose only
    				//GridBagConstraints gridBagConstraints = ((GridBagLayout) getLayout()).getConstraints(tn);
    				//System.out.println(gridBagConstraints.gridx+" "+gridBagConstraints.gridy);
    				//System.out.println(gridBagConstraints.gridwidth);
    			}
    		});
    		tn.setPreferredSize(new Dimension(maxNodeWidth, tn.getPreferredSize().height));
    	}    	
    	this.setPreferredSize(new Dimension(maxNodeWidth*(nextNodeX+2),getPreferredSize().height*3));
    }
	
	public void clearSelection() {
    	if(selectedNode!=null)
			selectedNode.setSelected(false);
		selectedNode = null;
		JTreePanel.this.repaint();		
	}
    
    public BinaryTreeNode<Persona> getSelectedInfo()
    {
    	// Devuelve el nodo seleccionado
    	BinaryTreeNode<Persona>info = null;
    	if(selectedNode!=null)
    		info = selectedNode.getInfo();
    	
    	return info;
    }

    private void addNode(BinaryTreeNode<Persona> node, JTreeNode parent) {
    	// Este m�todo agrega un nodo al panel, y luego agrega todos sus hijos �
    	// recursivamente.
    	
    	// Crea el JTreeNode correspondiente. El JTreeNode es el objeto visual en
    	// s�, el que ser� mostrado en la interfaz gr�fica.
    	JTreeNode treeNode = new JTreeNode(node, parent);
    	
    	// GridBagConstraints sirve para especificar en que posici�n del GridBagLayout
    	// aparecer� el nodo. Recordar que este layout representa, esencialmente, una
    	// tabla.
    	GridBagConstraints gridBagConstraints = new GridBagConstraints();
    	// El objeto necesita "peso" para poder posicionarse en la posici�n correcta
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		// gridwidth indica cu�l es el ancho en columnas del componente. No deforma
		// al componente en s�, pero lo obliga a aparecer en el centro de dichas
		// columnas.
		// Ejemplo 1: Todos los objetos con ancho=1
		// 		Obj1
		//		Obj2	Obj3	Obj4
		// Ejemplo 2: Obj1 con ancho=2, los dem�s con ancho=1
		// 		    Obj1
		//		Obj2	Obj3	Obj4
		// Ejemplo 3: Obj1 con ancho=3, los dem�s con ancho=1
		// 				Obj1
		//		Obj2	Obj3	Obj4
		gridBagConstraints.gridwidth = 1;
		
    	// Si se trata de la ra�z, el nodo se dibuja en (0,0)
		if(parent==null){
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
    	}else{
    		// Si no se trata de la ra�z, obtener GridBagConstraints del padre
    		GridBagLayout layout = (GridBagLayout) getLayout();
			GridBagConstraints parentConstraints = layout.getConstraints(parent);			
			
			// La fila del nodo actual ser� la inmediata inferior a la del padre 
			int level = parentConstraints.gridy+1;
			
			// El primer hijo se colocar� en la misma columna que el padre, pero a 
			// partir del segundo, se colocar�n en filas siguientes
			boolean increaseX = false;
			if(parent.childrenCount>0){
				nextNodeX++;
				increaseX = true;
			}
			gridBagConstraints.gridx = nextNodeX;
			gridBagConstraints.gridy = level;
			
			parent.childrenCount++;
			// Si se agreg� una nueva columna se debe actualizar el ancho (explicado 
			// arriba) de todos los ancestros, para garantizar que se cumpla la regla
			// de que todo nodo est� centrado encima de su descendencia
			if(increaseX){
				JTreeNode tn = treeNode;
				while(tn.getParentNode()!=null){
					tn = tn.getParentNode();
					parentConstraints = layout.getConstraints(tn);
					parentConstraints.gridwidth++;
					layout.setConstraints(tn, parentConstraints);
				}
			}
    	}
    	nodes.addFirst(treeNode);	// Agregar a la lista
		this.add(treeNode, gridBagConstraints);	// Agregar al panel
		
		// Obtener el ancho del mayor nodo
		if(maxNodeWidth<treeNode.getPreferredSize().width)
			maxNodeWidth=treeNode.getPreferredSize().width;
    	
		// Llamar al mismo m�todo recursivamente
		List<BinaryTreeNode<Persona>> hijos = tree.getSons(node);
		Iterator<BinaryTreeNode<Persona>> iter = hijos.iterator();
		while(iter.hasNext())	
			addNode(iter.next(), treeNode);
	}


	public void paintComponent(Graphics g)
    {
		// Este m�todo se llama al crearse el Panel, y cada vez que este sufre cambios
		// (por ejemplo, de tama�o)
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D)g);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	// Selected node highlighting
        // Destaca el nodo seleccionado, dibujando dos rect�ngulos a su alrededor
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
    	
        // Lines
        // Dibuja todas las l�neas del �rbol: recorre la lista de nodos y, para cada
        // nodo, traza una l�nea hasta su padre
        Iterator<JTreeNode> iter = nodes.iterator();
    	while(iter.hasNext()){
    		JTreeNode tn = iter.next();
    		if(tn.getParentNode()!=null){
        		JTreeNode ptn = tn.getParentNode();
        		int x1 = tn.getX()+tn.getWidth()/2;
        		int y1 = tn.getY();
        		int x2 = ptn.getX()+ptn.getWidth()/2;
        		int y2 = ptn.getY()+ptn.getHeight();
        		
        		GradientPaint gp = new GradientPaint(x1, y1, tn.color().darker().darker(),
                        x2, y2, ptn.color().darker().darker()); 
                g2d.setPaint(gp);
        		g2d.drawLine(x1, y1, x2, y2);
        	}
    	}  
    }
	
	
	private JPopupMenu getPopupMenu() {
		if (popupMenu == null) {
			popupMenu = new JPopupMenu();
			popupMenu.add(getInsertMI());
			popupMenu.add(getModifyMI());
			popupMenu.add(getRemoveMI());
		}
		return popupMenu;
	}

	public JMenuItem getInsertMI() {
		if(insertMI == null){
			insertMI = new JMenuItem("Insertar hijo");
		}
		return insertMI;
	}

	public JMenuItem getModifyMI() {
		if(modifyMI == null){
			modifyMI = new JMenuItem("Modificar");
		}
		return modifyMI;
	}

	public JMenuItem getRemoveMI() {
		if(removeMI == null){
			removeMI = new JMenuItem("Eliminar");
		}
		return removeMI;
	}

}

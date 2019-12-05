package Auxiliar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import modelo.Persona;


import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;

// Esta clase se utiliza para representar un nodo de JTreePanel
public class JTreeNode extends JLabel{
	private static final long serialVersionUID = 8077460567674749253L;
	private BinaryTreeNode<Persona> node;	// BinaryTreeNode del árbol original
	int childrenCount;		// Cantidad de hijos de este nodo
	private JTreeNode parent;	// Referencia al padre del nodo
	private boolean selected;	// Indica si el nodo está seleccionado o no
	
	public JTreeNode(BinaryTreeNode<Persona> node, JTreeNode parent){
		this.node = node;
		this.parent = parent;
		childrenCount = 0;
		setSelected(false);
		setText(node.getInfo().getNombre());
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setPreferredSize(new Dimension(getPreferredSize().width+8, getPreferredSize().height+6));
	}
	
	public BinaryTreeNode<Persona> getInfo(){
		return node;
	}
	public void setParent(JTreeNode parent) {
		this.parent = parent;
	}
	public JTreeNode getParentNode() {
		return parent;
	}
	public void paintComponent(Graphics g)
    {
        // Este método es llamado por el contenedor, JTreePanel
        Graphics2D g2d = ((Graphics2D)g);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	       
        GradientPaint gp;
        // En función de si está seleccionado o no, utiliza un gradiente distinto
        if(!selected)
        	gp = new GradientPaint(0f, 0f, color(), 0f, 10f, Color.white,true);
        else
        	gp = new GradientPaint(0f, 0f, Color.white, 0f, 10f, color(),true);
        g2d.setPaint(gp);
        
        // Dibuja el rectángulo redondeado
        g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
        
        // Elige un color más oscuro y dibuja los bordes del rectángulo
        g2d.setColor(color().darker());
        g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
        
        // Llama al paintComponent de JLabel, el cual dibuja el texto del label
        // con la fuente apropiada, especificada en el constructor
        super.paintComponent(g);
    }
	
	public Color color(){
		Color color;
        if(node.getInfo().getSexo().equals(Sexo.FEMENINO))
        	color = new Color(237,172,172);	// Color rosado
        else
        	color = new Color(172,195,237); // Color azul
        return color;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
}

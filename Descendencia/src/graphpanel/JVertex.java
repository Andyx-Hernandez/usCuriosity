package graphpanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import modelo.Vivienda;
import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;

public class JVertex extends JLabel{
	private static final long serialVersionUID = 8077460567674749253L;
	private BinaryTreeNode<Vertex> node;
	int childrenCount;
	private JVertex parent;
	private JVertex previousSibling;
	private boolean selected;
	
	public JVertex(BinaryTreeNode<Vertex> node, JVertex parent){
		this.node = node;
		this.parent = parent;
		childrenCount = 0;
		setSelected(false);
		//setText(((Vivienda) node.getInfo().getInfo()).getDireccion());
		Vivienda v = (Vivienda) node.getInfo().getInfo();
		
		String toolTip = "<html>";
		if(!v.isDisponible())
			toolTip += "Casa de "+v.getPropietario().getNombre();
		else
			toolTip += "Casa vacía";
		
		toolTip += "<br>Dirección: "+v.getDireccion()+"<br>Ciudad: "+v.getCiudad()+"</html>";		
		setToolTipText(toolTip);
		
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(new Font("Dialog", Font.PLAIN, 12));
		//setPreferredSize(new Dimension(getPreferredSize().width+8, getPreferredSize().height+6));
		setIcon(new ImageIcon(JVertex.class.getResource("/res/b_home.png")));
		setSize(new Dimension(20, 20));
	}
	
	public Vertex getInfo(){
		return node.getInfo();
	}
	public void setParent(JVertex parent) {
		this.parent = parent;
	}
	public JVertex getParentNode() {
		return parent;
	}
	
	public void setPreviousSibling(JVertex previousSibling) {
		this.previousSibling = previousSibling;
	}

	public JVertex getPreviousSibling() {
		return previousSibling;
	}

	public void paintComponent(Graphics g)
    {
        
        Graphics2D g2d = ((Graphics2D)g);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	       
        GradientPaint gp;
        if(!selected)
        	gp = new GradientPaint(0f, 0f, color(), 0f, 10f, Color.white,true);
        else
        	gp = new GradientPaint(0f, 0f, Color.white, 0f, 10f, color(),true);
        g2d.setPaint(gp);
        g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
        
        g2d.setColor(color().darker());
        g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
        
        super.paintComponent(g);
    }
	
	public Color color(){
		Color color;
		if(((Vivienda)node.getInfo().getInfo()).isDisponible())
        	color = new Color(150,150,150);
        else
        	color = new Color(172,195,237);
        return color;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
}

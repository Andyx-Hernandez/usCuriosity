package graphpanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.HashMap;

import javax.swing.JPanel;

public class CirclePanel extends JPanel {
	// Un panel en que los componentes serán mostrados como si estuvieran ubicados
	// en un círculo
	
	private static final long serialVersionUID = 1L;
	private int crowns;		// cantidad de coronas en que se divide el panel
	private int sectors;	// cantidad de sectores en que se divide el panel
	
	// Coordenadas del centro del panel y radio del círculo
	private int centerX, centerY, maxRadius;
	
	// Mapa que contiene cada componente asociado a la posición que ocupará
	private HashMap<Component, PolarPosition> positions;
	private boolean drawLines;	// Especifica si dibujar los límites de sectores y
								// coronas
	
	public CirclePanel(int crowns, int sectors){
		super();
		setLayout(null);
		positions = new HashMap<Component, PolarPosition>();
		this.crowns = crowns;
		this.sectors = sectors;
		drawLines = false;
	}
	public CirclePanel(){
		super();
		setLayout(null);
		positions = new HashMap<Component, PolarPosition>();
		this.crowns = 1;
		this.sectors = 1;
		drawLines = false;
	}
	
	public void addInPosition(Component c, int crown, int sector){
		// Agrega un componente en la posición especificada
		this.add(c);
		positions.put(c, new PolarPosition(crown, sector));
	}
	
	public void addInPosition(Component c, int crown, int sector, int width){
		this.add(c);
		positions.put(c, new PolarPosition(crown, sector, width));
	}
	
	private void updateSize(){
		// Calcula la posición del centro del círculo basado en el tamaño del panel.
		// Luego determina el radio del cículo, también basándose en el espacio
		// disponible
		int prefWidth = getSize().width;
		int prefHeight = getSize().height;
		centerX = prefWidth/2;
		centerY = prefHeight/2;
		maxRadius = (prefWidth<prefHeight?centerX:centerY) - 15;	
		updateLocations();
	}
	
	private void updateLocations(){
		for(Component c: getComponents())
			calculateLocation(c);
	}

	private int crownRadius(){
		return maxRadius / crowns;
	}
	private double sectorAngle(){
		return Math.PI*2 / sectors;
	}
	
	public void paintComponent(Graphics g)
    {
		updateSize();
		super.paintComponent(g);
		// Dibuja las líneas en caso de ser requerido
		if(drawLines){
			Graphics2D g2d = ((Graphics2D)g);
			g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(new Color(230,230,230));
			// Crowns        
			// Dibuja los límites de cada corona como círculos concéntricos
			for(int i=1; i<=crowns; i++)
				drawCircle(g2d, crownRadius()*i);

			// Sectors
			// Dibuja los límites de sector como radios de la circunferencia
			if(sectors>1){
				AffineTransform aT = g2d.getTransform();
				double theta = sectorAngle();
				AffineTransform rotate = 
					AffineTransform.getRotateInstance(theta, centerX, centerY) ;
				for(int i=1; i<=sectors; i++){
					g2d.draw(new Line2D.Float(centerX, centerY, centerX, centerY+maxRadius));
					g2d.transform(rotate);      	
				}  
				g2d.setTransform(aT);
			}
		}
    }
	
	public void drawCircle(Graphics2D g2d, int radius){
		// Dibuja un círculo con centro en (centerX, centerY), con el radio 
		// especificado
		int x = centerX - radius;
		int y = centerY - radius;
		int diameter = 2*radius;
		g2d.drawOval(x, y, diameter, diameter);
	}
	
	private void calculateLocation(Component c) {
		PolarPosition pos = positions.get(c);
		if(pos!=null)
			calculateLocation(c, pos.getCrown(), pos.getSector(), pos.getWidth());
	}
	
	public void calculateLocation(Component c, int crown, int sector, int width){
		// Calcula la posición, en x e y, de un componente dados la corona y sector
		// donde se encuentra, así como su ancho (cantidad de sectores que ocupa
		
		// Comienza tomando las coordenadas del centro
		int x = centerX;
		int y = centerY;
		
		// Calcula el desplazamiento correspondiente a la corona en que se encuentra
		int yDistance=crown*crownRadius();	
		int xDistance=yDistance;
		
		// Actualiza el desplazamiento teniendo en cuenta el sector, utilizando
		// trigonometría para determinar el correspondiente desplazamiento en x e y,
		// a partir del ángulo en que se encontraría
		double theta = sector*sectorAngle();
		if(width>1)
			theta = (2*theta + (width-1)*sectorAngle()) / 2; 
		yDistance*= Math.cos(theta);
		xDistance*= Math.sin(theta);
		
		// Actualiza la posición utilizando el desplazamiento calculado
		y+=yDistance;
		x-=xDistance;
		
		// Resta la mitad del ancho y el alto a la posición, para que el objeto
		// aparezca centrado
		x-=c.getWidth()/2;
		y-=c.getHeight()/2;
		
		// Actualiza la posición real del componente, luego de haber finalizado los
		// cálculos
		c.setLocation(x, y);	
	}
	

	public int getCrowns() {
		return crowns;
	}
	public void setCrowns(int crowns) {
		this.crowns = crowns;
	}
	public int getSectors() {
		return sectors;
	}
	public void setSectors(int sectors) {
		this.sectors = sectors;
	}
}

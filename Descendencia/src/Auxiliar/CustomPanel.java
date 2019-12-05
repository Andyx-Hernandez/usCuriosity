package Auxiliar;


import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class CustomPanel extends JPanel{

	private URL url;
    private Image image;
    
    @Override
    public void paint(Graphics g){
    
    	g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        super.setOpaque(false);
    	super.paint(g);
       
    }
    
    public void setURL(String direccion)
    {
    	this.url = getClass().getResource(direccion);
    	image = new ImageIcon(url).getImage();
    }
}

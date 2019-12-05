package Auxiliar;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class Validaciones {

	public  static KeyListener soloLetras(){

		KeyListener key = new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e)
			{
				JTextField text = (JTextField) e.getSource();

				char c = e.getKeyChar();
				if(Character.isDigit(c))
				{
					e.consume();

				}
				else if(e.getKeyChar() == ' ' && text.getText().length() == 0)
					e.consume();

			}
		};

		return key;
	}

	public static KeyListener soloNumerosId()
	{
		KeyListener key = new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				JTextField text = (JTextField) e.getSource();
				if(text.getText().length() >= 11)
				{e.consume();}
				else if(e.getKeyChar() == ' ' && text.getText().length() == 0)
					e.consume();
				else{
					char c = e.getKeyChar();
					if(!(Character.isDigit(c)))
					{
						e.consume();
					}
				}

			}
		};
          return key;
	}
}

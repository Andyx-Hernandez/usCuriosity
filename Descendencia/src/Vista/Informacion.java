package Vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.io.File;

import javax.swing.JDesktopPane;

import Auxiliar.isNotATreeInformation;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import modelo.Controladora;
import modelo.Persona;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class Informacion extends JFrame {

	private JPanel contentPane;
	private JTextPane txtpnParaRealizarLa;
	private JButton btnNewButton;
	private JButton btnSalir;
	private JButton btnComenzarAsignacion;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Informacion frame = new Informacion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Informacion() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Informacion.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif")));
		setTitle("Informaci\u00F3n");
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 552, 266);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel_1 = new JLabel("\r\n");
		lblNewLabel_1.setIcon(new ImageIcon(Informacion.class.getResource("/Imagenes/More Info_96px.png")));
		lblNewLabel_1.setBounds(0, 13, 100, 87);
		contentPane.add(lblNewLabel_1);
		
		txtpnParaRealizarLa = new JTextPane();
		txtpnParaRealizarLa.setBackground(Color.WHITE);
		txtpnParaRealizarLa.setBounds(99, 13, 437, 140);
		contentPane.add(txtpnParaRealizarLa);
		txtpnParaRealizarLa.setEditable(false);
		txtpnParaRealizarLa.setText("Para realizar la asignaci\u00F3n de una vivienda, la persona original debe especificar la ciudad en la que desea vivir. A partir de esta informaci\u00F3n, una vez asignada una vivienda a la persona original, se deben asignar viviendas a su descendencia, teniendo en cuenta que la vivienda est\u00E9 disponible y sea la m\u00E1s cercana a la de su padre. \r\n\r\n\r\nSi todavia no has creado un arbol de descendencia puedes construirlo en el men\u00FA principal o puedes usar los arboles de muestra");
		
		lblNewLabel = new JLabel("No existe un arbol de descendencia...Cree una nueva descendencia");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		lblNewLabel.setBounds(40, 146, 445, 32);
		contentPane.add(lblNewLabel);
		
		btnNewButton = new JButton("Cargar Arbol");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					JFileChooser chooser = new JFileChooser();
					int returnVal = chooser.showOpenDialog(getContentPane());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();

						Controladora.getInstance().cargarArbolExterno(file);
						btnComenzarAsignacion.setEnabled(true);
						
						BinaryTreeNode<Persona> original = (BinaryTreeNode<Persona>) Controladora.getInstance().getLineaDescendencia().getRoot();
						lblNewLabel.setText("Existe un arbol de descendencia perteneciente a:  " + original.getInfo().getNombre());
						lblNewLabel.setForeground(Color.GREEN);
						lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
						lblNewLabel.setBounds(20, 163, 445, 32);
					
						
					}} catch (isNotATreeInformation e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(Informacion.this, e1.getMessage());
					}
			}
		});
		btnNewButton.setBounds(231, 196, 130, 23);
		contentPane.add(btnNewButton);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnSalir.setBounds(396, 196, 89, 23);
		contentPane.add(btnSalir);
		
		btnComenzarAsignacion = new JButton("Comenzar Asignacion");
		btnComenzarAsignacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verAsignacion frame = new verAsignacion();
				frame.setVisible(true);
				dispose();
			}
		});
		btnComenzarAsignacion.setEnabled(true);
		if(Controladora.getInstance().getLineaDescendencia().isEmpty())
			btnComenzarAsignacion.setEnabled(false);
		else
		{
			BinaryTreeNode<Persona> original = (BinaryTreeNode<Persona>) Controladora.getInstance().getLineaDescendencia().getRoot();
			lblNewLabel.setText("Existe un arbol de descendencia perteneciente a:  " + original.getInfo().getNombre());
			lblNewLabel.setForeground(Color.GREEN);
			lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
			lblNewLabel.setBounds(20, 163, 445, 32);
		}
			
		btnComenzarAsignacion.setBounds(30, 196, 175, 23);
		contentPane.add(btnComenzarAsignacion);
			
		
	}
}

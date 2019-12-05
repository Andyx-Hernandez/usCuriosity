package Vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;

import javax.swing.UIManager;
import javax.swing.JRadioButton;

import cu.edu.cujae.ceis.tree.TreeNode;
import modelo.Controladora;
import modelo.Persona;
import Auxiliar.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JProgressBar;

import java.awt.Toolkit;

public class geneologyNew extends JFrame {

	private JPanel contentPane;
	private JButton btnComenzar;
	private JLabel label;
	private JLabel lblApellidos;
	private JPanel panel;
	private JTextField nombre;
	private JTextField apellidos;
	private JTextField id;
	private JLabel lblCarnetDeIdentidad;
	private JLabel lblSexo;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnFemenino;
	private ButtonGroup btnGroup = new ButtonGroup();
	private CustomPanel panel_1;
	private JLabel lblNewLabel;

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
					geneologyNew frame = new geneologyNew();
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
	public geneologyNew() {
		setTitle("Sobre Ti");
		setIconImage(Toolkit.getDefaultToolkit().getImage(geneologyNew.class.getResource("/Imagenes/Genealogy_50px.png")));
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		setLocationByPlatform(true);
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 465, 333);
		contentPane = new CustomPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnComenzar = new JButton("Este soy Yo");
		btnComenzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			String nombreP = nombre.getText();
			String apellidosP = apellidos.getText();
			String idP = id.getText();
			Sexo sexo = null;
			
			if(rdbtnFemenino.isSelected())
				sexo = Sexo.FEMENINO;
			else
				sexo = Sexo.MASCULINO;
			
			if(nombreP.isEmpty() || apellidosP.isEmpty() || idP.isEmpty() || !rdbtnFemenino.isSelected() && !rdbtnNewRadioButton.isSelected())
				JOptionPane.showMessageDialog(geneologyNew.this, "Llene los espacio en blanco o Seleccione su sexo");
			else{
			Persona persona = new Persona(idP, nombreP, apellidosP, sexo);
			
			Controladora.getInstance().setCabezaFamilia(persona);
			dispose();
				FamilyTree view = new FamilyTree();
				view.setVisible(true);
				
				dispose();
			}
				
			}
		});
		btnComenzar.setBounds(49, 263, 241, 23);
		contentPane.add(btnComenzar);
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(12, 12, 293, 152);
		contentPane.add(panel);
		panel.setLayout(null);
		
		label = new JLabel("Nombre");
		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		label.setBounds(82, 38, 85, 14);
		panel.add(label);
		
		lblApellidos = new JLabel("Apellidos");
		lblApellidos.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		lblApellidos.setBounds(69, 67, 68, 25);
		panel.add(lblApellidos);
		
		nombre = new JTextField();
		nombre.setToolTipText("Por favor no dejar vacio");
		nombre.addKeyListener(Validaciones.soloLetras());
		
		
	
		nombre.setBounds(148, 36, 111, 20);
		panel.add(nombre);
		nombre.setColumns(10);
		
		apellidos = new JTextField();
		apellidos.addKeyListener(Validaciones.soloLetras());
		apellidos.addKeyListener(keyComponent);
		apellidos.setBounds(148, 67, 111, 20);
		panel.add(apellidos);
		apellidos.setColumns(10);
		
		id = new JTextField();
		id.setBounds(148, 105, 111, 20);
		panel.add(id);
		id.setColumns(10);
		id.addKeyListener(Validaciones.soloNumerosId());
		
		lblCarnetDeIdentidad = new JLabel("Carnet de Identidad");
		lblCarnetDeIdentidad.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		lblCarnetDeIdentidad.setBounds(12, 111, 137, 14);
		panel.add(lblCarnetDeIdentidad);
		
		lblNewLabel = new JLabel("Nos gustar\u00EDa saber quien eres");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		lblNewLabel.setBounds(12, 1, 230, 25);
		panel.add(lblNewLabel);
		
		lblSexo = new JLabel("Sexo");
		lblSexo.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		lblSexo.setBounds(135, 188, 85, 14);
		contentPane.add(lblSexo);
		
		rdbtnNewRadioButton = new JRadioButton("Masculino");
		rdbtnNewRadioButton.setOpaque(false);
		rdbtnNewRadioButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		rdbtnNewRadioButton.setBounds(49, 210, 121, 24);
		
			
		contentPane.add(rdbtnNewRadioButton);
		
		rdbtnFemenino = new JRadioButton("Femenino");
		rdbtnFemenino.setOpaque(false);
		rdbtnFemenino.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		rdbtnFemenino.setBounds(184, 210, 121, 24);
		contentPane.add(rdbtnFemenino);
		
		btnGroup.add(rdbtnFemenino);
		btnGroup.add(rdbtnNewRadioButton);
		
		panel_1 = new CustomPanel();
		panel_1.setURL("/Imagenes/family-1150995_640.jpg");
		panel_1.setBounds(317, -1, 149, 334);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
	}
	
	private KeyListener keyComponent = new KeyListener() {
		
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
}

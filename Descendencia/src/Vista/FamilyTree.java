package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;







import modelo.Controladora;
import modelo.Persona;
import Auxiliar.JTreePanel;
import Auxiliar.Sexo;
import Auxiliar.Validaciones;
import Auxiliar.isNotATreeInformation;
import Auxiliar.tips;
import cu.edu.cujae.ceis.sectree.FamilySecTree;
import cu.edu.cujae.ceis.sectree.PreOrderSecTree;
import cu.edu.cujae.ceis.sectree.SecTree;
import cu.edu.cujae.ceis.sectree.SecTree.LoadOpResult;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;

import java.awt.Cursor;
import java.io.File;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;


public class FamilyTree extends JFrame {
	private SecTree<Persona> sectree;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblSexo;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFemenino;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel labeltips;
	private JScrollPane scrollPane;
	//private JTreePanel jTreePanel = new JTreePanel(Controladora.getInstance().getLineaDescendencia());
	private JTreePanel jTreePanel;
	private BinaryTreeNode<Persona> selectedNode =null;;
	private JTextField textField_3;
	private JButton btnEliminar;
	private JButton btnModificar;
	private JButton btnInsertar;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FamilyTree frame = new FamilyTree();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public FamilyTree() {
		setTitle("Editor del Arbol");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FamilyTree.class.getResource("/Imagenes/Genealogy_50px.png")));
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 849, 500);
		setVisible(true);
		getContentPane().setLayout(null);
		JInternalFrame internalFrame = new JInternalFrame("Datos Nodo");
		internalFrame.setToolTipText("Para recoger los datos de la nueva insercion");
		internalFrame.setBounds(480, 6, 351, 415);
		internalFrame.setClosable(false);
		getContentPane().add(internalFrame);
		internalFrame.getContentPane().setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 347, 395);
		internalFrame.getContentPane().add(desktopPane);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
				eliminarSeleccionado();
				cleanComponents();
				selectedNode = null;
    		}
		});
		btnEliminar.setBounds(243, 332, 85, 27);
		desktopPane.add(btnEliminar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setEnabled(false);
		btnModificar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if(textField.getText().isEmpty() || textField_1.getText().isEmpty() || textField_3.getText().isEmpty())
					JOptionPane.showMessageDialog(FamilyTree.this, "Por favor, rellene los espacios en blanco");
    			else{
    			String nombreP = textField.getText();
				String apellidosP = textField_3.getText();
				String idP = textField_1.getText();
				Sexo sexo = null;
				
				if(rdbtnFemenino.isSelected())
					sexo = Sexo.FEMENINO;
				else
					sexo = Sexo.MASCULINO;
								
				Persona persona = new Persona(idP, nombreP, apellidosP, sexo);
				if(selectedNode != null){
					selectedNode.setInfo(persona);
					actualizarArbol();
				}
				
				cleanComponents();
    		}
    		}
		});
		btnModificar.setBounds(136, 332, 95, 27);
		desktopPane.add(btnModificar);
		
		btnInsertar = new JButton("Insertar Hijo");
		btnInsertar.setEnabled(false);
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textField.getText().isEmpty() || textField_1.getText().isEmpty() || textField_3.getText().isEmpty())
					JOptionPane.showMessageDialog(FamilyTree.this, "Por favor, rellene los espacios en blanco");
				else{
				String nombreP = textField.getText();
				String apellidosP = textField_3.getText();
				String idP = textField_1.getText();
				Sexo sexo = null;
				
				if(rdbtnFemenino.isSelected())
					sexo = Sexo.FEMENINO;
				else
					sexo = Sexo.MASCULINO;
				
				
				//Persona persona = new Persona("", "", "", null);
				Persona persona = new Persona(idP, nombreP, apellidosP, sexo);
				Controladora.getInstance().getLineaDescendencia().insertNode(new BinaryTreeNode<Persona>(persona), selectedNode);
				actualizarArbol();
				cleanComponents();
				}
			}
		});
		btnInsertar.setBounds(6, 332, 118, 27);
		desktopPane.add(btnInsertar);
		
		JLabel lblNewLabel = new JLabel("Datos");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblNewLabel.setBounds(119, 6, 89, 27);
		desktopPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(FamilyTree.class.getResource("/Imagenes/linea.png")));
		lblNewLabel_1.setBounds(53, 34, 283, 16);
		desktopPane.add(lblNewLabel_1);
		
		JLabel lblCarnetDeIdentidad = new JLabel("Carnet de Identidad");
		lblCarnetDeIdentidad.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblCarnetDeIdentidad.setBounds(6, 154, 162, 40);
		desktopPane.add(lblCarnetDeIdentidad);
		
		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		lblApellidos.setBounds(53, 104, 77, 27);
		desktopPane.add(lblApellidos);
		
		textField = new JTextField();
		textField.addKeyListener(Validaciones.soloLetras());
			
		textField.setBounds(173, 60, 129, 20);
		desktopPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(173, 166, 129, 20);
		textField_1.addKeyListener(Validaciones.soloNumerosId());
		desktopPane.add(textField_1);
		
		lblSexo = new JLabel("SEXO");
		lblSexo.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		lblSexo.setBounds(136, 230, 77, 16);
		desktopPane.add(lblSexo);
		
		rdbtnMasculino = new JRadioButton("Masculino");
		buttonGroup.add(rdbtnMasculino);
		rdbtnMasculino.setBounds(40, 254, 111, 24);
		rdbtnMasculino.setBackground(Color.WHITE);
		buttonGroup.add(rdbtnMasculino);
		desktopPane.add(rdbtnMasculino);
		
		rdbtnFemenino = new JRadioButton("Femenino");
		rdbtnFemenino.setBounds(191, 254, 111, 24);
		desktopPane.add(rdbtnFemenino);
		buttonGroup.add(rdbtnFemenino);
		
		JLabel label = new JLabel("Nombre");
		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		label.setBounds(53, 60, 77, 16);
		desktopPane.add(label);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(173, 109, 129, 20);
		textField_3.addKeyListener(Validaciones.soloLetras());
		desktopPane.add(textField_3);
		
		JLabel lblNewLabel_3 = new JLabel("My Family-Tree");
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
		lblNewLabel_3.setIcon(new ImageIcon(FamilyTree.class.getResource("/Imagenes/Old Person_48px.png")));
		lblNewLabel_3.setBounds(60, 6, 261, 60);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(FamilyTree.class.getResource("/Imagenes/Java_48px.png")));
		lblNewLabel_2.setBounds(12, 425, 48, 36);
		getContentPane().add(lblNewLabel_2);
		
		labeltips = new JLabel(tips.getTips().get(0));
		labeltips.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		labeltips.setBounds(71, 435, 760, 23);
		getContentPane().add(labeltips);
		
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setBounds(12, 77, 457, 345);
		scrollPane.setViewportView(getJTreePanel());
		getContentPane().add(scrollPane);
		
		btnNewButton = new JButton("");
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setMargin(new Insets(0,0,0,0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(getContentPane());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					Controladora.getInstance().guardarArbol(file);
				}
				} catch (isNotATreeInformation e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(FamilyTree.this, e1.getMessage());
				}
				
				dispose();
			}	
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setFocusPainted(false);
		btnNewButton.setRolloverIcon(new ImageIcon(FamilyTree.class.getResource("/Imagenes/Oak Tree_48px.png")));
		btnNewButton.setOpaque(false);
		btnNewButton.setIcon(new ImageIcon(FamilyTree.class.getResource("/Imagenes/Save as_48px.png")));
		btnNewButton.setBorder(null);
		btnNewButton.setBounds(429, 29, 39, 48);
		getContentPane().add(btnNewButton);
		
		button = new JButton("");
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Controladora.getInstance().getLineaDescendencia().setRoot(null);
				dispose();
			}
		});
		button.setRolloverIcon(new ImageIcon(FamilyTree.class.getResource("/Imagenes/Close Window_48px.png")));
		button.setIcon(new ImageIcon(FamilyTree.class.getResource("/Imagenes/Close Program_48px.png")));
		button.setOpaque(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorder(null);
		button.setBounds(368, 29, 48, 48);
		getContentPane().add(button);
		internalFrame.setVisible(true);
		
	

		generartips();
	
	}					
	
	private void actualizarArbol() {
		getJTreePanel().update();
		getJTreePanel().repaint();
		/*scrollPane.repaint();
		getContentPane().repaint();*/
		for(Component c: jTreePanel.getComponents())
    		c.addMouseListener(panelClickListener);	 
	}


	public void generartips()
	{
		new Thread(){
			
			public void run()
			{
				try{
				while(true){
					int ran = (int)(Math.random() * 4);
					
			
				labeltips.setText(tips.getTips().get(ran));
				
				
					sleep(5000);
				}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return;
				}
			
			
		}.start();
	}
	
	private JTreePanel getJTreePanel(){
		if (jTreePanel == null) {
			jTreePanel = new JTreePanel(Controladora.getInstance().getLineaDescendencia());
			jTreePanel.addMouseListener(panelClickListener);
	    	for(Component c: jTreePanel.getComponents())
	    		c.addMouseListener(panelClickListener);	
	    	
	    	jTreePanel.getInsertMI().addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			// Insertar hijo
					
					btnModificar.setEnabled(false);
					btnEliminar.setEnabled(false);
					btnInsertar.setEnabled(true);
					
					//Clean Componentes
					cleanComponents();
					
	    		}
	    	});
	    	jTreePanel.getModifyMI().addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
					// Modificar
					
					btnModificar.setEnabled(true);
					btnEliminar.setEnabled(true);
					btnInsertar.setEnabled(false);
				
				}	    		
	    	});
	    	jTreePanel.getRemoveMI().addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
					// Eliminar
					eliminarSeleccionado();
					cleanComponents();
				}	    		
	    	});
		}
		return jTreePanel;
	}
	
	private void eliminarSeleccionado() {
		if(selectedNode != null){
			Controladora.getInstance().eliminar(selectedNode);
			selectedNode = null;
			jTreePanel.clearSelection();
			actualizarArbol();			
			btnModificar.setEnabled(false);
			btnEliminar.setEnabled(false);
			btnInsertar.setEnabled(Controladora.getInstance().getLineaDescendencia().isEmpty());
		}
	}

	private MouseListener panelClickListener = new java.awt.event.MouseAdapter() {
		public void mousePressed(java.awt.event.MouseEvent e) {
			String nombre, ci,apellidos = null;
			Sexo sexo = null;
			
			if(jTreePanel.getSelectedInfo()==null){
				nombre = "Vacío";
				ci = "Vacío";
				apellidos="Vacío";
				selectedNode = null;
				btnModificar.setEnabled(false);
				btnEliminar.setEnabled(false);
				btnInsertar.setEnabled(Controladora.getInstance().getLineaDescendencia().isEmpty());
			}
			else{
				BinaryTreeNode<Persona> seleccionada = jTreePanel.getSelectedInfo();
				selectedNode = seleccionada;
				nombre = seleccionada.getInfo().getNombre();
				ci = seleccionada.getInfo().getCIdentidad();
				sexo = seleccionada.getInfo().getSexo();
				apellidos = seleccionada.getInfo().getApellidos();
				
				btnModificar.setEnabled(true);
				btnEliminar.setEnabled(true);
				btnInsertar.setEnabled(false);
			}
			
			textField.setText(nombre);
			textField_1.setText(ci);
			textField_3.setText(apellidos);
			
			if(selectedNode != null){
			    if(selectedNode.getInfo().getSexo().equals(Sexo.FEMENINO))
				rdbtnFemenino.setSelected(true);
			    else
				rdbtnMasculino.setSelected(true);
			}
		}
	};
	private JButton btnNewButton;
	private JButton button;
	
	
	private void cleanComponents()
	{
		//Clean Componentes
		textField.setText(new String());
		textField_1.setText(new String());
		textField_3.setText(new String());
		
		//rdbtnFemenino.setSelected(false);
		//rdbtnMasculino.setSelected(false);
		buttonGroup.clearSelection();
	}
	
	private void saveTree() {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showSaveDialog(FamilyTree.this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = chooser.getSelectedFile().getAbsolutePath();

			if(!sectree.save(filename)) {
				JOptionPane.showMessageDialog(FamilyTree.this,"");
			}
		}
	}
}

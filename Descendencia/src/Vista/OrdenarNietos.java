package Vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Controladora;
import modelo.Persona;
import Auxiliar.JTreePanel;
import Auxiliar.verArbol;
import MetodosOrdenamiento.OrdenarPor;
import cu.edu.cujae.ceis.tree.iterators.general.InDepthIterator;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Color;
import java.awt.Frame;

import javax.swing.ImageIcon;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;

public class OrdenarNietos extends JFrame {

	private JPanel contentPane;
	private JLabel lblDecidaLaManera;
	private JButton btnInserci;
	private JButton btnShell;
	private JButton btnMerge;
	private JScrollPane scrollPane;
	private JTable table;
	/**
	 * @wbp.nonvisual location=167,-11
	 */
	private DefaultTableModel TableModel;
	private boolean apellidos2 =true;
	private boolean carnet=true;
	private JComboBox comboBox;
	private JMenuBar menuBar;
	private JMenu mnGuardarComo;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private JButton btnNewButton;
	private JScrollPane scrollPane_1;
	private JTreePanel jtree;
	private JMenu mnNewMenu;
	private JMenuItem mntmArbol;
	private ArrayList<Persona> Listadonietos = new ArrayList<Persona>();

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
					OrdenarNietos frame = new OrdenarNietos();
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
	public OrdenarNietos() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(OrdenarNietos.class.getResource("/Imagenes/Old Person_48px.png")));
		setTitle("Ordenar Nietos\r\n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 463, 367);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnGuardarComo = new JMenu("Guardar Como:");
		mnGuardarComo.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		menuBar.add(mnGuardarComo);
		
		mntmNewMenuItem = new JMenuItem("Texto");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!Listadonietos.isEmpty()){
					JFileChooser fileC = new JFileChooser();
					int opcion = fileC.showSaveDialog(getContentPane());
					if(opcion == JFileChooser.APPROVE_OPTION)
					{
						File file = fileC.getSelectedFile();
						
						try {
							Controladora.guardarNietosTexto(Listadonietos, file);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Listadonietos.clear();
						table.setModel(new DefaultTableModel(new String[]{"Nombre","Apellidos","Id"}, 5));
						}
						
					}
					else{
						
						JOptionPane.showMessageDialog(OrdenarNietos.this, "Necesita decidir como Ordenar a los nietos para guardarlo en el fichero");
					}
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(OrdenarNietos.class.getResource("/Imagenes/Text Box_30px.png")));
		mnGuardarComo.add(mntmNewMenuItem);
		
		mntmNewMenuItem_1 = new JMenuItem("Binario\r\n");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Listadonietos.isEmpty()){
				JFileChooser fileC = new JFileChooser();
				int opcion = fileC.showSaveDialog(getContentPane());
				if(opcion == JFileChooser.APPROVE_OPTION)
				{
					File file = fileC.getSelectedFile();
					
					Controladora.getInstance().mostrarEnFichero(Listadonietos, file);
					Listadonietos.clear();
					table.setModel(new DefaultTableModel(new String[]{"Nombre","Apellidos","Id"}, 5));
					}
					
				}
				else{
					
					JOptionPane.showMessageDialog(OrdenarNietos.this, "Necesita decidir como Ordenar a los nietos para guardarlo en el fichero");
				}
			}
		});
		mntmNewMenuItem_1.setIcon(new ImageIcon(OrdenarNietos.class.getResource("/Imagenes/Binary File_30px.png")));
		mnGuardarComo.add(mntmNewMenuItem_1);
		
		mnNewMenu = new JMenu("Ver ");
		mnNewMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				verArbol v = new verArbol();
				v.setVisible(true);
			}
		});
		mnNewMenu.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		menuBar.add(mnNewMenu);
		
		mntmArbol = new JMenuItem("Arbol");
		mntmArbol.setIcon(new ImageIcon(OrdenarNietos.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		mntmArbol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verArbol v = new verArbol();
				v.setVisible(true);

			}
		});
		mnNewMenu.add(mntmArbol);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnInserci = new JButton("Inserci\u00F3n");
		btnInserci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								//Construir la tabla con:
				if(comboBox.getSelectedItem()!= null){
					String completo = (String) comboBox.getSelectedItem();
					String id = completo.substring(0, 11);
					Listadonietos = Controladora.getInstance().listadoNietos(Controladora.getInstance().buscarPersona(id));
					OrdenarPor.ordenamientoXInsercionNietos(Listadonietos);
					cargarTablaI(Listadonietos);
					
				}
			}
		});
				
				scrollPane_1 = new JScrollPane();
				scrollPane_1.setVisible(false);
				scrollPane_1.setBounds(112, 300, 280, 200);
				contentPane.add(scrollPane_1);
				btnInserci.setBounds(10, 111, 89, 23);
				contentPane.add(btnInserci);
                jtree = new JTreePanel(Controladora.getInstance().getLineaDescendencia());
                scrollPane_1.setViewportView(jtree);
				btnShell = new JButton("Shell");
				btnShell.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Construir la tabla con:
						if(comboBox.getSelectedItem()!= null){
							String completo = (String) comboBox.getSelectedItem();
							String id = completo.substring(0, 11);
							Listadonietos = Controladora.getInstance().listadoNietos(Controladora.getInstance().buscarPersona(id));
							OrdenarPor.shellSort(Listadonietos);
							cargarTablaS(Listadonietos);
							
						}
					}
				});
				btnShell.setBounds(10, 167, 89, 23);
				contentPane.add(btnShell);
				
				btnMerge = new JButton("Merge");
				btnMerge.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Construir la tabla con:
						if(comboBox.getSelectedItem()!= null){
							String completo = (String) comboBox.getSelectedItem();
							String id = completo.substring(0, 11);
							Listadonietos = Controladora.getInstance().listadoNietos(Controladora.getInstance().buscarPersona(id));
							OrdenarPor.mergeSort(Listadonietos);
							cargarTablaM(Listadonietos);
							
						}
						
					}
				});
				btnMerge.setBounds(10, 225, 89, 23);
				contentPane.add(btnMerge);

				lblDecidaLaManera = new JLabel("Decida la manera de ordenar a sus nietos :");
				lblDecidaLaManera.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
				lblDecidaLaManera.setBounds(10, 68, 283, 14);
				contentPane.add(lblDecidaLaManera);

				JLabel lblQuinEres = new JLabel("¿Qui\u00E9n eres ? :");
				lblQuinEres.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
				lblQuinEres.setBounds(10, 31, 98, 14);
				contentPane.add(lblQuinEres);

				scrollPane = new JScrollPane();
				scrollPane.setBounds(126, 93, 316, 155);
				contentPane.add(scrollPane);

				table = new JTable();
				scrollPane.setViewportView(table);

				comboBox = new JComboBox();
				comboBox.setBounds(126, 29, 316, 20);
				contentPane.add(comboBox);
				
				btnNewButton = new JButton("Ver Arbol");
				btnNewButton.setVisible(false);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						initAnimation();
					}
				});
				btnNewButton.setContentAreaFilled(false);
				btnNewButton.setIcon(new ImageIcon(OrdenarNietos.class.getResource("/Imagenes/Eye_48px.png")));
				btnNewButton.setBounds(159, 260, 158, 41);
				contentPane.add(btnNewButton);
				llenarModeloComboBox();
	}
	
	public void llenarModeloComboBox(){
		InDepthIterator<Persona> iter = Controladora.getInstance().getLineaDescendencia().inDepthIterator();

		while(iter.hasNext()){
			Persona p = iter.next();
			String s = new String( p.getCIdentidad() + " " + p.getNombre());
			comboBox.addItem(s);
		}
	}
	public void cargarTablaI(ArrayList<Persona> nietos){
		ArrayList<Object> nombre = new ArrayList<Object>();
		ArrayList<Object> apellidos = new ArrayList<Object>();
		ArrayList<Object> ci = new ArrayList<Object>();

		for(int i=0;i<nietos.size();i++){
			Persona n = nietos.get(i);
			nombre.add(n.getNombre());

			if(apellidos2)
				apellidos.add(n.getApellidos());
			if(carnet)
				ci.add(n.getCIdentidad());
		}
		TableModel = new DefaultTableModel();
		TableModel.addColumn("Nombre",nombre.toArray());  
		if(apellidos2)
			TableModel.addColumn("Apellidos",apellidos.toArray());
		if(carnet)
			TableModel.addColumn("Carnet",ci.toArray());
		table.setModel(TableModel);
	}
	public void cargarTablaS(ArrayList<Persona> nietos){
		ArrayList<Object> nombre = new ArrayList<Object>();
		ArrayList<Object> apellidos = new ArrayList<Object>();
		ArrayList<Object> ci = new ArrayList<Object>();

		for(int i=0;i<nietos.size();i++){
			Persona n = nietos.get(i);
			nombre.add(n.getNombre());

			if(apellidos2)
				apellidos.add(n.getApellidos());
			if(carnet)
				ci.add(n.getCIdentidad());
		}
		TableModel = new DefaultTableModel();
		TableModel.addColumn("Nombre",nombre.toArray());  
		if(apellidos2)
			TableModel.addColumn("Apellidos",apellidos.toArray());
		if(carnet)
			TableModel.addColumn("Carnet",ci.toArray());
		table.setModel(TableModel);
	}
	public void cargarTablaM(ArrayList<Persona> nietos){
		ArrayList<Object> nombre = new ArrayList<Object>();
		ArrayList<Object> apellidos = new ArrayList<Object>();
		ArrayList<Object> ci = new ArrayList<Object>();

		for(int i=0;i<nietos.size();i++){
			Persona n = nietos.get(i);
			nombre.add(n.getNombre());

			if(apellidos2)
				apellidos.add(n.getApellidos());
			if(carnet)
				ci.add(n.getCIdentidad());
		}
		TableModel = new DefaultTableModel();
		TableModel.addColumn("Nombre",nombre.toArray());  
		if(apellidos2)
			TableModel.addColumn("Apellidos",apellidos.toArray());
		if(carnet)
			TableModel.addColumn("Carnet",ci.toArray());
	   table.setModel(TableModel);
	}
	
	private void initAnimation(){

		new Thread(){

			public void run() 
			{
				int initPos = scrollPane_1.getLocation().y;
				System.out.println(initPos);
				int xpos = 110;
				int ypos = 100;

				try {
					while(true){

						if(initPos <= ypos){
							scrollPane_1.setLocation(xpos, ypos);
							break;
						}
						else
							scrollPane_1.setLocation(xpos, initPos);

						initPos -= 10;
						sleep(50);
						scrollPane_1.setVisible(true);
						scrollPane_1.setFocusable(true);
					}


				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}


		}.start();


	}
}

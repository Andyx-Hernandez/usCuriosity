package Vista;

import graphpanel.JGraphPanel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Controladora;
import modelo.Persona;
import modelo.Vivienda;
import Auxiliar.JTreePanel;
import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;

import javax.swing.SwingConstants;
import javax.swing.JInternalFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Toolkit;

public class verAsignacion extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JComboBox comboBox;
	private JButton eliminar;
	private JPanel panel;
	private JGraphPanel treePanel;
	private BinaryTreeNode<Persona>originalPerson = (BinaryTreeNode<Persona>) Controladora.getInstance().getLineaDescendencia().getRoot();
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private String [] viviendasColumnas = new String[]{"Direccion","Ciudad","Asignada A"};
	private String [] NoviviendasColumnas = new String[]{"Direccion","Ciudad"};
	private String [] PersonasColumnas = new String[]{"Nombre","Apellidos","ID"};

	private DefaultTableModel viviendaTableModel;
	private DefaultTableModel NviviendaTableModel;
	private DefaultTableModel personaTableModel;
	private JLabel lblViviendasAsignadas;
	private JLabel lblViviendasSinAsignar;
	private JLabel lblPersonasPorAsignar;
	private JButton btnNewButton;
	private JScrollPane scrollPane_3;
	private JTreePanel jTreePanel;


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
					verAsignacion frame = new verAsignacion();
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
	public verAsignacion() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(verAsignacion.class.getResource("/Imagenes/Home_50px.png")));
		setTitle("Asignacion de Viviendas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 914, 610);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane_3 = new JScrollPane();
		scrollPane_3.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				scrollPane_3.setFocusable(false);
				scrollPane_3.setLocation(scrollPane_3.getLocation().x,560);
			}
		});
		scrollPane_3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				scrollPane_3.setFocusable(false);
				scrollPane_3.setLocation(scrollPane_3.getLocation().x,560);
			}
		});
		scrollPane_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				scrollPane_3.setLocation(scrollPane_3.getLocation().x,560);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				scrollPane_3.setVisible(false);
				scrollPane_3.setLocation(scrollPane_3.getLocation().x,560);
				
			}
		});
		scrollPane_3.setVisible(false);
		scrollPane_3.setBounds(300, 560, 341, 270);
		contentPane.add(scrollPane_3);
		jTreePanel = new JTreePanel(Controladora.getInstance().getLineaDescendencia());
		scrollPane_3.setViewportView(jTreePanel);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(12, 12, 466, 493);
		contentPane.add(panel);
		panel.setLayout(null);

		treePanel = new JGraphPanel(Controladora.getInstance().getGrafoVivienda(), 0);
		treePanel.setBounds(30, 80, 400, 400);
		panel.add(treePanel);

		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(12, 12, 74, 65);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setIcon(new ImageIcon(verAsignacion.class.getResource("/Imagenes/Tower Of Pisa_48px.png")));

		lblNewLabel = new JLabel("Asignación A la Descendencia De:");
		lblNewLabel.setBounds(61, 12, 252, 32);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));

		lblNewLabel_2 = new JLabel("¿Te gusta una ciudad? ¿Cuál Prefieres?");
		lblNewLabel_2.setBounds(61, 45, 281, 32);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));

		lblNewLabel_1 = new JLabel("Person\r\n");
		lblNewLabel_1.setText(originalPerson.getInfo().getNombre());
		lblNewLabel_1.setBounds(340, 12, 170, 32);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LinkedList<Vivienda> sinAsignar = new LinkedList<Vivienda>();
				Iterator<Vertex> it = Controladora.getInstance().getGrafoVivienda().getVerticesList().iterator();
				while(it.hasNext()){
					Vivienda aux = (Vivienda) it.next().getInfo();
					if(aux.isDisponible())
						sinAsignar.addFirst(aux);
				}


				String ciudad = (String) comboBox.getSelectedItem();
				Queue<Vivienda> viviendas = Controladora.getInstance().iniciarProcesoDeAsignacion(ciudad,originalPerson,sinAsignar);

				Queue<BinaryTreeNode<Persona>> pNAsignadas = Controladora.getInstance().personasSinAsignar(viviendas);

				llenarTablas(viviendas, sinAsignar, pNAsignadas);

				comboBox.setEnabled(false);
				eliminar.setVisible(true);

				treePanel.update();
				panel.repaint();
				repaint();				
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Seleccionar", "Oz", "Amor", "Invernalia", "NoneDream", "Neverland", "Oasis", "Pixabey"}));
		comboBox.setBounds(340, 50, 117, 25);
		panel.add(comboBox);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(507, 32, 377, 155);
		contentPane.add(scrollPane);

		table_1 = new JTable();
		scrollPane.setViewportView(table_1);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(507, 219, 377, 125);
		contentPane.add(scrollPane_1);

		table = new JTable();
		scrollPane_1.setViewportView(table);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(507, 380, 377, 125);
		contentPane.add(scrollPane_2);

		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);

		eliminar = new JButton();
		eliminar.setBounds(474, 61, 25, 25);
		eliminar.setIcon(new ImageIcon(verAsignacion.class.getResource("/Imagenes/delete.png")));
		eliminar.setContentAreaFilled(false);
		eliminar.setFocusPainted(false);
		eliminar.setBorderPainted(false);
		eliminar.setMargin(new Insets(0,0,0,0));
		eliminar.setToolTipText("Eliminar la asignación de viviendas");
		eliminar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Controladora.getInstance().limpiarAsignación();
				inicializarTablas();
				comboBox.setSelectedIndex(0);
				comboBox.setEnabled(true);
				eliminar.setVisible(false);

				treePanel.update();
				panel.repaint();
				repaint();	
			}			
		});
		eliminar.setVisible(false);
		contentPane.add(eliminar);

		lblViviendasAsignadas = new JLabel("Viviendas asignadas");
		lblViviendasAsignadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblViviendasAsignadas.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblViviendasAsignadas.setBounds(507, 12, 377, 17);
		contentPane.add(lblViviendasAsignadas);

		lblViviendasSinAsignar = new JLabel("Viviendas por asignar");
		lblViviendasSinAsignar.setHorizontalAlignment(SwingConstants.CENTER);
		lblViviendasSinAsignar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblViviendasSinAsignar.setBounds(507, 199, 377, 17);
		contentPane.add(lblViviendasSinAsignar);

		lblPersonasPorAsignar = new JLabel("Personas por asignar");
		lblPersonasPorAsignar.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonasPorAsignar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		lblPersonasPorAsignar.setBounds(507, 356, 377, 17);
		contentPane.add(lblPersonasPorAsignar);
		
				btnNewButton = new JButton("Ver Arbol");
				btnNewButton.setIcon(new ImageIcon(verAsignacion.class.getResource("/Imagenes/Eye_48px.png")));
				btnNewButton.setContentAreaFilled(false);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
                
						initAnimation();
					}
				});
				btnNewButton.setBounds(371, 538, 171, 33);
				contentPane.add(btnNewButton);

		inicializarTablas();
	}

	private void inicializarTablas(){
		viviendaTableModel = new DefaultTableModel(viviendasColumnas, 9);
		table_1.setModel(viviendaTableModel);



		Object[][] datosVNAsignadas = new Object[Controladora.getInstance().getGrafoVivienda().getVerticesList().size()][2];
		int i=0;
		Iterator<Vertex> iter = Controladora.getInstance().getGrafoVivienda().getVerticesList().iterator();
		while(iter.hasNext()){
			Vivienda v = (Vivienda)iter.next().getInfo();
			datosVNAsignadas[i][0] = v.getDireccion();
			datosVNAsignadas[i][1] = v.getCiudad();
			i++;
		}
		NviviendaTableModel =  new DefaultTableModel(datosVNAsignadas, NoviviendasColumnas);
		table.setModel(NviviendaTableModel);



		Queue<BinaryTreeNode<Persona>> pNAsignadas = Controladora.getInstance().personasSinAsignar(null);		
		Object[][] datosPNAsignadas = new Object[pNAsignadas.size()][3];
		i=0;
		while(!pNAsignadas.isEmpty()){
			BinaryTreeNode<Persona> p = pNAsignadas.poll();
			datosPNAsignadas[i][0] = p.getInfo().getNombre();
			datosPNAsignadas[i][1] = p.getInfo().getApellidos();
			datosPNAsignadas[i][1] = p.getInfo().getCIdentidad();
			//datosPNAsignadas[i][1] = Controladora.getInstance().getLineaDescendencia().getFather(p).getInfo().getNombre();
			i++;
		}
		personaTableModel =  new DefaultTableModel(datosPNAsignadas, PersonasColumnas);
		table_2.setModel(personaTableModel);
	}

	private void llenarTablas(Queue<Vivienda> vAsignadas, LinkedList<Vivienda> vNAsignadas, Queue<BinaryTreeNode<Persona>> pNAsignadas){

		Object[][] datosVAsignadas = new Object[vAsignadas.size()][3];
		int i=0;
		while(!vAsignadas.isEmpty()){
			Vivienda v = vAsignadas.poll();
			datosVAsignadas[i][0] = v.getDireccion();
			datosVAsignadas[i][1] = v.getCiudad();
			datosVAsignadas[i][2] = v.getPropietario().getNombre();
			i++;
		}
		viviendaTableModel =  new DefaultTableModel(datosVAsignadas, viviendasColumnas);
		table_1.setModel(viviendaTableModel);

		Object[][] datosVNAsignadas = new Object[vNAsignadas.size()][2];
		i=0;
		while(!vNAsignadas.isEmpty()){
			Vivienda v = vNAsignadas.removeFirst();
			datosVNAsignadas[i][0] = v.getDireccion();
			datosVNAsignadas[i][1] = v.getCiudad();
			i++;
		}
		NviviendaTableModel =  new DefaultTableModel(datosVNAsignadas, NoviviendasColumnas);
		table.setModel(NviviendaTableModel);


		Object[][] datosPNAsignadas = new Object[pNAsignadas.size()][3];
		i=0;
		while(!pNAsignadas.isEmpty()){
			BinaryTreeNode<Persona> p = pNAsignadas.poll();
			datosPNAsignadas[i][0] = p.getInfo().getNombre();
			datosPNAsignadas[i][1] = p.getInfo().getApellidos();
			datosPNAsignadas[i][2] = p.getInfo().getCIdentidad();
			//datosPNAsignadas[i][1] = Controladora.getInstance().getLineaDescendencia().getFather(p).getInfo().getNombre();
			i++;
		}
		personaTableModel =  new DefaultTableModel(datosPNAsignadas, PersonasColumnas);
		table_2.setModel(personaTableModel);
	}

	private void initAnimation(){

		new Thread(){

			public void run() 
			{
				int initPos = scrollPane_3.getLocation().y;
				System.out.println(initPos);
				int xpos = 310;
				int ypos = 310;

				try {
					while(true){

						if(initPos <= ypos){
							scrollPane_3.setLocation(310, 310);
							break;
						}
						else
							scrollPane_3.setLocation(xpos, initPos);

						initPos -= 30;
						sleep(50);
						scrollPane_3.setVisible(true);
						scrollPane_3.setFocusable(true);
					}


				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}


		}.start();


	}
}

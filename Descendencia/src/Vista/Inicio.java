package Vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Auxiliar.CustomPanel;
import Auxiliar.Initialize;
import Auxiliar.Nosotros;
import Auxiliar.isNotATreeInformation;
import Auxiliar.tips;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.org.apache.xml.internal.serializer.ElemDesc;

import cu.edu.cujae.ceis.sectree.SecTree.LoadOpResult;
import modelo.Controladora;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;

import java.awt.Toolkit;

public class Inicio extends JFrame {

	private JPanel contentPane;
	private CustomPanel panel;
	private JLabel lblTodosDeberianConocer;
	private JLabel lblDeDondeDescendieron;
	private JLabel lblMuestranosTuDescendencia;
	private JLabel lblORecurreA;
	private JButton btnNewButton;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenu mnNewMenu_1;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private JMenuItem mntmEquipo;

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
            
					Initialize.initializateGraph();
					tips.fillTips();
					Inicio frame = new Inicio();
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
	public Inicio() {
		setTitle("Geneology");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Inicio.class.getResource("/Imagenes/Genealogy_50px.png")));
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 500);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Reportes");
		mnNewMenu.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("Asignar Viviendas");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Informacion frameAsignacion = new Informacion();
				frameAsignacion.setVisible(true);
			}
		});
		mntmNewMenuItem.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		mnNewMenu.add(mntmNewMenuItem);
		
		mntmNewMenuItem_1 = new JMenuItem("Listado de Nietos");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(Controladora.getInstance().getLineaDescendencia().isEmpty())
					JOptionPane.showMessageDialog(Inicio.this, "No se ha creado aun un arbol de descendencia.Construya alguno");
				else{
				OrdenarNietos nietoFrame = new OrdenarNietos();
				nietoFrame.setVisible(true);
				}
			}
		});
		mntmNewMenuItem_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		mnNewMenu.add(mntmNewMenuItem_1);
		
		mnNewMenu_1 = new JMenu("Acerca De");
		mnNewMenu_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		menuBar.add(mnNewMenu_1);
		
		mntmEquipo = new JMenuItem("Nuestro Equipo");
		mntmEquipo.setIcon(new ImageIcon(Inicio.class.getResource("/Imagenes/Coworking_32px.png")));
		mntmEquipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Nosotros aux = new Nosotros();
				aux.setVisible(true);
			}
		});
		mntmEquipo.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		mnNewMenu_1.add(mntmEquipo);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new CustomPanel();
		panel.setFocusTraversalPolicyProvider(true);
		panel.setBounds(0, 72, 862, 399);
		panel.setURL("/Imagenes/father-2770301__340_(1).jpg");
		contentPane.add(panel);
		panel.setLayout(null);

		lblTodosDeberianConocer = new JLabel("Todos deberian conocer \r\n");
		lblTodosDeberianConocer.setFont(new Font("Century", Font.PLAIN, 16));
		lblTodosDeberianConocer.setForeground(Color.WHITE);
		lblTodosDeberianConocer.setBounds(42, 69, 197, 50);
		panel.add(lblTodosDeberianConocer);

		lblDeDondeDescendieron = new JLabel("de donde descendieron");
		lblDeDondeDescendieron.setFont(new Font("Century", Font.PLAIN, 16));
		lblDeDondeDescendieron.setForeground(Color.WHITE);
		lblDeDondeDescendieron.setBounds(42, 102, 181, 50);
		panel.add(lblDeDondeDescendieron);

		lblMuestranosTuDescendencia = new JLabel("Muestranos tu descendencia");
		lblMuestranosTuDescendencia.setFont(new Font("Century", Font.PLAIN, 16));
		lblMuestranosTuDescendencia.setForeground(Color.WHITE);
		lblMuestranosTuDescendencia.setBounds(42, 141, 213, 50);
		panel.add(lblMuestranosTuDescendencia);

		lblORecurreA = new JLabel("O recurre a la presencia de otras");
		lblORecurreA.setFont(new Font("Century", Font.PLAIN, 16));
		lblORecurreA.setForeground(Color.WHITE);
		lblORecurreA.setBounds(42, 178, 245, 50);
		panel.add(lblORecurreA);

		JButton btnCargar = new JButton("CARGAR");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser chooser = new JFileChooser();
					int returnVal = chooser.showOpenDialog(getContentPane());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();

						Controladora.getInstance().cargarArbolExterno(file);
						FamilyTree frame = new FamilyTree();
						frame.setVisible(true);

					}} catch (isNotATreeInformation e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(Inicio.this, e1.getMessage());
					}
			}
		});
		btnCargar.setBounds(194, 239, 98, 37);
		panel.add(btnCargar);

		btnNewButton = new JButton("Nueva Descendencia");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				geneologyNew view = new geneologyNew();
				view.setVisible(true);
			}
		});
		btnNewButton.setBounds(29, 240, 153, 36);
		panel.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("GENEOLOGY");
		lblNewLabel.setFont(new Font("Castellar", Font.PLAIN, 17));
		lblNewLabel.setIcon(new ImageIcon(Inicio.class.getResource("/Imagenes/Genealogy_50px.png")));
		lblNewLabel.setBounds(23, 0, 210, 66);
		contentPane.add(lblNewLabel);
	}
}

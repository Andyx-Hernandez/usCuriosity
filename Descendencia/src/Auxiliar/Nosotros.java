package Auxiliar;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Nosotros extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblAndyHernandez;
	private JLabel lblArielSaumel;
	private JLabel lblElbertMesa;
	private JLabel lblSiguenosEn;
	private JLabel lblErickTaylor;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel lblCeahernandezalceiscujaeeducu;
	private JLabel lblEmesaceiscujaeeducu;
	private JLabel lblAsaumelceiscujaeeducu;
	private JLabel lblEtaylorceiscujaeeducu;

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
					Nosotros frame = new Nosotros();
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
	public Nosotros() {
		setTitle("Us Team");
		setBackground(new Color(220, 20, 60));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 311);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 69, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("TEAM  GENEOLOGY GRUPO 22");
		lblNewLabel.setForeground(new Color(240, 248, 255));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 21));
		lblNewLabel.setBounds(68, 24, 314, 25);
		contentPane.add(lblNewLabel);
		
		lblAndyHernandez = new JLabel("Andy Hernandez");
		lblAndyHernandez.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblAndyHernandez.setBounds(10, 60, 133, 28);
		contentPane.add(lblAndyHernandez);
		
		lblArielSaumel = new JLabel("Ariel Saumel");
		lblArielSaumel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblArielSaumel.setBounds(32, 136, 93, 14);
		contentPane.add(lblArielSaumel);
		
		lblElbertMesa = new JLabel("Elbert Mesa");
		lblElbertMesa.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblElbertMesa.setBounds(32, 99, 89, 14);
		contentPane.add(lblElbertMesa);
		
		lblSiguenosEn = new JLabel("Siempre Activos en:");
		lblSiguenosEn.setForeground(new Color(240, 248, 255));
		lblSiguenosEn.setBackground(new Color(240, 248, 255));
		lblSiguenosEn.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblSiguenosEn.setBounds(138, 207, 143, 21);
		contentPane.add(lblSiguenosEn);
		
		lblErickTaylor = new JLabel("Erick Taylor");
		lblErickTaylor.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblErickTaylor.setBounds(32, 176, 89, 21);
		contentPane.add(lblErickTaylor);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(Nosotros.class.getResource("/Imagenes/Whale_100px.png")));
		label.setBounds(327, 176, 107, 114);
		contentPane.add(label);
		
		label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Nosotros.class.getResource("/Imagenes/Stack Overflow_32px.png")));
		label_1.setBounds(180, 220, 57, 53);
		contentPane.add(label_1);
		
		label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(Nosotros.class.getResource("/Imagenes/GitHub_32px.png")));
		label_2.setBounds(228, 220, 40, 47);
		contentPane.add(label_2);
		
		label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(Nosotros.class.getResource("/Imagenes/LinkedIn_32px.png")));
		label_3.setBounds(138, 231, 40, 29);
		contentPane.add(label_3);
		
		lblCeahernandezalceiscujaeeducu = new JLabel("ceahernandezal@ceis.cujae.edu.cu");
		lblCeahernandezalceiscujaeeducu.setIcon(new ImageIcon(Nosotros.class.getResource("/Imagenes/Group Message_32px.png")));
		lblCeahernandezalceiscujaeeducu.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblCeahernandezalceiscujaeeducu.setBounds(153, 60, 281, 28);
		contentPane.add(lblCeahernandezalceiscujaeeducu);
		
		lblEmesaceiscujaeeducu = new JLabel("emesa@ceis.cujae.edu.cu");
		lblEmesaceiscujaeeducu.setIcon(new ImageIcon(Nosotros.class.getResource("/Imagenes/Group Message_32px.png")));
		lblEmesaceiscujaeeducu.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblEmesaceiscujaeeducu.setBounds(155, 92, 279, 28);
		contentPane.add(lblEmesaceiscujaeeducu);
		
		lblAsaumelceiscujaeeducu = new JLabel("asaumel@ceis.cujae.edu.cu\r\n");
		lblAsaumelceiscujaeeducu.setIcon(new ImageIcon(Nosotros.class.getResource("/Imagenes/Group Message_32px.png")));
		lblAsaumelceiscujaeeducu.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblAsaumelceiscujaeeducu.setBounds(153, 129, 281, 28);
		contentPane.add(lblAsaumelceiscujaeeducu);
		
		lblEtaylorceiscujaeeducu = new JLabel("etaylor@ceis.cujae.edu.cu");
		lblEtaylorceiscujaeeducu.setIcon(new ImageIcon(Nosotros.class.getResource("/Imagenes/Group Message_32px.png")));
		lblEtaylorceiscujaeeducu.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblEtaylorceiscujaeeducu.setBounds(153, 168, 281, 28);
		contentPane.add(lblEtaylorceiscujaeeducu);
	}
}

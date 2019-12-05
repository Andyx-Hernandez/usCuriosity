package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.BreakIterator;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Auxiliar.Convert;
import Auxiliar.Sexo;
import Auxiliar.isNotATreeInformation;
import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedEdgeNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.sectree.PreOrderSecTree;
import cu.edu.cujae.ceis.sectree.SecTree;
import cu.edu.cujae.ceis.sectree.SecTree.LoadOpResult;
import cu.edu.cujae.ceis.tree.binary.BinaryTree;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import cu.edu.cujae.ceis.tree.general.GeneralTree;
import cu.edu.cujae.ceis.tree.iterators.general.InBreadthIterator;
import cu.edu.cujae.ceis.tree.iterators.general.InDepthIterator;
import dJisktra.Camino;
import dJisktra.Djisktra;

public class Controladora {		// nombre?
	private GeneralTree<Persona> lineaDescendencia;
	private ILinkedWeightedEdgeNotDirectedGraph grafoVivienda;
	private static Controladora SINGLETON = null;

	public static Controladora getInstance()
	{
		if(SINGLETON == null)
		{
			SINGLETON = new Controladora();
		}

		return SINGLETON;
	}

	Controladora(){
		lineaDescendencia = new GeneralTree<Persona>();
		grafoVivienda = new LinkedGraph();
	}

	/*public Controladora(Persona persona){
		BinaryTreeNode<Persona> nodoPersona = new BinaryTreeNode<Persona>(persona);
		lineaDescendencia = new GeneralTree<Persona>(nodoPersona);
	}
	public Controladora(String cIdentidad, String nombre, char sexo){
		Persona persona = new Persona(cIdentidad, nombre, sexo);
		BinaryTreeNode<Persona> nodoPersona = new BinaryTreeNode<Persona>(persona);
		lineaDescendencia = new GeneralTree<Persona>(nodoPersona);
	}
	 */

	public GeneralTree<Persona> getLineaDescendencia() {
		return lineaDescendencia;
	}


	public void setLineaDescendencia(GeneralTree<Persona> lineaDescendencia) {
		this.lineaDescendencia = lineaDescendencia;
	}

	public ILinkedWeightedEdgeNotDirectedGraph getGrafoVivienda() {
		return grafoVivienda;
	}

	public void setGrafoVivienda(ILinkedWeightedEdgeNotDirectedGraph grafoVivienda) {
		this.grafoVivienda = grafoVivienda;
	}


	/* 
	   ------------------------
	< I´m not a bug, I´m a programer finding the bug  >
	  ------------------------
	       \ 
	        \
	       (,__,)
	        /||\
	 	   * || *
	        /  \

	 */


	/*Metod para obtener el listado de los nietos les dejo si es array o linked a su entender*/


	public ArrayList<Persona> listadoNietos(Persona abuelo)
	{
		ArrayList<Persona> listNietos = new ArrayList<Persona>();

		if(!lineaDescendencia.isEmpty())
		{
			InBreadthIterator<Persona> iter = lineaDescendencia.inBreadthIterator();
			boolean find = false;

			while(iter.hasNext() && !find){

				BinaryTreeNode<Persona> granNode = iter.nextNode();
				if(granNode.getInfo().equals(abuelo))
				{
					find = true;
					granNode = granNode.getLeft();

					while(granNode != null)
					{
						BinaryTreeNode<Persona> nieto = granNode.getLeft();

						while(nieto != null){
							listNietos.add(nieto.getInfo());
							nieto = nieto.getRight();
						}

						granNode = granNode.getRight();
					}
				}
			}

		}


		return listNietos;
	}

	public Persona eliminar(BinaryTreeNode<Persona> node)
	{
		Persona info = null;
		boolean find = false;

		if(node.equals(lineaDescendencia.getRoot()))
		{
			lineaDescendencia.setRoot(null);
		}
		else
		{
			InDepthIterator<Persona> it = lineaDescendencia.inDepthIterator();

			while(it.hasNext() && !find)
			{
				BinaryTreeNode<Persona> personaNode = it.nextNode();
				if(personaNode.getLeft() != null && personaNode.getLeft().equals(node))
				{
					find=true;
					personaNode.setLeft(node.getRight());

					if(personaNode.getLeft() == null)// este if es pq quizas no tenga hermanos el nodo que se elimino
						personaNode.setLeft(node.getLeft());
					else
						insertLastBrother(personaNode.getLeft(), node.getLeft());
				}
				else{
					BinaryTreeNode<Persona> previus = personaNode.getLeft();
					if(previus != null){ 

						BinaryTreeNode<Persona> now = previus.getRight();

						while(now != null && !find){
							if(now.equals(node)){
								find = true;
								previus.setRight(now.getRight());
								insertLastBrother(previus, node.getLeft());
							}
							else{
								previus = now;
								now = now.getRight();
							}
						}
					}
				}

			}
		}

		if(find)
			info = node.getInfo();


		return info;	
	}

	private void insertLastBrother(BinaryTreeNode<Persona> brother , BinaryTreeNode<Persona> lastBrother)
	{
		if(brother != null){			   
			if(brother.getRight()== null)  
				brother.setRight(lastBrother);
			else
			{
				BinaryTreeNode<Persona> brotherSecuence = brother.getRight();
				while(brotherSecuence.getRight() != null)
				{
					brotherSecuence = brotherSecuence.getRight();
				}

				brotherSecuence.setRight(lastBrother);

			}
		}
	}

	public void setCabezaFamilia(Persona info)
	{
		BinaryTreeNode<Persona> cabeza = new BinaryTreeNode<Persona>(info);
		lineaDescendencia.setRoot(cabeza);
	}

	public void cargarArbolExterno(File file) throws isNotATreeInformation{

		SecTree<Persona> sectree = new PreOrderSecTree<Persona>();
		LoadOpResult r = sectree.load(file.getAbsolutePath()); 

		if(LoadOpResult.success == r) {

			//Genera el árbol general del árbol secuencial
			setLineaDescendencia(sectree.createGeneralTree());


		} else if(LoadOpResult.signatureError == r) {
			throw new isNotATreeInformation("Este fichero no contiene la informacion suficiente para generar el arbol");
		} else {
			throw new isNotATreeInformation("Error cargando el archivo");
		}

	}

	public void guardarArbol(File file) throws isNotATreeInformation{

		SecTree<Persona> secTree = new PreOrderSecTree<Persona>(lineaDescendencia);
		String filename = file.getAbsolutePath();
		if(!secTree.save(filename)){
			throw new isNotATreeInformation("No es posible guardar la información");
		}
	}

	public Queue<Vivienda> iniciarProcesoDeAsignacion(String ciudad, BinaryTreeNode<Persona> originalNode, LinkedList<Vivienda> sinAsignar)
	{
		Queue<Vivienda> viviandasAsignadas = new ArrayDeque<Vivienda>();

		if(!sinAsignar.isEmpty()){
			Vivienda vivienda = viviendaPreferencia(ciudad, originalNode.getInfo());

			if(vivienda != null){
				sinAsignar.remove(vivienda);//lo remueve de la lista sin asignar
				vivienda.setDisponible(false);//cambia la disponibilidad
				vivienda.setPropietario(originalNode.getInfo());//pone el propietario
				viviandasAsignadas.offer(vivienda);
				viviandasAsignadas.addAll(completarProceso(originalNode,sinAsignar));
			}

		}
		return viviandasAsignadas;	
	}

	private Queue<Vivienda> completarProceso(BinaryTreeNode<Persona>father, LinkedList<Vivienda> sinAsignar){

		Queue<Vivienda> viviendasAsignadas = new ArrayDeque<Vivienda>();
		GeneralTree<Persona> auxTree = new GeneralTree<Persona>(father);
		InBreadthIterator<Persona> breadthIt = auxTree.inBreadthIterator();

		while(breadthIt.hasNext() && !sinAsignar.isEmpty())
		{
			BinaryTreeNode<Persona> node = breadthIt.nextNode();

			Queue<Persona> colaHijos = new ArrayDeque<Persona>(auxTree.getSonsInfo(node)); //pone los hijos en una cola para procesarlos en orden

			if(!colaHijos.isEmpty())
				viviendasAsignadas.addAll(asignarViviendaAlosHijos(encontrarCasa(node.getInfo()), colaHijos, sinAsignar));

		}

		return viviendasAsignadas;
	}

	private Queue<Vivienda> asignarViviendaAlosHijos(Vertex vivienda,Queue<Persona> hijos,LinkedList<Vivienda> sinAsignar){

		Queue<Vivienda> viviendaAsiganadas = new ArrayDeque<Vivienda>();
		PriorityQueue<Camino> colaCaminos = new PriorityQueue<Camino>(16,null);

		Iterator<Vertex> it = grafoVivienda.getVerticesList().iterator();

		while(it.hasNext()){

			Vertex vVivienda = it.next();
			if(((Vivienda)vVivienda.getInfo()).isDisponible())
			{
				colaCaminos.offer(Djisktra.caminoMasCorto(grafoVivienda, vivienda, vVivienda));
			}
		}

		while(!hijos.isEmpty() && colaCaminos.peek() != null){

			Vivienda aAsignar = (Vivienda) colaCaminos.poll().getCaminosvertex().getLast().getInfo();
			sinAsignar.remove(aAsignar);//Elimina la vivienda de la lista de las no asignadas
			aAsignar.setDisponible(false);
			aAsignar.setPropietario(hijos.poll());
			viviendaAsiganadas.offer(aAsignar);
		}

		return viviendaAsiganadas;

	}


	private Vivienda viviendaPreferencia(String ciudad,Persona persona)
	{
		boolean findSomeone = false;
		Vivienda vivienda = null;

		Iterator<Vertex> it = grafoVivienda.getVerticesList().iterator();

		while(it.hasNext() && !findSomeone)
		{
			Vertex aux = it.next();
			Vivienda vivi = (Vivienda) aux.getInfo();

			if(vivi.getCiudad().equalsIgnoreCase(ciudad) && vivi.isDisponible())
			{
				vivienda = vivi;
				findSomeone = true;
			}
		}

		return vivienda;
	}

	private Vertex encontrarCasa(Persona persona){

		Vertex verticeVivienda = null;
		Iterator<Vertex> it = grafoVivienda.getVerticesList().iterator();
		boolean findHouse = false;

		while(it.hasNext() && !findHouse)
		{
			Vertex aux = it.next();
			Vivienda vivi = (Vivienda) aux.getInfo();

			if(!vivi.isDisponible()){
				if(vivi.getPropietario().equals(persona))
				{
					verticeVivienda = aux;
					findHouse = true;
				}
			}
		}

		return verticeVivienda;
	}
   
	public Persona buscarPersona(String id){
		
		Persona pResult = null;
		InBreadthIterator<Persona> it = lineaDescendencia.inBreadthIterator();
		boolean find = false;
		
		while(it.hasNext() && !find)
		{
			Persona p = it.next();
			if(p.getCIdentidad().equalsIgnoreCase(id)){
				find = true;
				pResult = p;
			}
		}
		
		return pResult;
	}
	

	public Queue<BinaryTreeNode<Persona>> personasSinAsignar(Queue<Vivienda> viviendasAsignadas) {
		Queue<BinaryTreeNode<Persona>> personas = new ArrayDeque<BinaryTreeNode<Persona>>();
		Queue<Vivienda> aux = new ArrayDeque<Vivienda>();
		
		InBreadthIterator<Persona> bIter = lineaDescendencia.inBreadthIterator();
		while(bIter.hasNext())
			personas.offer(bIter.nextNode());
			
		if(viviendasAsignadas!=null){
			while(!viviendasAsignadas.isEmpty()){
				aux.offer(viviendasAsignadas.poll());
				personas.poll();
			}
			while(!aux.isEmpty())
				viviendasAsignadas.offer(aux.poll());
		}
		
		return personas;
	}

	public void limpiarAsignación() {
		Iterator<Vertex> iter = grafoVivienda.getVerticesList().iterator();
		while(iter.hasNext()){
			Vivienda v = (Vivienda) iter.next().getInfo();
			if(!v.isDisponible()){
				v.setPropietario(null);
				v.setDisponible(true);
			}
		}
	}
	
	public void mostrarEnFichero(ArrayList<Persona> nietos,File ficheroNieto){ 
		try {
			RandomAccessFile fichero = new RandomAccessFile(ficheroNieto , "rw");
			fichero.writeInt(nietos.size());
			for (int i = 0; i < nietos.size(); i++) {
				Persona nieto = nietos.get(i);
				byte[] arreglo = Convert.toBytes(nieto);
				fichero.writeInt(arreglo.length);
				 fichero.write(arreglo);		
			}
			fichero.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
			
	}
	
	public static void guardarNietosTexto(ArrayList<Persona> nietos, File file) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(file);
		pw.println(nietos.size());
		pw.println("CI,Nombre,Apellidos,Sexo");
		for(Persona p: nietos)
			pw.println(p.getCIdentidad()+","+p.getNombre()+","+p.getApellidos()+","+p.getSexo().name());
		pw.close();
	}
	
	public static void leerNietosTexto(ArrayList<Persona> nietos, File file) throws FileNotFoundException{
		Scanner scanner = new Scanner(file);
		int cant = scanner.nextInt();
		scanner.nextLine();	//Saltar el encabezado
		scanner.nextLine();
		int i=0;
		while(i<cant && scanner.hasNextLine()){
			String[] linea = scanner.nextLine().split(",",4);
			Persona p = new Persona(linea[0],linea[1],linea[2],Sexo.valueOf(linea[3]));
			nietos.add(p);
		}
		scanner.close();
	}

	
	/*
	 * public boolean insertarHijo(Persona hijo, Persona padre, int posicion){
		boolean encontrado = false;
		BinaryTreeNode<Persona> nodoHijo = new BinaryTreeNode<Persona>(hijo);
		if(padre == null){
			if(lineaDescendencia.isEmpty()){
				encontrado = true;
				lineaDescendencia.setRoot(nodoHijo);
			}			
		}else{
			InDepthIterator<Persona> iter = lineaDescendencia.inDepthIterator();
			while(iter.hasNext() && !encontrado){
				BinaryTreeNode<Persona> nodoActual = iter.nextNode();
				if(nodoActual.getInfo().equals(padre)){
					encontrado = true;
					if(nodoActual.getLeft()==null)
						nodoActual.setLeft(nodoHijo);
					else{
						if(posicion == 0){
							BinaryTreeNode<Persona> nodoSiguiente = nodoActual.getLeft();
							nodoActual.setLeft(nodoHijo);
							nodoHijo.setRight(nodoSiguiente);
						}else if(posicion == -1){
							BinaryTreeNode<Persona> nodoSiguiente = nodoActual.getLeft();
							while(nodoSiguiente.getRight()!=null)
								nodoSiguiente = nodoSiguiente.getRight();
							nodoSiguiente.setRight(nodoHijo);
						}else{
							BinaryTreeNode<Persona> nodoAnterior = nodoActual.getLeft();							
							BinaryTreeNode<Persona> nodoSiguiente = nodoAnterior.getRight();

							int i=1;
							while(i<posicion && nodoSiguiente!=null){
								i++;
								nodoAnterior = nodoSiguiente;
								nodoSiguiente = nodoSiguiente.getRight();
							}
							nodoAnterior.setRight(nodoHijo);
							nodoHijo.setRight(nodoSiguiente);
						}
					}
				}
			}
		}

		return encontrado;		
	}

	public boolean insertarHijo(Persona hijo, Persona padre){
		return insertarHijo(hijo, padre, -1);
	}

	public boolean insertarHijo(String cIdentidad, String nombre,String apellidos, Sexo sexo, Persona padre, int posicion){
		Persona hijo = new Persona(cIdentidad, nombre, apellidos, sexo);
		return insertarHijo(hijo, padre, posicion);
	}

	public boolean insertarHijo(String cIdentidad, String nombre,String apellidos, Sexo sexo, Persona padre){
		return insertarHijo(cIdentidad, nombre, apellidos, sexo, padre, -1);
	}
	*/
	 
}

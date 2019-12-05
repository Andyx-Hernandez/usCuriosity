package Auxiliar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class tips {

	private static ArrayList<String>tips = new ArrayList<String>();
	

	 public static void fillTips() {
		String tip1 = "Sabias que si eliminas al cabeza de familia el arbol geneologico se eliminara completo";
		String tip2 = "Has pensado en tener hijos.Puedes insertar hijos a cualquier nodo";
		String tip3 = "Puedes modificar a conveniencia la informacion de cada nodo de arbol";
		String tip4 = "Te agrada la vida en la ciudad.Cual sera tu preferida";
		String tip5 = "Al seleccionar con click derecho un nodo del arbol podras: Insertar un hijo , Modificar su informacion o eliminarlo";
		
		tips.add(tip1);
		tips.add(tip2);
		tips.add(tip3);
		tips.add(tip5);
		tips.add(tip4);
		
		
		
	}

	public static ArrayList<String> getTips() {
		return tips;
	}

	public static void setTips(ArrayList<String> tips) {

	}
	
	
}

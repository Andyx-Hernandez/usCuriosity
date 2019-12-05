package MetodosOrdenamiento;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import modelo.Persona;

public class OrdenarPor {


	public static void shellSort(ArrayList<Persona> lista) {
		int j,k,salto;
		Persona aux;
		salto=lista.size()/2;
		while(salto>0) {
			for (int i = salto; i < lista.size(); i++) {
				j=i-salto;
				while(j>=0){
					k= j + salto;

					if(lista.get(j).compareTo(lista.get(k)) > 0) {
						j=-1;
					}
					else{
						aux =lista.get(j);
						lista.set(j, lista.get(k));
						lista.set(k,aux);
						j-=salto;
					}
				}
			}
			salto=salto/2;
		}
	}


	

	public static void ordenamientoXInsercionNietos (ArrayList<Persona> listadoNietos){
		 int  j;
		    for (int i = 1; i < listadoNietos.size(); i++) {
		        Persona tmp = listadoNietos.get(i);
		        j = i;
		        while ((j > 0) && (listadoNietos.get(j - 1).obtenerEdad() >= tmp.obtenerEdad())) {
		            listadoNietos.set(j, listadoNietos.get(j - 1));
		            j--;
		        }
		        listadoNietos.set(j, tmp);
		    }  	  
	}
	
	/*public static void mergeSort(ArrayList<Persona> nietos) {
		// TODO Auto-generated method stub
		
	}*/
	
	public static void mergeSort(ArrayList<Persona> lista){
		mergeSort(lista, 0, lista.size()-1);
	}
	public static void mergeSort(ArrayList<Persona> lista, int i, int j){
		if(i<j){
			int m = (int) Math.ceil((i+j)/2);
			mergeSort(lista, i, m);
			mergeSort(lista, m+1, j);

			merge(lista, i, j, m);
		}
	}

	private static void merge(ArrayList<Persona> lista, int i, int j, int m) {
		ArrayList<Persona> aux = new ArrayList<Persona>();
		
		int p = i;
		int q = m+1;
		
		while(p<=m && q<=j){
			if(lista.get(p).compareTo(lista.get(q))>0){
				aux.add(lista.get(p));
				p++;
			}else{
				aux.add(lista.get(q));
				q++;
			}
		}
		while(p<=m){
			aux.add(lista.get(p));
			p++;
		}while(q<=j){
			aux.add(lista.get(q));
			q++;
		}
		
		int k=0;
		while(i<=j){
			lista.set(i, aux.get(k));
			i++;
			k++;
		}
	}
	

	public static GregorianCalendar CovierteAFecha(String cIdentidad) {

		GregorianCalendar fechaNacimiento = new GregorianCalendar();

		char[] primDigitos = {'0','0','0','0','0','0'}; //Creo el arreglo y lo inicializo

		cIdentidad.getChars(0, 6, primDigitos, 0);//Copio los Primeros 6 digitos del carnet que dicen la fecha	

		String anno= String.valueOf(primDigitos, 0, 2);//copio el valor del anno

		String mes = String.valueOf(primDigitos, 2, 2);//copio el valor del mes 

		String dia = String.valueOf(primDigitos, 4, 2);//copio el valor del dia


		if(Integer.valueOf(anno)>=20) {//Si es despues de 1920
			fechaNacimiento=new GregorianCalendar(1900 +(int)Integer.valueOf(anno), Integer.valueOf(mes), Integer.valueOf(dia));
		}

		else {//Si es antes de 2019
			fechaNacimiento=new GregorianCalendar(2000 + (int)Integer.valueOf(anno), Integer.valueOf(mes), Integer.valueOf(dia));
		}
		return fechaNacimiento;

	}
}

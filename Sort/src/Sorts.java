import java.util.List;

public class Sorts {
	public static <E extends Comparable<E>> void quickSort(List<E> list) {
		quickSort(list, 0, list.size() - 1);
	}
	
	private static <E extends Comparable<E>> void quickSort(List<E> list, int low, int high) {
		if(low < high) {
			int partition = partition(list, low, high);
			quickSort(list, low, partition-1);
			quickSort(list, partition+1, high);
		}
	}
	
	private static <E extends Comparable<E>> int partition(List<E> list, int low, int high) {
		int i = low + 1;
		int j = high;
		E pivot = list.get(low);
		
		while(i <= j) {
			if(list.get(i).compareTo(pivot) < 0) i++;
			
			else if(list.get(j).compareTo(pivot) > 0) j--;
			
			else if(i <= j) {
				swap(list,i,j);
				i++;
				j--;
			}
		}
		swap(list,low,j);
		return j;
	}
	
	private static <E extends Comparable<E>> void swap(List<E> list, int i, int j) {
		E aux = list.get(i);
		list.set(i, list.get(j));
		list.set(j, aux);
	}
	
	public static <E extends Comparable<E>> void insertionSort(List<E> list) {
		E pos;
		int j;
		
		for (int i = 1; i < list.size(); i++) {
			pos = list.get(i);
			
			j = i-1;
			for(; j >= 0 && list.get(j).compareTo(pos) > 0; j--) {
				list.set(j+1, list.get(j));
			}
			
			list.set(j+1, pos);
		}
	}
	
	public static <E extends Comparable<E>> void selectionSort(List<E> list) {
		int min;
		int size = list.size()-1;
		E aux;
		
		for (int i = 0; i < size; i++) {
			min = i;
			for(int j = i+1; j < list.size(); j++) {
				if(list.get(j).compareTo(list.get(min)) < 0)
					min = j;
			}
			if(i != min) {
				aux = list.get(i);
				list.set(i, list.get(min));
				list.set(min, aux);
			}
		}
	}
	
	public static <E extends Comparable<E>> void bubbleSort(List<E> list) {
		int size = list.size()-1;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - i; j++) {
				if(list.get(j).compareTo(list.get(j+1)) > 0) {
					swap(list, j, j+1);
				}
			}
		}
	}
}

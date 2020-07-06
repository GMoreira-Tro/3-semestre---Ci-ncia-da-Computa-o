import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Integer> list = generateIntList();
		System.out.println("Quick Sort:\n" + list);
		
		long time = System.nanoTime();
		Sorts.quickSort(list);
		System.out.println(("\n" + (System.nanoTime() - time)/1000000000f));
		System.out.println("\n" + list);
		
		list = generateIntList();
		System.out.println("\nJava Sort:\n" + list);
		
		time = System.nanoTime();
		list.sort(null);
		System.out.println(("\n" + (System.nanoTime() - time)/1000000000f));
		System.out.println("\n" + list);
		
		list = generateIntList();
		System.out.println("\nInsertion Sort:\n" + list);
		
		time = System.nanoTime();
		Sorts.insertionSort(list);
		System.out.println(("\n" + (System.nanoTime() - time)/1000000000f));
		System.out.println("\n" + list);
		
		list = generateIntList();
		System.out.println("\nSelection Sort:\n" + list);
		
		time = System.nanoTime();
		Sorts.selectionSort(list);
		System.out.println(("\n" + (System.nanoTime() - time)/1000000000f));
		System.out.println("\n" + list);
		
		list = generateIntList();
		System.out.println("\nBubble Sort:\n" + list);
		
		time = System.nanoTime();
		Sorts.bubbleSort(list);
		System.out.println(("\n" + (System.nanoTime() - time)/1000000000f));
		System.out.println("\n" + list);

	}
	
	public static List<Integer> generateIntList() {
		int rand = 16;
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < rand; i++) {
			list.add((int)(Math.random() * 50));
		}
		
		return list;
	}

}

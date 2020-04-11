public class Main {

	public static void main(String[] args) throws DuplicatedKeyException {
		AVLTree arvore = new AVLTree();
		
		for (int i = 0; i < 15; i++) {
			//int rand = (int)(Math.random()*51);
			int rand = i + 12;
			try {
				arvore.insert(rand);
			} catch (DuplicatedKeyException e) {}
			System.out.println(rand);
		}
		
		System.out.println("\n" + arvore + "\n");
		
		for (int i = 10; i < 15; i++) {
			System.out.println("Tem o n�mero: " + i + " ? " + arvore.binarySearch(i));
		}
		
		System.out.println();
		for(int i = 10; i < 20; i++) {
			System.out.println("Tentando remover o elemento: " + i);
			arvore.remove(i);
		}
		
		System.out.println("\n" + arvore);
	}

}

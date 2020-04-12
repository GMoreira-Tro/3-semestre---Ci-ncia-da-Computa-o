public class Main {

	public static void main(String[] args) throws DuplicatedKeyException {
		AVLTree arvore = new AVLTree();
		
		for (int i = 0; i < 15; i++) {
			int rand = (int)(Math.random()*31);
			//int rand = i + 12;
			try {
				arvore.insert(rand);
			} catch (DuplicatedKeyException e) {}
			System.out.println(rand);
		}
		
		System.out.println("\n" + arvore + "\n");
		
		for (int i = 10; i < 15; i++) {
			System.out.println("Tem o n�mero: " + i + " ? " + arvore.binarySearch(i));
		}
		
		for(int i = 10; i < 30; i++) {
			System.out.println("\nTentando remover o elemento: " + i);
			arvore.remove(i);
		}
		
		System.out.println("\n" + arvore + "\n\nN�mero de elementos: " + arvore.numElements);
		System.out.println("\nTransformando em ArrayList: " + arvore.toArrayList());
	}

}

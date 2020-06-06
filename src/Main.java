import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws DuplicatedKeyException {
		Scanner s = new Scanner(System.in);
		File file = new File("logArvore.txt");
		
		AVLTree<Integer> arvore = new AVLTree();
		char op;
		String param;
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String line = in.readLine();
			
			while (line != null) {
				/* Lê comando e parâmetro */
				op = line.charAt(0);
				param = line.substring(1, line.length());
				
				/* Valida o parâmetro e executa o comando correspondente */
				if(validaParam(param)) {
					int intParam = Integer.parseInt(param);
					
					switch(op) {
					/* Insert */
					case 'i':
						try {
							System.out.println("Inserindo " + param);
							arvore.insert(intParam);
							System.out.println("Número elementos árvore: " + arvore.numElements + "\n");
						} catch (DuplicatedKeyException e) {
							System.out.println("Tentativa de inserção duplicada\n");
						}
						
						break;
					/* Search */
					case 'b':
						System.out.println("Buscando o elemento: " + param + " ...");
						System.out.println("Existe " + param + "? " + arvore.binarySearch(intParam) + "\n");
						break;
					/* Remove */
					case 'r':
						
						System.out.println("Tentando remover o elemento: " + param + " ...");
						arvore.remove(intParam);
						System.out.println("Número elementos árvore:" + arvore.numElements + "\n");
						break;
					}					
				}
				
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo \""+file+"\" não existe.");
		} catch (IOException e) {
			System.out.println("Erro na leitura de " + file+".");
		}
			
		/* Leitura de valores do teclado para os comandos */
		
		/*do {
			switch(op.toLowerCase()) {
			case "i":
				System.out.print("Digite o número que deseja inserir: ");
				param = Integer.parseInt(s.nextLine());
				
				try {
					arvore.insert(param);
				} catch (DuplicatedKeyException e) {}
				
				break;
			case "b":
				System.out.print("Digite o número que deseja buscar: ");
				param = Integer.parseInt(s.nextLine());
				
				System.out.println("\nBuscando o elemento: " + param + " ...");
				System.out.println("Existe " + param + " ? " + arvore.binarySearch(param));
				break;
			case "r":	
				System.out.print("Digite o número que deseja remover: ");
				param = Integer.parseInt(s.nextLine());
				
				System.out.println("\nTentando remover o elemento: " + param + " ...");
				arvore.remove(param);
				break;
			default:
				break;	
			}
			System.out.println("\n" + arvore + "\n\nNúmero de elementos: " + arvore.numElements);
			System.out.print("\n+------------+"
						  + "\n+    Menu    +"
						  + "\n- i: inserção"
						  + "\n- b: busca"
						  + "\n- r: remoção"
						  + "\n- s: sair"
						  + "\n: ");
		
			op = s.nextLine();	
			
		} while(!op.toLowerCase().equals("s"));*/
		
		System.out.println("Arvorezita:\n" + arvore + "\n");
		System.out.println("Transformando em ArrayList: " + arvore.toArrayList());
	}
	
	private static boolean validaParam(String param) {
		try {
	        int i = Integer.parseInt(param);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
		return true;
	}
}


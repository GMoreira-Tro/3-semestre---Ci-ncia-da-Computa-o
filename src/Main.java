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
	
	private static boolean validaCPF(long cpf) {
		return String.valueOf(cpf).length() == 11;
	}
	
	private static boolean validaRG(long rg) {
		return String.valueOf(rg).length() == 9;
	}
	
	private static boolean validaData(String data) {
		String[] calendar = data.split("/");
		
		if (calendar.length != 3) {
			return false;
		}
		
		try {
			int year = Integer.parseInt(calendar[2]);
			if (year > 9999 || year < 1000) {
				return false;
			}
			
			int month = Integer.parseInt(calendar[1]);
			if (month > 12 || month < 1) {
				return false;
			}
			
			int day = Integer.parseInt(calendar[0]);
			if (day > 31 || day < 1) {
				return false;
			}
			else if (day == 31) {
				if(month < 8) {
					if (month % 2 == 0) {
						return false;
					}
				}
				else {
					if (month % 2 == 1) {
						return false;
					}
				}
			}
			else if (day == 29 && month == 2) {
				if (!ehAnoBissexto(year)) {
					return false;
				}
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
		
	}
	
	private static boolean ehAnoBissexto(int ano) {
		return (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0));
	}
}


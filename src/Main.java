import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
	
	private static JFrame frame;

	public static void main(String[] args) throws DuplicatedKeyException {
		Scanner s = new Scanner(System.in);
		
		File file;
		
		 JFileChooser chooser = new JFileChooser();
	     FileNameExtensionFilter ext = new FileNameExtensionFilter(
	                "Text", "txt", "csv");
	     chooser.setFileFilter(ext);
	        
	     int returnVal = chooser.showOpenDialog(frame);
	     if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	 file = new File(chooser.getSelectedFile().getAbsolutePath());
	    	 System.out.println(file);
	      }
	     else {
	    	 file = new File("logArvore.txt");
	    }
	
		char op = '0';
		
		List<Pessoa> listaPessoas = new PessoaDAO().buscaPessoas(file);		
		
		/* Arvore Nomes */
		AVLTree<Pessoa> arvoreNomes = new AVLTree( (element,  other) -> {
			return ((Pessoa)element).nome.compareTo(((Pessoa)other).nome);
		});

		/* Arvore CPF */
		AVLTree<Pessoa> arvoreCpf = new AVLTree( (element, other) -> {
			if (((Pessoa)element).cpf > ((Pessoa)other).cpf)
				return 1;
			else if (((Pessoa)element).cpf == ((Pessoa)other).cpf)
				return 0;
			else 
				return -1;
		});
		
		/* Arvore Data */
		AVLTree<Pessoa> arvoreDatas = new AVLTree( (element, other) -> {
			return ((Pessoa)element).dataNascimento.compareTo(((Pessoa)other).dataNascimento);
		});
		
		for(Pessoa p : listaPessoas) {	
			try {
				arvoreCpf.insert(p);
				arvoreNomes.insertButCanDuplicateKeys(p);
				arvoreDatas.insertButCanDuplicateKeys(p);
			}
			catch(DuplicatedKeyException e) {}
		}
		
		while(op != 9) {
		
			System.out.print("Menu"
					+ "\n1. Consultar por CPF"
					+ "\n2. Consultar pelo nome"
					+ "\n3. Consultar pela data de nascimento"
					+ "\n4. Sair"
					+ "\n: ");
		
			try {
				op = (s.nextLine().charAt(0));
			} catch (Exception e) {
				
			}
			
			switch(op) {
			case '1':
				break;
			
			case '2':
				break;
			
			case '3':
				break;
			
			default:
				System.out.println("");
				break;
				
			}
		}	
	}		
}
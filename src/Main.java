import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
	
	private static JFrame frame;
	private static Scanner s = new Scanner(System.in);

	public static void main(String[] args) throws DuplicatedKeyException, ParseException {
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
			return (((Pessoa)element).nome.compareTo(((Pessoa)other).nome));
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
			return (((Pessoa)element).dataNascimento.compareTo(((Pessoa)other).dataNascimento));
		});
		
		for(Pessoa p : listaPessoas) {	
			try {
				arvoreCpf.insert(p);
				arvoreNomes.insertButCanDuplicateKeys(p);
				arvoreDatas.insertButCanDuplicateKeys(p);
			}
			catch(DuplicatedKeyException e) {}
		}
		
		while(op != '4') {
		
			System.out.print(
					"1. Consultar por CPF"
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
				consultaPorCpf(arvoreCpf);
				break;
			
			case '2':
				consultaPorNomes(arvoreNomes);
				break;
			
			case '3':
				consultaPorDataDeNascimento(arvoreDatas);
				break;
			
			default:
				System.out.println();
				break;
				
			}
		}	
	}
	
	private static void consultaPorCpf(AVLTree<Pessoa> arvore) {
		try {
			System.out.print("\nInforme o CPF a ser pesquisado: ");
			long cpf = Long.parseLong(s.nextLine());
			if (!PessoaDAO.validaCPF(cpf))
				throw new NumberFormatException();
			
			DelegatedAttribute delegate = ( (element) -> {
				return (((Pessoa)element).cpf);
			});
			Pessoa p = arvore.binarySearchElementByAttribute(cpf, delegate);
			
			if(p != null) {
				System.out.println("\n" + p + "\n");
			}
			else {
				System.out.println("\nPessoa não encontrada!");
			}
			
		}
		catch(NumberFormatException e) {
			System.out.println("\nCPF inválido!");
		}
	}
	
	private static void consultaPorNomes(AVLTree<Pessoa> arvore) {
		while(true) {
			//System.out.println(arvore);
			try {
				System.out.println("\nInforme o começo do nome das pessoas: ");
				String start = s.nextLine();
				if(start.length() == 0)
					throw new StringIndexOutOfBoundsException();
				
				ArrayList<Pessoa> al = arvore.toArrayList( (element) -> {
				return (((Pessoa)element).nome);}, start);
				 
				for (Pessoa pessoa : al) {
					System.out.println("\n" + pessoa + "\n");
				}
				
				if(al.isEmpty())
					System.out.println("\nNenhuma pessoa encontrada!");
				
				break;
			}
		catch (StringIndexOutOfBoundsException e) {
			
			}
		}
	}
	
	private static void consultaPorDataDeNascimento(AVLTree<Pessoa> arvore) throws ParseException {
		try {
			System.out.print("\nInforme a data de início: ");
			String dataInicioS = s.nextLine();
			
			if(!PessoaDAO.validaData(dataInicioS))
				throw new IllegalArgumentException();
			
			System.out.print("\nInforme a data de término: ");
			String dataTerminoS = s.nextLine();
			if(!PessoaDAO.validaData(dataTerminoS))
				throw new IllegalArgumentException();
			
			Date dataInicio = PessoaDAO.formataData(dataInicioS);
			Date dataTermino = PessoaDAO.formataData(dataTerminoS);
			
			if(dataTermino.compareTo(dataInicio) <= 0)
				throw new IllegalArgumentException();
			
			ArrayList<Pessoa> al = arvore.toArrayList( (element) -> {
				return (((Pessoa)element).dataNascimento);}, dataInicio, dataTermino);
				 
				for (Pessoa pessoa : al) {
					System.out.println("\n" + pessoa + "\n");
				}
				
				if(al.isEmpty())
					System.out.println("\nNenhuma pessoa encontrada!"); 
		}
		catch(IllegalArgumentException e) {
			System.out.println("\nData inválida!");
		}
	}
}
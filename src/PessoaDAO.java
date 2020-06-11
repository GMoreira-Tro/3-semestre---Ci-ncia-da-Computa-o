import java.io.*;
import java.text.*;
import java.util.*;

public class PessoaDAO {

	public static List<Pessoa> buscaPessoas(File file) {
		List<Pessoa> listaPessoas = new ArrayList();
		Set<String> setRg = new HashSet();
		Set<String> setCpf = new HashSet();
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String line = in.readLine();
			
			while (line != null) {		
				String[] info = line.split(";");
				
				if(info.length != 5);
				else {
					String nome = info[2];
					String cpf = info[0];
					String rg = info[1];
					String dataNascimento = info[3];
					String cidade = info[4];
					
					if(!setRg.contains(rg) && !setCpf.contains(cpf)) {
						setRg.add(rg);
						setCpf.add(cpf);
						
						Pessoa pessoa = criaPessoa(nome, cpf, rg, dataNascimento, cidade);
						
						if(pessoa != null) listaPessoas.add(pessoa);
					}
					/*else if(setRg.contains(rg) && !setCpf.contains(cpf)) {
						for (Pessoa p : listaPessoas) {
							if (String.valueOf(p.rg).equals(rg)) {
								listaPessoas.remove(p);
								break;
							}
						}
					}
					else {
						for (Pessoa p : listaPessoas) {
							if (String.valueOf(p.cpf).equals(cpf)) {
								listaPessoas.remove(p);
								break;
							}
						}
					}*/
				}
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo \""+file+"\" não existe.");
		} catch (IOException e) {
			System.out.println("Erro na leitura de " + file+".");
		}
		
		return listaPessoas;
	}
	
	public static Pessoa criaPessoa(String nome, String cpf, String rg, String dataNascimento, String cidade) {
		
		if(!validaParamLong(cpf) || !validaParamLong(rg)) return null;
		
		long cpfPessoa = Long.parseLong(cpf);
		long rgPessoa = Long.parseLong(rg);
		
		if(!validaCPF(cpfPessoa) || /*!validaRG(rgPessoa) ||*/ !validaData(dataNascimento)) return null;
		
		java.sql.Date dataPessoa = null;
		try {
			dataPessoa = formataData(dataNascimento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		return new Pessoa(nome, cpfPessoa, rgPessoa, dataPessoa, cidade);
	}
	
	/* Validação dos dados */
	public static boolean validaParamLong(String param) {
		try {
	        long i = Long.parseLong(param);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
		return true;
	}
	public static boolean validaCPF(long cpf) {
		if (cpf < 0)
			return false;
		return String.valueOf(cpf).length() == 11;
	}

	
	/* Revisar validação do RG*/
	public static boolean validaRG(long rg) {
		if (rg < 0)
			return false;
		return String.valueOf(rg).length() == 9;
	}
	public static boolean validaData(String data) {
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
	public static java.sql.Date formataData(String data) throws ParseException { 
 		if (data == null || data.equals(""))
 			return null;
 		
         java.sql.Date date = null;
         try {
             DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
             date = new java.sql.Date( ((java.util.Date)formatter.parse(data)).getTime() );
         } catch (ParseException e) {            
             throw e;
         }
         return date;
 	}
	public static boolean ehAnoBissexto(int ano) {
		return (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0));
	}
}

public class Pessoa {
	protected String nome;
	protected long cpf;
	protected long rg;
	protected String dataNascimento;
	protected String cidade;
	
	public Pessoa(String nome, long cpf, long rg, String dataNascimento, String cidade) {
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
		this.dataNascimento = dataNascimento;
		this.cidade = cidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	public long getRg() {
		return rg;
	}

	public void setRg(long rg) {
		this.rg = rg;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		return "\nNome: " + this.nome +
				"\nCpf: " + this.cpf +
				"\nRg: " + this.rg +
				"\nData de nascimento: " + this.dataNascimento +
				"\nCidade natal: " + this.cidade;
	}
} 

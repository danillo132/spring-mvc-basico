package curso.spring.model;


public enum Cargo {

	JUNIOR("Júnior"),
	PLENO("Pleno"),
	SENIOR("Sênior");
	
	private String nome;
	private String valor;
	
	
	
	
	public String getValor() {
		return valor = this.name();
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	private Cargo(String name) {
		this.nome = name;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	
}

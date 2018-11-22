package entity;

public class Modello {
	
	private String nome;
	
	public Modello(){
		this.nome = null;
	}
	
	public Modello(String nome){
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}

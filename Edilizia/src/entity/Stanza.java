package entity;

public class Stanza {
	
	private String nome;
	private String edificio;
	private String piano;
	private String tipo;
	
	public Stanza(){
		this.nome = null;
		this.edificio = null;
		this.piano = null;
		this.tipo = null;
	}
	
	public Stanza(String nome, String edificio, String piano, String tipo){
		this.nome = nome;
		this.edificio = edificio;
		this.piano = piano;
		this.tipo = tipo;
	}
	
	public String getNome(){
		return nome;
	}
	
	public String getEdificio() {
		return edificio;
	}

	public String getPiano() {
		return piano;
	}

	public String getTipo() {
		return tipo;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}

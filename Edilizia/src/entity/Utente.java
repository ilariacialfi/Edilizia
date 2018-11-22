package entity;

public class Utente {
	
	private String id;
	private String nome;
	private String cognome;
	private String email;
	private String pass;
	private String ruolo;
	
	public Utente(){
		this.id = null;
		this.nome = null;
		this.cognome = null;
		this.email = null;
		this.pass = null;
		this.ruolo = null;
	}
	
	public Utente(String id, String nome, String cognome, String email, String pass, String ruolo){
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.pass = pass;
		this.ruolo = ruolo;
	}
	
	//mi servirano questi metodi??
	public String getId(){
		return id;
	}
	public String getNome(){
		return nome;
	}
	
	public String getCognome(){
		return cognome;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPass(){
		return pass;
	}
	
	public String getRuolo(){
		return ruolo;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public void setCognome(String cognome){
		this.cognome = cognome;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setPass(String pass){
		this.pass = pass;
	}
	
	public void setRuolo(String ruolo){
		this.ruolo = ruolo;
	}
	
}

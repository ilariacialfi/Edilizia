package entity;

public class AttrezzaturaStanza {
	
	private String attr;
	private String stanza;
	private int quantita;
	
	public AttrezzaturaStanza(){
		this.attr = null;
		this.stanza = null;
		this.quantita = 0;
	}
	
	public AttrezzaturaStanza(String attr, String stanza, int quantita){
		this.attr = attr;
		this.stanza = stanza;
		this.quantita = quantita;
	}
	
	public String getStanza(){
		return stanza;
	}
	
	public String getAttr() {
		return attr;
	}
	
	public int getQuantita() {
		return quantita;
	}
	
	public void setStanza(String stanza){
		this.stanza = stanza;
	}
	
	public void setAttr(String attr) {
		this.attr = attr;
	}
	
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	
	
}

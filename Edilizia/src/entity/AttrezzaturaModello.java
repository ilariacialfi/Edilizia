package entity;

public class AttrezzaturaModello {

	private String attr;
	private String modello;

	public AttrezzaturaModello() {
		this.attr = null;
		this.modello = null;
	}

	public AttrezzaturaModello(String attr, String modello) {
		this.attr = attr;
		this.modello = modello;
	}

	public String getAttr() {
		return attr;
	}

	public String getModello() {
		return modello;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}
}

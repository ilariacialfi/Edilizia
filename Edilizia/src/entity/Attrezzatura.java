package entity;

public class Attrezzatura {

	private String nome;
	private int min;
	private int max;

	public Attrezzatura() {
		this.nome = null;
		this.min = 0;
		this.max = 0;
	}

	public Attrezzatura(String nome, int min, int max) {
		this.nome = nome;
		this.min = min;
		this.max = max;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
}

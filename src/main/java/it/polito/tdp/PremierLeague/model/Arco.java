package it.polito.tdp.PremierLeague.model;

public class Arco implements Comparable<Arco>{
	Team sorgente;
	Team destinazione;
	int peso;
	public Arco(Team sorgente, Team destinazione, int peso) {
		super();
		this.sorgente = sorgente;
		this.destinazione = destinazione;
		this.peso = peso;
	}
	public Team getSorgente() {
		return sorgente;
	}
	public void setSorgente(Team sorgente) {
		this.sorgente = sorgente;
	}
	public Team getDestinazione() {
		return destinazione;
	}
	public void setT2(Team destinazione) {
		this.destinazione = destinazione;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	@Override
	public int compareTo(Arco o) {
		// TODO Auto-generated method stub
		return this.getPeso()-o.getPeso();
	}
	
	

}

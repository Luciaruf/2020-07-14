package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	private Graph<Team, DefaultWeightedEdge> grafo;
	
	
	
	
	public Model() {
		this.dao = new PremierLeagueDAO();
	}

	public Graph<Team,DefaultWeightedEdge> creaGrafo(){
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.listAllTeams());
	
		
		for(Team t1 : this.grafo.vertexSet()) {
			for(Team t2 : this.grafo.vertexSet()) {
				if(t1.getTeamID() != t2.getTeamID()) {
					Arco a = getArco(t1, t2);
					if(a!=null) {
						Graphs.addEdgeWithVertices(this.grafo, a.getSorgente(), a.getDestinazione(), a.getPeso());
						
					}
				}
			}
		}
		
		return grafo;
	}
	
	public Arco getArco(Team t1, Team t2) {
		
		List<Match> matchPerTeam1Casa = this.dao.listMatchByTeamHome(t1);
		List<Match> matchPerTeam2Casa = this.dao.listMatchByTeamHome(t2);
		
		int classifica1 = 0;
		int classifica2 = 0;
		
		//punteggio solo per partite giocate in casa
		for(Match m1 : matchPerTeam1Casa) {
			if(m1.resultOfTeamHome==1) {
				classifica1+=3;
			}
			else if(m1.resultOfTeamHome==0){
				classifica1+=1;
			}
		}
		
		for(Match m2 : matchPerTeam2Casa) {
			if(m2.resultOfTeamHome==1) {
				classifica2+=3;
			}
			else if(m2.resultOfTeamHome==0){
				classifica2+=1;
			}
		}
		
		List<Match> matchPerTeam1Away = this.dao.listMatchByTeamAway(t1);
		List<Match> matchPerTeam2Away = this.dao.listMatchByTeamAway(t2);
		
		for(Match a1 : matchPerTeam1Away) {
			if(a1.resultOfTeamHome==-1) {
				classifica1+=3;
			}
			else if(a1.resultOfTeamHome==0){
				classifica1+=1;
			}
		}
		
		for(Match a2 : matchPerTeam2Away) {
			if(a2.resultOfTeamHome==-1) {
				classifica2+=3;
			}
			else if(a2.resultOfTeamHome==0){
				classifica2+=1;
			}
		}
		
		int peso = 0;
		Arco arco=null;
		
		if(classifica1>classifica2) {
			peso = classifica1-classifica2;
			arco = new Arco(t1,t2,peso);
		}
		else if(classifica2>classifica1) {
			peso = classifica2-classifica1;
			arco = new Arco(t2,t1,peso);
		}
		//caso in cui il totale sia in entrambi 0?
		
		return arco;
	}
	
	
}

package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Map<Integer, Airport> idMapAirport; 
	private ExtFlightDelaysDAO dao; 
	private Graph<Airport, DefaultWeightedEdge> graph; 
	
	
	public Model() {
		this.idMapAirport= new HashMap<>(); 
		this.dao= new ExtFlightDelaysDAO(); 
		dao.loadAllAirports(idMapAirport);
	}
	
	public void creaGrafo(double miglia) {
		this.graph= new SimpleWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		
		//vertici
		Graphs.addAllVertices(this.graph, this.idMapAirport.values()); 
		
		//archi 
		for (CoppiaAirports c : dao.getAirportsCollegati(idMapAirport)) {
			// non essendo orientato mi chiedo se l'arco esiste gia' per quella coppia
			DefaultWeightedEdge e= this.graph.getEdge(c.getA1(), c.getA2()); 
			
			if(e==null) {
				//lo aggiungo se soddifa la condizione
				if(c.getPeso()> miglia) {
				Graphs.addEdge(this.graph, c.getA1(), c.getA2(), c.getPeso()); 
				}
			}
			else {
				//aggiorno il peso
				double peso= this.graph.getEdgeWeight(e); 
				double pesoNuovo= (peso+c.getPeso())/2; 
				//non faccio un nuovo arco ma semplicemente vi aggiorno il peso a quello gia' presente
				this.graph.setEdgeWeight(e, pesoNuovo);
			}
		}
		
	}

	public int nVertex() {
		return this.graph.vertexSet().size(); 
	}
	public int nArchi() {
		return this.graph.edgeSet().size(); 
	}
	public List<CoppiaAirports> getAirportsDelGrafo(){
		List<DefaultWeightedEdge> listaArchi= new ArrayList<>(this.graph.edgeSet());
		List<CoppiaAirports> coppie= new ArrayList<>(); 
		
		for (DefaultWeightedEdge e : listaArchi) {
			Airport p= this.graph.getEdgeSource(e); 
			Airport a= this.graph.getEdgeTarget(e); 
			double peso= this.graph.getEdgeWeight(e);
			
			CoppiaAirports co= new CoppiaAirports(p, a, peso); 
			coppie.add(co); 
		}
		return coppie; 
		
		
	}
}

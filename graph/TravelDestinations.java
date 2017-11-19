import java.util.*;

public class TravelDestinations {

	private Graph<String, Integer> graph;

	public TravelDestinations(Graph<String, Integer> graph) {
		this.graph = graph;
	}

	/* Exercise 1 methods */

	/**
	 * Return all the countries that are a single direct flight away from the
	 * given country, in any order. If no flights depart this country, return an
	 * empty List.
	 */
	public List<String> getDirectDestinations(String fromCountry) {
		ArrayList<String> destinations = new ArrayList<String>();
		if(graph== null){
			return destinations;
		}
		Iterable<Vertex<String>> vertices = graph.vertices();
		
		Vertex<String> from = null;
		//get the vertex of from country
		for(Vertex<String> vertex: vertices){
			if(vertex.getElement().toString().equals(fromCountry)){
				from = vertex;
		}
	}
			if(from==null){
				return destinations;
			}
			//get all the edges
			Iterable<Edge<Integer>> edges = graph.outgoingEdges(from);
			Vertex<String> to = null;
				if(edges==null){
					return destinations;
				}
			for(Edge<Integer> edge: edges){
				to = graph.opposite(from, edge);
				if(graph.opposite(to, edge).equals(from)){
				destinations.add(to.getElement().toString());
				}			
			}
	return destinations;
	}

	/**
	 * Return true if there is a direct flight from 'fromCountry' to
	 * 'toCountry'. Otherwise, return false.
	 */
	public boolean isDirectFlight(String fromCountry, String toCountry) {
		if(graph== null){
			return false;
		}
		Iterable<Vertex<String>> vertices = graph.vertices();
			Vertex<String> from = null;
				for(Vertex<String> vertex: vertices){
					if(vertex.getElement().toString().equals(fromCountry)){
						 from = vertex;
					}
				}
		if(from == null){
				return false;
			}
		
		Iterable<Edge<Integer>> edges = graph.outgoingEdges(from);
		for(Edge<Integer> edge: edges){
			if(graph.opposite(from,edge).getElement().toString().equals(toCountry)){
				return true;
			}
		}
		return false;
	}

	/* Exercise 2 methods */
	/**
	 * Return all the countries that are reachable from the given country, using
	 * any number of flights (for example, if we can fly from A to B, then from
	 * B to C, then we can say that both B and C are reachable from A.
	 */
	public List<String> getReachableDestinations(String country) {
		ArrayList<String> countries = new ArrayList<String>();
		Set<String> countries1 = new HashSet<>();
		ArrayList<String> countries2 = new ArrayList<String>();
		countries2.addAll(getDirectDestinations(country));
		countries1.addAll(getDirectDestinations(country));
		while(true){
			for(int i = 0; i < countries2.size(); i++){
				countries2.addAll(getDirectDestinations(countries2.get(i)));
			}
			
			if(countries1.containsAll(countries2)){
				break;
			}
			countries1.addAll(countries2);
				
		}
		Object[] countries3 = countries1.toArray();
		for(int i = 0; i < countries3.length; i++){
			countries.add(countries3[i].toString());
		}
		return countries;
		}
	
}

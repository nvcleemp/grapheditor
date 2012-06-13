package be.ugent.caagt.grapheditor.client.data;

public class Edge {
	
	private final Vertex from;
	private final Vertex to;
	
	public Edge(Vertex from, Vertex to) {
		this.from = from;
		this.to = to;
	}
	
	public Vertex getFrom() {
		return from;
	}

	public Vertex getTo() {
		return to;
	}

	public boolean contains(Vertex v){
		return v!=null && (v.equals(from) || v.equals(to));
	}
	
	public boolean startsIn(Vertex v){
		return v!=null && v.equals(from);
	}
	
	public boolean endsIn(Vertex v){
		return v!=null && v.equals(to);
	}
}

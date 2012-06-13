package be.ugent.caagt.grapheditor.client.data;

import java.util.ArrayList;

public class Graph {
	
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	private VertexListener vertexListener = new VertexListener() {
		
		@Override
		public void coordinatesChanged(Vertex source, int x, int y) {
			fireVertexEmbeddingChanged(source);
		}
	};
	
	public int getOrder(){
		return vertices.size();
	}
	
	public int getSize(){
		return edges.size();
	}
	
	public Vertex getVertex(int i){
		return vertices.get(i);
	}

	public Vertex addVertex(int x, int y){
		Vertex v = new Vertex(x, y);
		addVertex(v);
		return v;
	}
	
	public void addVertex(Vertex v){
		if(vertices.contains(v)) return;

		v.addVertexListener(vertexListener);
		vertices.add(v);
		fireVertexAdded(v);
	}
	
	public void removeVertex(Vertex v){
		//build list of edges that need to be removed
		ArrayList<Edge> toRemove = new ArrayList<Edge>();
		for(Edge e : edges){
			if(e.contains(v)){
				toRemove.add(e);
			}
		}
		
		//remove these edges
		for(Edge e : toRemove){
			removeEdge(e);
		}
		
		//remove the vertex
		vertices.remove(v);
		v.removeVertexListener(vertexListener);
		fireVertexRemoved(v);
	}
	
	public Edge getEdge(int i){
		return edges.get(i);
	}
	
	public Edge addEdge(Vertex from, Vertex to){
		Edge e = new Edge(from, to);
		addEdge(e);
		return e;
	}
	
	public void addEdge(Edge e){
		if(edges.contains(e) ||
				!vertices.contains(e.getFrom()) ||
				!vertices.contains(e.getTo()) ||
				areAdjacent(e.getFrom(), e.getTo()))
			return;
		edges.add(e);
		fireEdgeAdded(e);
	}
	
	public void removeEdge(Edge e){
		edges.remove(e);
		fireEdgeRemoved(e);
	}
	
	public void clear(){
		vertices.clear();
		edges.clear();
		fireGraphCleared();
	}
	
	public boolean areAdjacent(Vertex v1, Vertex v2){
		int i = 0;
		while(i<edges.size() && !(edges.get(i).contains(v1) && edges.get(i).contains(v2))){
			i++;
		}
		return i != edges.size();
	}
	
	private ArrayList<GraphListener> listeners = new ArrayList<GraphListener>();
	
	public void addGraphListener(GraphListener l){
		listeners.add(l);
	}
	
	public void removeGraphListener(GraphListener l){
		listeners.remove(l);
	}
	
	private void fireVertexAdded(Vertex v){
		for(GraphListener l : listeners){
			l.vertexAdded(v);
		}
	}
	
	private void fireVertexRemoved(Vertex v){
		for(GraphListener l : listeners){
			l.vertexRemoved(v);
		}
	}
	
	private void fireVertexEmbeddingChanged(Vertex v){
		for(GraphListener l : listeners){
			l.vertexEmbeddingChanged(v);
		}
	}
	
	private void fireEdgeAdded(Edge e){
		for(GraphListener l : listeners){
			l.edgeAdded(e);
		}
	}
	
	private void fireEdgeRemoved(Edge e){
		for(GraphListener l : listeners){
			l.edgeRemoved(e);
		}
	}
	
	private void fireGraphCleared(){
		for(GraphListener l : listeners){
			l.graphCleared();
		}
	}
}

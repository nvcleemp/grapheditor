package be.ugent.caagt.grapheditor.client.data;

public interface GraphListener {
	void vertexAdded(Vertex v);
	void vertexRemoved(Vertex v);
	void vertexEmbeddingChanged(Vertex v);
	
	void edgeAdded(Edge e);
	void edgeRemoved(Edge e);
	
	void graphCleared();
}

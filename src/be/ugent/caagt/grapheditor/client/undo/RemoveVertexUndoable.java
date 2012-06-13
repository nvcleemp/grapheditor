package be.ugent.caagt.grapheditor.client.undo;

import be.ugent.caagt.grapheditor.client.data.Graph;
import be.ugent.caagt.grapheditor.client.data.Vertex;

public class RemoveVertexUndoable extends AbstractUndoable {
	
	private final Vertex v;

	public RemoveVertexUndoable(Graph graph, Vertex v) {
		super(graph);
		this.v = v;
	}

	@Override
	public void undo() {
		graph.addVertex(v);
	}

	@Override
	public void redo() {
		graph.removeVertex(v);
	}

}

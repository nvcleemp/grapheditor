package be.ugent.caagt.grapheditor.client.undo;

import be.ugent.caagt.grapheditor.client.data.Graph;
import be.ugent.caagt.grapheditor.client.data.Vertex;

public class AddVertexUndoable extends AbstractUndoable {
	
	private final Vertex v;

	public AddVertexUndoable(Graph graph, Vertex v) {
		super(graph);
		this.v = v;
	}

	@Override
	public void undo() {
		graph.removeVertex(v);
	}

	@Override
	public void redo() {
		graph.addVertex(v);
	}

}

package be.ugent.caagt.grapheditor.client.undo;

import be.ugent.caagt.grapheditor.client.data.Edge;
import be.ugent.caagt.grapheditor.client.data.Graph;

public class AddEdgeUndoable extends AbstractUndoable {
	
	private final Edge e;

	public AddEdgeUndoable(Graph graph, Edge e) {
		super(graph);
		this.e = e;
	}

	@Override
	public void undo() {
		graph.removeEdge(e);
	}

	@Override
	public void redo() {
		graph.addEdge(e);
	}

}

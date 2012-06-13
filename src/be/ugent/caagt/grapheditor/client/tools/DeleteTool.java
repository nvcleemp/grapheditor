package be.ugent.caagt.grapheditor.client.tools;

import be.ugent.caagt.grapheditor.client.GraphSelectionModel;
import be.ugent.caagt.grapheditor.client.data.Edge;
import be.ugent.caagt.grapheditor.client.data.Graph;
import be.ugent.caagt.grapheditor.client.data.Vertex;
import be.ugent.caagt.grapheditor.client.undo.RemoveEdgeUndoable;
import be.ugent.caagt.grapheditor.client.undo.RemoveVertexUndoable;
import be.ugent.caagt.grapheditor.client.undo.UndoManager;

public class DeleteTool extends AbstractGraphEditorTool {

	public DeleteTool(Graph graph, GraphSelectionModel selectionModel, UndoManager undoManager) {
		super(graph, selectionModel, undoManager);
	}

	@Override
	public void handleVertexClick(Vertex vertex) {
		graph.removeVertex(vertex);
		undoManager.addUndoable(new RemoveVertexUndoable(graph, vertex));
	}

	@Override
	public void handleEdgeClick(Edge edge) {
		graph.removeEdge(edge);
		undoManager.addUndoable(new RemoveEdgeUndoable(graph, edge));
	}

}

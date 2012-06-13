package be.ugent.caagt.grapheditor.client.tools;

import be.ugent.caagt.grapheditor.client.GraphSelectionModel;
import be.ugent.caagt.grapheditor.client.data.Graph;
import be.ugent.caagt.grapheditor.client.data.Vertex;
import be.ugent.caagt.grapheditor.client.undo.AddVertexUndoable;
import be.ugent.caagt.grapheditor.client.undo.UndoManager;

public class AddTool extends AbstractGraphEditorTool {

	public AddTool(Graph graph, GraphSelectionModel selectionModel, UndoManager undoManager) {
		super(graph, selectionModel, undoManager);
	}

	@Override
	public void handleCanvasClick(int x, int y) {
		Vertex v = graph.addVertex(x, y);
		undoManager.addUndoable(new AddVertexUndoable(graph, v));
	}

}

package be.ugent.caagt.grapheditor.client.tools;

import be.ugent.caagt.grapheditor.client.GraphSelectionModel;
import be.ugent.caagt.grapheditor.client.data.Edge;
import be.ugent.caagt.grapheditor.client.data.Graph;
import be.ugent.caagt.grapheditor.client.data.Vertex;
import be.ugent.caagt.grapheditor.client.undo.UndoManager;

public abstract class AbstractGraphEditorTool implements GraphEditorTool {

	protected final Graph graph;
	protected final GraphSelectionModel selectionModel;
	protected final UndoManager undoManager;
	
	public AbstractGraphEditorTool(Graph graph, GraphSelectionModel selectionModel, UndoManager undoManager) {
		this.graph = graph;
		this.selectionModel = selectionModel;
		this.undoManager = undoManager;
	}

	@Override
	public void handleCanvasClick(int x, int y) {
		//empty

	}

	@Override
	public void handleVertexClick(Vertex vertex) {
		//empty

	}

	@Override
	public void handleEdgeClick(Edge edge) {
		//empty

	}

	@Override
	public void handleMove(int x, int y) {
		//empty
	}

	@Override
	public void install() {
		//empty
	}

	@Override
	public void deinstall() {
		selectionModel.setSelectedVertex(null);
	}

}

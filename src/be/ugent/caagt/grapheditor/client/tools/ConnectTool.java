package be.ugent.caagt.grapheditor.client.tools;

import be.ugent.caagt.grapheditor.client.GraphSelectionModel;
import be.ugent.caagt.grapheditor.client.data.Edge;
import be.ugent.caagt.grapheditor.client.data.Graph;
import be.ugent.caagt.grapheditor.client.data.Vertex;
import be.ugent.caagt.grapheditor.client.undo.AddEdgeUndoable;
import be.ugent.caagt.grapheditor.client.undo.UndoManager;

public class ConnectTool extends AbstractGraphEditorTool {

	public ConnectTool(Graph graph, GraphSelectionModel selectionModel, UndoManager undoManager) {
		super(graph, selectionModel, undoManager);
	}

	@Override
	public void handleVertexClick(Vertex vertex) {
		if(selectionModel.getSelectedVertex()==null){
			selectionModel.setSelectedVertex(vertex);
		} else if(selectionModel.getSelectedVertex().equals(vertex)){
			selectionModel.setSelectedVertex(null);
		} else if(!graph.areAdjacent(vertex, selectionModel.getSelectedVertex())){
			Edge e = graph.addEdge(selectionModel.getSelectedVertex(), vertex);
			selectionModel.setSelectedVertex(null);
			undoManager.addUndoable(new AddEdgeUndoable(graph, e));
		}
	}

}

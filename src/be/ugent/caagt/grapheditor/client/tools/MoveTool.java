package be.ugent.caagt.grapheditor.client.tools;

import be.ugent.caagt.grapheditor.client.GraphSelectionModel;
import be.ugent.caagt.grapheditor.client.data.Graph;
import be.ugent.caagt.grapheditor.client.data.Vertex;
import be.ugent.caagt.grapheditor.client.undo.MoveVertexUndoable;
import be.ugent.caagt.grapheditor.client.undo.UndoManager;

public class MoveTool extends AbstractGraphEditorTool {
	
	private Vertex storedVertex = null;
	private int x;
	private int y;

	public MoveTool(Graph graph, GraphSelectionModel selectionModel, UndoManager undoManager) {
		super(graph, selectionModel, undoManager);
	}

	@Override
	public void handleVertexClick(Vertex vertex) {
		/*
		if(selectionModel.getSelectedVertex()==null || 
				!selectionModel.getSelectedVertex().equals(vertex)){
			selectionModel.setSelectedVertex(vertex);
			
			//store state for undoable management
			storedVertex = vertex;
			x = vertex.getX();
			y = vertex.getY();
		} else {
			if(storedVertex!=null && (storedVertex.getX()!=x || storedVertex.getY()!=y)){
				undoManager.addUndoable(new MoveVertexUndoable(graph, storedVertex, x, y, storedVertex.getX(), storedVertex.getY()));
				storedVertex = null;
			}
			selectionModel.setSelectedVertex(null);
		}
		*/
	}

	@Override
	public void handleVertexDown(Vertex vertex) {
		selectionModel.setSelectedVertex(vertex);
		
		//store state for undoable management
		storedVertex = vertex;
		x = vertex.getX();
		y = vertex.getY();
	}

	@Override
	public void handleVertexUp(Vertex vertex) {
		if(storedVertex!=null && (storedVertex.getX()!=x || storedVertex.getY()!=y)){
			undoManager.addUndoable(new MoveVertexUndoable(graph, storedVertex, x, y, storedVertex.getX(), storedVertex.getY()));
			storedVertex = null;
		}
		selectionModel.setSelectedVertex(null);
	}

	@Override
	public void handleMove(int x, int y) {
		if(selectionModel.getSelectedVertex()!=null){
			selectionModel.getSelectedVertex().setCoordinates(x, y);
		}
	}

	@Override
	public void deinstall() {
		if(storedVertex!=null && (storedVertex.getX()!=x || storedVertex.getY()!=y)){
			undoManager.addUndoable(new MoveVertexUndoable(graph, storedVertex, x, y, storedVertex.getX(), storedVertex.getY()));
			storedVertex = null;
		}
	}

}

package be.ugent.caagt.grapheditor.client.undo;

import be.ugent.caagt.grapheditor.client.data.Graph;
import be.ugent.caagt.grapheditor.client.data.Vertex;

public class MoveVertexUndoable extends AbstractUndoable {
	
	private Vertex v;
	private int xOld;
	private int yOld;
	private int xNew;
	private int yNew;

	public MoveVertexUndoable(Graph graph, Vertex v, int xOld, int yOld, int xNew, int yNew) {
		super(graph);
		this.v = v;
		this.xOld = xOld;
		this.yOld = yOld;
		this.xNew = xNew;
		this.yNew = yNew;
	}

	@Override
	public void undo() {
		v.setCoordinates(xOld, yOld);
	}

	@Override
	public void redo() {
		v.setCoordinates(xNew, yNew);
	}

}

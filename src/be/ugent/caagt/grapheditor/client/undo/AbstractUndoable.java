package be.ugent.caagt.grapheditor.client.undo;

import be.ugent.caagt.grapheditor.client.data.Graph;

public abstract class AbstractUndoable implements Undoable {
	
	protected final Graph graph;
	private Undoable previous = null;
	private Undoable next = null;

	public AbstractUndoable(Graph graph) {
		this.graph = graph;
	}

	@Override
	public Undoable getNext() {
		return next;
	}

	@Override
	public Undoable getPrevious() {
		return previous;
	}

	@Override
	public void setNext(Undoable next) {
		this.next = next;
	}

	@Override
	public void setPrevious(Undoable previous) {
		this.previous = previous;
	}

}

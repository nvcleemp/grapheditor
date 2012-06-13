package be.ugent.caagt.grapheditor.client.undo;

import be.ugent.caagt.grapheditor.client.data.Graph;

public class BulkUndoable extends AbstractUndoable {
	
	private Undoable first = null;
	private Undoable last = null;

	public BulkUndoable(Graph graph) {
		super(graph);
	}
	
	public void addUndoable(Undoable undoable){
		if(first==null){
			first = undoable;
			last = undoable;
		} else {
			last.setNext(undoable);
			undoable.setPrevious(last);
			last = undoable;
		}
	}

	@Override
	public void undo() {
		Undoable current = last;
		while(current != null){
			current.undo();
			current = current.getPrevious();
		}
	}

	@Override
	public void redo() {
		Undoable current = first;
		while(current != null){
			current.redo();
			current = current.getNext();
		}
	}

}

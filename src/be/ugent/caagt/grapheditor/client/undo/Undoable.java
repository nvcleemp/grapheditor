package be.ugent.caagt.grapheditor.client.undo;

public interface Undoable {
	public void undo();
	public void redo();
	public Undoable getNext();
	public Undoable getPrevious();
	public void setNext(Undoable next);
	public void setPrevious(Undoable previous);
}

package be.ugent.caagt.grapheditor.client.undo;

public class UndoManager {
	
	private Undoable current = new AbstractUndoable(null) {
		
		@Override
		public void undo() {
			//empty
		}
		
		@Override
		public void redo() {
			// empty
		}
	};
	
	public void undo(){
		if(current.getPrevious()!=null){
			current.undo();
			current = current.getPrevious();
		}
	}
	
	public void redo(){
		Undoable next = current.getNext();
		if(next!=null){
			next.redo();
			current = next;
		}
	}
	
	public void addUndoable(Undoable undoable){
		current.setNext(undoable);
		undoable.setPrevious(current);
		current = undoable;
	}
}

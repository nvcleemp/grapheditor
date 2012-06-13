package be.ugent.caagt.grapheditor.client;

import java.util.ArrayList;

import be.ugent.caagt.grapheditor.client.data.Vertex;

public class GraphSelectionModel {

	private Vertex selectedVertex = null;

	public Vertex getSelectedVertex() {
		return selectedVertex;
	}

	public void setSelectedVertex(Vertex selectedVertex) {
		if(selectedVertex == null ? 
				this.selectedVertex!=null :
				!selectedVertex.equals(this.selectedVertex)){
			Vertex oldSelection = this.selectedVertex;
			this.selectedVertex = selectedVertex;
			fireSelectionChanged(oldSelection, selectedVertex);
		}
	}
	
	private ArrayList<GraphSelectionListener> listeners = new ArrayList<GraphSelectionListener>();
	
	public void addSelectionListener(GraphSelectionListener l){
		listeners.add(l);
	}
	
	public void removeSelectionListener(GraphSelectionListener l){
		listeners.remove(l);
	}
	
	private void fireSelectionChanged(Vertex oldSelection, Vertex newSelection){
		for(GraphSelectionListener l : listeners){
			l.selectionChanged(oldSelection, newSelection);
		}
	}
}

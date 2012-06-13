package be.ugent.caagt.grapheditor.client;

import be.ugent.caagt.grapheditor.client.data.Vertex;

public interface GraphSelectionListener {
	public void selectionChanged(Vertex oldSelection, Vertex newSelection);
}

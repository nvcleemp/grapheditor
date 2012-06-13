package be.ugent.caagt.grapheditor.client.tools;

import be.ugent.caagt.grapheditor.client.data.Edge;
import be.ugent.caagt.grapheditor.client.data.Vertex;

public interface GraphEditorTool {
	void install();
	void deinstall();
	
	void handleCanvasClick(int x, int y);
	void handleVertexClick(Vertex vertex);
	void handleEdgeClick(Edge edge);
	
	void handleMove(int x, int y);
}

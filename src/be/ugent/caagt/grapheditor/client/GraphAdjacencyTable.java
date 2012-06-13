package be.ugent.caagt.grapheditor.client;

import be.ugent.caagt.grapheditor.client.data.Graph;
import be.ugent.caagt.grapheditor.client.data.Vertex;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;

public class GraphAdjacencyTable extends Composite {

	private final Graph graph;
	private final Grid grid;

	public GraphAdjacencyTable(Graph graph) {
		this.graph = graph;
		grid = new Grid();
		initWidget(grid);
	}
	
	public void syncTable(){
		int order = graph.getOrder();
		grid.resize(order, order);
		for(int i = 0; i<order; i++){
			grid.setHTML(i, i, "0");
		}
		for(int i = 0; i<order-1; i++){
			Vertex vRow = graph.getVertex(i);
			for(int j = i + 1; j<order; j++){
				Vertex vColumn = graph.getVertex(j);
				String adjacency;
				if(graph.areAdjacent(vRow, vColumn)){
					adjacency = "1";
				} else {
					adjacency = "0";
				}
				grid.setHTML(i, j, adjacency);
				grid.setHTML(j, i, adjacency);
			}
		}
	}
}

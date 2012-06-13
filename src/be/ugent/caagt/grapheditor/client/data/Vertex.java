package be.ugent.caagt.grapheditor.client.data;

import java.util.ArrayList;

public class Vertex {
	private int x;
	private int y;
	
	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setCoordinates(int x, int y) {
		if(this.x != x || this.y != y){
			this.x = x;
			this.y = y;
			fireCoordinatesChanged(x, y);
		}
	}
	
	private ArrayList<VertexListener> listeners = new ArrayList<VertexListener>();
	
	public void addVertexListener(VertexListener listener){
		listeners.add(listener);
	}
	
	public void removeVertexListener(VertexListener listener){
		listeners.remove(listener);
	}
	
	private void fireCoordinatesChanged(int x, int y){
		for(VertexListener l : listeners){
			l.coordinatesChanged(this, x, y);
		}
	}
}

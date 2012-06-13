package be.ugent.caagt.grapheditor.client;

import java.util.HashMap;

import be.ugent.caagt.grapheditor.client.data.Edge;
import be.ugent.caagt.grapheditor.client.data.Graph;
import be.ugent.caagt.grapheditor.client.data.GraphListener;
import be.ugent.caagt.grapheditor.client.data.Vertex;
import be.ugent.caagt.grapheditor.client.tools.AddTool;
import be.ugent.caagt.grapheditor.client.tools.ConnectTool;
import be.ugent.caagt.grapheditor.client.tools.DeleteTool;
import be.ugent.caagt.grapheditor.client.tools.GraphEditorTool;
import be.ugent.caagt.grapheditor.client.tools.MoveTool;
import be.ugent.caagt.grapheditor.client.undo.BulkUndoable;
import be.ugent.caagt.grapheditor.client.undo.RemoveEdgeUndoable;
import be.ugent.caagt.grapheditor.client.undo.RemoveVertexUndoable;
import be.ugent.caagt.grapheditor.client.undo.UndoManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vaadin.contrib.gwtgraphics.client.DrawingArea;
import com.vaadin.contrib.gwtgraphics.client.Line;
import com.vaadin.contrib.gwtgraphics.client.Strokeable;
import com.vaadin.contrib.gwtgraphics.client.shape.Circle;

/**
 * Simple graph editor.
 */
public class GraphEditor implements EntryPoint {

	private DrawingArea canvas;
	
	private GraphEditorTool tool = null;
	
	private GraphSelectionModel selectionModel = new GraphSelectionModel();
	
	private HTML infoText = new HTML();
	
	private UserSettings settings;
	
	private ClickHandler vertexHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			if(tool!=null){
				Circle c = (Circle)(event.getSource());
				tool.handleVertexClick(circle2Vertex.get(c));
			}
			event.stopPropagation();
		}
	};
	
	private MouseOverHandler mouseOverHandler = new MouseOverHandler() {
		
		@Override
		public void onMouseOver(MouseOverEvent event) {
			Strokeable s = (Strokeable)(event.getSource());
			s.setStrokeColor("#0088ff");
		}
	};
	
	private MouseOutHandler mouseOutHandler = new MouseOutHandler() {
		
		@Override
		public void onMouseOut(MouseOutEvent event) {
			Strokeable s = (Strokeable)(event.getSource());
			s.setStrokeColor("black");
		}
	};
	
	private MouseDownHandler mouseDownHandler = new MouseDownHandler() {
		
		@Override
		public void onMouseDown(MouseDownEvent event) {
			if(tool!=null){
				Circle c = (Circle)(event.getSource());
				tool.handleVertexDown(circle2Vertex.get(c));
			}
			event.stopPropagation();
		}
	};
	
	private MouseUpHandler mouseUpHandler = new MouseUpHandler() {
		
		@Override
		public void onMouseUp(MouseUpEvent event) {
			if(tool!=null){
				Circle c = (Circle)(event.getSource());
				tool.handleVertexUp(circle2Vertex.get(c));
			}
			event.stopPropagation();
		}
	};
	
	private ClickHandler edgeHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			if(tool!=null){
				Line l = (Line)(event.getSource());
				tool.handleEdgeClick(line2Edge.get(l));
			}
		}
	};
	
	private GraphSelectionListener selectionListener = new GraphSelectionListener() {
		
		@Override
		public void selectionChanged(Vertex oldSelection, Vertex newSelection) {
			if(oldSelection!=null && vertex2Circle.containsKey(oldSelection)){
				vertex2Circle.get(oldSelection).setFillColor("white");
			}
			if(newSelection!=null && vertex2Circle.containsKey(newSelection)){
				vertex2Circle.get(newSelection).setFillColor("red");
			}
		}
	};

	private Graph graph = new Graph();
	
	private GraphListener graphListener = new GraphListener() {
		
		@Override
		public void vertexRemoved(Vertex v) {
			Circle c = vertex2Circle.get(v);
			canvas.remove(c);
			
			vertex2Circle.remove(v);
			circle2Vertex.remove(c);
		}
		
		@Override
		public void vertexEmbeddingChanged(Vertex v) {
			Circle c = vertex2Circle.get(v);
			c.setX(v.getX());
			c.setY(v.getY());
			for(Edge edge : edge2Line.keySet()){
				if(edge.startsIn(v)){
					Line l = edge2Line.get(edge);
					l.setX1(v.getX());
					l.setY1(v.getY());
				} else if(edge.endsIn(v)){
					Line l = edge2Line.get(edge);
					l.setX2(v.getX());
					l.setY2(v.getY());
				}
			}
		}
		
		@Override
		public void vertexAdded(Vertex v) {
			final Circle circle = new Circle(v.getX(), v.getY(), settings.getVertexRadius());
			circle2Vertex.put(circle, v);
			vertex2Circle.put(v, circle);
			canvas.add(circle);
			
			circle.addClickHandler(vertexHandler);
			circle.addMouseDownHandler(mouseDownHandler);
			circle.addMouseUpHandler(mouseUpHandler);
			circle.addMouseOverHandler(mouseOverHandler);
			circle.addMouseOutHandler(mouseOutHandler);
		}
		
		@Override
		public void graphCleared() {
			selectionModel.setSelectedVertex(null);
			canvas.clear();
			vertex2Circle.clear();
			circle2Vertex.clear();
			edge2Line.clear();
			line2Edge.clear();
		}
		
		@Override
		public void edgeRemoved(Edge e) {
			Line l = edge2Line.get(e);
			canvas.remove(l);
			
			line2Edge.remove(l);
			edge2Line.remove(e);
		}
		
		@Override
		public void edgeAdded(Edge e) {
			final Line line = new Line(e.getFrom().getX(), e.getFrom().getY(), e.getTo().getX(), e.getTo().getY());
			line.setStrokeWidth(settings.getEdgeWidth());
			
			line2Edge.put(line, e);
			edge2Line.put(e, line);
			canvas.add(line);
			
			//TODO: extent DrawingArea to also include move to back method!
			//      Currently we are bringing all the vertices to the front
			for(Circle c : circle2Vertex.keySet()){
				canvas.pop(c);
			}
			
			line.addClickHandler(edgeHandler);
			line.addMouseOverHandler(mouseOverHandler);
			line.addMouseOutHandler(mouseOutHandler);
		}
	};

	//provide the mapping between the circles (GUI elements) and the vertices (data objects)
	private HashMap<Vertex, Circle> vertex2Circle = new HashMap<Vertex, Circle>();
	private HashMap<Circle, Vertex> circle2Vertex = new HashMap<Circle, Vertex>();
	
	//provide the mapping between the lines (GUI elements) and the edges (data objects)
	private HashMap<Edge, Line> edge2Line = new HashMap<Edge, Line>();
	private HashMap<Line, Edge> line2Edge = new HashMap<Line, Edge>();
	
	public void onModuleLoad() {
		settings = new UserSettings();
		graph.addGraphListener(graphListener);
		selectionModel.addSelectionListener(selectionListener);
		
		VerticalPanel mainPanel = new VerticalPanel();
		RootPanel.get().add(mainPanel);
		
		mainPanel.add(buildMenuBar());

		canvas = new DrawingArea(400, 400);
		canvas.setStyleName("drawing-area");
		canvas.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(tool!=null){
					tool.handleCanvasClick(event.getX(), event.getY());
				}
			}
		});
		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				if(tool!=null){
					tool.handleMove(event.getX(), event.getY());
				}
			}
		});
		mainPanel.add(canvas);
		
		mainPanel.add(infoText);
		infoText.setHTML("Logged in as " + settings.getUserName());
	}
	
	private MenuBar buildMenuBar(){
		final MenuBar mainMenu = new MenuBar();
		
		MenuBar fileMenu = new MenuBar(true);
		fileMenu.addItem("Save", new MessageCommand("Save is not yet implemented"));
		fileMenu.addItem("Import", new MessageCommand("Import is not yet implemented"));
		
		final UndoManager undoManager = new UndoManager();
		MenuBar editMenu = new MenuBar(true);
		editMenu.addItem("Undo", new Command() {
			
			@Override
			public void execute() {
				undoManager.undo();
			}
		});
		editMenu.addItem("Redo", new Command() {
			
			@Override
			public void execute() {
				undoManager.redo();
			}
		});
		
		MenuBar toolsMenu = new MenuBar(true);
		toolsMenu.addItem("Add", new ToolCommand(new AddTool(graph, selectionModel, undoManager)));
		toolsMenu.addItem("Connect", new ToolCommand(new ConnectTool(graph, selectionModel, undoManager)));
		toolsMenu.addItem("Move", new ToolCommand(new MoveTool(graph, selectionModel, undoManager)));
		toolsMenu.addItem("Delete", new ToolCommand(new DeleteTool(graph, selectionModel, undoManager)));
		toolsMenu.addSeparator();
		toolsMenu.addItem("Clear", new Command() {
			@Override
			public void execute() {
				BulkUndoable bulkUndoable = new BulkUndoable(graph);
				for(int i = graph.getSize()-1; i>=0; i--){
					bulkUndoable.addUndoable(new RemoveEdgeUndoable(graph, graph.getEdge(i)));
				}
				for(int i = graph.getOrder()-1; i>=0; i--){
					bulkUndoable.addUndoable(new RemoveVertexUndoable(graph, graph.getVertex(i)));
				}
				undoManager.addUndoable(bulkUndoable);
				graph.clear();
			}
		});
		
		MenuBar infoMenu = new MenuBar(true);
		infoMenu.addItem("Adjacency matrix", new ShowGraphAdjacencyTableCommand(mainMenu, graph));
		
		mainMenu.addItem("File", fileMenu);
		mainMenu.addItem("Edit", editMenu);
		mainMenu.addItem("Tools", toolsMenu);
		mainMenu.addItem("Info", infoMenu);
		
		return mainMenu;
	}

	private class MessageCommand implements Command {
		
		private final String message;

		public MessageCommand(String message) {
			this.message = message;
		}

		@Override
		public void execute() {
			infoText.setHTML(message);
		}
		
	}

	private class ToolCommand implements Command {
		
		private final GraphEditorTool tool;

		public ToolCommand(GraphEditorTool tool) {
			this.tool = tool;
		}

		@Override
		public void execute() {
			if(GraphEditor.this.tool!=null){
				GraphEditor.this.tool.deinstall();
			}
			GraphEditor.this.tool = tool;
			tool.install();
		}
		
	}
}
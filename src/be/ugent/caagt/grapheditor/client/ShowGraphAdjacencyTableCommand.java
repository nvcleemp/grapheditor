package be.ugent.caagt.grapheditor.client;

import be.ugent.caagt.grapheditor.client.data.Graph;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel;

public class ShowGraphAdjacencyTableCommand implements Command {
	private final MenuBar mainMenu;
	private final GraphAdjacencyTable graphAdjacencyTable;
	private final PopupPanel popup = new PopupPanel(true, true);

	public ShowGraphAdjacencyTableCommand(MenuBar mainMenu, Graph graph) {
		graphAdjacencyTable = new GraphAdjacencyTable(graph);
		this.mainMenu = mainMenu;
		popup.setWidget(graphAdjacencyTable);
	}

	@Override
	public void execute() {
		graphAdjacencyTable.syncTable();
		popup.showRelativeTo(mainMenu);
		popup.show();
	}
}
package be.ugent.caagt.grapheditor.client;

import com.google.gwt.i18n.client.Dictionary;

public class UserSettings {
	
	private String userName;
	private int vertexRadius;
	private int edgeWidth;

	public UserSettings() {
		Dictionary dictionary = Dictionary.getDictionary("settings");
		userName = dictionary.get("user");
		vertexRadius = Integer.parseInt(dictionary.get("vertexRadius"));
		edgeWidth = Integer.parseInt(dictionary.get("edgeWidth"));
	}

	public String getUserName() {
		return userName;
	}

	public int getVertexRadius() {
		return vertexRadius;
	}

	public int getEdgeWidth() {
		return edgeWidth;
	}
}

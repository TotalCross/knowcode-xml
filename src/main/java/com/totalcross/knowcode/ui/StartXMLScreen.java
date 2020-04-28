package com.totalcross.knowcode.ui;

import com.totalcross.knowcode.parse.XmlScreenFactory;
import com.totalcross.knowcode.parse.XmlScreenAbstractLayout;
import com.totalcross.knowcode.util.Colors;
import totalcross.ui.Container;
import totalcross.ui.MainWindow;


public class StartXMLScreen extends Container {
	
	private XmlScreenAbstractLayout container;
	
	public StartXMLScreen() {
		this.setBackColor(Colors.PRIMARY);
	}

	public StartXMLScreen(String xml) {
		this.setBackColor(Colors.PRIMARY);
		Container container = XmlScreenFactory.create(xml);
		MainWindow.getMainWindow().swap(container);
		this.container = (XmlScreenAbstractLayout)container;
	}
	
	
	public void initUI() {

	}

	public XmlScreenAbstractLayout getContainer() {
		return container;
	}
}

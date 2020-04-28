package com.totalcross.knowcode.ui;

import com.totalcross.knowcode.parse.XmlScreenFactory;
import com.totalcross.knowcode.parse.XmlScreenAbstractLayout;
import com.totalcross.knowcode.util.Fonts;

import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;

public class StartXMLScreen extends Container {

	private XmlScreenAbstractLayout container;

	public StartXMLScreen() {
	}

	public StartXMLScreen(String xml) {
		Container container = XmlScreenFactory.create(xml);
		MainWindow.getMainWindow().swap(container);
		this.container = (XmlScreenAbstractLayout)container;
	}


	public void initUI() {
		Label label = new Label("KnowCode Application!");
		add(label,CENTER,CENTER);

		Button readMore = new Button("Read more");
		readMore.setFont(Fonts.latoRegularDefaultSize);
		readMore.addPressListener((e) -> {
			Vm.exec("viewer", "https://github.com/TotalCross/KnowCodeXML/", 0, true);
		});

		add(readMore, CENTER, CENTER+50);

	}

	public XmlScreenAbstractLayout getContainer() {
		return container;
	}
}

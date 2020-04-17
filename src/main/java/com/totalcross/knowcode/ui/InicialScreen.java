// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: LGPL-3.0-only

package com.totalcross.knowcode.ui;

import com.totalcross.knowcode.util.Colors;
import com.totalcross.knowcode.util.Constants;
import com.totalcross.knowcode.util.Fonts;
import com.totalcross.knowcode.util.Images;

import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;
import totalcross.util.UnitsConverter;

public class InicialScreen extends Container {
	public InicialScreen() {
		this.setBackColor(Colors.PRIMARY);
	}

	public InicialScreen(String xml) {
		this.setBackColor(Colors.PRIMARY);
		Container src1 = XmlScreenFactory.create(xml);
		MainWindow.getMainWindow().swap(src1);

	}

	public void initUI() {
		String pathLinearXml = "xml/linearSample.xml";
		String pathRelativeXml = "xml/relativeSample.xml";
		String pathAbsoluteXml = "xml/absoluteSample.xml";

		Images.loadImage();

		ImageControl background = new ImageControl(Images.background);
		background.scaleToFit = true;
		background.centerImage = true;
		background.hwScale = true;
		background.strechImage = true;
		add(background, LEFT, TOP, FILL, FILL);

		ImageControl logo = new ImageControl(Images.logo_nome_borda);
		logo.scaleToFit = true;
		logo.transparentBackground = true;
		add(logo, CENTER, TOP + UnitsConverter.toPixels(16 + Control.DP), PARENTSIZE + 70, PARENTSIZE + 20);

		Container cont = new Container();
		cont.transparentBackground = true;
		add(cont, LEFT + Constants.BORDER_SPACING, BOTTOM, FILL - Constants.BORDER_SPACING, PARENTSIZE + 65);

		Label lbl = new Label("Choose the Android\n XML to be load");
		lbl.setFont(Fonts.latoBoldPlus10);
		lbl.transparentBackground = true;
		lbl.setForeColor(Colors.TEXT_ON_P);
		cont.add(lbl, LEFT, TOP + 15);

		Button bt01 = new Button("LinearLayout Sample ", Button.BORDER_OUTLINED);
		bt01.setForeColor(Colors.PRIMARY);
		cont.add(bt01, CENTER, AFTER + (Constants.COMPONENT_SPACING) * 2, PREFERRED, PREFERRED);
		bt01.addPressListener((e) -> {
			// XMLScreen01 src1 = new XMLScreen01();
			// String pathXml = pathLinearXml;
			Container src1 = XmlScreenFactory.create(pathLinearXml);
			MainWindow.getMainWindow().swap(src1);
		});

		Button bt02 = new Button("AbsoluteLayout Sample ", Button.BORDER_OUTLINED);
		bt02.setForeColor(Colors.PRIMARY);
		cont.add(bt02, CENTER, AFTER + (Constants.COMPONENT_SPACING) * 2, PREFERRED, PREFERRED);
		bt02.addPressListener((e) -> {
			// XMLScreen01 src1 = new XMLScreen01();
			// String pathXml = "xml2.xml";
			Container src1 = XmlScreenFactory.create(pathAbsoluteXml);
			MainWindow.getMainWindow().swap(src1);
		});

		Button bt03 = new Button("RelativeLayout Sample ", Button.BORDER_OUTLINED);
		bt03.setForeColor(Colors.PRIMARY);
		cont.add(bt03, CENTER, AFTER + (Constants.COMPONENT_SPACING) * 2, PREFERRED, PREFERRED);
		bt03.addPressListener((e) -> {
			// XMLScreen01 src1 = new XMLScreen01();

			Container src1 = XmlScreenFactory.create(pathRelativeXml);
			MainWindow.getMainWindow().swap(src1);
		});

		Button readMore = new Button("Read more", Button.BORDER_NONE);
		readMore.transparentBackground = true;
		readMore.setFont(Fonts.latoRegularDefaultSize);
		readMore.setForeColor(Color.WHITE);

		cont.add(readMore, CENTER, AFTER + Constants.COMPONENT_SPACING, PREFERRED, PREFERRED);

		readMore.addPressListener(new PressListener() {
			@Override
			public void controlPressed(ControlEvent e) {
				MessageBox mb = new MessageBox("",
						"Know Code Application\n\nComputer Vision and \nAndroid XML to Totalcross Parser",
						new String[] { "Ok" });
				mb.setTextAlignment(CENTER);
				mb.setRect(CENTER, CENTER, SCREENSIZE + 70, SCREENSIZE + 50);
				mb.setBackForeColors(Colors.ON_P_600, Colors.ON_P_300);
				mb.popup();
			}
		});
	}
}

// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: LGPL-3.0-only

package com.totalcross.knowcode.ui;

import com.totalcross.knowcode.util.Colors;

import totalcross.sys.Vm;
import totalcross.ui.Container;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.image.ImageException;
import totalcross.xml.AttributeList;
import totalcross.xml.ContentHandler;
import totalcross.xml.SyntaxException;
import totalcross.xml.XmlReader;

public class XmlScreenFactory {
	private String nameLayout = null;
	static final String[] layouts = { "ConstraintLayout", "LinearLayout", "FrameLayout", "RelativeLayout",
			"AbsolutLayout" };

	public static Container create(String pathXml) {
		try {
			XmlScreenFactory xMLScreenFactory = new XmlScreenFactory();
			xMLScreenFactory.readXml(pathXml);
			if (xMLScreenFactory.getNameLayout() == null) {
				throw new Exception("Layout do xml não consta lista dos conhecidos.");
			}

			Class clazz = Class.forName("com.totalcross.knowcode.ui.XmlScreen" + xMLScreenFactory.getNameLayout());

			XmlScreenAbstractLayout container = (XmlScreenAbstractLayout) clazz.newInstance();
			container.setPathXml(pathXml);
			return container;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private class Handler extends ContentHandler {
		NodeSax auxNodeSax;

		private Handler() throws totalcross.io.IOException, ImageException {
			auxNodeSax = new NodeSax();
		}

		@Override
		public void characters(String arg0) {
			System.out.println("Characters " + arg0);

		}

		@Override
		public void endElement(int arg0) {

		}

		@Override
		public void startElement(int arg0, AttributeList atts) {
			AttributeList.Iterator it = atts.new Iterator();
			while (it.next()) {
				auxNodeSax.inserts(it.getAttributeName(), it.getAttributeValue());
			}
			if (getNameLayout() == null) {
				for (int i = 0; i < layouts.length; i++) {
					System.out.println(auxNodeSax.getAttributeName());
					if (auxNodeSax.getAttributeName().contains(layouts[i])) {
						setNameLayout(layouts[i]);
						break;
					}
				}
			}
		}

		// @Override
		public void endElementString(String arg0) {
			// TODO Auto-generated method stub
			System.out.println("endElementString " + arg0);
		}

		@Override
		public void startElementString(String arg0, AttributeList arg1) {
			// TODO Auto-generated method stub
			System.out.println("startElementString " + arg0);
			auxNodeSax.setAttributeName(arg0);
			auxNodeSax.reset();
		}
	}

	private void readXml(String pathXml) throws totalcross.io.IOException, ImageException {

		Handler handler = new Handler();
		XmlReader rdr = new XmlReader();
		rdr.setContentHandler(handler);

		byte[] xml = Vm.getFile(pathXml);

		if (xml != null) {

			try {
				rdr.parse(xml, 0, xml.length);
			} catch (SyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			MessageBox mb = new MessageBox("Message", "XML not found.", new String[] { "Close" });
			mb.setForeColor(Colors.SURFACE);
			mb.popup();
		}

	}

	public String getNameLayout() {
		return nameLayout;
	}

	public void setNameLayout(String nameLayout) {
		this.nameLayout = nameLayout;
	}

}

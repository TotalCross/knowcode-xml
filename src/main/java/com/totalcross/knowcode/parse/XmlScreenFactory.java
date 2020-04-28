// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: GPL-2.0-only

package com.totalcross.knowcode.parse;

import totalcross.sys.Vm;
import totalcross.ui.Container;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.image.ImageException;
import totalcross.xml.AttributeList;
import totalcross.xml.ContentHandler;
import totalcross.xml.SyntaxException;
import totalcross.xml.XmlReader;

import java.io.UnsupportedEncodingException;

import com.totalcross.knowcode.util.Colors;

public class XmlScreenFactory {
	private String nameLayout = null;
	static final String[] layouts = { "ConstraintLayout", "LinearLayout", "FrameLayout", "RelativeLayout",
			"AbsolutLayout" };

	public static Container create(String pathXml) {
		try {
			XmlScreenFactory xmlScreenFactory = new XmlScreenFactory();
			xmlScreenFactory.readXml(pathXml);
			if (xmlScreenFactory.getNameLayout() == null) {
				throw new Exception("Layout do xml não consta lista dos conhecidos.");
			}

			Class clazz = Class.forName("com.totalcross.knowcode.parse.XmlScreen" + xmlScreenFactory.getNameLayout());

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

		}

		@Override
		public void endElement(int arg0) {

		}

		@Override
		public void startElement(int arg0, AttributeList atts) {

		}

		@Override
		public void tagName(int a,String arg0,AttributeList atts) {
			// TODO Auto-generated method stub
			auxNodeSax.setAttributeName(arg0);
			auxNodeSax.reset();
			AttributeList.Iterator it = atts.new Iterator();
			while (it.next()) {
				auxNodeSax.inserts(it.getAttributeName(), it.getAttributeValue());
			}
			if (getNameLayout() == null) {
				for (int i = 0; i < layouts.length; i++) {
					if (auxNodeSax.getAttributeName().contains(layouts[i])) {
						setNameLayout(layouts[i]);
						break;
					}
				}
			}
		}
	}

	private void readXml(String pathXml) throws totalcross.io.IOException, ImageException, UnsupportedEncodingException {

		Handler handler = new Handler();
		XmlReader rdr = new XmlReader();
		rdr.setContentHandler(handler);

		byte[] xml = Vm.getFile(pathXml);

		if (xml != null) {
			xml = new String(xml, 0, xml.length, "UTF-8").getBytes("ISO-8859-1");
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

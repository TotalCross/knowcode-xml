/********************************************************************************* 
 * (c) 2020 by TotalCross Global Mobile Platform LTDA
 * SPDX-License-Identifier: LGPL-3.0-only
  *********************************************************************************/
package com.totalcross.knowcode.xml;

import totalcross.sys.Vm;
import totalcross.ui.Container;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.image.ImageException;
import totalcross.xml.AttributeList;
import totalcross.xml.ContentHandler;
import totalcross.xml.SyntaxException;
import totalcross.xml.XmlReader;

import java.io.UnsupportedEncodingException;

import com.totalcross.knowcode.ui.CustomInitUI;
import com.totalcross.knowcode.ui.XmlContainerLayout;

/**
 * XmlContainerFactory is responsible to read the XML file, verify what kind of view group is the layout 
 * and to instantiate the specialized class of XmlContainerLayout.
 * <p>
 * It uses SAX is a standard interface for event-based XML parsing with the {@link XmlReader} class, 
 * used to read HTML or XML documents, reporting events to handlers {@link ContentHandler}
 * <p>
 * Here is an example of create a new Totalcross Container Screen from a XML file:
 * <pre>
 *	public void initUI(){
 *		Container cont = XmlContainerFactory.create("linearSample.xml");
 *		MainWindow.getMainWindow().swap(cont);
 *	}
 * </pre>
 */
public class XmlContainerFactory {
	private String nameLayout = null;
	static final String[] layouts = { "ConstraintLayout", "LinearLayout", "FrameLayout", "RelativeLayout"/*,"AbsolutLayout"*/ };

	/**
	 * Responsible for create the specialized layout Container
	 * @param pathXml
	 * 		the path of xml file
	 * @param cust
	 * 		CustomInitUI object
	 * @return container of the layout
	 *  */
	public static Container create(String pathXml, CustomInitUI cust) {
		try {
			XmlContainerFactory xmlScreenFactory = new XmlContainerFactory();
			xmlScreenFactory.readXml(pathXml);
			if (xmlScreenFactory.getNameLayout() == null) {
				throw new Exception("Layout do xml não consta lista dos conhecidos.");
			}

			Class clazz = Class
					.forName("com.totalcross.knowcode.parse.XmlContainer" + xmlScreenFactory.getNameLayout());

			XmlContainerLayout container = (XmlContainerLayout) clazz.newInstance();
			container.setPathXml(pathXml);
			container.setCustomInitUI(cust);
			return container;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Responsible for create the specialized layout Container without CustomInitUI customization
	 * @param pathXml
	 * 		the path of xml file
	 * @return container of the layout
	 *  */
	public static Container create(String pathXml) {
		return create(pathXml, null);
	}

	private class Handler extends ContentHandler {
		NodeSax auxNodeSax;

		private Handler() throws totalcross.io.IOException, ImageException {
			auxNodeSax = new NodeSax();
		}
		
		/** Does nothing */
		@Override
		public void characters(String arg0) {

		}
		
		/** Does nothing */
		@Override
		public void endElement(int arg0) {
		}
		
		/** Does nothing */
		@Override
		public void startElement(int arg0, AttributeList atts) {
		}

		/**
		 * Name of the tag.
		 *
		 * <p>The XmlReader will invoke this method at the beginning of every
		 * element in the XML document. All of the element's content will be
		 * reported in order. </p>
		 * @param tag
		 *           tag identifier for this element
		 * @param tagName
		 *           tag name
		 * @param atts
		 * 		   attribute list
		 */
		@Override
		public void tagName(int tag,String tagName,AttributeList atts) {
			auxNodeSax.setAttributeName(tagName);
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

	private void readXml(String pathXml) 
			throws totalcross.io.IOException, ImageException, UnsupportedEncodingException {
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
			mb.popup();
		}
	}

	/**
	 * Get the name of layout
	 * @return name of layout
	 * */
	public String getNameLayout() {
		return nameLayout;
	}

	/**
	 * Set the name of layout
	 * @param nameLayout
	 * 		name of layout
	 * */
	public void setNameLayout(String nameLayout) {
		this.nameLayout = nameLayout;
	}

}

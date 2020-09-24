/********************************************************************************* 
 * (c) 2020 by TotalCross Global Mobile Platform LTDA
 * SPDX-License-Identifier: LGPL-3.0-only
 *********************************************************************************/
package com.totalcross.knowcode.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.TreeMap;

import com.totalcross.knowcode.xml.NodeSax;
import com.totalcross.knowcode.xml.XmlContainerFactory;

import totalcross.sys.InvalidNumberException;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.Check;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.ProgressBar;
import totalcross.ui.Radio;
import totalcross.ui.Slider;
import totalcross.ui.Spinner;
import totalcross.ui.Switch;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;
import totalcross.ui.layout.VBox;
import totalcross.util.UnitsConverter;
import totalcross.xml.AttributeList;
import totalcross.xml.ContentHandler;
import totalcross.xml.SyntaxException;
import totalcross.xml.XmlReader;

/**
 * XmlContainerLayout is a super class that abstracts the common methods to specialized classes of layout. 
 * <p>
 * It uses SAX is a standard interface for event-based XML parsing with the {@link XmlReader} class, 
 * used to read HTML or XML documents, reporting events to handlers {@link ContentHandler}
 * <p>
 * Here is an example of create a new Totalcross Container Screen from a XML file.
 * {@link XmlContainerFactory} read the XML file and instantiate the specialized class of XmlContainerLayout:
 * <pre>
 *	public void initUI(){
 *		Container container = XmlContainerFactory.create("linearSample.xml");
 *		MainWindow.getMainWindow().swap(container);
 *		Control control = ((XmlContainerLayout) container).getControlByID("@+id/btRegister");
 control.setFont(Fonts.latoBoldDefaultSize);
 *	}
 * </pre>
 */
public abstract class XmlContainerLayout extends Container {
	int layout = 0;
	private String pathXml;

	/* Treemap that saves the components of the XML file (String id, Control control)*/
	TreeMap<String, Control> componentsMap = new TreeMap<String, Control>();

	/* Control responsible to save the last control of map*/
	Control lastControl = null;

	Container centralContainer;

	CustomInitUI custom = null;

	/* Init UI after read the XML file and configure all controls and containers*/
	public void initUI() {
		try {
			readXml();
			if (custom != null) {
				custom.postInitUI(this);
			}

		} catch (IOException ei) {
			ei.printStackTrace();
		} catch ( ArrayIndexOutOfBoundsException ea) {
			ea.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  */
	public void setCustomInitUI(CustomInitUI customization) {
		this.custom = customization;
	}

	/**
	 * Abstract method that add all components of a XML file on Container
	 * Must be implemented by the child classes
	 * @exception totalcross.io.IOException
	 * @throws ImageException
	 * @param node
	 * 		XML node
	 * */
	public abstract void addscreen(NodeSax node)
			throws totalcross.io.IOException, ImageException, InvalidNumberException;

	/**
	 * Responsible to create the instances of XML components and put on a TreeMap
	 * The TreeMap saves the id of XML component and its associated Control object
	 * @param nodes
	 * 		node of a XML file
	 * @return control of id
	 * @throws totalcross.io.IOException
	 * @exception ImageException
	 * */
	public Control createInstanceOf(NodeSax nodes)
			throws totalcross.io.IOException, ImageException, InvalidNumberException {
		if (nodes.getAttributeName().contains("Button")) {
			componentsMap.put(nodes.getId(), createButton(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("Switch")) {
			componentsMap.put(nodes.getId(), createSwitch(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("SeekBar")) {
			componentsMap.put(nodes.getId(), createSlider(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().contains("TextView")) {
			componentsMap.put(nodes.getId(), createLabel(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("ProgressBar")) {
			componentsMap.put(nodes.getId(), createProgressBar(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("CheckBox")) {
			componentsMap.put(nodes.getId(), createCheck(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("EditText")) {
			componentsMap.put(nodes.getId(), createEdit(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("RadioButton")) {
			componentsMap.put(nodes.getId(), createRadio(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().contains("ImageView")) {
			componentsMap.put(nodes.getId(), createImageView(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("Spinner")) {
			componentsMap.put(nodes.getId(), createSpinner(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else {
			return new Label("");
		}
	}

	private Control createSpinner(NodeSax node) throws totalcross.io.IOException, ImageException {
		ComboBox.usePopupMenu = false;
		ComboBox simpleComboBox = new ComboBox();
		if(node.getBackgroundColor()!=null)
			simpleComboBox.setBackColor(Color.getRGB(node.getBackgroundColor()));
		return simpleComboBox;
	}

	private Control createImageView(NodeSax node)
			throws totalcross.io.IOException, ImageException, InvalidNumberException {
		ImageControl ic = null;
		String bg = node.getBackgroundImage();
		if (bg != null) {
			ic = new ImageControl(new Image(bg).getHwScaledInstance(node.getW(), node.getH()));
		} else {
			bg = node.getSrc();
			if (bg != null) {
				ic = new ImageControl(new Image(bg).getHwScaledInstance(node.getW(), node.getH()));
				// ic.scaleToFit = true;
			}
		}
		ic.transparentBackground=true;
		return ic;
	}
//
//	public Container createInstanceOfLinearLayout(NodeSax node) {
//		return new VBox(VBox.LAYOUT_FILL, VBox.ALIGNMENT_STRETCH);
//	}

	private Control createButton(NodeSax node)
			throws totalcross.io.IOException, ImageException, InvalidNumberException {
		Button button = null;
		String background = node.getBackgroundImage();
		if (background != null && "".equals(background) == false && background.charAt(0)!='#') {
			button = new Button(new Image(node.getBackgroundImage()).getHwScaledInstance(node.getW(), node.getH()),Button.BORDER_NONE);
			button.transparentBackground=true;
			//button.effect=null;
		} else {
			background = node.getBackgroundColor();
			button = new Button(node.getText());
			button.setBackColor(Color.getRGB(background));
		}

		return button;
	}

	private Control createSwitch(NodeSax node) {
		return new Switch();
	}

	private Control createSlider(NodeSax node) {
		Slider s = new Slider();
		s.setMaximum(Integer.parseInt(node.getMax()));
		return s;
	}

	private Control createLabel(NodeSax node) throws InvalidNumberException {
		Label label = new Label(node.getText(), node.getGravity());
		String bg = node.getBackgroundColor();
		String color = node.getTextColor();
		boolean txStyleBold = node.getTextStyleBold();
		txStyleBold = false;
        label.autoSplit = true;
		label.setBackForeColors(Color.getRGB(bg), Color.getRGB(color));
		if (bg == null)
			label.transparentBackground=true;
		if (node.getTextsize() != 0) {
			if (Settings.platform.equals("Android"))
				label.setFont(Font.getFont(txStyleBold, (int) ((float) (node.getTextsize()) / 2.65)));
			else
				label.setFont(Font.getFont(txStyleBold, node.getTextsize()));
		}
		else
			label.setFont(Font.getFont(txStyleBold,label.FONTSIZE));
		return label;

	}

	private Control createProgressBar(NodeSax node) {
		if (node.getStyle() == null || node.getStyle().contains("Horizontal")) {
			ProgressBar bar = new ProgressBar(0, Integer.parseInt(node.getMax()));
			bar.setValue(Integer.parseInt(node.getProgress()));
			return bar;
		} else {
			Spinner spinner = new Spinner();
			spinner.setBackForeColors(Color.getRGB("000000"),Color.getRGB(node.getTintColor()));
			return spinner;
		}
	}

	private Control createCheck(NodeSax node) {
		return new Check(node.getText());
	}

	private Control createEdit(NodeSax node) {
		Edit edit = new Edit();

		if (node.getText() != null) {
			edit.caption = node.getText();
		} else if (node.getHint() != null) {
			edit.caption = node.getHint();
		}

		return edit;
	}

	private Control createRadio(NodeSax node) {
		return new Radio(node.getText());
	}

	private class Handler extends ContentHandler {
		NodeSax auxNodeSax;

		private Handler() throws totalcross.io.IOException, ImageException {
			auxNodeSax = new NodeSax();
		}

		/* Does nothing */
		@Override
		public void characters(String arg0) {

		}

		/* Does nothing */
		@Override
		public void endElement(int arg0) {

		}

		/* Does nothing */
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
		public void tagName(int a,String arg0,AttributeList atts) {
			auxNodeSax.setAttributeName(arg0);
			auxNodeSax.reset();
			AttributeList.Iterator it = atts.new Iterator();
			while (it.next()) {
				auxNodeSax.inserts(it.getAttributeName(), it.getAttributeValue());
			}
			try {
				addscreen(auxNodeSax);
			} catch (totalcross.io.IOException e) {
				e.printStackTrace();
			} catch (ImageException e) {
				e.printStackTrace();
			} catch (InvalidNumberException e) {
				e.printStackTrace();
			}
		}
	}

	private void readXml() throws totalcross.io.IOException, ImageException, UnsupportedEncodingException {

		Handler handler = new Handler();
		XmlReader rdr = new XmlReader();
		rdr.setContentHandler(handler);

		byte[] xml = Vm.getFile(getPathXml());

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

	/** Get the XML Path
	 * @return path of XML
	 * */
	public String getPathXml() {
		return pathXml;
	}

	/** Get the Control of a given id from a component of XML file
	 * @return Control object of this ID
	 * */
	public Control getControlByID(String a) {
		return componentsMap.get(a);
	}

	/** Get the TreeMap with all Controls of the XML file
	 * @return Controls of XML
	 * */
	public TreeMap<String, Control> getControls() {
		return componentsMap;
	}

	/** Set the path of XML file
	 * @param pathXml
	 * 		path of XML file
	 * */
	public void setPathXml(String pathXml) {
		this.pathXml = pathXml;
	}

}
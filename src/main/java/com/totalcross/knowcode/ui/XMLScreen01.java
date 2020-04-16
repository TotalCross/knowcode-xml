// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: GPL-2.0-only

package com.totalcross.knowcode.ui;

import java.io.IOException;
import java.util.TreeMap;

import com.totalcross.knowcode.util.Colors;

import totalcross.sys.InvalidNumberException;
import totalcross.sys.Vm;
import totalcross.ui.*;
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

public class XMLScreen01 extends Container {
	// boolean isLayout = true;
	int layout = 0;

	int xpos = LEFT + 5;
	int ypos = TOP + 10;
	int widthPos = PARENTSIZE;
	int heightPos = PARENTSIZE;
	// int widthPos = FILL;
	// int heightPos =FILL;

	// FIXME: variaveis posicionamento global - Iaggo
	int px = LEFT;
	int py = TOP;
	int pw = PARENTSIZE;
	int ph = PARENTSIZE;

	public static final int LINEAR_LAYOUT = 1;
	public static final int FRAME_LAYOUT = 2;
	public static final int ABSOLUT_LAYOUT = 3;
	public static final int RELATIVE_LAYOUT = 4;
	public static final int CONSTRAINT_LAYOUT_ABSOLUTE = 5;
	public static final int CONSTRAINT_LAYOUT_RELATIVE = 6;
	public static final int TABLE_LAYOUT = 7;

	// FindByID: array of the class ComponentsList to save all controls and its name
	// private static ArrayList<ComponentsList> IDComponentsArray = new
	// ArrayList<ComponentsList>();
	TreeMap<String, Control> componentsMap = new TreeMap<String, Control>();
	private Control lastControl = null;

	private Container centralContainer;
	private int centralLayout = 0;

	public void initUI() {
		try {

			centralContainer = new Container();
			add(centralContainer, xpos, ypos, widthPos, heightPos);

			readXml();

			// Label botao = (Label) getControlByID("botao");
			// botao.setText("helooo");
			// FindByID: get a control and change some property
			// getControlByID("@+id/buttonCadastro").setBackColor(Color.BRIGHT);
			// getControlByID("@+id/nomeEmpresa").setFont(Fonts.latoBoldMinus1);
			// getControlByID("@+id/imageCadastro").setVisible(false);
			// ((Spinner) getControlByID("@+id/progressCadastro")).start();

			// Add the main control to screen
			// add(firstControl, xpos, ypos, width, height);
			System.out.println();
		} catch (IOException | ArrayIndexOutOfBoundsException ea) {
			ea.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addscreen(NodeSax node) throws totalcross.io.IOException, ImageException, InvalidNumberException {
		int xLocal = xpos;
		int yLocal = ypos;
		int widLocal = widthPos;
		int heiLocal = heightPos;

		if (layout == 0) {
			// isLayout = false;
			String bckG = node.getBackgroundColor();
			if (bckG != null) {
				centralContainer.setBackColor(Color.getRGB(bckG));
			}

			if (node.getAttributeName().contains("ConstraintLayout")) {
				layout = CONSTRAINT_LAYOUT_RELATIVE;
			} else if (node.getAttributeName().equals("LinearLayout")) {
				layout = LINEAR_LAYOUT;
			} else if (node.getAttributeName().equals("FrameLayout")) {
				layout = FRAME_LAYOUT;
			} else if (node.getAttributeName().equals("RelativeLayout")) {
				layout = RELATIVE_LAYOUT;
			}
		} else {
			if (node.isAbsoluteLayout()) {
				if (layout == CONSTRAINT_LAYOUT_RELATIVE) {
					layout = CONSTRAINT_LAYOUT_ABSOLUTE;
				} else {
					layout = ABSOLUT_LAYOUT;
				}
			}
			// FIXME: utilizar variaveis locais em xpos, ypos, pois pode ocorrer das mesmas
			// posições serem utilizadas mais de uma vez.
			if ((layout == CONSTRAINT_LAYOUT_ABSOLUTE) || (layout == ABSOLUT_LAYOUT)) {
				if (node.getAbsoluteX() != 0) {
					xLocal = node.getAbsoluteX();
				}
				if (node.getAbsoluteY() != 0) {
					yLocal = node.getAbsoluteY();
				} else {
					if (componentsMap.size() > 0) {
						if (lastControl == null) {
							yLocal = ypos + lastControl.getHeight() + 5;
							;
						} else {
							yLocal = ypos + lastControl.getHeight() + 5;
						}
					}
				}
				if (node.getW() != 0) {
					widLocal = node.getW();
				}
				if (node.getH() != 0) {
					heiLocal = node.getH();
				}
			}

			if ((layout == CONSTRAINT_LAYOUT_ABSOLUTE) || (layout == ABSOLUT_LAYOUT)) {

			}

			centralContainer.add(createInstanceOf(node), xLocal, yLocal, widLocal, heiLocal, lastControl);

		}
	}

	public Control createInstanceOf(NodeSax nodes) throws totalcross.io.IOException, ImageException, InvalidNumberException {

		if (nodes.getAttributeName().contains("Button")) {
			// return createButton(node);
			// IDComponentsArray.add(new ComponentsList(createButton(nodes),nodes.getId()));
			// return IDComponentsArray.get(IDComponentsArray.size()-1).getComp();
			componentsMap.put(nodes.getId(), createButton(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("Switch")) {
			// return createSwitch(node);
			// IDComponentsArray.add(new ComponentsList(createSwitch(nodes),nodes.getId()));
			// return IDComponentsArray.get(IDComponentsArray.size()-1).getComp();
			componentsMap.put(nodes.getId(), createSwitch(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("SeekBar")) {
			// return createSlider(nodes);
			componentsMap.put(nodes.getId(), createSlider(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().contains("TextView")) {
			// return createLabel(node);
			// IDComponentsArray.add(new ComponentsList(createLabel(nodes),nodes.getId()));
			// return IDComponentsArray.get(IDComponentsArray.size()-1).getComp();
			componentsMap.put(nodes.getId(), createLabel(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("ProgressBar")) {
			// return createProgressBar(node);
			// IDComponentsArray.add(new
			// ComponentsList(createProgressBar(nodes),nodes.getId()));
			// return IDComponentsArray.get(IDComponentsArray.size()-1).getComp();
			componentsMap.put(nodes.getId(), createProgressBar(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("CheckBox")) {
			// return createCheck(node);
			// IDComponentsArray.add(new ComponentsList(createCheck(nodes),nodes.getId()));
			// return IDComponentsArray.get(IDComponentsArray.size()-1).getComp();
			componentsMap.put(nodes.getId(), createCheck(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("EditText")) {
			// return createEdit(node);
			// IDComponentsArray.add(new ComponentsList(createEdit(nodes),nodes.getId()));
			// return IDComponentsArray.get(IDComponentsArray.size()-1).getComp();
			componentsMap.put(nodes.getId(), createEdit(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("RadioButton")) {
			// return createRadio(node);
			// IDComponentsArray.add(new ComponentsList(createRadio(nodes),nodes.getId()));
			// return IDComponentsArray.get(IDComponentsArray.size()-1).getComp();
			componentsMap.put(nodes.getId(), createRadio(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().contains("ImageView")) {
			// return createImageView(node);
			// IDComponentsArray.add(new
			// ComponentsList(createImageView(nodes),nodes.getId()));
			// return IDComponentsArray.get(IDComponentsArray.size()-1).getComp();
			componentsMap.put(nodes.getId(), createImageView(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("Spinner")) {
			// return createSpinner(node);
			// IDComponentsArray.add(new
			// ComponentsList(createSpinner(nodes),nodes.getId()));
			// return IDComponentsArray.get(IDComponentsArray.size()-1).getComp();
			componentsMap.put(nodes.getId(), createSpinner(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else {
			// return new Label("Comp: "+node.getNodeName());
			return new Label("");
		}
	}

	private Control createSpinner(NodeSax node) throws totalcross.io.IOException, ImageException {
		String[] items = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };

		ComboBox.usePopupMenu = false;
		ComboBox simpleComboBox = new ComboBox(items);
		simpleComboBox.caption = "Teste Combo";

		return simpleComboBox;
	}

	private Control createImageView(NodeSax node) throws totalcross.io.IOException, ImageException, InvalidNumberException {
		ImageControl ic = new ImageControl(
				new Image(node.getBackgroundImage()).getHwScaledInstance(node.getW(), node.getH()));
		return ic;
	}

	public Container createInstanceOfLinearLayout(NodeSax node) {
		return new VBox(VBox.LAYOUT_FILL, VBox.ALIGNMENT_STRETCH);
	}

	/*
	 * Get the XML Components and put in a NodeList
	 */

	//
	// private Container createHBox(int layout, int alignment) {
	// return new HBox(layout, alignment);
	// }
	//
	// private Container createVBox(int layout, int alignment) {
	// return new VBox(layout, alignment);
	// }

	private Control createButton(NodeSax node) throws totalcross.io.IOException, ImageException, InvalidNumberException {
		Button button = null;
		String background = node.getBackgroundImage();
		if (background != null && "".equals(background) == false) {
			button = new Button(new Image(node.getBackgroundImage()).getHwScaledInstance(node.getW(), node.getH()));
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
		// s.setUnitIncrement(Integer.parseInt(node.getProgress()));
		return s;
	}

	private Control createLabel(NodeSax node) {
		Label label = new Label(node.getText());
		String bg = node.getBackgroundColor();

		// System.out.println(bg);

		String color = node.getTextColor();
		boolean txStyleBold = node.getTextStyleBold();
		String txSize = node.getTextsize();

		if (bg != null)
			label.setBackForeColors(Color.getRGB(bg), Color.getRGB(color));

		if (txStyleBold != false && txSize != null)
			label.setFont(Font.getFont(txStyleBold, UnitsConverter.toPixels(Integer.parseInt(txSize) + DP)));

		return label;

	}

	private Control createProgressBar(NodeSax node) {
		if (node.getStyle() == null || node.getStyle().contains("Horizontal")) {

			ProgressBar bar = new ProgressBar(0, Integer.parseInt(node.getMax()));
			bar.setValue(Integer.parseInt(node.getProgress()));
			return bar;
		} else {
			return new Spinner();
		}
	}

	private Control createCheck(NodeSax node) {
		return new Check(node.getText());
	}

	private Control createEdit(NodeSax node) {
		Edit edit = new Edit();
		edit.caption = node.getText();
		return edit;
	}

	private Control createRadio(NodeSax node) {
		return new Radio(node.getText());
	}

	private Control getControlByID(String a) {
		// for(int i =0;i<=IDComponentsArray.size();i++)
		// if(IDComponentsArray.get(i).getAttributeName().equals(a))
		// return IDComponentsArray.get(i).getComp();
		// return null;

		return componentsMap.get(a);
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

		// @Override
		public void endElementString(String arg0) {
			// TODO Auto-generated method stub
			System.out.println("endElementString " + arg0);
		}

		// @Override
		public void startElementString(String arg0, AttributeList arg1) {
			// TODO Auto-generated method stub
			System.out.println("startElementString " + arg0);
			auxNodeSax.setAttributeName(arg0);
			auxNodeSax.reset();
		}
	}

	private void readXml() throws totalcross.io.IOException, ImageException {

		Handler handler = new Handler();
		XmlReader rdr = new XmlReader();
		rdr.setContentHandler(handler);

		byte[] xml = Vm.getFile("xml2.xml");

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
}
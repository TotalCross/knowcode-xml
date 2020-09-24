/********************************************************************************* 
 * (c) 2020 by TotalCross Global Mobile Platform LTDA
 * SPDX-License-Identifier: LGPL-3.0-only
  *********************************************************************************/
package com.totalcross.knowcode.ui;

import com.totalcross.knowcode.xml.NodeSax;

import totalcross.sys.InvalidNumberException;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.ImageException;

/**
 * XmlContainerFrameLayout is responsible to parse a FrameLayout Android XML to a Container Totalcross.
 * <p>
 * This class specialize the super class XmlContainerLayout and it is responsible to create all Controls 
 * from the XML components of a FrameLayout.
 * <p>
 * XmlContainerFrameLayout is instantiated automatically, the XmlContainerFactory class reads the type of XML layout and 
 * instantiates the corresponding layout class.
 */
public class XmlContainerFrameLayout extends XmlContainerLayout {
	boolean isLayout = true;

	private Container centralContainer = new Container();

	/**
	 * Responsible to add all components of a XML file on Container
	 * This method is call by <code>tagName</code> on the super class {@link XmlContainerLayout} 
	 * who make the read of each tag of XML file
	 * @param node
	 * 		a node of a XML file
	 *  */
	public void addscreen(NodeSax node) throws totalcross.io.IOException, ImageException, InvalidNumberException {
		int xLocal = LEFT;
		int yLocal = TOP;
		int widLocal = PARENTSIZE;
		int heiLocal = PARENTSIZE;

		if (isLayout) {
			isLayout = false;
			setBackColor(Color.getRGB(node.getBackgroundColor()));
			xLocal = LEFT + node.getPaddingLeft();
			yLocal = TOP + node.getPaddingTop();
			widLocal = FILL - node.getPaddingRight();
			heiLocal = FILL - node.getPaddingBottom();

			add(centralContainer, xLocal, yLocal, widLocal, heiLocal);
		} else if (node.getAttributeName().equals("Switch")) {
			node.setAttributeName("TextView");
			node.setId(node.getId() + "l");
			centralContainer.add(createInstanceOf(node), node.getRelativeX(), node.getRelativeY(), node.getW(),
					node.getH());
			node.setAttributeName("Switch");
			centralContainer.add(createInstanceOf(node), AFTER, node.getRelativeY(), node.getW(), node.getH());
		} else {
			centralContainer.add(createInstanceOf(node), node.getRelativeX(), node.getRelativeY(), node.getW(),
					node.getH(), getRelativeControl(node));
		}
	}

	private Control getRelativeControl(NodeSax node) {
		if (node.getRelative() != null) {
			return getControlByID(node.getRelative());
		} else
			return lastControl;
	}

}
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
 * XmlContainerRelativeLayout is responsible to parse a RelativeLayout Android XML to a Container Totalcross.
 * <p>
 * This class works together with the abstract class XmlContainerLayout and it's responsible to create all Controls 
 * from the XML components.
 * RelativeLayout is a view group that displays child views in relative positions. 
 * <p>
 * XmlContainerRelativeLayout is instantiated automatically, the XmlContainerFactory class reads the type of XML layout and 
 * instantiates the corresponding layout class.
 */

public class XmlContainerRelativeLayout extends XmlContainerLayout {
	boolean isLayout = true;

	int xpos = LEFT;
	int ypos = TOP;
	int widthPos = PARENTSIZE;
	int heightPos = PARENTSIZE;

	/**
	 * Responsible to add all components of a XML file on Container
	 * This method is call by <code>tagName</code> on the super class {@link XmlContainerLayout} 
	 * who make the read of each tag of XML file
	 * @param node
	 * 		a node of a XML file
	 *  */
	public void addscreen(NodeSax node) throws totalcross.io.IOException, ImageException, InvalidNumberException {
		int xLocal = xpos;
		int yLocal = ypos;
		int widLocal = widthPos;
		int heiLocal = heightPos;

		if (isLayout) {
			isLayout = false;
			setBackColor(Color.getRGB(node.getBackgroundColor()));
			xLocal = LEFT + node.getPaddingLeft();
			yLocal = TOP + node.getPaddingTop();
			widLocal = FILL - node.getPaddingRight();
			heiLocal = FILL - node.getPaddingBottom();

			centralContainer = new Container();

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

/********************************************************************************* 
 * (c) 2020 by TotalCross Global Mobile Platform LTDA
 * SPDX-License-Identifier: LGPL-3.0-only
  *********************************************************************************/
package com.totalcross.knowcode.parse;

import totalcross.sys.InvalidNumberException;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.ImageException;

/**
 * XmlContainerConstraintLayout is responsible to parse a ConstraintLayout Android XML to a Container Totalcross.
 * <p>
 * This class works together with the abstract class XmlContainerLayout and it is responsible to create all Controls 
 * from the XML components.
 * ConstraintLayout can work with relative positioning components, where the positioning of the component depends 
 * on the screen size, or absolute positioning components, where the component's location is fixed on the screen 
 * <p>
 * this class is instantiated automatically, the XmlContainerFactory class reads the type of xml layout and 
 * instantiates the corresponding layout class according to the nomenclature of the main tag.
 */

public class XmlContainerConstraintLayout extends XmlContainerLayout {
	boolean isLayout = true;

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
			node.setWp();
			node.setHp();
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
/********************************************************************************* 
 * (c) 2020 by TotalCross Global Mobile Platform LTDA
 * SPDX-License-Identifier: LGPL-3.0-only
  *********************************************************************************/
package com.totalcross.knowcode.ui;

import com.totalcross.knowcode.xml.NodeSax;

import totalcross.sys.InvalidNumberException;

import totalcross.ui.Container;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.ImageException;

/**
 * XmlContainerLinearLayout is responsible to parse a LinearLayout Android XML to a Container Totalcross.
 * <p>
 * This class specialize the super class XmlContainerLayout and it's responsible to create all Controls 
 * from the XML components of a LinearLayout.
 * LinearLayout is a view group that aligns all children in a single direction, vertically or horizontally.
 * <p>
 * XmlContainerLinearLayout is instantiated automatically, the XmlContainerFactory class reads the type of XML layout and 
 * instantiates the corresponding layout class.
 */

public class XmlContainerLinearLayout extends XmlContainerLayout {
	boolean isLayout = true;
	String orientation = null;

	int xpos = LEFT;
	int ypos = TOP;
	int widthPos = FILL;
	int heightPos = FILL;
	
	/**
	 * Responsible to add all components of a XML file on Container
	 * This method is call by <code>tagName</code> on the super class {@link XmlContainerLayout} 
	 * who make the read of each tag of XML file
	 * @param node
	 * 		a node of a XML file
	 *  */
	public void addscreen(NodeSax node) throws totalcross.io.IOException, ImageException, InvalidNumberException {
		
		if (isLayout) {
			isLayout = false;
			centralContainer = new Container();

			String bckG = node.getBackgroundColor();
			if (bckG != null) {
				centralContainer.setBackColor(Color.getRGB(bckG));
			}

			orientation = node.getOrientation();
			// FIXME: atualizar x e y de acordo com a orientação e atributos
			add(centralContainer, xpos, ypos, widthPos, heightPos);
			// FIXME: atualizar para quando existirem vários layouts em um mesmo xml
		} else if (node.getAttributeName().equals("LinearLayout")) {
			orientation = node.getOrientation();
		} else {
			if ((componentsMap.size() > 0) && (lastControl != null)) {
				if (orientation.equals("horizontal")) {
					xpos = xpos + lastControl.getWidth() + 3;
				} else if ((orientation.equals("vertical"))) {
					ypos = ypos + lastControl.getHeight() + 3;
				}
				xpos = node.getRelativeX();
				widthPos = node.getW();
				heightPos = node.getH();
			} else {
				// FIXME: ATUALIZAR!! Muita coisa faltando do LinearLayout
				if (node.getLayout_gravity() == null) {
					xpos = node.getRelativeX();
				} else {
					if (node.getLayout_gravity().equals("center")) {
						xpos = CENTER;
					}
				}

				ypos = node.getRelativeY();
				widthPos = node.getW();
				heightPos = node.getH();

			}
			centralContainer.add(createInstanceOf(node), xpos, ypos, widthPos, heightPos, lastControl);
		}
	}
}
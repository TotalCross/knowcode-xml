// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: GPL-2.0-only

package com.totalcross.knowcode.parse;

import totalcross.sys.InvalidNumberException;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.ImageException;

public class XmlContainerLinearLayout extends XmlContainerLayout {
	boolean isLayout = true;
	String orientation = null;

	int xpos = LEFT + 10;
	int ypos = TOP + 15;
	int widthPos = FILL - 10;
	int heightPos = FILL - 10;

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

	@Override
	public void afterInitUI() throws totalcross.io.IOException, ImageException {
		super.afterInitUI();
		//createBackButton();
	}
}
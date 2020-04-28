// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: GPL-2.0-only

package com.totalcross.knowcode.parse;

import java.util.TreeMap;


import totalcross.sys.InvalidNumberException;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.ImageException;

public class XmlScreenAbsoluteLayout extends XmlScreenAbstractLayout {

	int layout = 0;

	int xpos = LEFT + 5;
	int ypos = TOP + 10;
	int widthPos = PARENTSIZE;
	int heightPos = PARENTSIZE;

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

	TreeMap<String, Control> componentsMap = new TreeMap<String, Control>();

	private Container centralContainer;

	public void addscreen(NodeSax node) throws totalcross.io.IOException, ImageException, InvalidNumberException {
		int xLocal = xpos;
		int yLocal = ypos;
		int widLocal = widthPos;
		int heiLocal = heightPos;

		if (layout == 0) {
			String bckG = node.getBackgroundColor();
			if (bckG != null) {
				centralContainer.setBackColor(Color.getRGB(bckG));
			}

			if (node.isAbsoluteLayout()) {
				if (layout == CONSTRAINT_LAYOUT_RELATIVE) {
					layout = CONSTRAINT_LAYOUT_ABSOLUTE;
				} else {
					layout = ABSOLUT_LAYOUT;
				}
			}

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

			centralContainer.add(createInstanceOf(node), xLocal, yLocal, widLocal, heiLocal, lastControl);

		}
	}
	
	@Override
	public void afterInitUI() throws totalcross.io.IOException, ImageException {
		super.afterInitUI();
		createBackButton();
	}
}
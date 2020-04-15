// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: GPL-2.0-only

package com.totalcross.knowcode.util;

import totalcross.ui.gfx.Color;

public class Colors {
	// The entire color palette follows the default material selected by the
	// Material Color Tool:
	// https://material.io/tools/color/#!/?view.left=0&view.right=1

	// Primary Colors
	public static int PRIMARY = 0xD32F2F; // RGB: 216, 27, 96;
	public static int P_LIGHT = 0xFF6659;
	public static int P_DARK = 0x9A0007;

	// Secondary Colors
	public static int SECONDARY = 0xF44336;
	public static int S_LIGHT = 0xFF7961;
	public static int S_DARK = 0xBA000D;

	// Texts Colors
	public static int TEXT_ON_P = 0xFFFFFF;
	public static int TEXT_ON_P_LIGHT = 0x000000;
	public static int TEXT_ON_P_DARK = 0xFFFFFF;

	public static int TEXT_ON_S = 0x000000;
	public static int TEXT_ON_S_LIGHT = 0x000000;
	public static int TEXT_ON_S_DARK = 0xFFFFFF;

	// Backcolor samples colors
	public static int BACKGROUND_WHITE = 0xFFFFFF;
	public static int BACKGROUND_GRAY_01 = Color.getRGB(245, 245, 246);
	public static int BACKGROUND_GRAY_02 = Color.getRGB(225, 225, 226);
	public static int BACKGROUND_GRAY_03 = Color.getRGB(205, 205, 206);

	// Others
	public static int SOFT_PEACH = 0xE9E2E1;
	public static int GRAY = 0XC0C1E8;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (P_050)
	 */
	public static final int ON_P_050 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (P_100)
	 */
	public static final int ON_P_100 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (P_200)
	 */
	public static final int ON_P_200 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (P_300)
	 */
	public static final int ON_P_300 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (P_400)
	 */
	public static final int ON_P_400 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (P_500)
	 */
	public static final int ON_P_500 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (P_600)
	 */
	public static final int ON_P_600 = 0xFFFFFF;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (P_700)
	 */
	public static final int ON_P_700 = 0xFFFFFF;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (P_800)
	 */
	public static final int ON_P_800 = 0xFFFFFF;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (P_900)
	 */
	public static final int ON_P_900 = 0xFFFFFF;

	/**
	 * This color should be used as background color of containers, cards, sheets,
	 * etc.
	 */
	public static final int P_050 = 0xe4f1fb;
	/**
	 * This color should be used as background color of containers, cards, sheets,
	 * etc.
	 */
	public static final int P_100 = 0xbfdcf7;
	/**
	 * This color should be used as background color of containers, cards, sheets,
	 * etc.
	 */
	public static final int P_200 = 0x98c7f1;
	/**
	 * This color should be used as background color of containers, cards, sheets,
	 * etc.
	 */
	public static final int P_300 = 0x73b1eb;
	/**
	 * This color should be used as background color of containers, cards, sheets,
	 * etc.
	 */
	public static final int P_400 = 0x5ba0e7;
	/**
	 * This is the same color as the PRIMARY color. This color should be used as
	 * background color of containers, cards, sheets, etc.
	 */
	public static final int P_500 = 0x4a91e2;
	/**
	 * This color should be used as background color of containers, cards, sheets,
	 * etc.
	 */
	public static final int P_600 = 0x4583d4;
	/**
	 * This color should be used as background color of containers, cards, sheets,
	 * etc.
	 */
	public static final int P_700 = 0x3e72c1;
	/**
	 * This color should be used as background color of containers, cards, sheets,
	 * etc.
	 */
	public static final int P_800 = 0x3861af;
	/**
	 * This color should be used as background color of containers, cards, sheets,
	 * etc.
	 */
	public static final int P_900 = 0x2d448f;

	private static final int ON_P_LIGHT = 0x000000;

	private static final int ON_P_DARK = 0xFFFFFF;
	// private static final int P_GREY = 0xCCCCCC;

	/**
	 * (P_500) This is the primary color of the application, the base color for
	 * everything on the app
	 */

	/**
	 * This is the foreground color of the primary color. Use this when you're using
	 * the PRIMARY color as the background
	 */
	public static final int ON_PRIMARY = ON_P_500;

	/**
	 * This color should be used as background color of selection controls, like
	 * sliders and switches; Highlighting selected text; Progress bars; Links and
	 * headlines.
	 */
	public static final int S_050 = 0xfaecee;
	/**
	 * This color should be used as background color of selection controls, like
	 * sliders and switches; Highlighting selected text; Progress bars; Links and
	 * headlines.
	 */
	public static final int S_100 = 0xf3d0d4;
	/**
	 * This color should be used as background color of selection controls, like
	 * sliders and switches; Highlighting selected text; Progress bars; Links and
	 * headlines.
	 */
	public static final int S_200 = 0xdda09f;
	/**
	 * This color should be used as background color of selection controls, like
	 * sliders and switches; Highlighting selected text; Progress bars; Links and
	 * headlines.
	 */
	public static final int S_300 = 0xcd7d7c;
	/**
	 * This color should be used as background color of selection controls, like
	 * sliders and switches; Highlighting selected text; Progress bars; Links and
	 * headlines.
	 */
	public static final int S_400 = 0xd5655e;
	/**
	 * This color should be used as background color of selection controls, like
	 * sliders and switches; Highlighting selected text; Progress bars; Links and
	 * headlines.
	 */
	public static final int S_500 = 0xd85a4a;
	/**
	 * This color should be used as background color of selection controls, like
	 * sliders and switches; Highlighting selected text; Progress bars; Links and
	 * headlines.
	 */
	public static final int S_600 = 0xca5247;
	/**
	 * This color should be used as background color of selection controls, like
	 * sliders and switches; Highlighting selected text; Progress bars; Links and
	 * headlines.
	 */
	public static final int S_700 = 0xb84a41;
	/**
	 * This color should be used as background color of selection controls, like
	 * sliders and switches; Highlighting selected text; Progress bars; Links and
	 * headlines.
	 */
	public static final int S_800 = 0xaa443b;
	/**
	 * This color should be used as background color of selection controls, like
	 * sliders and switches; Highlighting selected text; Progress bars; Links and
	 * headlines.
	 */
	public static final int S_900 = 0x993e33;

	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (S_050)
	 */
	public static final int ON_S_050 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (S_100)
	 */
	public static final int ON_S_100 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (S_200)
	 */
	public static final int ON_S_200 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (S_300)
	 */
	public static final int ON_S_300 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (S_400)
	 */
	public static final int ON_S_400 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (S_500)
	 */
	public static final int ON_S_500 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (S_600)
	 */
	public static final int ON_S_600 = 0x000000;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (S_700)
	 */
	public static final int ON_S_700 = 0xFFFFFF;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (S_800)
	 */
	public static final int ON_S_800 = 0xFFFFFF;
	/**
	 * This color should be used as foreground color of the elements with the
	 * corresponding background color (S_900)
	 */
	public static final int ON_S_900 = 0xFFFFFF;

	private static final int ON_S_LIGHT = 0x000000;

	private static final int ON_S_DARK = 0xFFFFFF;

	/**
	 * (S_800) This is the secondary color of the application, the base color for
	 * selection controls, like sliders and switches; Highlighting selected text;
	 * Progress bars; Links and headlines.
	 */

	/**
	 * This color is used as foreground when you're using the SECONDARY color as
	 * background
	 */
	public static final int ON_SECONDARY = ON_S_800;

	/** This color is used on the background of the content */
	public static final int BACKGROUND = 0xF7F7F7;
	/**
	 * This color is used as foreground when you're using the BACKGROUND color as
	 * background
	 */
	public static final int ON_BACKGROUND = 0x000000;

	/**
	 * This is the surface color, the one that is used in cards, sheets and menus.
	 */
	public static final int SURFACE = 0xFFFFFF;
	/**
	 * This color is used as foreground when you're using the SURFACE color as
	 * background
	 */
	public static final int ON_SURFACE = 0x000000;

	/** This color is used when something went correctly. A successful action. */
	public static final int SUCESS_GREEN = 0x16963c;
	/** This color is used as background when you want to highlight a warning */
	public static final int WARNING = S_LIGHT;
	/**
	 * This color is used as foreground when you're using the WARNING color as
	 * background
	 */
	public static final int ON_WARNING = ON_S_LIGHT;

	/** This color is used as background when an error happens */
	public static final int ERROR = S_DARK;
	/**
	 * This color is used as foreground when you're using the ERROR color as
	 * background
	 */
	public static final int ON_ERROR = ON_S_DARK;

	public static final int BLUE = 0x2461A9;
	public static final int RED = 0xef5350;
	public static final int ORANGE = 0xffb74d;
	public static final int YELLOW = 0xFFD700;
	public static final int KHAKI = 0xF0E68C;
	public static final int PURPLE = 0xce93d8;

}

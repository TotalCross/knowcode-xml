// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: GPL-2.0-only

package com.totalcross.knowcode.util;

import totalcross.ui.font.Font;

public class Fonts {

	public static final int FONT_DEFAULT_SIZE = 12;

	public static Font latoMediumDefaultSize;
	public static Font latoMediumPlus1;
	public static Font latoMediumPlus2;
	public static Font latoMediumPlus4;
	public static Font latoMediumMinus1;
	public static Font latoMediumMinus2;
	public static Font latoMediumMinus4;

	public static Font latoBoldDefaultSize;
	public static Font latoBoldMinus1;
	public static Font latoBoldMinus2;
	public static Font latoBoldMinus4;
	public static Font latoBoldPlus1;
	public static Font latoBoldPlus2;
	public static Font latoBoldPlus4;
	public static Font latoBoldPlus6;
	public static Font latoBoldPlus8;
	public static Font latoBoldPlus10;

	public static Font latoLightDefaultSize;
	public static Font latoLightPlus1;
	public static Font latoLightPlus2;
	public static Font latoLightPlus4;
	public static Font latoLightPlus6;
	public static Font latoLightMinus1;
	public static Font latoLightMinus2;
	public static Font latoLightMinus4;

	public static Font latoRegularMinus5;
	public static Font latoRegularPlus5;
	public static Font latoRegularDefaultSize;

	static {

		// Lato Regular
		latoRegularDefaultSize = Font.getFont("Lato Regular", false, FONT_DEFAULT_SIZE);
		latoRegularPlus5 = latoRegularDefaultSize.adjustedBy(5);
		latoRegularMinus5 = latoRegularDefaultSize.adjustedBy(-5);

		// Lato Medium
		latoMediumDefaultSize = Font.getFont("Lato Medium", false, FONT_DEFAULT_SIZE);
		latoMediumPlus1 = latoMediumDefaultSize.adjustedBy(1);
		latoMediumPlus2 = latoMediumDefaultSize.adjustedBy(2);
		latoMediumPlus4 = latoMediumDefaultSize.adjustedBy(4);
		latoMediumMinus1 = latoMediumDefaultSize.adjustedBy(-1);
		latoMediumMinus2 = latoMediumDefaultSize.adjustedBy(-2);
		latoMediumMinus4 = latoMediumDefaultSize.adjustedBy(-4);
		// Lato Bold
		latoBoldDefaultSize = Font.getFont("Lato Bold", false, FONT_DEFAULT_SIZE);
		latoBoldPlus1 = latoMediumDefaultSize.adjustedBy(1);
		latoBoldPlus2 = latoMediumDefaultSize.adjustedBy(2);
		latoBoldPlus4 = latoMediumDefaultSize.adjustedBy(4);
		latoBoldPlus6 = latoMediumDefaultSize.adjustedBy(6);
		latoBoldPlus8 = latoMediumDefaultSize.adjustedBy(8);
		latoBoldPlus10 = latoMediumDefaultSize.adjustedBy(10);
		latoBoldMinus1 = latoMediumDefaultSize.adjustedBy(-1);
		latoBoldMinus2 = latoMediumDefaultSize.adjustedBy(-2);
		latoBoldMinus4 = latoMediumDefaultSize.adjustedBy(-4);
		// Lato Light
		latoLightDefaultSize = Font.getFont("Lato Light", false, FONT_DEFAULT_SIZE);
		latoLightPlus1 = latoLightDefaultSize.adjustedBy(1);
		latoLightPlus2 = latoLightDefaultSize.adjustedBy(2);
		latoLightPlus4 = latoLightDefaultSize.adjustedBy(4);
		latoLightPlus6 = latoLightDefaultSize.adjustedBy(6);
		latoLightMinus1 = latoLightDefaultSize.adjustedBy(-1);
		latoLightMinus2 = latoLightDefaultSize.adjustedBy(-2);
		latoLightMinus4 = latoLightDefaultSize.adjustedBy(-4);
	}
}
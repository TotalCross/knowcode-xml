// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: GPL-2.0-only

package com.totalcross.knowcode;

import java.io.IOException;

import com.totalcross.knowcode.ui.StartXMLScreen;

import totalcross.sys.Settings;
import totalcross.ui.MainWindow;
import totalcross.ui.image.ImageException;

public class Knowcode extends MainWindow {

	public Knowcode() {
		setUIStyle(Settings.MATERIAL_UI);
	}

	static {
		Settings.applicationId = "TCMT";
		Settings.appVersion = "1.0.0";
		Settings.iosCFBundleIdentifier = "com.totalcross.easytiful";
	}

	public void initUI() {
		//SplashWindow sp;
		StartXMLScreen inicial = new StartXMLScreen();

		//sp = new SplashWindow();
		//sp.popupNonBlocking();
		swap(inicial);
	}
}

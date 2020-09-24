/********************************************************************************* 
 * (c) 2020 by TotalCross Global Mobile Platform LTDA
 * SPDX-License-Identifier: LGPL-3.0-only
  *********************************************************************************/

package com.totalcross.knowcode.ui;

/**
 * CustomInitUI is a interface to edit the components of the XML file before swap the window.  
 */
public interface CustomInitUI {
	void postInitUI(XmlContainerLayout container);
}

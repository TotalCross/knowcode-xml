// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: LGPL-3.0-only

package com.totalcross.knowcode.util;

import totalcross.io.IOException;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class Images {
	public static Image float_button_on_p, float_button_on_s, sample_image, logo, logo_white, background,
			logo_nome_borda, float_button_back_on_s;

	public static void loadImage() {

		try {
			float_button_on_p = new Image("images/float_button_white.png").smoothScaledBy(0.9, 0.9);
			float_button_on_s = new Image("images/float_button_black.png").smoothScaledBy(0.9, 0.9);
			sample_image = new Image("images/gota.png").smoothScaledBy(0.85, 0.85);
			logo = new Image("images/logo-totalcross-2.png");
			logo_white = new Image("images/logo-branca.png");
			background = new Image("images/imagem-template-01.png");
			logo_nome_borda = new Image("images/logo-tc-branca.png");
			float_button_back_on_s = new Image("images/seta.png").smoothScaledBy(0.9, 0.9);
		} catch (IOException | ImageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

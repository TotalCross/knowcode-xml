// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: LGPL-3.0-only

package com.totalcross.knowcode.ui;

import static totalcross.ui.Control.AFTER;
import static totalcross.ui.Control.BEFORE;
import static totalcross.ui.Control.BOTTOM;
import static totalcross.ui.Control.BOTTOM_OF;
import static totalcross.ui.Control.CENTER;
import static totalcross.ui.Control.CENTER_OF;
import static totalcross.ui.Control.DP;
import static totalcross.ui.Control.FILL;
import static totalcross.ui.Control.FIT;
import static totalcross.ui.Control.LEFT;
import static totalcross.ui.Control.PREFERRED;
import static totalcross.ui.Control.RIGHT;
import static totalcross.ui.Control.RIGHT_OF;
import static totalcross.ui.Control.SAME;
import static totalcross.ui.Control.TOP;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import totalcross.Launcher;
import totalcross.io.IOException;
import totalcross.ui.image.ImageException;
import totalcross.util.UnitsConverter;

public class NodeSax {
    private Map<String, String> attributesMap = new HashMap<String, String>();
    private String attributeValue;
    private String attributeName = new String();
    private String id;
    private String relative;

    public NodeSax() throws IOException, ImageException {
    }

    public void inserts(String name, String value) {
        attributesMap.put(name, value);
    }

    public void reset() {
        attributesMap.clear();
    }

    public void showAll() {
        Set<String> chaves = attributesMap.keySet();
        for (String chave : chaves) {
            if (chave != null)
                System.out.println("Chave: " + chave + " Atributo: " + attributesMap.get(chave));
        }
    }

    public String getRelative() {
        if (relative != null && !relative.contains("@+")) {
            relative = relative.replace("@", "@+");
        }
        return relative;
    }

    private String getValue(String a) {
        return attributesMap.get(a);

    }

    public String getMax() {
        if (getValue("android:max") == null) {
            return "100";
        }
        return getValue("android:max");
    }

    public String getProgress() {
        if (getValue("android:progress") == null)
            return "0";
        return getValue("android:progress");
    }

    public String getStyle() {
        if (getValue("android:textStyle") == null) {
            return "normal";
        }
        return getValue("android:textStyle");
    }

    public String getId() {

        if (id == null) {
            return getValue("android:id");
        } else {
            attributeValue = id;
        }
        id = null;
        return attributeValue;

    }

    public String getTextsize() {

        attributeValue = getValue("android:textSize");

        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        } else {
            if (attributeValue.contains("dp")) {
                attributeValue = attributeValue.replace("dp", "");
            }
        }
        return attributeValue;
        // return UnitsConverter.toPixels(Integer.parseInt(attributeValue)+ DP);
    }

    public boolean getTextStyleBold() {
        attributeValue = getValue("android:textStyle");
        if (attributeValue == null || "".equals(attributeValue)) {
            return false;
        } else {
            if (attributeValue.equals("bold"))
                return true;
            else
                return false;
        }
    }

    public String getBackgroundColor() {
        attributeValue = getValue("android:background");

        System.out.println("attributeValue:" + attributeValue);
        String attributeValue2 = getValue("app:srcCompat");
        System.out.println("attributeValue2:" + attributeValue2);
        ///

        if (attributeValue == null && attributeValue2 == null || "".equals(attributeValue)) {
            return null;
        } else {
            if (attributeValue == null && attributeValue2 != null) {
                attributeValue = attributeValue2;
            }
            if ('#' == attributeValue.charAt(0)) {
                attributeValue = attributeValue.substring(1);
            }
        }

        return attributeValue;
    }

    public String getBackgroundImage() {
        attributeValue = getValue("android:background");
        System.out.println("attributeValue:" + attributeValue);
        String attributeValue2 = getValue("app:srcCompat");
        System.out.println("attributeValue2:" + attributeValue2);
        ///
        if (attributeValue == null && attributeValue2 == null || "".equals(attributeValue)) {
            return attributeValue;
        } else {
            if (attributeValue == null && attributeValue2 != null) {
                attributeValue = attributeValue2;
            }
            if ('@' == attributeValue.charAt(0)) {
                attributeValue = attributeValue.substring(1);

                //// //Melhorar essa verificação
                // if (attributeValue.contains(".png") || attributeValue.contains(".jpg")) {
                // return attributeValue;
                // } else {
                // attributeValue += ".png";
                // }
            }
        }
        return getPathImage(attributeValue);
    }

    public String getPathImage(String path) {
        String raiz = path.substring(0, path.lastIndexOf("/"));
        URL url = Thread.currentThread().getContextClassLoader().getResource(raiz);
        String nomeImage = path.substring(path.lastIndexOf("/") + 1);
        File f = new File(url.getPath());
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(nomeImage);
            }
        });

        if (matchingFiles != null && matchingFiles.length > 0) {
            return matchingFiles[0].getPath();
        } else {
            return null;
        }
    }

    public String getTextColor() {
        attributeValue = getValue("android:textColor");

        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        } else {
            if ('#' == attributeValue.charAt(0)) {
                attributeValue = attributeValue.substring(1);
            }
            return attributeValue;
        }
    }

    public int getH() {
        attributeValue = getValue("android:layout_height");

        if (attributeValue == null || "".equals(attributeValue)) {
            return 0;
        } else {
            if (attributeValue.equals("wrap_content")) {
                return PREFERRED;
            } else {
                if (attributeValue.contains("dp")) {
                    attributeValue = attributeValue.replace("dp", "");
                }
            }
        }
        return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
    }

    // FIXME: esse atributo está sendo utilizado aqui e em getBackground- ,
    // verificar
    public String getSrcCompat() {
        attributeValue = getValue("app:srcCompat");

        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        } else {
            if ('@' == attributeValue.charAt(0)) {
                attributeValue = attributeValue.substring(1);
            }
            if (attributeValue.contains(".png") || attributeValue.contains(".jpg"))
                return attributeValue;
            attributeValue += ".png";
            System.out.println(attributeValue);
            return attributeValue;
        }
    }

    public int getPaddingTop() {
        attributeValue = getValue("android:paddingTop");
        if (attributeValue == null) {
            return 0;
        }
        if (attributeValue.contains("dp")) {
            attributeValue = attributeValue.replace("dp", "");
        }
        return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
    }

    public int getPaddingBottom() {
        attributeValue = getValue("android:paddingBottom");
        if (attributeValue == null) {
            return 0;
        }
        if (attributeValue.contains("dp")) {
            attributeValue = attributeValue.replace("dp", "");
        }
        return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
    }

    public int getPaddingRight() {
        attributeValue = getValue("android:paddingRight");
        if (attributeValue == null) {
            return 0;
        }
        if (attributeValue.contains("dp")) {
            attributeValue = attributeValue.replace("dp", "");
        }
        return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
    }

    public int getPaddingLeft() {
        attributeValue = getValue("android:paddingLeft");
        if (attributeValue == null) {
            return 0;
        }
        if (attributeValue.contains("dp")) {
            attributeValue = attributeValue.replace("dp", "");
        }
        return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
    }

    // public String getOrient() {
    // return orient;
    // }

    public String getAttributeName() {
        return attributeName;
    }

    public int getW() {
        attributeValue = getValue("android:layout_width");
        if (attributeValue == null || "".equals(attributeValue)) {
            return 0;
        } else {
            if (attributeValue.equals("wrap_content")) {
                return PREFERRED;
            }
            if (attributeValue.equals("match_parent") || attributeValue.equals("fill_parent")) {
                return FILL;
            }
            if (attributeValue.equals("0dp")) {
                return FIT;
            } else {
                if (attributeValue.contains("dp")) {
                    attributeValue = attributeValue.replace("dp", "");
                }
            }
        }
        return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
    }

    public String getLayout_width() {
        attributeValue = getValue("android:layout_width");
        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        }
        return attributeValue;
    }

    public String getLayout_height() {
        attributeValue = getValue("android:layout_height");
        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        }
        return attributeValue;
    }

    public int getAbsoluteX() {
        attributeValue = getValue("tools:layout_editor_absoluteX");

        if (attributeValue == null || "".equals(attributeValue)) {
            return 0;
        } else {
            if (attributeValue.equals("wrap_content"))
                return PREFERRED;
            else {
                if (attributeValue.contains("dp"))
                    attributeValue = attributeValue.replace("dp", "");
            }
            return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
        }
    }

    public int getAbsoluteY() {
        attributeValue = getValue("tools:layout_editor_absoluteY");

        if (attributeValue == null || "".equals(attributeValue)) {
            return 0;

        } else {
            if (attributeValue.equals("wrap_content")) {
                return PREFERRED;
            } else {
                if (attributeValue.contains("dp")) {
                    attributeValue = attributeValue.replace("dp", "");
                }
            }

            System.out.println("y:" + attributeValue);
            return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
        }
    }

    public String getOrientation() {
        attributeValue = getValue("android:orientation");

        if (attributeValue == null || "".equals(attributeValue)) {
            return null;

        } else {
            return attributeValue;
        }
    }

    // FIXME: verificar esse método
    public int getRelativeX() {
        attributeValue = getValue("android:layout_alignParentLeft");
        if (attributeValue != null) {
            return LEFT;
        }

        attributeValue = getValue("android:layout_alignParentRight");
        if (attributeValue != null) {
            return RIGHT;
        }

        attributeValue = getValue("android:layout_alignParentEnd");
        if (attributeValue != null) {
            return RIGHT;
        }

        attributeValue = getValue("android:layout_alignParentStart");
        if (attributeValue != null) {
            return LEFT;
        }

        attributeValue = getValue("android:layout_toEndOf");
        if (attributeValue != null) {
            relative = attributeValue;
            return AFTER;
        }

        attributeValue = getValue("android:layout_alignRight");
        if (attributeValue != null) {
            relative = attributeValue;
            return RIGHT_OF;
        }

        attributeValue = getValue("android:layout_alignLeft");
        if (attributeValue != null) {
            relative = attributeValue;
            return SAME;
        }

        attributeValue = getValue("android:layout_alignLeft");
        if (attributeValue != null) {
            relative = attributeValue;
            return SAME;
        }

        attributeValue = getValue("android:layout_alignEnd");
        if (attributeValue != null) {
            relative = attributeValue;
            return RIGHT_OF;
        }

        attributeValue = getValue("android:layout_toLeftof");
        if (attributeValue != null) {
            relative = attributeValue;
            return BEFORE;
        }

        attributeValue = getValue("android:layout_toRightOf");
        if (attributeValue != null) {
            relative = attributeValue;
            return AFTER;
        }

        attributeValue = getValue("android:layout_toStartOf");
        if (attributeValue != null) {
            relative = attributeValue;
            return BEFORE;
        }

        attributeValue = getValue("android:layout_centerInParent");
        if (attributeValue != null) {
            return CENTER;
        }

        attributeValue = getValue("android:layout_centerHorizontal");
        if (attributeValue != null) {
            return CENTER;
        }

        attributeValue = getValue("tools:layout_editor_absoluteX");
        if (attributeValue == null) {
            return LEFT;
        }

        if (attributeValue.equals("wrap_content")) {
            return PREFERRED;
        } else {
            if (attributeValue.contains("dp")) {
                attributeValue = attributeValue.replace("dp", "");
            }
        }
        return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
    }

    public String getLayout_gravity() {
        attributeValue = getValue("android:layout_gravity");
        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        } else {
            return attributeValue;
        }
    }

    public boolean isAbsoluteLayout() {
        if (getValue("tools:layout_editor_absoluteX") != null || getValue("tools:layout_editor_absoluteY") != null) {
            return true;
        }
        return false;
    }

    // FIXME: verificar esse método
    public int getRelativeY() {
        attributeValue = getValue("android:layout_alignBaseline");
        if (attributeValue != null) {
            relative = attributeValue;
            return CENTER_OF;
        }

        attributeValue = getValue("android:layout_alignTop");
        if (attributeValue != null) {
            relative = attributeValue;
            return SAME;
        }

        attributeValue = getValue("android:layout_alignBottom");
        if (attributeValue != null) {
            relative = attributeValue;
            return BOTTOM_OF;
        }

        attributeValue = getValue("android:layout_below");
        if (attributeValue != null) {
            relative = attributeValue;
            return AFTER;
        }

        attributeValue = getValue("android:layout_above");
        if (attributeValue != null) {
            relative = attributeValue;
            return BEFORE;
        }

        attributeValue = getValue("android:layout_alignParentTop");
        if (attributeValue != null) {
            return TOP;
        }

        attributeValue = getValue("android:layout_alignParentBottom");
        if (attributeValue != null) {
            return BOTTOM;
        }

        attributeValue = getValue("android:layout_centerVertical");
        if (attributeValue != null) {
            return CENTER;
        }

        attributeValue = getValue("android:layout_centerInParent");
        if (attributeValue != null) {
            return CENTER;
        }

        attributeValue = getValue("android:layout_centerInParent");
        if (attributeValue != null) {
            return CENTER;
        }

        attributeValue = getValue("tools:layout_editor_absoluteY");
        if (attributeValue == null) {
            return TOP;
        }

        if (attributeValue.equals("wrap_content")) {
            return PREFERRED;
        }

        else {
            if (attributeValue.contains("dp")) {
                attributeValue = attributeValue.replace("dp", "");
            }
        }
        return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getText() {
        return getValue("android:text");
    }

    public String getSrc() {
        attributeValue = getValue("android:src");

        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        } else {
            if ('@' == attributeValue.charAt(0)) {
                attributeValue = attributeValue.substring(1);
            }

            // FIXME: ADICIONAR AQUI UMA VERIFICAÇÃO DE EXTENÇÃO DE IMAGEM
            if (attributeValue.contains(".png") || attributeValue.contains(".jpg")) {
                return attributeValue;
            } else {
                attributeValue += ".jpg";
            }
        }
        return attributeValue;
    }

    public String getHint() {
        attributeValue = getValue("android:hint");
        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        }
        return attributeValue;
    }

}

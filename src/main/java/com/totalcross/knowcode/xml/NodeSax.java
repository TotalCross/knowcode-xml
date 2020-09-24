/********************************************************************************* 
 * (c) 2020 by TotalCross Global Mobile Platform LTDA
 * SPDX-License-Identifier: LGPL-3.0-only
 *********************************************************************************/
package com.totalcross.knowcode.xml;

import static totalcross.ui.Control.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import totalcross.io.IOException;
import totalcross.sys.InvalidNumberException;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.image.ImageException;
import totalcross.util.BigDecimal;
import totalcross.util.UnitsConverter;

/**
 * NodeSax access the nodes and attributes of the XML file.
 * Each method reads a specific tag and return its value.
 */
public class NodeSax {
    private Map<String, String> attributesMap = new HashMap<String, String>();
    protected String attributeValue;
    protected String attributeName = new String();
    protected String id;
    protected String relative;
    protected float wp = 0, hp = 0;
    NodeSax node = new NodeSax();

    /**
     * Creates a NodeSax object
     *
     * @throws ImageException
     * @throws IOException
     */
    public NodeSax() throws IOException, ImageException {
    }
    
    /** Creates a NodeSax object for the others NodeSax childrens
     * @exception ImageException
     * @throws IOException
     * */
    public NodeSax(NodeSax n) throws IOException, ImageException {
        this.node = n.node;
    }

    /**
     * Get the attribute name of tag
     *
     * @return attribute value of tag
     */
    public String getAttributeName() {
        return attributeName;
    }

    protected String getValue(String a) {
        return attributesMap.get(a);
    }

    /**
     * Get attribute value of tag <code>"android:max"</code>
     *
     * @return attribute value of tag
     */
    public String getMax() {
        if (getValue("android:max") == null) {
            return "100";
        }
        return getValue("android:max");
    }

    /**
     * Get attribute value of tag <code>"android:progress"</code>
     *
     * @return attribute value of tag
     */
    public String getProgress() {
        if (getValue("android:progress") == null)
            return "0";
        return getValue("android:progress");
    }

    /**
     * Get attribute value of tag <code>"android:textStyle"</code>
     *
     * @return attribute value of tag
     */
    public String getStyle() {
        if (getValue("style") != null)
            return getValue("style");
        if (getValue("android:textStyle") == null) {
            return "normal";
        }

        return getValue("android:textStyle");
    }

    /**
     * Get attribute value of tag <code>"android:id"</code>
     *
     * @return attribute value of tag
     */
    public String getId() {

        if (id == null) {
            return getValue("android:id");
        } else {
            attributeValue = id;
        }
        id = null;
        return attributeValue;
    }

    /**
     * Get attribute value of tag <code>"android:textSize"</code>
     *
     * @return attribute value of tag
     */
    public int getTextsize() throws InvalidNumberException {

        attributeValue = getValue("android:textSize");

        if (attributeValue == null || "".equals(attributeValue)) {
            return 0;
        } else {
            if (attributeValue.contains("dp")) {
                attributeValue = attributeValue.replace("dp", "");
            }
            if (attributeValue.contains("sp")) {
                attributeValue = attributeValue.replace("sp", "");
            }
        }
        return new BigDecimal(UnitsConverter.toPixels(Integer.parseInt(attributeValue) - 5 + DP)).multiply(BigDecimal.valueOf(hp)).intValue();

    }

    /**
     * Get attribute value of tag <code>"android:textStyle"</code>
     *
     * @return attribute value of tag
     */
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

    /**
     * Get attribute value of tag <code>"android:background"</code> to color background
     *
     * @return attribute value of tag
     */
    public String getBackgroundColor() {
        attributeValue = getValue("android:background");

        String attributeValue2 = getValue("app:srcCompat");

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

    /**
     * Get attribute value of tag <code>"android:background"</code> to image background
     *
     * @return attribute value of tag
     */
    public String getBackgroundImage() {
        attributeValue = getValue("android:background");
        String attributeValue2 = getValue("app:srcCompat");
        if (attributeValue == null && attributeValue2 == null || "".equals(attributeValue)) {
            return attributeValue;
        } else {
            if (attributeValue == null && attributeValue2 != null) {
                attributeValue = attributeValue2;
            }
            if ('@' == attributeValue.charAt(0)) {
                attributeValue = attributeValue.substring(1);
            }

            if (attributeValue.contains(".png") || attributeValue.contains(".jpg")) {
                return attributeValue;
            } else {
                attributeValue = getImageExtension(attributeValue);
            }
        }
        return attributeValue;

    }

    private String getImageExtension(String path) {
        String[] extensoes = {".jpg", ".png", ".bmp", ".jpeg", ".gif",
                ".JPG", ".PNG", ".BMP", ".JPEG", ".GIF"};
        byte[] xml = null;
        for (int i = 0; i < extensoes.length; i++) {
            xml = Vm.getFile(path + extensoes[i]);

            if (xml != null) {
                return path + extensoes[i];
            }
        }

        return path;
    }


    /**
     * Get attribute value of tag <code>"android:textColor"</code>
     *
     * @return attribute value of tag
     */
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

    /**
     * Get attribute value of tag <code>"android:indeterminateTint"</code>
     *
     * @return attribute value of tag
     */
    public String getTintColor() {
        attributeValue = getValue("android:indeterminateTint");

        if (attributeValue == null || "".equals(attributeValue)) {
            return "FFFFFF";
        } else {
            if ('#' == attributeValue.charAt(0)) {
                attributeValue = attributeValue.substring(1);
            }
            return attributeValue;
        }
    }

    /**
     * Get the height value converted to Pixel, checks the following tags <code>"android:layout_height"</code>
     * and <code>"app:layout_constraintHeight_percent"</code>
     *
     * @return height value converted to Pixel
     * @throws InvalidNumberException
     */
    public int getH() throws InvalidNumberException {
        attributeValue = getValue("app:layout_constraintHeight_percent");
        if (attributeValue != null)
            try {
                return new BigDecimal(attributeValue).multiply(BigDecimal.valueOf(Settings.screenHeight)).intValue();
            } catch (InvalidNumberException e) {
                return 0;
            }
        attributeValue = getValue("android:layout_height");
        if (attributeValue == null || "".equals(attributeValue)) {
            attributeValue = getValue("app:layout_constraintHeight_percent");
            if (attributeValue != null)
                return new BigDecimal(attributeValue).multiply(BigDecimal.valueOf(Settings.screenHeight)).intValue();
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
        return new BigDecimal(UnitsConverter.toPixels(Integer.parseInt(attributeValue))).multiply(BigDecimal.valueOf(hp)).intValue();
    }

    /**
     * Get attribute value of tag <code>"app:srcCompat"</code>
     *
     * @return attribute value of tag
     */
    public String getSrcCompat() {
        attributeValue = getValue("app:srcCompat");

        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        } else {
            if ('@' == attributeValue.charAt(0)) {
                attributeValue = attributeValue.substring(1);
            }
            if (attributeValue.contains(".png") || attributeValue.contains(".jpg")) {
                return attributeValue;
            }

            attributeValue = getImageExtension(attributeValue);
            return attributeValue;
        }
    }

    /**
     * Get attribute value of tag <code>"android:paddingTop"</code>
     *
     * @return attribute value of tag
     */
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


    /**
     * Get attribute value of tag <code>"android:paddingBottom"</code>
     *
     * @return attribute value of tag
     */
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

    /**
     * Get attribute value of tag <code>"android:paddingRight"</code>
     *
     * @return attribute value of tag
     */
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

    /**
     * Get attribute value of tag <code>"android:paddingLeft"</code>
     *
     * @return attribute value of tag
     */
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


    /**
     * Get the width value converted to Pixel, checks the following tags <code>"android:layout_width"</code>
     * and <code>"app:layout_constraintWidth_percent"</code>
     *
     * @return width value converted to Pixel
     */
    public int getW() throws InvalidNumberException {
        attributeValue = getValue("app:layout_constraintWidth_percent");
        if (attributeValue != null)
            try {
                return new BigDecimal(attributeValue).multiply(BigDecimal.valueOf(Settings.screenWidth)).intValue();
            } catch (InvalidNumberException e) {
                return 0;
            }

        attributeValue = getValue("android:layout_width");
        if (attributeValue == null || "".equals(attributeValue)) {
            return 0;
        } else {
            if (attributeValue.equals("wrap_content")) {
                return PREFERRED;
            }
            if (attributeValue.equals("match_parent") || attributeValue.equals("fill_parent")) {
                return Settings.screenWidth;
            }
            if (attributeValue.equals("0dp")) {
                return FIT;
            } else {
                if (attributeValue.contains("dp")) {
                    attributeValue = attributeValue.replace("dp", "");
                }
            }
        }
        return new BigDecimal(UnitsConverter.toPixels(Integer.parseInt(attributeValue))).multiply(BigDecimal.valueOf(wp)).intValue();
    }

    /**
     * Get attribute value of tag <code>"android:layout_width"</code>
     *
     * @return attribute value of tag
     */
    public String getLayout_width() {
        attributeValue = getValue("android:layout_width");
        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        }
        return attributeValue;
    }

    /**
     * Get attribute value of tag <code>"android:layout_height"</code>
     *
     * @return attribute value of tag
     */
    public String getLayout_height() {
        attributeValue = getValue("android:layout_height");
        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        }
        return attributeValue;
    }

    /**
     * Get value of x axis converted to Pixel of tag <code>"tools:layout_editor_absoluteX"</code>
     *
     * @return attribute value of tag converted to Pixel
     */
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

    /**
     * Get value of y axis converted to Pixel of tag <code>"tools:layout_editor_absoluteY"</code>
     *
     * @return attribute value of tag converted to Pixel
     */
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

            return UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
        }
    }

    /**
     * Get orientation based on tag <code>"android:orientation"</code>
     *
     * @return attribute value of tag
     */
    public String getOrientation() {
        attributeValue = getValue("android:orientation");

        if (attributeValue == null || "".equals(attributeValue)) {
            return null;

        } else {
            return attributeValue;
        }
    }

    


    

    /**
     * Get Layout Gravity based on tag <code>"android:layout_gravity"</code>
     *
     * @return value of tag
     */
    public String getLayout_gravity() {
        attributeValue = getValue("android:layout_gravity");
        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        } else {
            return attributeValue;
        }
    }

    /**
     * Get gravity based on tag <code>"android:gravity"</code>
     *
     * @return constant of position
     */
    public int getGravity() {
        attributeValue = getValue("android:gravity");
        if (attributeValue != null) {
            if (attributeValue.equals("left")) {
                return FILL;
            }
            if (attributeValue.equals("center"))
                return CENTER;
            if (attributeValue.equals("right"))
                return RIGHT;
        }
        return LEFT;
    }

    /**
     * Get wp
     *
     * @return value of wp
     */
    public float getWp() {
        return wp;
    }

    /**
     * Get hp
     *
     * @return value of hp
     */
    public float getHp() {
        return hp;
    }

    /**
     * Get text based on tag <code>"android:text"</code>
     *
     * @return value of the tag
     */
    public String getText() {
        return getValue("android:text");
    }

    /**
     * Get text based on tag <code>"android:scaleY"</code>
     *
     * @return value of the tag
     */
    public String getScaleY() {
        attributeValue = getValue("android:scaleY");
        if (attributeValue == null || "".equals(attributeValue)) {
            return "1";
        }
        return attributeValue;
    }

    /**
     * Get text based on tag <code>"android:scaleY"</code>
     *
     * @return value of the tag
     */
    public String getScaleX() {
        attributeValue = getValue("android:scaleX");
        if (attributeValue == null || "".equals(attributeValue)) {
            return "1";
        }
        return attributeValue;
    }

    /**
     * Get source of a image based on tag <code>"android:src"</code>
     *
     * @return value of the tag
     */
    public String getSrc() {
        attributeValue = getValue("android:src");

        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        } else {
            if ('@' == attributeValue.charAt(0)) {
                attributeValue = attributeValue.substring(1);
            }

            if (attributeValue.contains(".png") || attributeValue.contains(".jpg")) {
                return attributeValue;
            } else {
                attributeValue = getImageExtension(attributeValue);
            }
        }
        return attributeValue;
    }

    /**
     * Get information of the tag <code>"android:hint"</code>
     *
     * @return value of the tag
     */
    public String getHint() {
        attributeValue = getValue("android:hint");
        if (attributeValue == null || "".equals(attributeValue)) {
            return null;
        }
        return attributeValue;
    }

    /**
     * Clear the attributesMap HashMap of the XML component
     */
    public void reset() {
        attributesMap.clear();
    }

    /**
     * Get information if it is absolute layout based on tags <code>"tools:layout_editor_absoluteX"</code> and
     * <code>"tools:layout_editor_absoluteY"</code>
     *
     * @return true or false
     */
    public boolean isAbsoluteLayout() {
        if (getValue("tools:layout_editor_absoluteX") != null
                || getValue("tools:layout_editor_absoluteY") != null) {
            return true;
        }
        return false;
    }

    /**
     * Inserts a item in a attributesMap HashMap
     *
     * @param name  name of tag
     * @param value value of tag
     */
    public void inserts(String name, String value) {
        attributesMap.put(name, value);
    }

    /**
     * Set the id of a tag
     *
     * @param id id of XML component
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * set parameter width to try to resize a predefined screen size
     */
    public void setWp() throws InvalidNumberException {
        attributeValue = getValue("android:layout_width");
        if (attributeValue != null) {
            if (attributeValue.equals("match_parent") || attributeValue.equals("fill_parent")) {
                wp = Settings.screenWidth;
            } else {
                if (attributeValue.contains("dp")) {
                    attributeValue = attributeValue.replace("dp", "");
                }
                wp = UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
            }

        }
        wp = Settings.screenWidth / wp;

    }

    /**
     * set parameter height to try to resize a predefined screen size
     */
    public void setHp() throws InvalidNumberException {
        attributeValue = getValue("android:layout_height");
        if (attributeValue != null) {
            if (attributeValue.equals("match_parent") || attributeValue.equals("fill_parent")) {
                hp = Settings.screenHeight;
            } else {
                if (attributeValue.contains("dp")) {
                    attributeValue = attributeValue.replace("dp", "");
                }
                hp = UnitsConverter.toPixels(Integer.parseInt(attributeValue) + DP);
            }
        }
        hp = Settings.screenHeight / hp;
    }
    
    
    /**
     * Get the relative positioning of x axis.
     * Checks the following tags:
     * <code>"android:layout_alignParentLeft"</code>
     * <code>"app:layout_constraintLeft_toLeftOf"</code>
     * <code>"app:layout_constraintStart_toStartOf"</code>
     * <code>"app:layout_constraintStart_toEndOf"</code>
     * <code>"app:layout_constraintLeft_toRightOf"</code>
     * <code>"app:layout_constraintEnd_toStartOf"</code>
     * <code>"app:layout_constraintRight_toRightOf"</code>
     * <code>"app:layout_constraintEnd_toEndOf"</code>
     * <code>"android:layout_alignParentRight"</code>
     * <code>"android:layout_alignParentEnd"</code>
     * <code>"android:layout_alignParentStart"</code>
     * <code>"android:layout_toEndOf"</code>
     * <code>"android:layout_alignRight"</code>
     * <code>"android:layout_alignLeft"</code>
     * <code>"android:layout_alignEnd"</code>
     * <code>"android:layout_toLeftof"</code>
     * <code>"android:layout_toRightOf"</code>
     * <code>"android:layout_toStartOf"</code>
     * <code>"android:layout_centerInParent"</code>
     * <code>"android:layout_centerHorizontal"</code>
     * <code>"android:layout_editor_absoluteX"</code>
     *
     * @return totalcross positioning constant
     */
    public int getRelativeX() throws InvalidNumberException {
        attributeValue = getValue("android:layout_alignParentLeft");
        if (attributeValue != null) {
            return LEFT;
        }
        attributeValue = getValue("app:layout_constraintLeft_toLeftOf");
        if (attributeValue != null) {
            if (attributeValue.equals("parent")) {
                return LEFT;
            } else {
                relative = attributeValue;
                return SAME;
            }
        }
        attributeValue = getValue("app:layout_constraintStart_toStartOf");

        if (attributeValue != null) {
            if (attributeValue.equals("parent")) {
                return LEFT;
            } else {
                relative = attributeValue;
                return SAME;
            }
        }
        attributeValue = getValue("app:layout_constraintStart_toEndOf");
        if (attributeValue != null) {
            if (attributeValue.equals("parent")) {
                return LEFT;
            } else {
                relative = attributeValue;
                return SAME;
            }
        }
        attributeValue = getValue("app:layout_constraintLeft_toRightOf");
        if (attributeValue != null) {
            relative = attributeValue;
            return AFTER;
        }
        attributeValue = getValue("app:layout_constraintRight_toLeftOf");
        if (attributeValue != null) {
            relative = attributeValue;
            return BEFORE;
        }
        attributeValue = getValue("app:layout_constraintEnd_toStartOf");
        if (attributeValue != null) {
            relative = attributeValue;
            return BEFORE;
        }
        attributeValue = getValue("app:layout_constraintRight_toRightOf");
        if (attributeValue != null) {
            if (attributeValue.equals("parent")) {
                return RIGHT;
            } else {
                relative = attributeValue;
                return RIGHT_OF;
            }
        }
        attributeValue = getValue("app:layout_constraintEnd_toEndOf");
        if (attributeValue != null) {
            if (attributeValue.equals("parent")) {
                return RIGHT;
            } else {
                relative = attributeValue;
                return RIGHT_OF;
            }
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
        return new BigDecimal(UnitsConverter.toPixels(Integer.parseInt(attributeValue))).multiply(BigDecimal.valueOf(wp)).intValue();
    }
    
    /**
     * Get the relative positioning of y axis.
     * Checks the following tags:
     * <code>"app:layout_constraintTop_toBottomOf"</code>
     * <code>"app:layout_constraintTop_toTopOf"</code>
     * <code>"app:constraintBottom_toTopOf"</code>
     * <code>"app:layout_constraintBottom_toBottomOf"</code>
     * <code>"app:layout_constraintBaseline_toBaselineOf"</code>
     * <code>"app:layout_constraintRight_toRightOf"</code>
     * <code>"android:layout_alignBaseline"</code>
     * <code>"android:layout_alignTop"</code>
     * <code>"android:layout_alignBottom"</code>
     * <code>"android:layout_below"</code>
     * <code>"android:layout_above"</code>
     * <code>"android:layout_alignParentTop"</code>
     * <code>"android:layout_alignParentBottom"</code>
     * <code>"android:layout_centerVertical"</code>
     * <code>"android:layout_toRightOf"</code>
     * <code>"android:layout_toStartOf"</code>
     * <code>"android:layout_centerInParent"</code>
     * <code>"tools:layout_editor_absoluteY"</code>
     *
     * @return totalcross positioning constant
     */
    public int getRelativeY() throws InvalidNumberException {
        attributeValue = getValue("app:layout_constraintTop_toBottomOf");
        if (attributeValue != null) {
            if (attributeValue.equals("parent"))
                return TOP;
            relative = attributeValue;
            return AFTER;
        }

        attributeValue = getValue("app:layout_constraintTop_toTopOf");
        if (attributeValue != null) {
            if (attributeValue.equals("parent"))
                return TOP;
            relative = attributeValue;
            return SAME;
        }

        attributeValue = getValue("app:constraintBottom_toTopOf");
        if (attributeValue != null)
            return BEFORE;

        attributeValue = getValue("app:layout_constraintBottom_toBottomOf");
        if (attributeValue != null)
            if (attributeValue.equals("parent"))
                return BOTTOM;
            else {
                relative = attributeValue;
                return BOTTOM_OF;
            }

        attributeValue = getValue("layout_constraintBaseline_toBaselineOf");
        if (attributeValue != null)
            if (attributeValue.equals("parent"))
                return CENTER_OF;
            else {
                relative = attributeValue;
                return CENTER_OF;
            }

        attributeValue = getValue("app:layout_constraintRight_toRightOf");
        if (attributeValue != null) {
            if (attributeValue.equals("parent"))
                return Settings.screenWidth - getW();
            return RIGHT;
        }
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
        attributeValue = getValue("tools:layout_editor_absoluteY");
        if (attributeValue == null) {
            return TOP;
        }
        if (attributeValue.equals("wrap_content")) {
            return PREFERRED;
        } else {
            if (attributeValue.contains("dp")) {
                attributeValue = attributeValue.replace("dp", "");
            }
        }

        return new BigDecimal(UnitsConverter.toPixels(Integer.parseInt(attributeValue))).multiply(BigDecimal.valueOf(hp)).intValue();
    }

    
    /**
     * Get relative position set by <code>getRelativeX()</code> and <code>getRelativeY()</code>
     *
     * @return attribute value of tags related to relative positioning
     */
    public String getRelative() {
        if (relative != null && !relative.contains("@+")) {
            relative = relative.replace("@", "@+");
        }
        return relative;
    }
    
    /**
     * Set attribute name of a tag
     *
     * @param attributeName attribute name
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }


    /**
     * Show all elements of attributesMap HashMap, with the Key like a tag name and attribute of it
     */
    public void showAll() {
        Set<String> chaves = attributesMap.keySet();
        for (String chave : chaves) {
            if (chave != null)
                System.out.println("Key: " + chave + " Attribute: " + attributesMap.get(chave));
        }
    }

}
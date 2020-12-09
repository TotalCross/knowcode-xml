/*********************************************************************************
 * (c) 2020 by TotalCross Global Mobile Platform LTDA
 * SPDX-License-Identifier: LGPL-3.0-only
 *********************************************************************************/
package com.totalcross.knowcode.parse;

import java.lang.reflect.Field;

import com.totalcross.knowcode.parse.XmlContainerFactory;
import com.totalcross.knowcode.parse.XmlContainerLayout;

import totalcross.ui.Container;
import totalcross.ui.Control;

import static java.lang.Character.*;
/**
 * XMLPresenter is a super class that can extended to help minimize to use of code with knowcode.
 * <p>
 * It uses reflection to create container and bind variables to respectives ids ,
 * and also use sliding navigator to control windows swap with a simple animation.
 * <p>
 * Here is an example of how to use this class.
 * {@link XMLPresenter} read the class name converting it from camel case to snake case, if it dont have a correspondent
 *  xml it will giving a error you can also use a constructor to specify which xml is to use, it also will be trying to
 *  bind a public variable to his respective id:
 * <pre>
 *     public class SampleClass extends XMLPresenter {
 *     public Button ButtonNextScreen;
 *     @Override
 *     public void onPresent() {
 *         navigator.onClick(ButtonNextScreen, NextScreen.class);
 *     }
 * }
 * </pre>
 */
public abstract class XMLPresenter {

   protected Container content;
   protected SlidingNavigator navigator;
   public boolean isFirstPresent = true;

   public XMLPresenter(Container content) {
      this.content = content;
   }

   public XMLPresenter() {
      bind();
   }
    /**
     * method that will be called if xml isnt specified, converting the class name from camel case to snake case
     * and the snake case will be the xml name that will be trying to find
     * */
   private void bind() {
      final String clazzCanonicalName = this.getClass().getName();
      final int lastPeriodIdx = clazzCanonicalName.lastIndexOf('.');
      final String clazzName = lastPeriodIdx == -1 ? clazzCanonicalName
            : clazzCanonicalName.substring(lastPeriodIdx + 1);
      final String xmlName = snakeCaseFormat(
            clazzName.endsWith("Presenter") ? clazzName.substring(0, clazzName.length() - 9) : clazzName);
      content = XmlContainerFactory.create("layout/" + xmlName + ".xml");
   }
    /**
     * method that will be called if there are any public variable, this method will try to match the variable name
     * to his corresponding variable id
     * */
   public void bind2() {
      Field[] fields = this.getClass().getDeclaredFields();
      for (Field field : fields) {
         Control c = ((XmlContainerLayout) content).getControlByID("@+id/" + field.getName());
         if (c != null && field.getType().isAssignableFrom(c.getClass())) {
            try {
               // field.setAccessible(true);
               field.set(this, c);
            } catch (IllegalArgumentException | IllegalAccessException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
   }
   /**
    * Abstract method that can be use to control the event and paramenters of the container
    * Must be implemented by the child classes
    * */
   public abstract void onPresent();
   /**
    * Responsible to refresh
    * @param clazzName
    * @return snake case class name
    * */
   public void setNavigator(SlidingNavigator navigator) {
      this.navigator = navigator;
   }
   /**
    * Responsible to converter class name from camel case to snake case
    * @param clazzName
    * @return snake case class name
    * */
   private static String snakeCaseFormat(String name) {
      final StringBuilder result = new StringBuilder();

      boolean lastUppercase = false;

      for (int i = 0; i < name.length(); i++) {
         char ch = name.charAt(i);
         char lastEntry = i == 0 ? 'X' : result.charAt(result.length() - 1);
         if (ch == ' ' || ch == '_' || ch == '-' || ch == '.') {
            lastUppercase = false;

            if (lastEntry == '_') {
               continue;
            } else {
               ch = '_';
            }
         } else if (Character.isUpperCase(ch)) {
            ch = Character.toLowerCase(ch);
            // is start?
            if (i > 0) {
               if (lastUppercase) {
                  // test if end of acronym
                  if (i + 1 < name.length()) {
                     char next = name.charAt(i + 1);
                     if (!Character.isUpperCase(next) && /* Character. */isAlphabetic(next)) {
                        // end of acronym
                        if (lastEntry != '_') {
                           result.append('_');
                        }
                     }
                  }
               } else {
                  // last was lowercase, insert _
                  if (lastEntry != '_') {
                     result.append('_');
                  }
               }
            }
            lastUppercase = true;
         } else {
            lastUppercase = false;
         }

         result.append(ch);
      }
      return result.toString();
   }
   /**
    * Responsible to verify if the char is a alphabetic
    * @param char
    * @return char
    * */
   private static boolean isAlphabetic(char c) {
      return ((1 << Character.getType(c)) & ((1 << UPPERCASE_LETTER) | (1 << LOWERCASE_LETTER) | (1 << TITLECASE_LETTER)
            | (1 << MODIFIER_LETTER) | (1 << OTHER_LETTER) | (1 << LETTER_NUMBER))) != 0;
   }
}

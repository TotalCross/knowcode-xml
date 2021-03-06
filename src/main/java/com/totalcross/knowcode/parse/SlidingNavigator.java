/*********************************************************************************
 * (c) 2020 by TotalCross Global Mobile Platform LTDA
 * SPDX-License-Identifier: LGPL-3.0-only
 *********************************************************************************/
package com.totalcross.knowcode.parse;

import static totalcross.ui.Control.AFTER;
import static totalcross.ui.Control.FILL;
import static totalcross.ui.Control.LEFT;
import static totalcross.ui.Control.SCREENSIZE;
import static totalcross.ui.Control.TOP;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import totalcross.sys.Settings;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.Window;
import totalcross.ui.anim.ControlAnimation;
import totalcross.ui.anim.PathAnimation;
import totalcross.ui.event.Event;
/**
 * XmlContainerLayout is a class that control the navigation of {@link XMLPresenter} class.
 * <p>
 * It uses map of classes which extend or are {@link XMLPresenter} also call his method bind variables to respectives
 * ids ,sliding navigator control windows putting them in cache and use a simple animation when swapping to another
 * widow.
 * <p>
 * Here is an example of how to use this class.
 * {@link SlidingNavigator} using in the MainWindow class it will be easier to use this class it will get the main
 * window as parameter and change to a {@link XMLPresenter} which will be the home class
 * <pre>
 *     public class SampleClass extends MainWindow {
 * public void initUI() {
 * try {
 * new SlidingNavigator(this).present(SilverHome.class);
 * } catch (InstantiationException | IllegalAccessException e) {
 *  * e.printStackTrace();
 * }
 * </pre>
 */

public class SlidingNavigator {

   Window window;
   int totaltimeAnimation,tox1,fromx2,toy1,fromy2;
   Stack<XMLPresenter> presenters = new Stack<>();
   Map<Class<? extends XMLPresenter>, XMLPresenter> cache = new HashMap<>();

   public SlidingNavigator(Window window) {
      this.window = window;
      totaltimeAnimation = 600;
      this.tox1=-Settings.screenWidth;
      this.fromx2=Settings.screenWidth;
      this.toy1=0;
      this.fromy2=0;
   }
   public SlidingNavigator(Window window,int totaltimeAnimation) {
      this.window = window;
      this.totaltimeAnimation = totaltimeAnimation;
      this.tox1=-Settings.screenWidth;
      this.fromx2=Settings.screenWidth;
      this.toy1=0;
      this.fromy2=0;
   }
   public SlidingNavigator(Window window,int totaltimeAnimation,int tox1,int toy1,int fromx2,int fromy2) {
      this.window = window;
      this.totaltimeAnimation = totaltimeAnimation;
      this.tox1=tox1;
      this.fromx2=fromx2;
      this.toy1=toy1;
      this.fromy2=fromy2;
   }
   /**
    * Responsible to instantiate new classes, put them in cache and use animation
    * @param presenterClass
    * @throws InstantiationException
    * @throws IllegalAccessException
    * */
   public void present(Class<? extends XMLPresenter> presenterClass)
         throws InstantiationException, IllegalAccessException {
      final XMLPresenter presenter = cache.containsKey(presenterClass) ? cache.get(presenterClass)
            : presenterClass.newInstance();
      if (!cache.containsKey(presenterClass)) {
         cache.put(presenterClass, presenter);
      }

      if (presenters.isEmpty()) {
         window.add(presenter.content, LEFT, TOP, FILL, FILL);
      } else {
         XMLPresenter previous = presenters.lastElement();

         window.add(presenter.content, fromx2, fromy2, SCREENSIZE, SCREENSIZE, previous.content);
         PathAnimation.create(previous.content, tox1, toy1, new ControlAnimation.AnimationFinished() {
            @Override
            public void onAnimationFinished(ControlAnimation anim) {
               window.remove(previous.content);
            }
         }, totaltimeAnimation).with(PathAnimation.create(presenter.content, 0, 0, new ControlAnimation.AnimationFinished() {
            @Override
            public void onAnimationFinished(ControlAnimation anim) {
               presenter.content.setRect(LEFT, TOP, FILL, FILL);
            }
         }, totaltimeAnimation)).start();
      }
      presenter.setNavigator(this);
      presenters.push(presenter);
      presenter.bind2();
      if (presenter.isFirstPresent) {
         presenter.onPresent();
         presenter.isFirstPresent = false;
      }
   }
   /**
    * Responsible to go return to the last switched container
    * @param e
    * */
   public void back(Event e) {
      if (presenters.size() < 2) {
         // nothing to do
         return;
      }

      XMLPresenter current = presenters.pop();
      XMLPresenter previous = presenters.lastElement();

      window.add(previous.content, fromx2*(-1), fromy2*(-1), SCREENSIZE, SCREENSIZE);
      PathAnimation.create(current.content, tox1*(-1), toy1*(-1), new ControlAnimation.AnimationFinished() {
         @Override
         public void onAnimationFinished(ControlAnimation anim) {
            window.remove(current.content);
         }
      }, totaltimeAnimation).with(PathAnimation.create(previous.content, 0, 0, new ControlAnimation.AnimationFinished() {
         @Override
         public void onAnimationFinished(ControlAnimation anim) {
            previous.content.setRect(LEFT, TOP, FILL, FILL);
         }
      }, totaltimeAnimation)).start();
   }
   /**
    * Responsible to go return first/home container
    * @param e
    * */
   public void home(Event e) {
      if (presenters.size() < 2) {
         // nothing to do
         return;
      }

      XMLPresenter home = presenters.firstElement();
      window.removeAll();
      window.add(home.content, LEFT, TOP, SCREENSIZE, SCREENSIZE);
      presenters.setSize(1);
   }
   /**
    * Responsible to get class Container to edit from another class.
    * @param presenterClass
    * @return Container
    * */
   public Container getClassContainer(Class<? extends XMLPresenter> presenterClass){
      XMLPresenter aux = null;
      for(int a=0;a<presenters.size();a++) {
         aux = presenters.get(a);
         if(aux.getClass() == presenterClass)
            return aux.content;
      }
      return null;
   }
   /**
    * Responsible to get class to able to edit from another class.
    * @param presenterClass
    * @return Container
    * */
   public XMLPresenter getClass(Class<? extends XMLPresenter> presenterClass){
      XMLPresenter aux = null;
      for(int a=0;a<presenters.size();a++) {
         aux = presenters.get(a);
         if(aux.getClass() == presenterClass)
            return aux;
      }
      return null;
   }
   /**
    * Responsible to control when switch the screen/container which control should do it and which class should go.
    * @param presenterClass
    * @param target
    * */

   public void onClick(Control target, Class<? extends XMLPresenter> presenterClass) {
      target.addPressListener((e) -> {
         try {
            SlidingNavigator.this.present(presenterClass);
         } catch (InstantiationException | IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
      });
   }
}
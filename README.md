# Knowcode - Library to allow developers to run Android XML UI + TotalCross on Linux ARM, iOS, Android and more...
KnowCode is a project that was born to solve a problem, which is: 
How can developers build their screens faster based on images made by designers?

So, there is two main tasks:
* Computer vision to understand the image and convert it to a XML file
* Read a XML file, parse to Totalcross and run on Linux ARM, iOS, Android and more...

Here we have the second task, named KnowCodeXML.
KnowcodeXML is a library that interprets Android XML files and generates Totalcross screens ready to run on Android, iOS and Linux ARM devices.

### Steps to use Knowcode
* Create a Totalcross Project 
	You can create a simple hello world project like [Hello World project](https://learn.totalcross.com/documentation/get-started/install#create-a-hello-world-project)
* Use TotalCross sdk 6.1 or highter and add the dependency on file pom.xml

	<dependency>
		<groupId>com.totalcross</groupId>
		<artifactId>totalcross-sdk</artifactId>
		<version>6.1.0</version>
	</dependency>
	
* Add KnowcodeXML dependency

	<dependency>
		<groupId>com.totalcross.knowcode</groupId>
		<artifactId>KnowCodeXML</artifactId>
		<version>1.1</version>
	</dependency>
* Now, let's import our xml file and show the window in the initUI method of the MainWindow class of your project
	
	import com.totalcross.knowcode.parse.XmlContainerFactory;
	public class HelloKnowcode extends MainWindow {
		@Override
		public void initUI() {
			Container cont = XmlContainerFactory.create("simplescreen.xml");
			MainWindow.getMainWindow().swap(cont);
		}
	}
	
 * To change the components of xml file, use the method *getControlByID* passing like parameter the id of xml file
 
	import com.totalcross.knowcode.parse.XmlContainerLayout;
	import com.totalcross.knowcode.parse.XmlContainerFactory;
	public class HelloKnowcode extends MainWindow {
		@Override
		public void initUI() {
			Container cont = XmlContainerFactory.create("simplescreen.xml");
			MainWindow.getMainWindow().swap(cont);
			Control control = ((XmlContainerLayout) container).getControlByID("@+id/btRegister");
			control.setBackColor(Color.BRIGHT);
		}
	}

 * If you have to add some component or make some change before swap the window, use the CustomInitUI Interface

	import com.totalcross.knowcode.parse.XmlContainerLayout;
	import com.totalcross.knowcode.parse.CustomInitUI;
	import com.totalcross.knowcode.parse.XmlContainerFactory;
	public class HelloKnowcode extends MainWindow {
		@Override
		public void initUI() {
			Container cont = XmlContainerFactory.create("simplescreen.xml");	
			XmlContainerLayout xmlContainerLayout = (XmlContainerLayout)cont;
				
			xmlContainerLayout.setCustomInitUI(new CustomInitUI() {
				public void postInitUI(XmlContainerLayout contLayout) {
					Button btCancel = new Button("Cancel");
					btCancel.setBackColor(Color.BRIGHT);
					Control btRegister = contLayout.getControlByID("@+id/btRegister");
					btRegister.setBackColor(Color.BRIGHT);
					int posBtCancel = btRegister.getY()+btRegister.getHeight();
				
					contLayout.add(btCancel, Container.CENTER, posBtCancel+2, Container.PARENTSIZE, Container.PREFERRED);
				}
			});
		MainWindow.getMainWindow().swap(cont);
	}
	
## How KnowcodeXML works
We support all main Android layouts: ConstraintLayout, LinearLayout and RelativeLayout.

You just have to configure maven to use KnowcodeXML in your Totalcross project. After that, import the needed classes, *XmlContainerFactory* and *XmlContainerLayout*. These classes are the entry points to projects that use Knowcode Library. If you have to add some components that does not in your xml file, import the *CustomInitUI* too

![my project KC](https://imgur.com/fW7kgeC.png)

The *XmlContainerFactory* class reads the XML file and defines which layout to create based on the wrapping layout from XML. Once the layout has been identified, KnowcodeXML calls the abstract class that has all the common methods for layouts and instantiates one of the layout classes. The image below shows the class structure belonging to the project.

![class KC](https://imgur.com/oV08WZO.png)
### Sample
We have some projects on github using this API, like [HomeApplianceXML](https://github.com/TotalCross/HomeApplianceXML), a sample application that illustrate the use of API in a Home Appliance Controller.

![EW Sample](https://imgur.com/jkBlar1.png)

The create method of *XmlContainerFactory* class returns a Container object of the layout to put on the window with swap method.
The method *getControlByID* of the class *XmlContainerLayouts* returns a Control object created of the XML file.
		
		public void initUI() {
			XmlContainerLayout xmlCont = (XmlContainerLayout) XmlContainerFactory.create("xml/homeApplianceXML2.xml");
			swap(xmlCont);
	
			Button plus = (Button) xmlCont.getControlByID("@+id/plus");
			...
			
			plus.addPressListener(new PressListener() {
				@Override
				public void controlPressed(ControlEvent e) {
					// TODO
					try {
						String tempString = insideTempLabel.getText();
						int temp;
						temp = Convert.toInt(tempString);
						insideTempLabel.setText(Convert.toString(++temp));
	
					} catch (InvalidNumberException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}

## More Samples and Tutorials

* [HelloKnowcode](https://github.com/TotalCross/HelloKnowcode)
* [HomeApplianceXML](https://github.com/TotalCross/HomeApplianceXML)
* [KnowcodeSample](https://github.com/TotalCross/KnowcodeSample)
* [ToradexLauncherSample](https://github.com/TotalCross/ToradexLauncherSample)
* [Android XML + Totalcross = Rich UI/UX on Linux ARM](https://www.youtube.com/watch?v=7o3p14wQPsE)
* [Creating and using your own external Java library for your TotalCross Applications](https://www.youtube.com/watch?v=Cq5yEPTmZWI)







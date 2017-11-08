package iOS.test.helper;

import iOS.utils.ConfigProperty;
import iOS.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.Reporter;

public class BasicTest {

	public static IOSDriver<WebElement> wd;
	public static LogUtils log;
	private static DesiredCapabilities capabilities;

	public static DesiredCapabilities getCapabilities() {
		return capabilities;
	}

	public void beforeTest() {
		// set up appium
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("launchTimeout", 20000);
		// capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability("newCommandTimeout", 150);
		// capabilities.setCapability("fullReset", true);
		// capabilities.setCapability("noReset", true);
		capabilities.setCapability("autoLaunch", true);
		capabilities.setCapability("platformName", getValue("platformName"));
		capabilities.setCapability("deviceName", getValue("deviceName"));
		capabilities.setCapability("platformVersion", getValue("platformVersion"));
//		capabilities.setCapability("app", getValue("app"));
		capabilities.setCapability("bundleId", getValue("bundleId"));
		capabilities.setCapability("udid", getValue("udid"));
	}

	public void beforeMethod() throws MalformedURLException {
		wd = new IOSDriver<WebElement>(new URL(getValue("server_ip")), capabilities);
	}

	public static IOSDriver<WebElement> getDriver() {
		return wd;
	}

	public void catchExceptions(ITestResult result) throws IOException {
		System.out.println("result" + result);
		String methodName = result.getName();
		System.out.println(methodName);

		String fileName = methodName + ".png";
		String logFile = methodName + ".txt";

		File screenshot = wd.getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/target/surefire-reports/html/screenshot/";
		if (!(new File(path)).exists()) {
			(new File(path)).mkdirs();
		}

		String filePath = path + fileName;
		File destFile = new File(filePath);
		FileUtils.copyFile(screenshot, destFile);

		Reporter.setCurrentTestResult(result);
		Reporter.log("<img src='./screenshot/" + fileName + "' onclick='window.open(\"./screenshot/" + fileName
				+ "\")' height='160' width='90' />");
		Reporter.log("<a href=\"./logs/" + logFile + "\">Debug log</a>");
		// Reporter.log("<img src=\"../../../screenshot/" + fileName + "\"/>");
		// Reporter.log("</br>");
		// Reporter.log("<a href=\"./screenshot/" + fileName + "\">" + fileName
		// + "</a>");
		// Reporter.log("</br>");

		// File file = new File("");
		// Reporter.setCurrentTestResult(result);
		// System.out.println(file.getAbsolutePath());
		// Reporter.log(file.getAbsolutePath());
		// String filePath = file.getAbsolutePath();
		// filePath = filePath.replace("/opt/apache-tomcat-7.0.64/webapps",
		// "http://172.18.44.114:8080");
		// Reporter.log("<img src='" + filePath + "/" + result.getName() +
		// ".jpg' hight='100' width='100'/>");
		// int width = 100;
		// int height = 100;
		// String s = "这是一张测试图片";
		// File screenShotFile = new File(file.getAbsolutePath() + "/" +
		// result.getName() + ".jpg");
		//
		// Font font = new Font("Serif", Font.BOLD, 10);
		// BufferedImage bi = new BufferedImage(width, height,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics2D g2 = (Graphics2D) bi.getGraphics();
		// g2.setBackground(Color.BLACK);
		// g2.clearRect(0, 0, width, height);
		// g2.setPaint(Color.RED);
		//
		// FontRenderContext context = g2.getFontRenderContext();
		// Rectangle2D bounds = font.getStringBounds(s, context);
		// double x = (width - bounds.getWidth()) / 2;
		// double y = (height - bounds.getHeight()) / 2;
		// double ascent = -bounds.getY();
		// double baseY = y + ascent;
		//
		// g2.drawString(s, (int) x, (int) baseY);
		//
		// try {
		// ImageIO.write(bi, "jpg", screenShotFile);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public String getTestName() {
		ITestResult it = Reporter.getCurrentTestResult();
		String name = it.getMethod().getMethodName();
		System.out.println("TestName[" + name + "]");
		return name;
	}

	public void startLanxinApp(String phoneNum, String password) {
		log = new LogUtils(getTestName());
		printLog("step1=Start Lanxin App");
		String org_name = getValue("org_name");
		if (waitForNameExists(Constant.TXT_TABBAR_ADS, 3000)) {
			return;
		} else {
			printLog(0, "Pass the pre-setup");
			if (waitForXpathExists(Constant.XPATH_EV_LOGIN_NUM, 1000)
					&& waitForNameExists(Constant.TXT_NEXT_STEP, 1000)) {
				while (!waitForXpathExists(Constant.XPATH_EV_LOGIN_NUM, 1000)
						&& waitForNameExists(Constant.TXT_NEXT_STEP, 1000)) {
					clickName(Constant.TXT_NEXT_STEP);
				}
				if (waitForTextExists(Constant.TXT_LOGIN, 2000)) {
					clickText(Constant.TXT_LOGIN, false);
				}
			}

			assertTrue("Pass the pre-setup failed.", waitForXpathExists(Constant.XPATH_EV_LOGIN_NUM, 1000));

			printLog(1, "Input phone number");
			getElementByXpath(Constant.XPATH_TF_LOGIN_NUM).clear();
			enterTextByXpath(Constant.XPATH_TF_LOGIN_NUM, phoneNum);

			printLog(2, "Click the next button");
			clickName(Constant.TXT_NEXT_STEP);

			printLog(3, "Choose the platform");
			if (waitForXpathExists(Constant.XPATH_PLANTFORM_TIP, 3000)) {
				clickName(org_name);
			}
			assertTrue("Enter input password page failed.", waitForXpathExists(Constant.XPATH_EV_LOGIN_PWD, 5000));

			printLog(4, "Input password");
			enterTextByXpath(Constant.XPATH_EV_LOGIN_PWD, password);
			clickName(Constant.TXT_SIGN_IN);
		}
		assertTrue("Login App failed.", waitForNameExists(Constant.TXT_TABBAR_ADS, 3000));
	}

	public void assertTrue(String message, boolean condition) {
		AssertJUnit.assertTrue(message, condition);
	}

	public void assertFalse(String message, boolean condition) {
		AssertJUnit.assertFalse(message, condition);
	}

	public void assertFalse(String message) {
		AssertJUnit.fail(message);
	}

	public void saveLog(String data) {
		log.saveData(data);
	}

	public void printLog(int step, String content) {
		System.out.println("STEP" + step + "==" + content);
		Reporter.log("Step" + step + "==" + content);
		saveLog("Step" + step + "==" + content);
	}

	public void printLog(String title, String content) {
		System.out.println(title + "==" + content);
		Reporter.log(title + "==" + content);
		saveLog(title + "==" + content);
	}

	public void printLog(String content) {
		System.out.println(content);
		Reporter.log(content);
		saveLog(content);
	}

	public void enterTextByXpath(String xpath, String txt) {
		if (isViewWithXpath(xpath)) {
			try {
				wd.findElementByXPath(xpath).sendKeys(txt);
			} catch (Exception e) {
				assertTrue("sendKeys[" + txt + "] failed!!!", false);
			}
		} else {
			assertTrue("Xpath[" + xpath + "] not found!!!", false);
		}
	}

	public void enterTextByName(String name, String txt) {
		if (isViewWithName(name)) {
			try {
				wd.findElementByAccessibilityId(name).clear();
				wd.findElementByAccessibilityId(name).sendKeys(txt);
			} catch (Exception e) {
				assertTrue("sendKeys[" + txt + "] failed!!!", false);
			}
		} else {
			assertTrue("Name[" + name + "] not found!!!", false);
		}
	}

	public void enterNumbersByKeyboard(String num) {
		printLog("Helper==Enter Numbers By Keyboard");
		char[] list = num.toCharArray();
		for (int i = 0; i < list.length; i++) {
			String name = String.valueOf(list[i]);
			if (isViewWithName(name)) {
				wd.findElementByAccessibilityId(name).click();
			} else {
				assertTrue("name[" + name + "] not found!!!", false);
			}
		}
	}
	
	public void closeKeyboard() {
		if (wd.getKeyboard() != null) {
			wd.hideKeyboard();
		}
	}
	
	public void clickPoint(Point p) {
		wd.tap(1, p.x, p.y, 200);
	}
	
	public void clickPoint(int x, int y) {
		wd.tap(1, x, y, 200);
	}
	
	public void clickCenterPoint(WebElement e) {
		Point p = getElementCenterPoint(e);
		clickPoint(p);
	}
	
	public void clickElement(WebElement e) {
		Point p = getElementCenterPoint(e);
		saveLog("[Tap]: x[" + p.x + "] y[" + p.y + "]");
		wd.tap(1, p.x, p.y, 200);
	}

	public void clickName(String name) {
		if (isViewWithName(name)) {
			try {
				wd.findElementByAccessibilityId(name).click();
				saveLog("[Click]: name[" + name + "]");
			} catch (Exception e) {
				WebElement element = getElementByName(name);
				if (element != null) {
					Point p = element.getLocation();
					wd.tap(1, p.x, p.y, 200);
				} else {
					printLog("name[" + name + "] cann't be click!!!");
				}
			}
		} else {
			printLog("name[" + name + "] not found!!!");
		}
	}

	public void clickTextInList(String name, int index) {
		clickNameInList(name, index);
	}

	public void clickNameInList(String name, int index) {
		if (isViewWithName(name)) {
			List<WebElement> list = wd.findElementsByAccessibilityId(name);
			WebElement element = list.get(index);
			try {
				element.click();
				saveLog("[Click]: name[" + name + "] index[" + index + "]");
			} catch (Exception e) {
				if (element != null) {
					Point p = element.getLocation();
					wd.tap(1, p.x, p.y, 200);
				} else {
					assertTrue("name[" + name + "] cann't be click!!!", false);
				}
			}
		} else {
			assertTrue("name[" + name + "] not found!!!", false);
		}
	}

	public void clickText(String txt, boolean flag) {
		clickName(txt);
		if (flag) {
			sleep(1000);
		}
	}

	public void clickXpath(String xpath) {
		if (isViewWithXpath(xpath)) {
			try {
				wd.findElementByXPath(xpath).click();
				saveLog("[Click]: xpath[" + xpath + "]");
			} catch (Exception e) {
				WebElement element = getElementByXpath(xpath);
				if (element != null) {
					Point p = element.getLocation();
					wd.tap(1, p.x, p.y, 200);
				} else {
					printLog("xpath[" + xpath + "] cann't be click!!!");
				}
			}
		} else {
			printLog("xpath[" + xpath + "] not found!!!");
		}
	}

	public void longClickTextBySwipe(String text) {
		Point p = getElementByText(text).getLocation();
		wd.swipe(p.x, p.y, p.x, p.y, 3000);
	}

	public void longClickElementBySwipe(WebElement element) {
		if (element != null) {
			Point p = getElementCenterPoint(element);
			wd.swipe(p.x, p.y, p.x, p.y, 3000);
		} else {
			assertTrue("Element not exist!", false);
		}
	}
	
	public void longClickElementBySwipe(WebElement element, int timeout) {
		if (element != null) {
			Point p = getElementCenterPoint(element);
			wd.swipe(p.x, p.y, p.x, p.y, timeout);
		} else {
			assertTrue("Element not exist!", false);
		}
	}
	
	public Point getElementCenterPoint(WebElement element){
		Point p = element.getLocation();
		Dimension size = element.getSize();
		int x = p.x + size.width/2;
		int y = p.y + size.height/2;
		System.out.println("center=(" + x + ", "+ y + ")");
		return new Point(x,y);
	}
	
	public Point getElementCenterPointByName(String name){
		WebElement element = getElementByName(name);
		Point p = element.getLocation();
		Dimension size = element.getSize();
		int x = p.x + size.width/2;
		int y = p.y + size.height/2;
		System.out.println("center=(" + x + ", "+ y + ")");
		return new Point(x,y);
	}
	
	public Point getElementCenterPointByXpath(String xpath){
		WebElement element = getElementByXpath(xpath);
		Point p = element.getLocation();
		Dimension size = element.getSize();
		int x = p.x + size.width/2;
		int y = p.y + size.height/2;
		System.out.println("center=(" + x + ", "+ y + ")");
		return new Point(x,y);
	}

	public WebElement getLastMsgInChat() {
		int count = getElementsByUiAutomation(".tableViews()[1].cells()").size();
		printLog("chat msg count[" + count + "]");

		String locator = ".tableViews()[1].cells()[" + String.valueOf(count - 1) + "].buttons()[0]";
		WebElement element = getElementByUiAutomation(locator);
		if (element != null) {
			if (element.getLocation().x < 30) {
				locator = ".tableViews()[1].cells()[" + String.valueOf(count - 1) + "].buttons()[1]";
				element = getElementByUiAutomation(locator);
			}
			saveLog("count[" + count + "] location[" + element.getLocation().x + "," + element.getLocation().y);
		} else {
			locator = ".tableViews()[1].cells()[" + String.valueOf(count - 1) + "]";
			element = getElementByUiAutomation(locator);
			if (element != null) {
				saveLog("count[" + count + "] location[" + element.getLocation().x + "," + element.getLocation().y);
			}
		}
		return element;
	}

	public WebElement getLastMsgInChat(int count) {
		String locator = ".tableViews()[1].cells()[" + String.valueOf(count - 1) + "].buttons()[0]";
		WebElement element = getElementByUiAutomation(locator);
		if (element != null) {
			saveLog("count[" + count + "] location[" + element.getLocation().x + "," + element.getLocation().y);
		} else {
			locator = ".tableViews()[1].cells()[" + String.valueOf(count - 1) + "]";
			element = getElementByUiAutomation(locator);
			if (element != null) {
				saveLog("count[" + count + "] location[" + element.getLocation().x + "," + element.getLocation().y);
			}
		}
		return element;
	}
	
	public int getElementsCount(String locator) {
		List<WebElement> list = getElementsByUiAutomation(locator);
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
	}

	public WebElement getElementByUiAutomation(String locator) {
		WebElement element = null;
		try {
			element = wd.findElementByIosUIAutomation(locator);
		} catch (Exception e) {

		}
		return element;
	}

	public List<WebElement> getElementsByUiAutomation(String locator) {
		List<WebElement> list = null;
		try {
			list = wd.findElementsByIosUIAutomation(locator);
		} catch (Exception e) {

		}
		return list;
	}
	
	public WebElement getElementByNameForInstance(String name, int inst) {
		List<WebElement> list = null;
		try {
			list = wd.findElementsByAccessibilityId(name);
		} catch (Exception e) {
			
		}
		
		if (list != null) {
			if (inst < list.size() -1) {
				return list.get(inst);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void longClickXpathBySwipe(String xpath) {
		Point p = getElementByXpath(xpath).getLocation();
		wd.swipe(p.x, p.y, p.y, p.y, 2000);
	}

	public void swipeByText(String text) {
		Point p = getElementByText(text).getLocation();
		wd.swipe(p.x + 100, p.y + 15, 0, p.y + 15, 500);
		sleep(500);
	}

	public void swipeByXpath(String xpath) {
		Point p = getElementByXpath(xpath).getLocation();
		wd.swipe(toScreenX(0.8f), p.y, toScreenX(0.2f), p.y, 500);
	}

//	public WebElement scrollToFindText(String text) {
//		return wd.scrollTo(text);
//	}

	public boolean waitForNameExists(String name, long timeout) {
		for (int i = 0; i < timeout / 200; i++) {
			if (isViewWithName(name)) {
				break;
			} else {
				sleep(200);
			}
		}
		if (isViewWithName(name)) {
			saveLog("[Verify]: name[" + name + "] exist!");
			return true;
		} else {
			saveLog("[Verify]: name[" + name + "] not exist!");
			return false;
		}
	}

	public boolean waitForTextExists(String name, long timeout) {
		return waitForNameExists(name, timeout);
	}

	public boolean waitForNameGone(String name, long timeout) {
		// WebDriverWait wait = new WebDriverWait(wd, timeout);
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
		WebElement element;
		for (int i = 0; i < timeout / 200; i++) {
			element = getElementByName(name);
			if (element != null) {
				if (element.isDisplayed()) {
					sleep(200);
				} else {
					break;
				}
			} else {
				break;
			}
		}

		element = getElementByName(name);
		if (element != null) {
			if (element.isDisplayed()) {
				saveLog("[Verify]: name[" + name + "] still exist!");
				return false;
			} else {
				saveLog("[Verify]: name[" + name + "] has gone!");
				return true;
			}
		} else {
			saveLog("[Verify]: name[" + name + "] has gone!");
			return true;
		}
	}

	public boolean waitForTextGone(String name, long timeout) {
		return waitForNameGone(name, timeout);
	}

	public boolean isViewWithName(String name) {
		WebElement element = null;
		try {
			element = wd.findElementByAccessibilityId(name);
		} catch (Exception e) {

		}

		if (element == null) {
			return false;
		} else {
			// System.out.println("element isn't null");
			// if (element.isDisplayed()) {
			return true;
			// } else {
			// return false;
			// }
		}
	}

	public boolean isViewWithText(String name) {
		return isViewWithName(name);
	}

	public void waitForWindowUpdate(long timeout) {
		sleep(timeout);
	}

	public void waitForWindowUpdate() {
		sleep(3000);
	}

	public boolean waitForXpathExists(String xpath, long timeout) {
		for (int i = 0; i < timeout / 200; i++) {
			if (isViewWithXpath(xpath)) {
				break;
			} else {
				sleep(200);
			}
		}
		if (isViewWithXpath(xpath)) {
			saveLog("[Verify]: xpath[" + xpath + "] exist!");
			return true;
		} else {
			saveLog("[Verify]: xpath[" + xpath + "] exist!");
			return false;
		}
	}

	public boolean waitForXpathGone(String xpath, long timeout) {
		WebElement element;
		for (int i = 0; i < timeout / 200; i++) {
			element = getElementByXpath(xpath);
			if (element != null) {
				if (element.isDisplayed()) {
					sleep(200);
				} else {
					break;
				}
			} else {
				break;
			}
		}

		element = getElementByXpath(xpath);
		if (element != null) {
			if (element.isDisplayed()) {
				saveLog("[Verify]: xpath[" + xpath + "] still exist!");
				return false;
			} else {
				saveLog("[Verify]: xpath[" + xpath + "] has gone!");
				return true;
			}
		} else {
			saveLog("[Verify]: xpath[" + xpath + "] has gone!");
			return true;
		}
	}

	public boolean isViewWithXpath(String xpath) {
		WebElement element = null;
		try {
			element = wd.findElementByXPath(xpath);
		} catch (Exception e) {

		}

		if (element == null) {
			return false;
		} else {
			// if (element.isDisplayed()) {
			return true;
			// } else {
			// return false;
			// }
		}
	}

	public void sleep(long second) {
		saveLog("[Wait]: sleep[" + second + "] ms");
//		for (int i = 0; i < second/100; i++) {
//			wd.manage().timeouts().implicitlyWait(100, TimeUnit.MICROSECONDS);
//		}
		try {
			Thread.sleep(second);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getValue(String key) {
		ConfigProperty cp = new ConfigProperty("config.properties");
		String value = cp.getValue(key);

		return value;
	}

	public void workeGroup_CreateGroup(String phoneNum, String password) {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click titlebar_plus");
		if (isViewWithName(Constant.NAME_TITLEBAR_PLUS)) {
			clickName(Constant.NAME_TITLEBAR_PLUS);
		} else {
			clickXpath(Constant.XPATH_TITLEBAR_PLUS);
		}
		assertTrue("Click the titlebar plus failed.", waitForNameExists(Constant.TXT_NEW_WORK_GROUP, 3000));

		clickName(Constant.TXT_NEW_WORK_GROUP);
		waitForWindowUpdate(3000);
		assertTrue("Ui Element not exist: " + Constant.TXT_SELECT_BY_CONTACTS,
				waitForNameExists(Constant.TXT_SELECT_BY_CONTACTS, 8000));
	}

	public void callMeeting_CreateMeeting(String phoneNum, String password) {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click titlebar_plus");
		if (isViewWithName(Constant.NAME_TITLEBAR_PLUS)) {
			clickName(Constant.NAME_TITLEBAR_PLUS);
		} else {
			clickXpath(Constant.XPATH_TITLEBAR_PLUS);
		}
		assertTrue("Click the titlebar plus failed.", waitForNameExists(Constant.TXT_NEW_MEETING_CALL, 3000));

		clickName(Constant.TXT_NEW_MEETING_CALL);
		waitForWindowUpdate(3000);
		assertTrue("Ui Element not exist: " + Constant.TXT_SELECT_BY_CONTACTS,
				waitForNameExists(Constant.TXT_SELECT_BY_CONTACTS, 8000));
	}

	public void groupChat_CreateGroup(String phoneNum, String password) {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click titlebar_plus");
		if (isViewWithName(Constant.NAME_TITLEBAR_PLUS)) {
			clickName(Constant.NAME_TITLEBAR_PLUS);
		} else {
			clickXpath(Constant.XPATH_TITLEBAR_PLUS);
		}
		assertTrue("Click the titlebar plus failed.", waitForNameExists(Constant.TXT_NEW_GROUP_CHAT, 3000));

		clickName(Constant.TXT_NEW_GROUP_CHAT);
		waitForWindowUpdate(3000);
		assertTrue("Ui Element not exist: " + Constant.TXT_SELECT_BY_CONTACTS,
				waitForNameExists(Constant.TXT_SELECT_BY_CONTACTS, 8000));
	}

	public String getTextByXpath(String xpath) {
		WebElement element = null;
		if (isViewWithXpath(xpath)) {
			element = wd.findElementByXPath(xpath);
			return element.getText();
		} else {
			return "";
		}
	}

	public WebElement getElementByXpath(String xpath) {
		WebElement element = null;
		if (isViewWithXpath(xpath)) {
			try {
				element = wd.findElementByXPath(xpath);
			} catch (Exception e) {

			}
		}
		return element;
	}

	public WebElement getElementByName(String name) {
		WebElement element = null;
		if (isViewWithName(name)) {
			try {
				element = wd.findElementByAccessibilityId(name);
			} catch (Exception e) {
			}
		}
		return element;
	}
	
	public List <WebElement> getElementsByName(String name) {
		List<WebElement> list = null;
		if (isViewWithName(name)) {
			try {
				list = wd.findElementsByAccessibilityId(name);
			} catch (Exception e) {
			}
		}

		return list;
	}

	public WebElement getElementByText(String text) {
		return getElementByName(text);
	}

	public boolean isEnabledByXpath(String xpath) {
		WebElement element = null;
		if (isViewWithXpath(xpath)) {
			element = wd.findElementByXPath(xpath);
			return element.isEnabled();
		} else {
			return false;
		}
	}

	public boolean isSelectedByXpath(String xpath) {
		WebElement element = null;
		if (isViewWithXpath(xpath)) {
			element = wd.findElementByXPath(xpath);
			return element.isSelected();
		} else {
			return false;
		}
	}

	public boolean isEnabledByName(String name) {
		WebElement element = null;
		if (isViewWithName(name)) {
			element = wd.findElementByAccessibilityId(name);
			return element.isEnabled();
		} else {
			return false;
		}
	}

	public boolean isEnabledByText(String text) {
		return isEnabledByName(text);
	}

	public boolean isSelectedByName(String name) {
		WebElement element = null;
		if (isViewWithName(name)) {
			element = wd.findElementByAccessibilityId(name);
			return element.isSelected();
		} else {
			return false;
		}
	}

	public boolean isSelectedByText(String text) {
		return isSelectedByName(text);
	}

	public WebElement scrollToFindText(String text, String direction, int maxScrolls) {
		if (direction.equalsIgnoreCase("Horizontal")) {
			scrollToBegining("Horizontal", 3);

			for (int i = 0; i < maxScrolls; i++) {
				WebElement child = getElementByName(text);
				if (child != null) {
					return child;
				}
				dragDirection("left", 500);
			}
		} else if (direction.equalsIgnoreCase("Vertical")) {
			scrollToBegining("Vertical", 3);

			for (int i = 0; i < maxScrolls; i++) {
				WebElement child = getElementByName(text);
				if (child != null) {
					return child;
				}
				dragDirection("down", 500);
			}

			for (int i = 0; i < maxScrolls; i++) {
				WebElement child = getElementByName(text);
				if (child != null) {
					return child;
				}
				dragDirection("up", 500);
			}
		}

		return (getElementByName(text));
	}

	public void scrollToBegining(String direction, int maxScrolls) {
		if (direction.contains("Vertical")) {
			for (int i = 0; i < maxScrolls; i++) {
				dragDirection("down", 500);
			}
		} else {
			for (int i = 0; i < maxScrolls; i++) {
				dragDirection("right", 500);
			}
		}
	}

	public void dragDirection(String direction, int duration) {
		int startX = toScreenX(0.25f);
		int endX = toScreenX(0.75f);
		int startY = toScreenY(0.25f);
		int endY = toScreenY(0.75f);
		int H = wd.manage().window().getSize().height; // y
		int W = wd.manage().window().getSize().width; // x
		if (direction.equalsIgnoreCase("left")) {
			drag(toScreenX(0.8f), H / 2, toScreenX(0.2f), H / 2, duration);
		} else if (direction.equalsIgnoreCase("right")) {
			drag(toScreenX(0.2f), H / 2, toScreenX(0.8f), H / 2, duration);
		} else if (direction.equalsIgnoreCase("up")) {
			drag(W / 2, endY, W / 2, startY, duration);
		} else if (direction.equalsIgnoreCase("down")) {
			drag(W / 2, startY, W / 2, endY, duration);
		} else {
			drag(endX, H / 2, startX, H / 2, duration);
		}
	}

	public void drag(int startX, int startY, int endX, int endY, int duration) {
		wd.swipe(startX, startY, endX, endY, duration);
	}

	public int toScreenY(float normalizedY) {
		Float f1 = (normalizedY * Integer.valueOf(wd.manage().window().getSize().height));
		return f1.intValue();
	}

	public int toScreenX(float normalizedX) {
		Float f1 = (normalizedX * Integer.valueOf(wd.manage().window().getSize().width));
		return f1.intValue();
	}
	
	public void executeJS(String script) {
		wd.executeScript(script);
	}
	
	public void enterTextByKeyBoard(String text) {
		String script = "var target = UIATarget.localTarget();"
				+ "target.frontMostApp().keyboard().typeString(\"" + text + "\");";
		executeJS(script);
	}
	
	public void enterDeleteByKeyboard(int num) {
		String script = "target.frontMostApp().keyboard().keys()[\"delete\"].tapWithOptions({tapCount:"+ num +"});";
		executeJS(script);
	}
	
	public void deleteGroup(String name) throws MalformedURLException {
		saveLog("====TD-Delete Group====");
		clickXpath(Constant.XPATH_TABBAR_MSG);
		waitForXpathExists(Constant.XPATH_MSG_LIST, 5000);
		
		if (!waitForTextExists(name, 3000)) {
			return;
		}
		
		clickText(name, true);
		waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000);
		
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		dragDirection("up", 500);
		dragDirection("up", 500);
		clickName(Constant.TXT_WORKGROUP_SETTINGS_DELETE);
		sleep(1000);
		clickName(Constant.TXT_YES);
		sleep(1000);
		clickName(Constant.TXT_CONFIRM);
	}

	public void workGroup_CreateGroup_Member(String phoneNum, String password) {
		workeGroup_CreateGroup(phoneNum, password);

		printLog("step4=Select two members");
		// clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));
		// clickXpath(Constant.XPATH_SELECT_LIST_SECOND);
		clickName(Constant.TXT_CONTACT1);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER2, 3000));
		String txt = getTextByXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Confirm icon display not match expect: " + Constant.TXT_SELECT_CONFIRM2,
				txt.startsWith(Constant.TXT_SELECT_CONFIRM2));

		printLog("step5=Click the confirm button");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Click the confirm button failed.", waitForNameExists(Constant.TXT_NEWGROUP, 5000));
	}

	public void groupChat_CreateGroup_Member(String phoneNum, String password) {
		groupChat_CreateGroup(phoneNum, password);

		printLog("step4=Select two members");
		// clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));
		// clickXpath(Constant.XPATH_SELECT_LIST_SECOND);
		clickName(Constant.TXT_CONTACT1);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER2, 3000));
		String txt = getTextByXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Confirm icon display not match expect: " + Constant.TXT_SELECT_CONFIRM2,
				txt.startsWith(Constant.TXT_SELECT_CONFIRM2));

		printLog("step5=Click the confirm button");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Click the confirm button failed.", waitForNameExists(Constant.TXT_CREATE_CHAT_GROUP, 5000));
	}

	public void callMeeting_CreateMeeting_Member(String phoneNum, String password) {
		callMeeting_CreateMeeting(phoneNum, password);

		printLog("step4=Select two members");
		// clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));
		// clickXpath(Constant.XPATH_SELECT_LIST_SECOND);
		clickName(Constant.TXT_CONTACT1);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER2, 3000));
		String txt = getTextByXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Confirm icon display not match expect: " + Constant.TXT_SELECT_CONFIRM2,
				txt.startsWith(Constant.TXT_SELECT_CONFIRM2));

		printLog("step5=Click the confirm button");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Click the confirm button failed.", waitForNameExists(Constant.TXT_NEW_CALLMEETING, 5000));
	}

	public void workGroup_CreateGroup_Branch(String phoneNum, String password) {
		workeGroup_CreateGroup(phoneNum, password);

		printLog("step4=click org icon");
		clickName(Constant.TXT_SELECT_BY_ORG);
		assertTrue("Org has no branch", waitForNameExists(Constant.NAME_CHECKBOX, 5000));

		printLog("step5=select a branch");
		clickName(Constant.NAME_CHECKBOX);
		assertTrue("the select branch not be display", waitForXpathExists(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch not be selected", waitForNameExists(Constant.NAME_CHECKBOX_SELECTED, 3000));

		printLog("step6=Click the confirm button");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Click the confirm button failed.", waitForNameExists(Constant.TXT_NEWGROUP, 5000));
	}

	public void groupChat_CreateGroup_Branch(String phoneNum, String password) {
		groupChat_CreateGroup(phoneNum, password);

		printLog("step4=click org icon");
		clickName(Constant.TXT_SELECT_BY_ORG);
		assertTrue("Org has no branch", waitForNameExists(Constant.NAME_CHECKBOX, 5000));

		printLog("step5=select a branch");
		clickName(Constant.NAME_CHECKBOX);
		WebElement checkBox = wd.findElementByAccessibilityId(Constant.NAME_CHECKBOX);
		assertTrue("the select branch not be display", waitForXpathExists(Constant.XPATH_SELECTED_BRANCH, 3000));
		assertTrue("the branch not be selected.", checkBox.isSelected());

		printLog("step6=Click the confirm button");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Click the confirm button failed.", waitForNameExists(Constant.TXT_CREATE_CHAT_GROUP, 5000));
	}

	public void callMeeting_CreateMeeting_Branch(String phoneNum, String password) {
		callMeeting_CreateMeeting(phoneNum, password);

		printLog("step4=click org icon");
		clickName(Constant.TXT_SELECT_BY_ORG);
		assertTrue("Org has no branch", waitForNameExists(Constant.NAME_CHECKBOX, 5000));

		printLog("step5=select a branch");
		clickName(Constant.NAME_CHECKBOX);
		assertTrue("the select branch not be display", waitForXpathExists(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch not be selected", waitForNameExists(Constant.NAME_CHECKBOX_SELECTED, 3000));

		printLog("step6=Click the confirm button");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Click the confirm button failed.", waitForNameExists(Constant.TXT_NEW_CALLMEETING, 5000));
	}

	public void setHeadImageByPhoto() {
		printLog("step6=Click the head icon");
		clickName(Constant.TXT_EDIT_HEAD_IMAGE);
		assertTrue("Click Head icon to set Head failed.", waitForNameExists(Constant.TXT_DIALOG_SETHEAD_PHOTO, 5000));

		printLog("step7=Select " + Constant.TXT_DIALOG_SETHEAD_PHOTO);
		clickName(Constant.TXT_DIALOG_SETHEAD_PHOTO);
		assertTrue("Select photo to enter camera failed.", waitForNameExists(Constant.NAME_PHOTO_CAPTURE, 5000));

		printLog("step8=Click the shutter button");
		clickName(Constant.NAME_PHOTO_CAPTURE);
		assertTrue("Click the shutter failed.", waitForNameExists(Constant.TXT_USE_PHOTO, 5000));

		printLog("step9=Click " + Constant.TXT_USE_PHOTO);
		clickName(Constant.TXT_USE_PHOTO);
	}

	public void setHeadImageByPicture() {
		printLog("step6=Click the head icon");
		clickName(Constant.TXT_EDIT_HEAD_IMAGE);
		assertTrue("Click Head icon to set Head failed.", waitForNameExists(Constant.TXT_DIALOG_SETHEAD_PICTURE, 5000));

		printLog("step7=Select " + Constant.TXT_DIALOG_SETHEAD_PICTURE);
		clickName(Constant.TXT_DIALOG_SETHEAD_PICTURE);
		assertTrue("Enter Picture failed.", waitForNameExists(Constant.TXT_PICTURE, 5000));

		printLog("step8=Click " + Constant.TXT_CAMERA_FILM);
		clickName(Constant.TXT_CAMERA_FILM);
		assertTrue("Enter album failed.", waitForXpathExists(Constant.XPATH_ALBUM_LIST_FIRST, 3000));

		printLog("step9=Select one picture");
		clickXpath(Constant.XPATH_ALBUM_LIST_FIRST);
		assertTrue("Select one picture failed.", waitForNameExists(Constant.TXT_SELECT, 8000));

		printLog("step10=Click " + Constant.TXT_SELECT);
		WebElement element = getElementByText(Constant.TXT_SELECT);
		Point p = getElementCenterPoint(element);
		wd.tap(1, p.x, p.y, 200);
//		clickName(Constant.TXT_SELECT);
	}
	
	public void setHeadIcon_Photo() {
		clickName(Constant.TXT_DIALOG_SETHEAD_PHOTO);
		assertTrue("Select photo to enter camera failed.", waitForNameExists(Constant.NAME_PHOTO_CAPTURE, 5000));

		printLog("step8=Click the shutter button");
		clickName(Constant.NAME_PHOTO_CAPTURE);
		assertTrue("Click the shutter failed.", waitForNameExists(Constant.TXT_USE_PHOTO, 5000));

		printLog("step9=Click " + Constant.TXT_USE_PHOTO);
		clickName(Constant.TXT_USE_PHOTO);
	}
	
	public void setHeadIcon_Picture() {
		clickName(Constant.TXT_DIALOG_SETHEAD_PICTURE);
		assertTrue("Enter Picture failed.", waitForNameExists(Constant.TXT_PICTURE, 5000));

		printLog("step8=Click " + Constant.TXT_CAMERA_FILM);
		clickName(Constant.TXT_CAMERA_FILM);
		assertTrue("Enter album failed.", waitForXpathExists(Constant.XPATH_ALBUM_LIST_FIRST, 3000));

		printLog("step9=Select one picture");
		clickXpath(Constant.XPATH_ALBUM_LIST_FIRST);
		assertTrue("Select one picture failed.", waitForNameExists(Constant.TXT_SELECT, 8000));

		printLog("step10=Click " + Constant.TXT_SELECT);
		List<WebElement> list = getElementsByName(Constant.TXT_SELECT);
		if (list.size() > 0) {
			clickCenterPoint(list.get(list.size() - 1));
		}
	}

	public void addContacts_Enter(String phoneNum, String password) {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click titlebar_plus");
		if (isViewWithName(Constant.NAME_TITLEBAR_PLUS)) {
			clickName(Constant.NAME_TITLEBAR_PLUS);
		} else {
			clickXpath(Constant.XPATH_TITLEBAR_PLUS);
		}
		assertTrue("Click the titlebar plus failed.", waitForNameExists(Constant.TXT_TITLEBAR_ADD_CONTACTS, 3000));

		printLog("step3=Click " + Constant.TXT_TITLEBAR_ADD_CONTACTS);
		clickName(Constant.TXT_TITLEBAR_ADD_CONTACTS);
		waitForWindowUpdate(3000);
		assertTrue("Ui Element not exist: " + Constant.TXT_FIND_ET, waitForNameExists(Constant.TXT_FIND_ET, 8000));
	}

	public String createPrivateChatBySelectContactInContacts(String phoneNum, String password) {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_ADS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue("Click Address failed.", waitForTextExists(Constant.TXT_ADDRESS_ORG, 3000));

		printLog("step3=Click " + Constant.TXT_ADDRESS_CONTACT);
		clickText(Constant.TXT_ADDRESS_CONTACT, true);
		// assertTrue("Click ORG failed.",
		// waitForNameExists(Constant.NAME_ORG_SWITCH, 3000));
		//
		// printLog("step4=Click " + Constant.NAME_ORG_SWITCH);
		// clickName(Constant.NAME_ORG_SWITCH);
		assertTrue("Click Switch button failed.", waitForNameExists(Constant.TXT_MY_NAME, 3000));

		printLog("step4=Click " + Constant.TXT_MY_NAME);
		clickText(Constant.TXT_MY_NAME, true);
		assertTrue("Enter name card failed.", waitForNameExists(Constant.TXT_NAMECARD_SEND_LANXIN, 3000));

		printLog("step5=Click " + Constant.TXT_NAMECARD_SEND_LANXIN);
		// clickText(Constant.TXT_NAMECARD_SEND_LANXIN, true);
		clickXpath(Constant.XPATH_SEND_LANXIN);
		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		sendMsg_Text();

		printLog("step8=Back to Address");
		clickName(Constant.TXT_NAME_CARD);
		assertTrue("Enter name card failed.", waitForNameExists(Constant.TXT_NAMECARD_SEND_LANXIN, 3000));
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Address failed.", waitForTextExists(Constant.TXT_ADDRESS_ORG, 3000));

		return Constant.TXT_MY_NAME;
	}

	public String sendMsg_Text() {
		printLog("step6=Click input et and input some text");
		Date d = new Date();
		String longtime = String.valueOf(d.getTime());
		if (waitForXpathExists(Constant.XPATH_CHAT_MSG_INPUT_ET, 2000)) {
			clickXpath(Constant.XPATH_CHAT_MSG_INPUT_ET);
		} else {
			clickXpath(Constant.XPATH_CHAT_MSG_INPUT_ET2);
		}
		
		sleep(1000);
		assertTrue("Click input ET failed.", (wd.getKeyboard() != null));

		if (waitForXpathExists(Constant.XPATH_CHAT_MSG_INPUT_ET, 2000)) {
			enterTextByXpath(Constant.XPATH_CHAT_MSG_INPUT_ET, longtime);
		} else {
			enterTextByXpath(Constant.XPATH_CHAT_MSG_INPUT_ET2, longtime);
		}
		assertTrue("Input the msg failed.", waitForTextExists(Constant.TXT_CHAT_TOOL_SEND, 3000));

		printLog("step7=Click the send button");
		clickText(Constant.TXT_CHAT_TOOL_SEND, true);
		sleep(1000);
		return longtime;
	}
	
	public String sendMsg_Text(String txt) {
		printLog("step6=Click input et and input some text");
		if (waitForXpathExists(Constant.XPATH_CHAT_MSG_INPUT_ET, 2000)) {
			clickXpath(Constant.XPATH_CHAT_MSG_INPUT_ET);
		} else {
			clickXpath(Constant.XPATH_CHAT_MSG_INPUT_ET2);
		}
		
		sleep(1000);
		assertTrue("Click input ET failed.", (wd.getKeyboard() != null));

		if (waitForXpathExists(Constant.XPATH_CHAT_MSG_INPUT_ET, 2000)) {
			enterTextByXpath(Constant.XPATH_CHAT_MSG_INPUT_ET, txt);
		} else {
			enterTextByXpath(Constant.XPATH_CHAT_MSG_INPUT_ET2, txt);
		}
		assertTrue("Input the msg failed.", waitForTextExists(Constant.TXT_CHAT_TOOL_SEND, 3000));

		printLog("step7=Click the send button");
		clickText(Constant.TXT_CHAT_TOOL_SEND, true);
		sleep(1000);
		return txt;
	}
	
	public void sendMsg_Voice(int timeout ) {
		printLog("step6=Click voice button");
		clickName(Constant.NAME_MSG_VOICE_BTN);
		assertTrue("Click voice btn failed.", waitForNameExists(Constant.TXT_CHAT_TOOL_VOICE_PRESS, 3000));
		
		clickText(Constant.TXT_CHAT_TOOL_VOICE_PRESS, true);
		if (waitForTextExists(Constant.TXT_YES, 3000)) {
			clickText(Constant.TXT_YES, true);
		}

		printLog("step7=Long click the button");
		longClickElementBySwipe(getElementByName(Constant.TXT_CHAT_TOOL_VOICE_PRESS), 3000);
	}
	
	public void sendMsg_Photo() {
		printLog("step6=Click Menu tool");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("Click the Menu tool failed.", waitForNameExists(Constant.NAME_MSG_PHOTO_BTN, 3000));
		
		printLog("step7=Click photo btn");
		clickName(Constant.NAME_MSG_PHOTO_BTN);
		assertTrue("Select photo to enter camera failed.", waitForNameExists(Constant.NAME_PHOTO_CAPTURE, 5000));

		printLog("step8=Click the shutter button");
		clickName(Constant.NAME_PHOTO_CAPTURE);
		assertTrue("Click the shutter failed.", waitForNameExists(Constant.TXT_USE_PHOTO, 5000));

		printLog("step9=Click " + Constant.TXT_USE_PHOTO);
		clickText(Constant.TXT_USE_PHOTO, true);
		clickName(Constant.TXT_COMPLETE);
		assertTrue("Click the use video btn failed.", waitForNameExists(Constant.TXT_SEND, 3000));
		
		clickName(Constant.TXT_SEND);
		sleep(3000);
	}
	
	public void sendMsg_Photograph(int timeout) {
		printLog("step6=Click Menu tool");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("Click the Menu tool failed.", waitForNameExists(Constant.NAME_MSG_PHOTO_BTN, 3000));
		
		printLog("step7=Click photo btn");
		clickName(Constant.NAME_MSG_PHOTO_BTN);
		assertTrue("Click the photo btn failed.", waitForNameExists(Constant.NAME_CAMERA_MODE, 3000));
		
		printLog("step8=Switch to video");
		String script = "var target = UIATarget.localTarget();"
				+ "target.frontMostApp().mainWindow().elements()[\"CameraMode\"].tapWithOptions({tapOffset:{x:0.35, y:0.45}});";
		executeJS(script);
		sleep(1000);
		
		printLog("step9=Click start btn");
		clickName(Constant.NAME_VIDEO_CAPTURE);
		sleep(timeout);
		
		printLog("step10=Click end btn");
		clickName(Constant.NAME_VIDEO_CAPTURE);
		assertTrue("Click the end btn failed.", waitForNameExists(Constant.TXT_USE_VIDEO, 3000));
		
		clickName(Constant.TXT_USE_VIDEO);
		assertTrue("Click the use video btn failed.", waitForNameExists(Constant.TXT_SEND, 3000));
		
		clickName(Constant.TXT_SEND);
		sleep(3000);
	}
	
	public String sendMsg_Document() {
		printLog("step6=Click Menu tool");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("Click the Menu tool failed.", waitForNameExists(Constant.NAME_MSG_FILE_BTN, 3000));
		
		printLog("step7=Click file btn");
		clickName(Constant.NAME_MSG_FILE_BTN);
		assertTrue("Click the file btn failed.", waitForNameExists(Constant.TXT_CHOICE_FILE, 3000));
		
		printLog("step8=Select one file");
		String fileName = getElementByXpath(Constant.XPATH_SELECT_LIST_FIRST).getText();
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		sleep(1000);
		clickName(Constant.TXT_CONFIRM);
		assertTrue("Click the confirm btn failed.", waitForNameExists(Constant.TXT_SEND, 3000));
		
		clickName(Constant.TXT_SEND);
		sleep(1000);
		return fileName;
	}
	
	public void sendMsg_Location() {
		printLog("step6=Click Menu tool");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("Click the Menu tool failed.", waitForNameExists(Constant.NAME_MSG_LOCATION_BTN, 3000));
		
		printLog("step7=Click location btn");
		clickText(Constant.NAME_MSG_LOCATION_BTN, true);
		clickText(Constant.TXT_LOCATION_SEND, true);
		if (waitForNameExists(Constant.TXT_ALLOW, 3000)) {
			clickName(Constant.TXT_ALLOW);
		}
		assertTrue("Click the location btn failed.", waitForNameExists(Constant.TXT_LOCATION_SELECT, 3000));
	}
	
	public void sendMsg_ShareLocation() {
		printLog("step6=Click Menu tool");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("Click the Menu tool failed.", waitForNameExists(Constant.NAME_MSG_LOCATION_BTN, 3000));
		
		printLog("step7=Click location btn");
		clickText(Constant.NAME_MSG_LOCATION_BTN, true);
		clickText(Constant.TXT_LOCATION_SHARE, true);
		assertTrue("The text not exist: " + Constant.TXT_LOCATION_MAP, waitForTextExists(Constant.TXT_LOCATION_MAP, 5000));
	}
	
	public void sendMsg_ReportLocation(String title) {
		printLog("step6=Click Menu tool");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("Click the Menu tool failed.", waitForNameExists(Constant.NAME_MSG_LOCATION_BTN, 3000));
		
		printLog("step7=Click location btn");
		clickText(Constant.NAME_MSG_LOCATION_BTN, true);
		clickText(Constant.TXT_LOCATION_REPORT, true);
		assertTrue("The text not exist: " + Constant.TXT_LOCATION_REPORT, waitForTextExists(Constant.TXT_LOCATION_REPORT, 5000));
		clickText(Constant.TXT_LOCATION_REPORT_TITLE, true);
		enterTextByKeyBoard(title);
//		enterTextByName(Constant.TXT_LOCATION_REPORT_TITLE, title);
		clickText(Constant.TXT_CHAT_TOOL_SEND, true);
		assertTrue("Back to group chat failed.", waitForTextExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
	}
	
	public void sendMsg_Link(String link) {
		printLog("step6=Click Menu tool");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("Click the Menu tool failed.", waitForNameExists(Constant.NAME_MSG_LINK_BTN, 3000));
		
		printLog("step7=Click link btn tool");
		clickName(Constant.NAME_MSG_LINK_BTN);
		assertTrue("Click the link btn failed.", waitForNameExists(Constant.TXT_LINK_TITLE, 3000));
		
		printLog("step8=Input the link");
		clickXpath(Constant.XPATH_LINK_EV);
		if (waitForTextExists(Constant.TXT_LINK_CLEAR, 3000)) {
			clickText(Constant.TXT_LINK_CLEAR, true);
		}
		sleep(1000);
		enterTextByXpath(Constant.XPATH_LINK_EV, link);
		sleep(1000);
		clickName(Constant.TXT_LINK_REVIEW);
		assertTrue("Click link review failed.", waitForNameExists(Constant.TXT_LINK_LANXIN_DETAIL, 5000));
		
		printLog("step9=Click send btn");
		clickName(Constant.TXT_SEND);
		sleep(1000);
	}
	
	public String sendMsg_GroupCard(String chatName) {
		printLog("step2=Click " + Constant.TXT_TABBAR_ADS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue("Click Address failed.", waitForTextExists(Constant.TXT_ADDRESS_ORG, 3000));

		printLog("step3=Click " + Constant.TXT_ADDRESS_CONTACT);
		clickText(Constant.TXT_ADDRESS_CONTACT, true);
		assertTrue(Constant.TXT_ADDRESS_CONTACT + " not exist.", waitForTextExists(Constant.TXT_INTEREST_CHAT_GROUP, 5000));
		
		printLog("step4=Click " + Constant.TXT_INTEREST_CHAT_GROUP);
		clickText(Constant.TXT_INTEREST_CHAT_GROUP, true);
		assertTrue("Enter chat group failed.", waitForNameExists("搜索", 3000));
		
		printLog("step5=Click a group");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		String groupName = getElementByXpath(Constant.XPATH_GROUP_NAME).getText();
		assertTrue("Ui element not exist: Share icon", waitForNameExists(Constant.NAME_SETTING_SHARE, 2000));

		printLog("step6=Click the share icon");
		clickName(Constant.NAME_SETTING_SHARE);
		assertTrue("Click the share icon failed",
				waitForTextExists(Constant.TXT_RELAY_GROUP_NAMECARD, 3000));

		printLog("step7=Click share by name card");
		clickText(Constant.TXT_RELAY_GROUP_NAMECARD, true);
		assertTrue("Click share by name card failed", waitForTextExists(Constant.TXT_RELAY_TITLE, 3000));

		printLog("step8=Click one current chat");
		clickText(chatName, true);
		assertTrue("Clickt the current chat failed.", waitForTextExists(Constant.TXT_CONFIRM, 3000));

		printLog("step9=Click the confirm");
		clickText(Constant.TXT_CONFIRM, true);
		clickXpath(Constant.XPATH_BACK);
		sleep(1000);
		clickXpath(Constant.XPATH_BACK);
		return groupName;
	}
	
	public String sendMsg_Emotion() {
		printLog("step4=Click the emotion icon");
		clickName(Constant.NAME_MSG_EMOTION_BTN);
		assertTrue("Click the emotion failed", waitForXpathExists(Constant.XPATH_EMOTION_LIST, 3000));

		printLog("step5=Click some emotion");
		for (int i = 1; i < 4; i++) {
			String xpath = "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[3]/UIAButton[" + i +"]";
			clickXpath(xpath);
			sleep(800);
		}
		
		String emotion = "";
		if (waitForXpathExists(Constant.XPATH_CHAT_MSG_INPUT_ET, 2000)) {
			emotion = getElementByXpath(Constant.XPATH_CHAT_MSG_INPUT_ET).getAttribute("value");
		} else {
			emotion = getElementByXpath(Constant.XPATH_CHAT_MSG_INPUT_ET2).getAttribute("value");
		}

		printLog("step6=Click the send button");
		clickText(Constant.TXT_CHAT_TOOL_SEND, true);
		sleep(1000);
		return emotion;
	}
	
	public void sendMsg_Voice_Cancel() {
		printLog("step6=Click voice button");
		clickName(Constant.NAME_MSG_VOICE_BTN);
		assertTrue("Click voice btn failed.", waitForNameExists(Constant.TXT_CHAT_TOOL_VOICE_PRESS, 3000));
		
		clickText(Constant.TXT_CHAT_TOOL_VOICE_PRESS, true);
		if (waitForTextExists(Constant.TXT_YES, 3000)) {
			clickText(Constant.TXT_YES, true);
		}

		printLog("step7=Long click the button");
		Point p = getElementCenterPoint(getElementByName(Constant.TXT_CHAT_TOOL_VOICE_PRESS));
		wd.swipe(p.x, p.y, p.x, 200, 2000);
		sleep(1000);
	}
	
	public void sendMsg_Picture() {
		printLog("step6=Click Menu tool");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("Click the Menu tool failed.", waitForNameExists(Constant.NAME_MSG_IMAGE_BTN, 3000));
		
		printLog("step7=Click image btn");
		clickName(Constant.NAME_MSG_IMAGE_BTN);
		assertTrue("Click the image btn failed.", waitForNameExists(Constant.TXT_CAMERA_FILM, 3000));
		
		printLog("step8=Select one picture");
		clickXpath(Constant.XPATH_FIRST_PICTURE);
		assertTrue("Select one picture failed.", waitForNameExists(Constant.TXT_COMPLETE, 8000));
		
		printLog("step9=Click " + Constant.TXT_COMPLETE);
		clickName(Constant.TXT_COMPLETE);
		assertTrue("Click complete btn failed.", waitForNameExists(Constant.TXT_SEND, 3000));
		
		printLog("step10=Click " + Constant.TXT_SEND);
		clickName(Constant.TXT_SEND);
		sleep(3000);
	}
	
	public void sendMsg_Video() {
		printLog("step6=Click Menu tool");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("Click the Menu tool failed.", waitForNameExists(Constant.NAME_MSG_IMAGE_BTN, 3000));
		
		printLog("step7=Click image btn");
		clickName(Constant.NAME_MSG_IMAGE_BTN);
		assertTrue("Click the image btn failed.", waitForNameExists(Constant.TXT_CAMERA_FILM, 3000));
		
		printLog("step8=Select one video");
		clickXpath(Constant.XPATH_FIRST_VIDEO);
		assertTrue("Select one video failed.", waitForNameExists(Constant.NAME_PLAY_BUTTON, 8000));
		
		printLog("step9=Click " + Constant.TXT_COMPLETE);
		clickName(Constant.TXT_COMPLETE);
		if (waitForTextExists(Constant.TXT_CONFIRM, 5000)) {
			clickName(Constant.TXT_CONFIRM);
		}
		assertTrue("Click complete btn failed.", waitForNameExists(Constant.TXT_SEND, 3000));
		
		printLog("step10=Click " + Constant.TXT_SEND);
		clickName(Constant.TXT_SEND);
		sleep(3000);
	}
	
	public void sendMsg_Notify(String title) {
		printLog("step6=Click Menu tool");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("Click Menu tool failed.",
				waitForTextExists(Constant.NAME_MSG_NOTICE_BTN, 3000));

		printLog("step7=Click " + Constant.TXT_CHAT_TOOL_NOTIFY);
		clickText(Constant.NAME_MSG_NOTICE_BTN, true);
		assertTrue("The text not exist: " + Constant.TXT_NOTIFY_TITLE,
				waitForTextExists(Constant.TXT_NOTIFY_TITLE, 5000) || waitForTextExists(Constant.TXT_NOTIFY_TITLE2, 5000));

		printLog("step7=Send " + Constant.TXT_CHAT_TOOL_NOTIFY);
		if (waitForTextExists(Constant.TXT_NOTIFY_CONTENT, 2000)) {
			clickText(Constant.TXT_NOTIFY_CONTENT, true);
			enterTextByKeyBoard(title);
//			enterTextByName(Constant.TXT_NOTIFY_CONTENT, title);
		} else {
			clickText(Constant.TXT_NOTIFY_CONTENT2, true);
			String script = "target.frontMostApp().keyboard().typeString(\"" + title + "\n\");";
			executeJS(script);
//			enterTextByName(Constant.TXT_NOTIFY_CONTENT2, title);
		}
		clickText(Constant.TXT_CHAT_TOOL_SEND, true);
		waitForWindowUpdate(3000);
	}
	
	public void createWorkGroupByPlusButton(String phoneNum, String password, String groupName) {
		workGroup_CreateGroup_Member(phoneNum, password);

		printLog("step6=Click name editview and enter name");
		clickXpath(Constant.XPATH_EV_INPUT_GROUP_NAME);
		enterTextByXpath(Constant.XPATH_EV_INPUT_GROUP_NAME, groupName);
		sleep(1000);

		printLog("step7=Click Confirm icon");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		if (waitForXpathExists(Constant.XPATH_ALERT_CREATE_GROUP, 4000)) {
			printLog("step8=Click Confirm");
			clickName(Constant.TXT_CONFIRM);
		}
		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 5000));

		printLog("step9=Verify the group name");
		String name = getTextByXpath(Constant.XPATH_GROUP_TITLE_NAME);
		assertTrue("The group name" + name + " not match expect", name.contains(groupName));
	}
	
	public void renameGroupName(String name, String newName) {
		clickText(name, true);
		clickXpath(Constant.XPATH_GROUP_NAME_TV);
		sleep(1000);
		getElementByXpath(Constant.XPATH_GROUP_NAME_TV).clear();
		enterTextByXpath(Constant.XPATH_GROUP_NAME_TV, newName);
		sleep(1000);
		clickName("换行");
		sleep(1000);
		clickName(Constant.TXT_SAVE);
		if (waitForTextExists(Constant.TXT_I_KNOW, 5000)) {
			clickName(Constant.TXT_I_KNOW);
		}
		assertTrue("Reset group name failed.", waitForTextExists(newName, 5000));
	}
	
	public void setGroupSummary(String name, String sum) {
		clickText(name, true);
		clickName(Constant.TXT_EDIT_GROUP_SUM);
		sleep(1000);
		getElementByXpath(Constant.XPATH_GROUP_SUM_TV).clear();
		enterTextByXpath(Constant.XPATH_GROUP_SUM_TV, sum);
		sleep(1000);
		clickName("Return");
		sleep(1000);
		clickName(Constant.TXT_SAVE);
		if (waitForTextExists(Constant.TXT_I_KNOW, 5000)) {
			clickName(Constant.TXT_I_KNOW);
		}
		assertTrue("Reset group sum failed.", waitForTextExists(sum, 5000));
	}
	
	public void verifyManageMemberUiElement() {
		assertTrue("The UI Element not exist: " + Constant.TXT_MANAGE_MEMBER_REMOVE,
				waitForTextExists(Constant.TXT_MANAGE_MEMBER_REMOVE, 2000));
		assertTrue("The UI Element not exist: " + Constant.TXT_MANAGE_MEMBER_ADD,
				waitForTextExists(Constant.TXT_MANAGE_MEMBER_ADD, 2000));
		assertTrue("The UI Element not exist: " + Constant.TXT_MANAGE_MEMBER_COUNT,
				waitForTextExists(Constant.TXT_MANAGE_MEMBER_COUNT, 2000));
		assertTrue("The UI Element not exist: " + Constant.TXT_MANAGE_MEMBER_CREATEGROUP,
				waitForTextExists(Constant.TXT_MANAGE_MEMBER_CREATEGROUP, 2000));
	}
	
	public void verifyManageMemberCountUiElement() {
		assertTrue("The UI Element not exist: " + Constant.TXT_ACCESS_TYPE_COUNT,
				waitForTextExists(Constant.TXT_ACCESS_TYPE_COUNT, 2000));
		assertTrue("The UI Element not exist: " + Constant.TXT_ACCESS_TYPE_TIME,
				waitForTextExists(Constant.TXT_ACCESS_TYPE_TIME, 2000));
	}
	
	
}

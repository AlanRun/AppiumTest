package iOS.report;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.log4testng.Logger;

import iOS.test.helper.BasicTest;
import io.appium.java_client.ios.IOSDriver;

/**
 * Test result Listener.
 */
public class TestResultListener extends TestListenerAdapter {

	private static Logger logger = Logger.getLogger(TestResultListener.class);

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		try {
			saveScreenShot(tr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info(tr.getName() + " Failure");
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		logger.info(tr.getName() + " Skipped");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		logger.info(tr.getName() + " Success");
	}

	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		logger.info(tr.getName() + " Start");
	}

	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);

		// List of test results which we will delete later
		ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
		// collect all id's from passed test
		Set<Integer> passedTestIds = new HashSet<Integer>();
		for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
			logger.info("PassedTests = " + passedTest.getName());
			passedTestIds.add(getId(passedTest));
		}

		// Eliminate the repeat methods
		Set<Integer> skipTestIds = new HashSet<Integer>();
		for (ITestResult skipTest : testContext.getSkippedTests().getAllResults()) {
			logger.info("skipTest = " + skipTest.getName());
			// id = class + method + dataprovider
			int skipTestId = getId(skipTest);

			if (skipTestIds.contains(skipTestId) || passedTestIds.contains(skipTestId)) {
				testsToBeRemoved.add(skipTest);
			} else {
				skipTestIds.add(skipTestId);
			}
		}

		// Eliminate the repeat failed methods
		Set<Integer> failedTestIds = new HashSet<Integer>();
		for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
			logger.info("failedTest = " + failedTest.getName());
			// id = class + method + dataprovider
			int failedTestId = getId(failedTest);

			// if we saw this test as a failed test before we mark as to be
			// deleted
			// or delete this failed test if there is at least one passed
			// version
			if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)
					|| skipTestIds.contains(failedTestId)) {
				testsToBeRemoved.add(failedTest);
			} else {
				failedTestIds.add(failedTestId);
			}
		}

		// finally delete all tests that are marked
		for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator
				.hasNext();) {
			ITestResult testResult = iterator.next();
			if (testsToBeRemoved.contains(testResult)) {
				logger.info("Remove repeat Fail Test: " + testResult.getName());
				iterator.remove();
			}
		}

	}

	private int getId(ITestResult result) {
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;
	}

	private void saveScreenShot(ITestResult tr) throws IOException {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//		String mDateTime = formatter.format(new Date());
//		String fileName = mDateTime + "_" + tr.getName() + ".png";
		String fileName = tr.getName() + ".png";
		String filePath = "";

		IOSDriver<?> driver = BasicTest.getDriver();

		File screenshot = driver.getScreenshotAs(OutputType.FILE);
		String path;
//		String mavenP = System.getProperty("user.dir") + "/target/surefire-reports/";
//		String testngP = System.getProperty("user.dir") + "/test-output/";
//		if ((new File(mavenP)).exists()) {
//			path = mavenP + "screenshot/";
//			(new File(path)).mkdir();
//		} else {
//			path = testngP + "screenshot/";
//			(new File(path)).mkdir();
//		}
		
		path = System.getProperty("user.dir") + "/screenshot/";
		
		filePath = path + fileName;
		File destFile = new File(filePath);
		FileUtils.copyFile(screenshot, destFile);
		Reporter.setCurrentTestResult(tr);
//		Reporter.log("<img src=\"../../../screenshot/" + fileName + "\"/>");
		Reporter.log("<a href=\"../../../screenshot/" + fileName + "\">" + fileName + "</a>");
		Reporter.log("</br>");
//		Reporter.log("<img src=\"screenshot/" + fileName + "\"/>");
	}
}

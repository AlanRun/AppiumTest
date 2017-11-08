package iOS.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import iOS.test.helper.BasicTest;
import iOS.test.helper.Constant;
import io.appium.java_client.ios.IOSDriver;

//@Listeners({ iOS.report.TestResultListener.class })
public class FunctionTest extends BasicTest {
	public static IOSDriver<WebElement> driver;

	private String phoneNum = getValue("phoneNum");
	private String password = getValue("password");

	private String DeleteGroup ;

	@BeforeTest(alwaysRun = true)
	@Override
	public void beforeTest() {
		// set up appium
		super.beforeTest();
	}

	@BeforeMethod(alwaysRun = true)
	@Override
	public void beforeMethod() throws MalformedURLException {
		super.beforeMethod();
		driver = getDriver();
//		driver.launchApp();
		DeleteGroup = null;
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		try {
			if (!result.isSuccess()) {
				catchExceptions(result);
			}
			
			if (DeleteGroup != null) {
				driver.closeApp();
				driver.launchApp();
				deleteGroup(DeleteGroup);
			}
		} catch (Exception e) {
			saveLog(e.toString());
		}
		driver.quit();
	}

	@Test
	public void testFunction_TitleBar_Search_ResultSort() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 3000));

		printLog("step3=Click titlebar_search");
		if (isViewWithName(Constant.NAME_SEARCH)) {
			clickName(Constant.NAME_SEARCH);
		} else {
			clickXpath(Constant.XPATH_TITLEBAR_SEARCH);
		}
		assertTrue("Cick the search icon failed", waitForNameExists(Constant.TXT_SEARCH_INPUT_TITLE, 3000));

		printLog("step4=Input some for search");
		clickName(Constant.TXT_SEARCH_INPUT_TITLE);
		enterTextByName(Constant.TXT_SEARCH_INPUT_TITLE, phoneNum);
		assertTrue("Input number not find the contact", waitForNameExists(Constant.TXT_MY_NAME, 5000));
	}

	@Test
	public void testFunction_TitleBar_Search_Cancel() {
		
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 3000));

		printLog("step3=Click titlebar_search");
		if (isViewWithName(Constant.NAME_SEARCH)) {
			clickName(Constant.NAME_SEARCH);
		} else {
			clickXpath(Constant.XPATH_TITLEBAR_SEARCH);
		}
		assertTrue("Cick the search icon failed", waitForNameExists(Constant.TXT_SEARCH_INPUT_TITLE, 3000));

		printLog("step4=Input some for search");
		clickName(Constant.TXT_SEARCH_INPUT_TITLE);
		enterTextByName(Constant.TXT_SEARCH_INPUT_TITLE, "0000000");
		assertTrue("Input 0000000 find the contact", waitForNameExists(Constant.TXT_SEARCH_NO_RESULT, 5000));

		printLog("step5=Click the cancel");
		clickName(Constant.TXT_CANCEL);
		assertTrue("Click the cancel to back the msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 3000));
	}

	@Test
	public void testFunction_TitleBar_Search_Address_DisplayMore() {
		
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_ADS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue("Click Address failed.", waitForNameExists(Constant.TXT_ADDRESS_ORG, 3000));

		printLog("step3=Click search");
		clickXpath(Constant.XPATH_ADS_SEARCH);
		assertTrue("Cick the search icon failed", waitForNameExists(Constant.TXT_SEARCH_DISPLAY_MORE, 3000));

		printLog("step4=Click " + Constant.TXT_SEARCH_DISPLAY_MORE);
		clickText(Constant.TXT_SEARCH_DISPLAY_MORE, false);
		assertTrue("Click Close display failed.", waitForNameExists(Constant.TXT_SEARCH_DISPLAY_CLOSE, 3000));
	}

	@Test
	public void testFunction_TitleBar_WorkGroup_Create_SelectContacts_SelectMember() {
		workeGroup_CreateGroup(phoneNum, password);

		printLog("step4=Select a member");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));
	}

	@Test
	public void testFunction_TitleBar_WorkGroup_Create_SelectContacts_CancelSelectMember1() {
		workeGroup_CreateGroup(phoneNum, password);

		printLog("step4=Select a member");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));

		printLog("step5=Un-select the member");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		assertTrue("Select a memeber failed.", waitForXpathGone(Constant.XPATH_SELECTED_MEMBER1, 3000));
	}

	@Test
	public void testFunction_TitleBar_WorkGroup_Create_SelectContacts_CancelSelectMember2() {
		workeGroup_CreateGroup(phoneNum, password);

		printLog("step4=Select a member");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));

		printLog("step5=Un-select the member");
		clickXpath(Constant.XPATH_SELECTED_MEMBER1);
		assertTrue("Select a memeber failed.", waitForXpathGone(Constant.XPATH_SELECTED_MEMBER1, 3000));
	}

	@Test
	public void testFunction_TitleBar_WorkGroup_Create_SelectContacts_SelectBranch() {
		workeGroup_CreateGroup(phoneNum, password);

		printLog("step4=click org icon");
		clickName(Constant.TXT_SELECT_BY_ORG);
		assertTrue("Org has no branch", waitForNameExists(Constant.NAME_CHECKBOX, 5000));

		printLog("step5=select a branch");
		clickName(Constant.NAME_CHECKBOX);
		assertTrue("the select branch not be display", waitForXpathExists(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch not be selected", waitForNameExists(Constant.NAME_CHECKBOX_SELECTED, 3000));
	}

	@Test
	public void testFunction_TitleBar_WorkGroup_Create_SelectContacts_CancelSelectBranch1() {
		workeGroup_CreateGroup(phoneNum, password);

		printLog("step4=click org icon");
		clickName(Constant.TXT_SELECT_BY_ORG);
		assertTrue("Org has no branch", waitForNameExists(Constant.NAME_CHECKBOX, 5000));

		printLog("step5=select a branch");
		clickName(Constant.NAME_CHECKBOX);
		assertTrue("the select branch not be display", waitForXpathExists(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch not be selected", waitForNameExists(Constant.NAME_CHECKBOX_SELECTED, 3000));

		printLog("step6=Un-select the branch");
		clickName(Constant.NAME_CHECKBOX);
		assertTrue("the select branch be display", waitForXpathGone(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch be selected", waitForNameGone(Constant.NAME_CHECKBOX_SELECTED, 3000));
	}

	@Test
	public void testFunction_TitleBar_WorkGroup_Create_SelectContacts_CancelSelectBranch2() {
		workeGroup_CreateGroup(phoneNum, password);

		printLog("step4=click org icon");
		clickName(Constant.TXT_SELECT_BY_ORG);
		assertTrue("Org has no branch", waitForNameExists(Constant.NAME_CHECKBOX, 5000));

		printLog("step5=select a branch");
		clickName(Constant.NAME_CHECKBOX);
		assertTrue("the select branch not be display", waitForXpathExists(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch not be selected", waitForNameExists(Constant.NAME_CHECKBOX_SELECTED, 3000));

		printLog("step6=Un-select the branch");
		clickXpath(Constant.XPATH_SELECTED_BRANCH);
		assertTrue("the select branch be display", waitForXpathGone(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch be selected", waitForNameGone(Constant.NAME_CHECKBOX_SELECTED, 3000));
	}

	@Test
	public void testFunction_TitleBar_WorkGroup_Create_SelectContacts_ConfirmIcon1() {
		workeGroup_CreateGroup(phoneNum, password);

		printLog("step4=Verify Confirm icon");
		String txt = getTextByXpath(Constant.XPATH_NAVBAR_CONFIRM);

		assertTrue("Confirm icon can be click.", !isEnabledByXpath(Constant.XPATH_NAVBAR_CONFIRM));
		assertTrue("Confirm icon display not match expect: " + Constant.TXT_SELECT_CONFIRM0,
				txt.startsWith(Constant.TXT_SELECT_CONFIRM0));
	}
	
	@Test
	public void testFunction_TitleBar_WorkGroup_Create_SelectContacts_ConfirmIcon2() {
		workeGroup_CreateGroup(phoneNum, password);

		printLog("step4=Select a member");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		sleep(1000);
		String txt = getTextByXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Confirm icon cann't be click.", isEnabledByXpath(Constant.XPATH_NAVBAR_CONFIRM));
		assertTrue("Confirm icon display not match expect: " + Constant.TXT_SELECT_CONFIRM1,
				txt.startsWith(Constant.TXT_SELECT_CONFIRM1));
	}
	
	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_SetHeadIcon_Photo() {
		workGroup_CreateGroup_Member(phoneNum, password);
		assertTrue("Group chat default not exist", waitForNameExists(Constant.NAME_GROUP_CHAT_HEAD, 5000));

		setHeadImageByPhoto();
		assertTrue("Set group head icon failed.", waitForNameGone(Constant.NAME_GROUP_CHAT_HEAD, 8000));
	}
	
	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_SetHeadIcon_Picture() {
		workGroup_CreateGroup_Member(phoneNum, password);
		assertTrue("Group chat default not exist", waitForNameExists(Constant.NAME_GROUP_CHAT_HEAD, 5000));

		setHeadImageByPicture();
		assertTrue("Set group head icon failed.", waitForNameGone(Constant.NAME_GROUP_CHAT_HEAD, 8000));
	}
	
	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_SetGroupName(){
		workGroup_CreateGroup_Member(phoneNum, password);
		String groupName = getValue("workgroup_name");

		printLog("step6=Click name editview and enter name");
		clickXpath(Constant.XPATH_EV_INPUT_GROUP_NAME);
		enterTextByXpath(Constant.XPATH_EV_INPUT_GROUP_NAME, groupName);
		sleep(1000);

		printLog("step7=Click Confirm icon");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		if (waitForXpathExists(Constant.XPATH_ALERT_CREATE_GROUP, 3000)) {
			printLog("step8=Click Confirm");
			clickName(Constant.TXT_CONFIRM);
		}

		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 5000));
		

		printLog("step9=Verify the group name");
		String name = getTextByXpath(Constant.XPATH_GROUP_TITLE_NAME);
		DeleteGroup = name.split("\\(")[0];
		assertTrue("The group name" + name + " not match expect", name.contains(groupName));
	}
	
	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_DefaultGroupName() {
		workGroup_CreateGroup_Member(phoneNum, password);

		String name1 = Constant.TXT_MY_NAME;
		String name2 = Constant.TXT_CONTACT1;

		printLog("step6=Click Confirm icon");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		if (waitForXpathExists(Constant.XPATH_ALERT_CREATE_GROUP, 3000)) {
			printLog("step7=Click Confirm");
			clickName(Constant.TXT_CONFIRM);
		}

		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 5000));
		String title = getTextByXpath(Constant.XPATH_GROUP_TITLE_NAME);
		DeleteGroup = title.split("\\(")[0];

		printLog("step8=Verify the group name");
		assertTrue("The default group title: " + title + " not contain: " + name1, title.contains(name1));
		assertTrue("The default group title: " + title + " not contain: " + name2, title.contains(name2));
	}
	
	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_AddMember() {
		workGroup_CreateGroup_Member(phoneNum, password);

		printLog("step6=Click Add icon");
		clickName(Constant.NAME_ADD_MEMBER);
		assertTrue("Click add icon failed.", waitForNameExists(Constant.TXT_SELECT_BY_CONTACTS, 3000));

		printLog("step7=Verify Selected member");
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER2, 3000));
	}
	
	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_DeleteMember() {
		workGroup_CreateGroup_Member(phoneNum, password);

		String name1 = getTextByXpath(Constant.XPATH_GROUP_MEMEBER1);
		printLog("step7=Click Delete icon");
		clickXpath(Constant.XPATH_GROUP_MEMEBER_DELETE1);;
		assertTrue("Click remove icon failed.", waitForNameExists(Constant.TXT_REMOVE, 3000));

		printLog("step8=Click " + Constant.TXT_REMOVE);
		clickName(Constant.TXT_REMOVE);
		assertTrue("Remove the member failed.", waitForNameGone(name1, 4000));
	}
	
	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_DeleteBranch() {
		workGroup_CreateGroup_Branch(phoneNum, password);

		String name1 = getTextByXpath(Constant.XPATH_GROUP_MEMEBER1);
		printLog("step7=Click Delete icon");
		clickXpath(Constant.XPATH_GROUP_MEMEBER_DELETE1);;
		assertTrue("Click remove icon failed.", waitForNameExists(Constant.TXT_REMOVE, 3000));

		printLog("step8=Click " + Constant.TXT_REMOVE);
		clickName(Constant.TXT_REMOVE);
		assertTrue("Remove branch " + name1 + " failed.", waitForNameGone(name1, 4000));
	}
	
	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_DoubleConfirm_Confirm() {
		workGroup_CreateGroup_Member(phoneNum, password);

		printLog("step7=Click Confirm icon");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);

		if (waitForXpathExists(Constant.XPATH_ALERT_CREATE_GROUP, 3000)) {
			printLog("step8=Click Confirm");
			clickText(Constant.TXT_CONFIRM, true);
		}
		
		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 5000));
		String name = getTextByXpath(Constant.XPATH_GROUP_TITLE_NAME);
		DeleteGroup = name.split("\\(")[0];
	}
	
	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_DoubleConfirm_Cancel() {
		workGroup_CreateGroup_Member(phoneNum, password);

		printLog("step7=Click Confirm icon");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Click the cofirm icon failed.", waitForXpathExists(Constant.XPATH_ALERT_CREATE_GROUP, 3000));
		
		printLog("step8=Click Cancel");
//		WebElement element = getElementByText(Constant.TXT_CANCEL);
//		Point p = element.getLocation();
//		wd.tap(1, p.x, p.y, 200);
		clickText(Constant.TXT_CANCEL, true);
		assertTrue("Click cancel back to new group failed", waitForTextExists(Constant.TXT_NEWGROUP, 2000));
	}

	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_Back() {
		workGroup_CreateGroup_Member(phoneNum, password);

		printLog("step7=Click Back");
		clickText(Constant.TXT_CANCEL, true);
		assertTrue("Click back icon failed.", waitForTextExists(Constant.TXT_CANCEL_CREATE_GROUP, 3000));
	}

	@Test
	public void testFunction_TitleBar_WorkGroup_NewGroup_SingleMember() {
		workGroup_CreateGroup_Member(phoneNum, password);

		String name1 = getTextByXpath(Constant.XPATH_GROUP_MEMEBER1);
		printLog("step7=Click Delete icon");
		clickXpath(Constant.XPATH_GROUP_MEMEBER_DELETE1);;
		assertTrue("Click remove icon failed.", waitForNameExists(Constant.TXT_REMOVE, 3000));

		printLog("step8=Click " + Constant.TXT_REMOVE);
		clickName(Constant.TXT_REMOVE);
		assertTrue("Remove the member failed.", waitForNameGone(name1, 4000));

		printLog("step9=Click Confirm icon");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		
		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 5000));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_Create_SelectContacts_SelectMember() {
		callMeeting_CreateMeeting(phoneNum, password);

		printLog("step4=Select a member");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_Create_SelectContacts_CancelSelectMember1() {
		callMeeting_CreateMeeting(phoneNum, password);

		printLog("step4=Select a member");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));

		printLog("step5=Un-select the member");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		assertTrue("Select a memeber failed.", waitForXpathGone(Constant.XPATH_SELECTED_MEMBER1, 3000));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_Create_SelectContacts_CancelSelectMember2() {
		callMeeting_CreateMeeting(phoneNum, password);

		printLog("step6=Select a member");
		printLog("step4=Select a member");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));

		printLog("step5=Un-select the member");
		clickXpath(Constant.XPATH_SELECTED_MEMBER1);
		assertTrue("Select a memeber failed.", waitForXpathGone(Constant.XPATH_SELECTED_MEMBER1, 3000));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_Create_SelectContacts_SelectBranch() {
		callMeeting_CreateMeeting(phoneNum, password);

		printLog("step4=click org icon");
		clickName(Constant.TXT_SELECT_BY_ORG);
		assertTrue("Org has no branch", waitForNameExists(Constant.NAME_CHECKBOX, 5000));

		printLog("step5=select a branch");
		clickName(Constant.NAME_CHECKBOX);
		assertTrue("the select branch not be display", waitForXpathExists(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch not be selected", waitForNameExists(Constant.NAME_CHECKBOX_SELECTED, 3000));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_Create_SelectContacts_CancelSelectBranch1() {
		callMeeting_CreateMeeting(phoneNum, password);

		printLog("step4=click org icon");
		clickName(Constant.TXT_SELECT_BY_ORG);
		assertTrue("Org has no branch", waitForNameExists(Constant.NAME_CHECKBOX, 5000));

		printLog("step5=select a branch");
		clickName(Constant.NAME_CHECKBOX);
		assertTrue("the select branch not be display", waitForXpathExists(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch not be selected", waitForNameExists(Constant.NAME_CHECKBOX_SELECTED, 3000));

		printLog("step6=Un-select the branch");
		clickName(Constant.NAME_CHECKBOX);
		assertTrue("the select branch be display", waitForXpathGone(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch be selected", waitForNameGone(Constant.NAME_CHECKBOX_SELECTED, 3000));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_Create_SelectContacts_CancelSelectBranch2() {
		callMeeting_CreateMeeting(phoneNum, password);

		printLog("step4=click org icon");
		clickName(Constant.TXT_SELECT_BY_ORG);
		assertTrue("Org has no branch", waitForNameExists(Constant.NAME_CHECKBOX, 5000));

		printLog("step5=select a branch");
		clickName(Constant.NAME_CHECKBOX);
		assertTrue("the select branch not be display", waitForXpathExists(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch not be selected", waitForNameExists(Constant.NAME_CHECKBOX_SELECTED, 3000));

		printLog("step6=Un-select the branch");
		clickXpath(Constant.XPATH_SELECTED_BRANCH);
		assertTrue("the select branch be display", waitForXpathGone(Constant.XPATH_SELECTED_BRANCH, 3000));
		//assertTrue("the select branch be selected", waitForNameGone(Constant.NAME_CHECKBOX_SELECTED, 3000));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_Create_SelectContacts_ConfirmIcon1() {
		callMeeting_CreateMeeting(phoneNum, password);

		printLog("step4=Verify Confirm icon");
		String txt = getTextByXpath(Constant.XPATH_NAVBAR_CONFIRM);

		assertTrue("Confirm icon can be click.", !isEnabledByXpath(Constant.XPATH_NAVBAR_CONFIRM));
		assertTrue("Confirm icon display not match expect: " + Constant.TXT_SELECT_CONFIRM0,
				txt.startsWith(Constant.TXT_SELECT_CONFIRM0));
	}
	
	@Test
	public void testFunction_TitleBar_CallMeeting_Create_SelectContacts_ConfirmIcon2() {
		callMeeting_CreateMeeting(phoneNum, password);

		printLog("step4=Select a member");
		clickXpath(Constant.XPATH_SELECT_LIST_FIRST);
		sleep(1000);
		String txt = getTextByXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Confirm icon cann't be click.", isEnabledByXpath(Constant.XPATH_NAVBAR_CONFIRM));
		assertTrue("Confirm icon display not match expect: " + Constant.TXT_SELECT_CONFIRM1,
				txt.startsWith(Constant.TXT_SELECT_CONFIRM1));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_NewGroup_SetMeetingTitle() {
		callMeeting_CreateMeeting_Member(phoneNum, password);

//		printLog("step7=Click title editview and enter name");
//		clickResourceId(Constant.ID_MEETING_TITLE_INPUT);
//		enterTextInEditor(title, "android.widget.EditText", 0);
//		assertTrue("Enter name failed.", waitForTextExists(title, 3000));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_NewGroup_AddMember() {
		callMeeting_CreateMeeting_Member(phoneNum, password);

		printLog("step6=Click Add icon");
		clickName(Constant.NAME_ADD_MEMBER);
		assertTrue("Click add icon failed.", waitForNameExists(Constant.TXT_SELECT_BY_CONTACTS, 3000));

		printLog("step7=Verify Selected member");
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER2, 3000));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_NewGroup_DeleteMember() {
		callMeeting_CreateMeeting_Member(phoneNum, password);

		String name1 = getTextByXpath(Constant.XPATH_MEETING_MEMEBER1);
		printLog("step7=Click Delete icon");
		clickXpath(Constant.XPATH_MEETING_MEMEBER_DELETE1);;
		assertTrue("Click remove icon failed.", waitForNameExists(Constant.TXT_REMOVE, 3000));

		printLog("step8=Click " + Constant.TXT_REMOVE);
		clickName(Constant.TXT_REMOVE);
		assertTrue("Remove the member failed.", waitForNameGone(name1, 4000));
	}

	@Test
	public void testFunction_TitleBar_CallMeeting_NewGroup_DeleteBranch() {
		callMeeting_CreateMeeting_Branch(phoneNum, password);

		String name1 = getTextByXpath(Constant.XPATH_MEETING_BRANCH1);
		printLog("step7=Click Delete icon");
		clickXpath(Constant.XPATH_MEETING_BRANCH_DELETE1);;
		assertTrue("Click remove icon failed.", waitForNameExists(Constant.TXT_REMOVE, 3000));

		printLog("step8=Click " + Constant.TXT_REMOVE);
		clickName(Constant.TXT_REMOVE);
		assertTrue("Remove branch " + name1 + " failed.", waitForNameGone(name1, 4000));
	}
	
	@Test
	public void testFunction_TitleBar_CallMeeting_NewGroup_Back() {
		callMeeting_CreateMeeting_Member(phoneNum, password);

		printLog("step7=Click Back icon");
		clickText(Constant.TXT_CANCEL, true);
		assertTrue("Click back icon failed.", waitForTextExists(Constant.TXT_CANCEL_CALL_MEETING, 3000));
	}

	@Test
	public void testFunction_TitleBar_AddContacts_ContactDetails() {
		addContacts_Enter(phoneNum, password);

		printLog("step5=Click one contact");
		String name = "test1";
		clickText(name, true);
		assertTrue("Enter contact detail failed.", waitForTextExists(Constant.TXT_ADD_TO_CONTACTS, 3000));

		printLog("step6=Verify contact UI Element");
		assertTrue("The UI Element not exist: Back icon ", waitForTextExists(Constant.TXT_BACK, 2000));
		assertTrue("The UI Element not exist: Mobile ", waitForTextExists(Constant.TXT_DETAILS, 2000));
		assertTrue("The UI Element not exist: Image ", waitForNameExists(Constant.NAME_CONTACT_HEAD, 2000));
	}

	@Test
	public void testFunction_TitleBar_AddContacts_Search_ContactInAddress() {
		addContacts_Enter(phoneNum, password);

		printLog("step5=Click Search to enter name");
		String contact = "test1";
		clickText(Constant.TXT_FIND_ET, true);
		enterTextByName(Constant.TXT_FIND_ET, contact);
		clickText(Constant.TXT_SEARCH, true);
		clickText(contact, true);
		assertTrue("Enter contact detail failed.", waitForTextExists(Constant.TXT_ADD_TO_CONTACTS, 3000));
	}

	@Test
	public void testFunction_TitleBar_AddContacts_Search_ContactOutAddress() {
		addContacts_Enter(phoneNum, password);

		printLog("step5=Click Search to enter name");
		String number = "13100003333";
		clickText(Constant.TXT_FIND_ET, true);
		enterTextByName(Constant.TXT_FIND_ET, number);
		clickText(Constant.TXT_SEARCH, true);
		clickText(number, true);
		assertTrue("Enter contact detail failed.", waitForTextExists(Constant.TXT_ADD_TO_CONTACTS, 3000));
	}

	@Test
	public void testFunction_Msg_MsgList_DeleteMsg() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Swipe the msg and delete");
		swipeByText(chatName);
		assertTrue("Swipe the msg failed.", waitForTextExists(Constant.TXT_DELETE, 3000));
		clickText(Constant.TXT_DELETE, true);
		assertTrue("Delete the msg failed.", waitForTextGone(chatName, 3000));
	}

	@Test
	public void testFunction_Msg_MsgList_Draft() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		
		printLog("step3=Click chat");
		clickName(chatName);
		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
		
		printLog("step4=Click input et and input some text");
		Date d = new Date();
		String longtime = String.valueOf(d.getTime());
		clickXpath(Constant.XPATH_CHAT_MSG_INPUT_ET);
		sleep(1000);
		assertTrue("Click input ET failed.", (wd.getKeyboard()!=null));
		
		enterTextByXpath(Constant.XPATH_CHAT_MSG_INPUT_ET, longtime);
		assertTrue("Input the msg failed.", waitForTextExists(Constant.TXT_CHAT_TOOL_SEND, 3000));
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		
		String draftMsg = Constant.TXT_MSG_DRAFT+longtime;
		assertTrue("The msg draft failed.", waitForTextExists(draftMsg, 3000));
	}

	@Test
	public void testFunction_Msg_MsgList_MsgTop() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		
		int y0 = getElementByText(chatName).getLocation().y;
		int x0 = getElementByText(chatName).getLocation().x;
		saveLog("Before top: x[" + x0 + "] y[" + y0 + "]");

		printLog("step3=Swipe the msg and top");
		swipeByText(chatName);
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MSG_TOP, 3000));
//		clickText(Constant.TXT_MSG_TOP, true);
		WebElement e = getElementByName(Constant.TXT_MSG_TOP);
		clickElement(e);
		sleep(1000);
		
		int y1 = getElementByText(chatName).getLocation().y;
		int x1 = getElementByText(chatName).getLocation().x;
		saveLog("After top: x[" + x1 + "] y[" + y1 + "]");
		assertTrue("Msg not top", !(y1 > y0));
		
		printLog("step4=Swipe the msg and cancel top");
		swipeByText(chatName);
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_CANCEL_TOP, 3000));
		clickText(Constant.TXT_CANCEL_TOP, true);
	}

	@Test
	public void testFunction_Msg_PrivateChat_Settings_AddMember() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click the chat");
		clickName(chatName);
		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
		
		printLog("step4=Click the chat settings");
		clickName(Constant.NAME_CHAT_SETTINGS);
		assertTrue("Enter chat settings failed.", waitForNameExists(Constant.TXT_SETTINGS, 3000));

		printLog("step5=Click Add member icon");
		clickName(Constant.NAME_CHAT_ADD_MEMBER);
		assertTrue("Click Add icon to enter Select contact UI failed.", waitForTextExists(Constant.TXT_SELECT_BY_CONTACTS, 3000));

		printLog("step6=Select other contacts");
		clickName(Constant.TXT_CONTACT1);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER2, 3000));

		printLog("step7=Click confirm button");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Click confirm failed.", waitForTextExists(Constant.TXT_NEWGROUP, 3000));

		String groupName = getValue("workgroup_name");
		printLog("step8=Click name editview and enter name");
		clickXpath(Constant.XPATH_EV_INPUT_GROUP_NAME);
		enterTextByXpath(Constant.XPATH_EV_INPUT_GROUP_NAME, groupName);
		sleep(1000);

		printLog("step9=Click Confirm icon");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		if (waitForXpathExists(Constant.XPATH_ALERT_CREATE_GROUP, 3000)) {
			printLog("step8=Click Confirm");
			clickName(Constant.TXT_CONFIRM);
		}

		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 5000));

		printLog("step10=Verify the group name");
		String name = getTextByXpath(Constant.XPATH_GROUP_TITLE_NAME);
		DeleteGroup = name.split("\\(")[0];
		assertTrue("The group name" + name + " not match expect", name.contains(groupName));
	}

	@Test
	public void testFunction_Msg_PrivateChat_Settings_MsgTop() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		
		int y0 = getElementByText(chatName).getLocation().y;
		int x0 = getElementByText(chatName).getLocation().x;
		saveLog("Before top: x[" + x0 + "] y[" + y0 + "]");

		printLog("step3=Click the chat");
		clickName(chatName);
		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
		
		printLog("step4=Click the chat settings");
		clickName(Constant.NAME_CHAT_SETTINGS);
		assertTrue("Enter chat settings failed.", waitForNameExists(Constant.TXT_SETTINGS, 3000));
		
		printLog("step5=Click msg top");
		clickXpath(Constant.XPATH_SETTINGS_MSG_TOP);;
		assertTrue("Click msg top failed.", waitForXpathExists(Constant.XPATH_I_KNOW, 3000));
		
		clickXpath(Constant.XPATH_I_KNOW);
		sleep(1000);
		printLog("step6=Back msg list");
		clickXpath(Constant.XPATH_BACK);
		sleep(1000);
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		
		int y1 = getElementByText(chatName).getLocation().y;
		int x1 = getElementByText(chatName).getLocation().x;
		saveLog("After top: x[" + x1 + "] y[" + y1 + "]");
		assertTrue("Msg not top", !(y1 > y0));
		
		printLog("step7=Swipe the msg and cancel top");
		swipeByText(chatName);
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_CANCEL_TOP, 3000));
		clickText(Constant.TXT_CANCEL_TOP, true);
	}
	
	@Test
	public void testFunction_Msg_PrivateChat_MsgRelay_NewChat() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RELAY, 3000));

		printLog("step5=Select relay");
		clickText(Constant.TXT_SELECT_DIALOG_LIST_RELAY, true);
		assertTrue("Click the relay to enter option failed.", waitForTextExists(Constant.TXT_RELAY_TITLE, 3000));
		assertTrue("CLick relay failed.", waitForTextExists(Constant.TXT_RELAY_NEW_CHAT, 3000));

		printLog("step6=Click " + Constant.TXT_RELAY_NEW_CHAT);
		clickText(Constant.TXT_RELAY_NEW_CHAT, true);
		assertTrue("Click the relay new chat failed.", waitForTextExists(Constant.TXT_SELECT_BY_CONTACTS, 3000));

		printLog("step7=Select contacts");
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));

		printLog("step8=Click confirm button");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Click the confirm button failed.", waitForXpathExists(Constant.XPATH_CONFIRM, 3000));
		
		printLog("step8=Click confirm button");
		clickXpath(Constant.XPATH_CONFIRM);
		assertTrue("Back chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_MsgRelay_RecentChat() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RELAY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_RELAY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_RELAY, true);
		assertTrue("Click the relay to enter option failed.", waitForTextExists(Constant.TXT_RELAY_SELECT_CHAT, 3000));

		printLog("step6=Click one current chat");
		clickText(Constant.TXT_MY_NAME, true);
		assertTrue("Clickt the current chat failed.", waitForTextExists(Constant.TXT_CONFIRM, 3000));

		printLog("step7=Click the confirm");
		clickXpath(Constant.XPATH_CONFIRM);
		assertTrue("Back chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_MsgReply() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_REPLY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_REPLY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_REPLY, true);
		String value = getElementByXpath(Constant.XPATH_CHAT_MSG_INPUT_ET).getAttribute("value");
		saveLog("value=" + value);
		assertTrue("The text not has " + Constant.TXT_MY_NAME, value.contains(Constant.TXT_MY_NAME));
		assertTrue("The text not has " + Constant.TXT_REPLY, value.contains(Constant.TXT_REPLY));
	}

	@Test
	public void testFunction_Msg_PrivateChat_MsgFavorite() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_FAVORITE, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_FAVORITE);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_FAVORITE, true);

		printLog("step6=Click back");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back to Msg failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step7=Click " + Constant.TXT_TOOLBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click the More failed.", waitForTextExists(Constant.TXT_MORE_MY_FAVORITE, 3000));

		printLog("step8=Click " + Constant.TXT_MORE_MY_FAVORITE);
		clickText(Constant.TXT_MORE_MY_FAVORITE, true);
		assertTrue("Msg favorite failed.", waitForTextExists(Constant.TXT_PRIVATE_MSG, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_MsgRecall_RightNow() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		String msg = sendMsg_Text();
		//driver.hideKeyboard();
		
		printLog("step4=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
		
		clickName(Constant.TXT_MORE_OPTION);
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RECALL, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_RECALL);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_RECALL, true);
		String note = getLastMsgInChat().getAttribute("name");
		assertTrue("Recall notify not exist: " + Constant.TXT_RECALL_NOTIFY, note.contains(Constant.TXT_RECALL_NOTIFY));
		
		printLog("step6=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Recall msg failed.", waitForTextGone(msg, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_MsgRecall_Later() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		sendMsg_Text();
		//driver.hideKeyboard();
		sleep(120000);

		printLog("step4=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
		
		clickName(Constant.TXT_MORE_OPTION);
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RECALL, 3000));

		printLog("step7=Select " + Constant.TXT_SELECT_DIALOG_LIST_RECALL);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_RECALL, true);
		assertTrue("The msg be recall.", waitForTextExists(Constant.TXT_RECALL_FAILED_NOTE, 3000));
	}

//	@Test
//	public void testFunction_Msg_PrivateChat_MsgDelete() {
//		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
//		
//		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
//		clickXpath(Constant.XPATH_TABBAR_MSG);
//		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
//
//		printLog("step3=Click The private chat");
//		clickText(chatName, true);
//		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
//		
//		String msg = sendMsg_Text();
//
//		printLog("step4=Long click the msg");
//		longClickElementBySwipe(getLastMsgInChat());
//		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
//		
//		clickName(Constant.TXT_MORE_OPTION);
//		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_DELETE, 3000));
//
//		printLog("step7=Select " + Constant.TXT_SELECT_DIALOG_LIST_DELETE);
//		clickText(Constant.TXT_SELECT_DIALOG_LIST_DELETE, true);
//		clickText(Constant.TXT_CONFIRM, true);
//		
//		printLog("step8=Back to msg list and check delete result");
//		clickXpath(Constant.XPATH_BACK);
//		assertTrue("Delete the msg failed.", waitForTextGone(msg, 2000));
//	}
	
	@Test
	public void testFunction_Msg_PrivateChat_MsgType_Voice() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send voice");
		sendMsg_Voice(3000);
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send voice failed.", waitForNameExists(Constant.TXT_VOICE, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_MsgType_Picture() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send picture");
		sendMsg_Picture();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send picture failed.", waitForNameExists(Constant.TXT_IMAGE, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_MsgType_Video() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send video");
		sendMsg_Photograph(3000);
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_DOCUMENT, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_MsgType_Document() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send one document");
		sendMsg_Document();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_DOCUMENT, 3000));
	}
	
	@Test
	public void testFunction_Msg_PrivateChat_MsgType_Location() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send location");
		sendMsg_Location();
		
//		printLog("step5=click the location");
//		clickTextContains(Constant.TXT_LOCATION_INFO, true);
//		assertTrue("The UI Element not exist: " + Constant.TXT_LOCATION_DETAIL,
//				waitForTextExists(Constant.TXT_LOCATION_DETAIL, 3000));
//		assertTrue("Enter location detail failed.", waitForResourceId(Constant.ID_CHAT_MSG_LOCATION_MAP, 3000));
//
//		printLog("step6=click settings icon");
//		clickResourceId(Constant.ID_CHAT_SETTINGS);
//		assertTrue("The UI element not exist: " + Constant.TXT_SELECT_DIALOG_LIST_FAVORITE,
//				waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_FAVORITE, 3000));
//		assertTrue("The UI element not exist: " + Constant.TXT_SELECT_DIALOG_LIST_RELAY,
//				waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RELAY, 3000));
	}
	
	@Test
	public void testFunction_Msg_PrivateChat_MsgType_Link() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send link");
		String link = "http://www.lanxin.cn";
		sendMsg_Link(link);

		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_LINK, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_MsgType_GroupCard_NoJoin() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);

		printLog("step2=Send group name card");
		String name = sendMsg_GroupCard(Constant.TXT_MY_NAME);
		printLog("groupname=" + name);

		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Share groupcard failed.", waitForNameExists(Constant.TXT_GROUP_CARD, 2000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
		
		printLog("step5=click the name card");
		int count = getElementsByUiAutomation(".tableViews()[1].cells()").size();
		getElementByUiAutomation(".tableViews()[1].cells()[" + String.valueOf(count - 1) + "].buttons()[1]").click();
		assertTrue("Click the group name card failed.", waitForTextExists(Constant.TXT_GROUP_JOIN, 3000)
				|| waitForTextExists(Constant.TXT_GROUP_APPLYED, 3000));
		String groupName = getElementByXpath(Constant.XPATH_GROUP_NAME).getText();
		assertTrue("The group name not match", groupName.equals(name));
	}

	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendText() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		String msg = sendMsg_Text();
		//driver.hideKeyboard();
		
		printLog("step6=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send text failed.", waitForTextExists(msg, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendEmotion() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		String emotion = sendMsg_Emotion();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(emotion, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendVoice() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send voice");
		sendMsg_Voice(3000);
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send voice failed.", waitForNameExists(Constant.TXT_VOICE, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendVoice_Cancel() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send voice");
		sendMsg_Voice_Cancel();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Cancel voice failed.", waitForNameGone(Constant.TXT_VOICE, 2000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendPicture() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send picture");
		sendMsg_Picture();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send picture failed.", waitForNameExists(Constant.TXT_IMAGE, 3000));
	}
	
	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendPhoto() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send picture");
		sendMsg_Photo();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send picture failed.", waitForNameExists(Constant.TXT_IMAGE, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendVideo() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send video");
		sendMsg_Video();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_DOCUMENT, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendPhotograph() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send video");
		sendMsg_Photograph(3000);
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_DOCUMENT, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendDocument() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send one document");
		sendMsg_Document();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_DOCUMENT, 3000));
	}

	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendLocation() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send location");
		sendMsg_Location();
	}

	@Test
	public void testFunction_Msg_PrivateChat_SendBar_SendLink() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send link");
		String link = "http://www.lanxin.cn";
		sendMsg_Link(link);

		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_LINK, 3000));
	}
	
	@Test
	public void testFunction_Msg_PrivateChat_SendBar_Emotion_Enter() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Click emotion favorite icon");
		clickText(Constant.NAME_MSG_EMOTION_BTN, true);
		clickText(Constant.NAME_EMOTION_COLLECTION, true);
		assertTrue("Click the emotion favrite icon failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS2, 3000));
	}
	
	@Test
	public void testFunction_Msg_PrivateChat_SendBar_Emotion_Favorite_Enter() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
		
		printLog("step4=Click the emotion icon");
		clickName(Constant.NAME_MSG_EMOTION_BTN);
		assertTrue("Click the emotion failed", waitForXpathExists(Constant.XPATH_EMOTION_LIST, 3000));
		
		printLog("step5=Click emotion favorite icon");
		clickName(Constant.NAME_EMOTION_COLLECTION);
		assertTrue("Click the emotion favrite icon failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS2, 3000));
		
		printLog("step6=Click Add emotion icon");
		clickName(Constant.NAME_EMOTION_PLUS2);
		assertTrue("Click emtion plus failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS, 3000));
		assertTrue("Click emtion plus failed.", waitForTextExists(Constant.TXT_MY_FAVORITE_EMOTION, 3000));
	}
	
	@Test
	public void testFunction_Msg_PrivateChat_SendBar_Emotion_Favorite_Add() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
		
		printLog("step4=Click the emotion icon");
		clickName(Constant.NAME_MSG_EMOTION_BTN);
		assertTrue("Click the emotion failed", waitForXpathExists(Constant.XPATH_EMOTION_LIST, 3000));
		
		printLog("step5=Click emotion favorite icon");
		clickName(Constant.NAME_EMOTION_COLLECTION);
		assertTrue("Click the emotion favrite icon failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS2, 3000));
		
		printLog("step6=Click Add emotion icon");
		clickName(Constant.NAME_EMOTION_PLUS2);
		assertTrue("Click emtion plus failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS, 3000));
		assertTrue("Click emtion plus failed.", waitForTextExists(Constant.TXT_MY_FAVORITE_EMOTION, 3000));
		
		printLog("step7=Click Add icon");
		clickXpath(Constant.XPATH_ADD_EMOTION_FIRST);
		assertTrue("Click Add icon", waitForTextExists(Constant.TXT_CAMERA_FILM, 3000));
		
		printLog("step8=Select one picture");
		clickXpath(Constant.XPATH_FIRST_PICTURE);
		assertTrue("The Ui Element not exist: " + Constant.TXT_COMPLETE, waitForTextExists(Constant.TXT_COMPLETE, 3000));
		clickText(Constant.TXT_COMPLETE, true);
		assertTrue("Back emtion plus failed.", waitForTextExists(Constant.TXT_MY_FAVORITE_EMOTION, 5000));
		String name = getElementByXpath(Constant.XPATH_ADD_EMOTION_FIRST).getText();
		assertTrue("Click emtion plus failed.", !name.equals(Constant.NAME_EMOTION_PLUS));
	}
	
	@Test
	public void testFunction_Msg_PrivateChat_SendBar_Emotion_Favorite_Delete() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
		
		printLog("step4=Click the emotion icon");
		clickName(Constant.NAME_MSG_EMOTION_BTN);
		assertTrue("Click the emotion failed", waitForXpathExists(Constant.XPATH_EMOTION_LIST, 3000));
		
		printLog("step5=Click emotion favorite icon");
		clickName(Constant.NAME_EMOTION_COLLECTION);
		assertTrue("Click the emotion favrite icon failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS2, 3000));
		
		printLog("step6=Click Add emotion icon");
		clickName(Constant.NAME_EMOTION_PLUS2);
		assertTrue("Click emtion plus failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS, 3000));
		assertTrue("Click emtion plus failed.", waitForTextExists(Constant.TXT_MY_FAVORITE_EMOTION, 3000));
		
		String name = getElementByXpath(Constant.XPATH_ADD_EMOTION_FIRST).getText();
		if (name.equals(Constant.NAME_EMOTION_PLUS)) {
			printLog("step7=Click Add icon");
			clickXpath(Constant.XPATH_ADD_EMOTION_FIRST);
			assertTrue("Click Add icon", waitForTextExists(Constant.TXT_CAMERA_FILM, 3000));
			
			printLog("step8=Select one picture");
			clickXpath(Constant.XPATH_FIRST_PICTURE);
			assertTrue("The Ui Element not exist: " + Constant.TXT_COMPLETE, waitForTextExists(Constant.TXT_COMPLETE, 3000));
			clickText(Constant.TXT_COMPLETE, true);
			assertTrue("Back emtion plus failed.", waitForTextExists(Constant.TXT_MY_FAVORITE_EMOTION, 5000));
			name = getElementByXpath(Constant.XPATH_ADD_EMOTION_FIRST).getText();
			assertTrue("Click emtion plus failed.", !name.equals(Constant.NAME_EMOTION_PLUS));
		}
		
		printLog("step9=Delete the emotion");
		clickText(Constant.TXT_EDIT, true);
		clickXpath(Constant.XPATH_ADD_EMOTION_FIRST);
		sleep(1000);
		clickText(Constant.TXT_DELETE_ONE, true);
		clickText(Constant.TXT_CONFIRM, true);
		String name2 = getElementByXpath(Constant.XPATH_ADD_EMOTION_FIRST).getText();
		assertTrue("Delete emtion failed.", name2.equals(Constant.NAME_EMOTION_PLUS));
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_Settings_SetHeadIcon_ByPhoto() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		setHeadImageByPhoto();
		assertTrue("Set group head icon failed.", waitForNameGone(Constant.NAME_GROUP_CHAT_HEAD, 8000));
		clickXpath(Constant.XPATH_BACK);
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_SetHeadIcon_ByPicture() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		setHeadImageByPicture();
		assertTrue("Set group head icon failed.", waitForNameGone(Constant.NAME_GROUP_CHAT_HEAD, 8000));
		clickXpath(Constant.XPATH_BACK);
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_SetGruopName() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=Rename group name");
		String newName = "renamegroup";
		renameGroupName(groupName, newName);
		DeleteGroup = newName;
		clickXpath(Constant.XPATH_BACK);
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_Settings_SetGroupSummary() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=set group summary");
		String sum = "autotestgroup";
		setGroupSummary(groupName, sum);
		clickXpath(Constant.XPATH_BACK);
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_ManageMember_Element() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_WORKGROUP_SETTINGS_MANAGE_MEMBER);
		clickText(Constant.TXT_WORKGROUP_SETTINGS_MANAGE_MEMBER, true);
		assertTrue("Enter manage member failed.", waitForTextExists(Constant.TXT_MANAGE_MEMBER_TITLE, 3000));

		printLog("step4=Verify the UI Element");
		verifyManageMemberUiElement();
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_ManageMember_AddMember() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_WORKGROUP_SETTINGS_MANAGE_MEMBER);
		clickText(Constant.TXT_WORKGROUP_SETTINGS_MANAGE_MEMBER, true);
		assertTrue("Enter manage member failed.", waitForTextExists(Constant.TXT_MANAGE_MEMBER_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MANAGE_MEMBER_ADD);
		clickText(Constant.TXT_MANAGE_MEMBER_ADD, true);
		assertTrue("Click add to enter Select UI failed.", waitForTextExists(Constant.TXT_SELECT_BY_CONTACTS, 3000));

		printLog("step5=Select one contact");
		clickName(Constant.TXT_CONTACT2);
		assertTrue("Select the contact failed.", getElementByXpath(Constant.XPATH_NAVBAR_CONFIRM).getText().contains(Constant.TXT_SELECT_CONFIRM3));

		printLog("step6=Click the confirm button");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		sleep(1000);
		clickText(Constant.TXT_CONFIRM, true);
		assertTrue("Add the contact failed.", waitForTextExists(Constant.TXT_CONTACT2, 5000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_ManageMember_RemoveMember() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_WORKGROUP_SETTINGS_MANAGE_MEMBER);
		clickText(Constant.TXT_WORKGROUP_SETTINGS_MANAGE_MEMBER, true);
		assertTrue("Enter manage member failed.", waitForTextExists(Constant.TXT_MANAGE_MEMBER_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MANAGE_MEMBER_REMOVE);
		clickText(Constant.TXT_MANAGE_MEMBER_REMOVE, true);
		assertTrue("Click the remove failed.", waitForXpathExists(Constant.XPATH_GROUP_MEMEBER_DELETE2, 3000));

		printLog("step5=Click the remove button");
		String name = getElementByXpath(Constant.XPATH_GROUP_MEMEBER_DELETE2).getText();
		clickXpath(Constant.XPATH_GROUP_MEMEBER_DELETE2);
		assertTrue("Remove the member failed.", waitForNameGone(name, 3000));
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_Settings_ManageMember_AccessCount() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_WORKGROUP_SETTINGS_MANAGE_MEMBER);
		clickText(Constant.TXT_WORKGROUP_SETTINGS_MANAGE_MEMBER, true);
		assertTrue("Enter manage member failed.", waitForTextExists(Constant.TXT_MANAGE_MEMBER_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MANAGE_MEMBER_COUNT);
		clickText(Constant.TXT_MANAGE_MEMBER_COUNT, true);
		assertTrue("Click the Count failed.", waitForTextExists(Constant.TXT_MANAGE_MEMBER_COUNT, 3000));

		printLog("step5=Verify the UI Element");
		verifyManageMemberCountUiElement();
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_ManageMember_CreateGroup() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_WORKGROUP_SETTINGS_MANAGE_MEMBER);
		clickText(Constant.TXT_WORKGROUP_SETTINGS_MANAGE_MEMBER, true);
		assertTrue("Enter manage member failed.", waitForTextExists(Constant.TXT_MANAGE_MEMBER_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MANAGE_MEMBER_CREATEGROUP);
		clickText(Constant.TXT_MANAGE_MEMBER_CREATEGROUP, true);
		assertTrue("Click create group failed.", waitForNameExists(Constant.NAME_CHECKBOX, 3000));

		printLog("step5=Select members");
		List<WebElement> list = getElementsByName(Constant.NAME_CHECKBOX);
		WebElement element1 = list.get(0);
		WebElement element2 = list.get(1);
		element1.click();
		element2.click();

		printLog("step6=Click " + Constant.TXT_CONFIRM);
		clickText(Constant.TXT_CONFIRM, true);
		assertTrue("Click " + Constant.TXT_CONFIRM + " failed.", waitForTextExists(Constant.TXT_NEWGROUP, 3000));
		clickXpath(Constant.XPATH_BACK);
		sleep(1000);
		clickText(Constant.TXT_YES, true);
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_MsgTop() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;
		
		clickXpath(Constant.XPATH_BACK);
		sleep(1000);

		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		
		int y0 = getElementByText(groupName).getLocation().y;
		int x0 = getElementByText(groupName).getLocation().x;
		saveLog("Before top: x[" + x0 + "] y[" + y0 + "]");

		printLog("step3=Click the chat");
		clickName(groupName);
		assertTrue("Enter chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
		
		printLog("step4=Click the chat settings");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Enter chat settings failed.", waitForNameExists(Constant.TXT_SETTINGS, 3000));
		
		printLog("step5=Click msg top");
		clickXpath(Constant.XPATH_GROUP_SETTINGS_MSG_TOP);
		assertTrue("Click msg top failed.", waitForXpathExists(Constant.XPATH_I_KNOW3, 3000));
		
		clickXpath(Constant.XPATH_I_KNOW3);
		sleep(1000);
		printLog("step6=Back msg list");
		clickXpath(Constant.XPATH_BACK);
		sleep(1000);
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		
		int y1 = getElementByText(groupName).getLocation().y;
		int x1 = getElementByText(groupName).getLocation().x;
		saveLog("After top: x[" + x1 + "] y[" + y1 + "]");
		assertTrue("Msg not top", !(y1 > y0));
		
		printLog("step7=Swipe the msg and cancel top");
		swipeByText(groupName);
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_CANCEL_TOP, 3000));
		clickText(Constant.TXT_CANCEL_TOP, true);
	}
	@Test
	public void testFunction_Msg_WorkGroup_Settings_MsgMute() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=Click " + Constant.TXT_WORKGROUP_SETTINGS_MSG_MUTE);
		clickXpath(Constant.XPATH_GROUP_MSG_MUTE);
		sleep(1000);
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_Settings_AllowBeSearch() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=Click " + Constant.TXT_WORKGROUP_SETTINGS_BE_SEARCH);
		clickXpath(Constant.XPATH_GROUP_BE_SEATCH);
		assertTrue("Set group be search failed.", waitForTextExists(Constant.TXT_WORKGROUP_SETTINGS_GROUP_QRCODE, 5000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_ShareGroup() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=Click " + Constant.TXT_WORKGROUP_SETTINGS_BE_SEARCH);
		clickXpath(Constant.XPATH_GROUP_BE_SEATCH);
		assertTrue("Set group be search failed.", waitForTextExists(Constant.TXT_WORKGROUP_SETTINGS_GROUP_QRCODE, 5000));

		printLog("step4=Click Share icon");
		clickName(Constant.NAME_SETTING_SHARE);
		assertTrue("Click the share icon failed.", waitForTextExists(Constant.TXT_RELAY_GROUP_NAMECARD, 3000));

		printLog("step5=Click " + Constant.TXT_RELAY_GROUP_NAMECARD);
		clickText(Constant.TXT_RELAY_GROUP_NAMECARD, true);
		assertTrue("Enter share option failed.", waitForTextExists(groupName, 3000));

		clickText(groupName, true);
		clickText(Constant.TXT_CONFIRM, true);
		sleep(2000);
		clickXpath(Constant.XPATH_BACK);
		sleep(1000);
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Share group name card failed", waitForTextExists(Constant.TXT_GROUP_CARD, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_GroupMute() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=Click " + Constant.TXT_WORKGROUP_SETTINGS_GROUP_MUTE);
		clickText(Constant.TXT_WORKGROUP_SETTINGS_GROUP_MUTE, true);
		assertTrue("Enter Group mute failed.", waitForTextExists(Constant.TXT_COMPLETE, 5000));

		printLog("step4=Select one member");
		clickText(Constant.TXT_MY_NAME, true);

		printLog("step5=Click " + Constant.TXT_COMPLETE);
		clickText(Constant.TXT_COMPLETE, true);
		assertTrue("Back settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step6=Back to group chat and send some text");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Muse faield.", waitForTextExists(Constant.TXT_MUTING, 5000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_ManagePermission() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		dragDirection("up", 500);

		printLog("step3=Click " + Constant.TXT_WORKGROUP_SETTINGS_MANAGE_PERMISSION);
		clickXpath(Constant.XPATH_GROUP_MANAGE_PERMISSION);
		sleep(2000);
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_ShareLocation() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		dragDirection("up", 500);

		printLog("step3=Click " + Constant.TXT_WORKGROUP_SETTINGS_SHARE_LOCATION);
		clickXpath(Constant.XPATH_GROUP_SHARE_LOCATION);
		sleep(2000);
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_HelpAdmin() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		dragDirection("up", 500);

		printLog("step3=Click " + Constant.TXT_WORKGROUP_SETTINGS_HELP_ADMIN);
		clickText(Constant.TXT_WORKGROUP_SETTINGS_HELP_ADMIN, true);
		assertTrue("Click the button failed.", waitForTextExists(Constant.TXT_CONFIRM, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_HideNum() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		dragDirection("up", 500);

		printLog("step3=Click " + Constant.TXT_WORKGROUP_SETTINGS_HIDE_NUM);
		clickXpath(Constant.XPATH_GROUP_NUM_HIDE);
		sleep(2000);
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_MsgCount() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		dragDirection("up", 500);

		printLog("step3=Get current Msg count");
		WebElement ele = getElementByXpath(Constant.XPATH_GROUP_MSG_COUNT);
		int count1 = Integer.parseInt(ele.getText());
		printLog("count1=" + count1);

		printLog("step4=Back group chat to send text");
		clickXpath(Constant.XPATH_BACK);
		
		printLog("step5=Click input et and input some text");
		sendMsg_Text();

		printLog("step6=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		dragDirection("up", 500);

		printLog("step7=Get current Msg count");
		ele = getElementByXpath(Constant.XPATH_GROUP_MSG_COUNT);
		int count2 = Integer.parseInt(ele.getText());
		printLog("count2=" + count2);
		assertTrue("The msg count not expect infact", count2 == count1);
	}

	@Test
	public void testFunction_Msg_WorkGroup_Settings_DeleteGroup() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		dragDirection("up", 500);
		
		printLog("step3=click " + Constant.TXT_GROUPCHAT_SETTINGS_DELETE);
		clickName(Constant.TXT_WORKGROUP_SETTINGS_DELETE);
		sleep(1000);
		clickName(Constant.TXT_YES);
		sleep(1000);
		clickName(Constant.TXT_CONFIRM);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 3000));
		DeleteGroup = "";
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_MsgRelay_NewChat() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Send some msg");
		sendMsg_Text();

		printLog("step3=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RELAY, 3000));

		printLog("step4=Select relay");
		clickText(Constant.TXT_SELECT_DIALOG_LIST_RELAY, true);
		assertTrue("Click the relay to enter option failed.", waitForTextExists(Constant.TXT_RELAY_TITLE, 3000));
		assertTrue("CLick relay failed.", waitForTextExists(Constant.TXT_RELAY_NEW_CHAT, 3000));

		printLog("step5=Click " + Constant.TXT_RELAY_NEW_CHAT);
		clickText(Constant.TXT_RELAY_NEW_CHAT, true);
		assertTrue("Click the relay new chat failed.", waitForTextExists(Constant.TXT_SELECT_BY_CONTACTS, 3000));

		printLog("step6=Select contacts");
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Select a memeber failed.", waitForXpathExists(Constant.XPATH_SELECTED_MEMBER1, 3000));

		printLog("step7=Click confirm button");
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Click the confirm button failed.", waitForXpathExists(Constant.XPATH_CONFIRM, 3000));
		
		printLog("step8=Click confirm button");
		clickXpath(Constant.XPATH_CONFIRM);
		assertTrue("Back chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgRelay_RecentChat() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Send some msg");
		sendMsg_Text();

		printLog("step3=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RELAY, 3000));

		printLog("step4=Select " + Constant.TXT_SELECT_DIALOG_LIST_RELAY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_RELAY, true);
		assertTrue("Click the relay to enter option failed.", waitForTextExists(Constant.TXT_RELAY_SELECT_CHAT, 3000));

		printLog("step5=Click one current chat");
		clickText(Constant.TXT_MY_NAME, true);
		assertTrue("Clickt the current chat failed.", waitForTextExists(Constant.TXT_CONFIRM, 3000));

		printLog("step6=Click the confirm");
		clickXpath(Constant.XPATH_CONFIRM);
		assertTrue("Back chat page failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgReply() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Send some msg");
		sendMsg_Text();

		printLog("step3=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_REPLY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_REPLY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_REPLY, true);
		String value = getElementByXpath(Constant.XPATH_CHAT_MSG_INPUT_ET2).getAttribute("value");
		saveLog("value=" + value);
		assertTrue("The text not has " + Constant.TXT_MY_NAME, value.contains(Constant.TXT_MY_NAME));
		assertTrue("The text not has " + Constant.TXT_REPLY, value.contains(Constant.TXT_REPLY));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgFavorite() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Send some msg");
		sendMsg_Text();

		printLog("step3=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_FAVORITE, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_FAVORITE);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_FAVORITE, true);

		printLog("step6=Click back");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back to Msg failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step7=Click " + Constant.TXT_TOOLBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click the More failed.", waitForTextExists(Constant.TXT_MORE_MY_FAVORITE, 3000));

		printLog("step8=Click " + Constant.TXT_MORE_MY_FAVORITE);
		clickText(Constant.TXT_MORE_MY_FAVORITE, true);
		assertTrue("Msg favorite failed.", waitForTextExists(Constant.TXT_PRIVATE_MSG, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgRecall_RightNow() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Send some msg");
		String longtime = sendMsg_Text();

		printLog("step3=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
		
		clickName(Constant.TXT_MORE_OPTION);
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RECALL, 3000));

		printLog("step4=Select " + Constant.TXT_SELECT_DIALOG_LIST_RECALL);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_RECALL, true);
		sleep(1000);
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Recall notify not exist: " + Constant.TXT_RECALL_NOTIFY, waitForTextExists(Constant.TXT_RECALL_NOTIFY2, 3000));
		assertTrue("Recall msg failed.", waitForTextGone(longtime, 2000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgRecall_Later() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Send some text");
		sendMsg_Text();
		//driver.hideKeyboard();
		sleep(120000);

		printLog("step4=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
		
		clickName(Constant.TXT_MORE_OPTION);
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RECALL, 3000));

		printLog("step7=Select " + Constant.TXT_SELECT_DIALOG_LIST_RECALL);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_RECALL, true);
		assertTrue("The msg be recall.", waitForTextExists(Constant.TXT_RECALL_FAILED_NOTE, 3000));
	}

//	@Test
//	public void testFunction_Msg_WorkGroup_MsgDelete() {
//		String groupName = getValue("workgroup_name");
//
//		createWorkGroupByPlusButton(phoneNum, password, groupName);
//		DeleteGroup = groupName;
//
//		printLog("step2=Send some text");
//		String msg = sendMsg_Text();
//
//		printLog("step4=Long click the msg");
//		longClickElementBySwipe(getLastMsgInChat());
//		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
//		
//		clickName(Constant.TXT_MORE_OPTION);
//		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_DELETE, 3000));
//
//		printLog("step7=Select " + Constant.TXT_SELECT_DIALOG_LIST_DELETE);
//		clickText(Constant.TXT_SELECT_DIALOG_LIST_DELETE, true);
//		clickText(Constant.TXT_CONFIRM, true);
//		
//		printLog("step8=Back to msg list and check delete result");
//		clickXpath(Constant.XPATH_BACK);
//		assertTrue("Delete the msg failed.", waitForTextGone(msg, 2000));
//	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_Voice() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send voice");
		sendMsg_Voice(3000);
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send voice failed.", waitForNameExists(Constant.TXT_VOICE, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_Picture() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send picture");
		sendMsg_Picture();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send picture failed.", waitForNameExists(Constant.TXT_IMAGE, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_Video() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send video");
		sendMsg_Photograph(3000);
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_DOCUMENT, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_Document() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send one document");
		sendMsg_Document();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_DOCUMENT, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_Location() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send location");
		sendMsg_Location();
		
//		printLog("step5=click the location");
//		clickTextContains(Constant.TXT_LOCATION_INFO, true);
//		assertTrue("The UI Element not exist: " + Constant.TXT_LOCATION_DETAIL,
//				waitForTextExists(Constant.TXT_LOCATION_DETAIL, 3000));
//		assertTrue("Enter location detail failed.", waitForResourceId(Constant.ID_CHAT_MSG_LOCATION_MAP, 3000));
//
//		printLog("step6=click settings icon");
//		clickResourceId(Constant.ID_CHAT_SETTINGS);
//		assertTrue("The UI element not exist: " + Constant.TXT_SELECT_DIALOG_LIST_FAVORITE,
//				waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_FAVORITE, 3000));
//		assertTrue("The UI element not exist: " + Constant.TXT_SELECT_DIALOG_LIST_RELAY,
//				waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RELAY, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_ReportLocation() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Report location");
		String title = "autotest";
		sendMsg_ReportLocation(title);

		printLog("step5=Click the report location");
		clickCenterPoint(getLastMsgInChat());
		assertTrue("Enter report location failed", waitForTextExists(Constant.TXT_LOCATION_REPORT_DETAIL, 3000));

		printLog("step6=Click stop report location");
		clickText(Constant.TXT_LOCATION_REPORT_STOP, true);
		clickText(Constant.TXT_YES, true);
		assertTrue("Stop report location failed", waitForTextGone(Constant.TXT_LOCATION_REPORT_STOP, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_Link() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send link");
		String link = "http://www.lanxin.cn";
		sendMsg_Link(link);

		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_LINK, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_GroupCard_Joined() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Click settings icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the icon to enter settings failed.", waitForTextExists(Constant.TXT_SETTINGS, 3000));

		printLog("step3=Click " + Constant.TXT_WORKGROUP_SETTINGS_BE_SEARCH);
		clickXpath(Constant.XPATH_GROUP_BE_SEATCH);
		assertTrue("Set group be search failed.", waitForTextExists(Constant.TXT_WORKGROUP_SETTINGS_GROUP_QRCODE, 5000));

		printLog("step4=Click Share icon");
		clickName(Constant.NAME_SETTING_SHARE);
		assertTrue("Click the share icon failed.", waitForTextExists(Constant.TXT_RELAY_GROUP_NAMECARD, 3000));

		printLog("step5=Click " + Constant.TXT_RELAY_GROUP_NAMECARD);
		clickText(Constant.TXT_RELAY_GROUP_NAMECARD, true);
		assertTrue("Enter share option failed.", waitForTextExists(groupName, 3000));

		clickText(groupName, true);
		clickText(Constant.TXT_CONFIRM, true);
		sleep(2000);
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back group chat failed", waitForTextExists(Constant.NAME_GROUP_SETTING_BTN, 3000));

		printLog("step6=Click group card");
		int count = getElementsByUiAutomation(".tableViews()[2].cells()").size();
		getElementByUiAutomation(".tableViews()[2].cells()[" + String.valueOf(count - 1) + "].buttons()[1]").click();
		assertTrue("Click the group name card failed.", waitForTextExists(Constant.TXT_GROUP_JOINED, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_GroupCard_NoJoin() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		clickXpath(Constant.XPATH_BACK);

		printLog("step2=Send group name card");
		String name = sendMsg_GroupCard(groupName);
		printLog("groupname=" + name);

		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Share groupcard failed.", waitForNameExists(Constant.TXT_GROUP_CARD, 2000));

		printLog("step3=Click The group chat");
		clickText(groupName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));
		
		printLog("step5=click group card");
		int count = getElementsByUiAutomation(".tableViews()[2].cells()").size();
		getElementByUiAutomation(".tableViews()[2].cells()[" + String.valueOf(count - 1) + "].buttons()[1]").click();
		assertTrue("Click the group name card failed.", waitForTextExists(Constant.TXT_GROUP_JOIN, 3000)
				|| waitForTextExists(Constant.TXT_GROUP_APPLYED, 3000));
		groupName = getElementByXpath(Constant.XPATH_GROUP_NAME).getText();
		assertTrue("The group name not match", groupName.equals(name));
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_MsgType_Notify() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send notify");
		String title = "testnotify";
		sendMsg_Notify(title);

//		printLog("step5=click the notify");
//		clickText(Constant.TXT_NOTIFY_CHECK, true);
//		assertTrue("The UI Element not exist: " + Constant.TXT_NOTIFY_DETAIL,
//				waitForTextExists(Constant.TXT_NOTIFY_DETAIL, 3000));
//		assertTrue("The UI Element not exist: " + title, waitForTextExists(title, 2000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_Note() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send notify");
		String title = "testnotify";
		sendMsg_Notify(title);
		
		closeKeyboard();

		printLog("step5=Click " + Constant.TXT_WORKGROUP_NOTE);
		clickText(Constant.TXT_WORKGROUP_NOTE, true);
		assertTrue("The Note not exist", waitForTextExists(title, 3000));

		printLog("step6=Click the note");
		clickText(title, true);
		assertTrue("The UI Element not exist: " + Constant.TXT_NOTIFY_DETAIL,
				waitForTextExists(Constant.TXT_NOTIFY_DETAIL, 3000));
		assertTrue("The UI Element not exist: " + title, waitForTextExists(title, 2000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_Files() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send one document");
		String fileName = sendMsg_Document();
		
		closeKeyboard();

		printLog("step5=Click " + Constant.TXT_WORKGROUP_DOCUMENT);
		clickText(Constant.TXT_WORKGROUP_DOCUMENT, true);
		assertTrue("Enter Files list failed.", waitForTextExists(fileName, 3000));

		printLog("step6=Click one document");
		clickXpath(Constant.XPATH_DOCUMENT_FIRST);
		String file = getElementByXpath(Constant.XPATH_GROUP_TITLE_NAME).getText();
		assertTrue("The file not be open", file.equals(fileName));
	}

	@Test
	public void testFunction_Msg_WorkGroup_MsgType_Album() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send picture");
		sendMsg_Photo();
		
		closeKeyboard();

		printLog("step5=Click " + Constant.TXT_WORKGROUP_ALBUM);
		clickText(Constant.TXT_WORKGROUP_ALBUM, true);
		assertTrue("There is no picture.", waitForXpathExists(Constant.XPATH_ALBUM_FIRST, 3000));

		printLog("step6=Click one picture");
		clickXpath(Constant.XPATH_ALBUM_FIRST);
		assertTrue("Review the picture failed", waitForXpathExists(Constant.XPATH_BITMAP, 5000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendText() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step2=Send some msg");
		String msg = sendMsg_Text();
		
		printLog("step6=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send text failed.", waitForTextExists(msg, 3000));
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendEmotion() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		String emotion = sendMsg_Emotion();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(emotion, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendVoice() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send voice");
		sendMsg_Voice(3000);
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send voice failed.", waitForNameExists(Constant.TXT_VOICE, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendVoice_Cancel() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send voice");
		sendMsg_Voice_Cancel();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Cancel voice failed.", waitForNameGone(Constant.TXT_VOICE, 2000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendPicture() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send picture");
		sendMsg_Photo();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send picture failed.", waitForNameExists(Constant.TXT_IMAGE, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendVideo() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send video");
		sendMsg_Video();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_DOCUMENT, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendPhotograph() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send video");
		sendMsg_Photograph(3000);
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_DOCUMENT, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendPhoto() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send picture");
		sendMsg_Photo();
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send picture failed.", waitForNameExists(Constant.TXT_IMAGE, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendLocation() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send location");
		sendMsg_Location();
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_ReportLocation() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Report location");
		String title = "autotest";
		sendMsg_ReportLocation(title);
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_ShareLocation() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Share location");
		sendMsg_ShareLocation();
		
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		sleep(2000);
		clickXpath(Constant.XPATH_LOCATION_EXIT);
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendLink() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send link");
		String link = "http://www.lanxin.cn";
		sendMsg_Link(link);
		
		printLog("step5=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));
		assertTrue("Send video failed.", waitForNameExists(Constant.TXT_LINK, 3000));
	}

	@Test
	public void testFunction_Msg_WorkGroup_SendBar_SendNotify() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;

		printLog("step4=Send notify");
		String title = "testnotify";
		sendMsg_Notify(title);
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_SendBar_Emotion_Enter() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;
		
		printLog("step4=Click the emotion icon");
		clickName(Constant.NAME_MSG_EMOTION_BTN);
		assertTrue("Click the emotion failed", waitForXpathExists(Constant.XPATH_EMOTION_LIST, 3000));
		
		printLog("step5=Click emotion favorite icon");
		clickText(Constant.NAME_EMOTION_COLLECTION, true);
		assertTrue("Click the emotion favrite icon failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS2, 3000));
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_SendBar_Emotion_Favorite_Enter() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;
		
		printLog("step4=Click the emotion icon");
		clickName(Constant.NAME_MSG_EMOTION_BTN);
		assertTrue("Click the emotion failed", waitForXpathExists(Constant.XPATH_EMOTION_LIST, 3000));
		
		printLog("step5=Click emotion favorite icon");
		clickName(Constant.NAME_EMOTION_COLLECTION);
		assertTrue("Click the emotion favrite icon failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS2, 3000));
		
		printLog("step6=Click Add emotion icon");
		clickName(Constant.NAME_EMOTION_PLUS2);
		assertTrue("Click emtion plus failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS, 3000));
		assertTrue("Click emtion plus failed.", waitForTextExists(Constant.TXT_MY_FAVORITE_EMOTION, 3000));
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_SendBar_Emotion_Favorite_Add() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;
		
		printLog("step4=Click the emotion icon");
		clickName(Constant.NAME_MSG_EMOTION_BTN);
		assertTrue("Click the emotion failed", waitForXpathExists(Constant.XPATH_EMOTION_LIST, 3000));
		
		printLog("step5=Click emotion favorite icon");
		clickName(Constant.NAME_EMOTION_COLLECTION);
		assertTrue("Click the emotion favrite icon failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS2, 3000));
		
		printLog("step6=Click Add emotion icon");
		clickName(Constant.NAME_EMOTION_PLUS2);
		assertTrue("Click emtion plus failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS, 3000));
		assertTrue("Click emtion plus failed.", waitForTextExists(Constant.TXT_MY_FAVORITE_EMOTION, 3000));
		
		printLog("step7=Click Add icon");
		clickXpath(Constant.XPATH_ADD_EMOTION_FIRST);
		assertTrue("Click Add icon", waitForTextExists(Constant.TXT_CAMERA_FILM, 3000));
		
		printLog("step8=Select one picture");
		clickXpath(Constant.XPATH_FIRST_PICTURE);
		assertTrue("The Ui Element not exist: " + Constant.TXT_COMPLETE, waitForTextExists(Constant.TXT_COMPLETE, 3000));
		clickText(Constant.TXT_COMPLETE, true);
		assertTrue("Back emtion plus failed.", waitForTextExists(Constant.TXT_MY_FAVORITE_EMOTION, 5000));
		String name = getElementByXpath(Constant.XPATH_ADD_EMOTION_FIRST).getText();
		assertTrue("Click emtion plus failed.", !name.equals(Constant.NAME_EMOTION_PLUS));
	}
	
	@Test
	public void testFunction_Msg_WorkGroup_SendBar_Emotion_Favorite_Delete() {
		String groupName = getValue("workgroup_name");

		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;
		
		printLog("step4=Click the emotion icon");
		clickName(Constant.NAME_MSG_EMOTION_BTN);
		assertTrue("Click the emotion failed", waitForXpathExists(Constant.XPATH_EMOTION_LIST, 3000));
		
		printLog("step5=Click emotion favorite icon");
		clickName(Constant.NAME_EMOTION_COLLECTION);
		assertTrue("Click the emotion favrite icon failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS2, 3000));
		
		printLog("step6=Click Add emotion icon");
		clickName(Constant.NAME_EMOTION_PLUS2);
		assertTrue("Click emtion plus failed.", waitForNameExists(Constant.NAME_EMOTION_PLUS, 3000));
		assertTrue("Click emtion plus failed.", waitForTextExists(Constant.TXT_MY_FAVORITE_EMOTION, 3000));
		
		String name = getElementByXpath(Constant.XPATH_ADD_EMOTION_FIRST).getText();
		if (name.equals(Constant.NAME_EMOTION_PLUS)) {
			printLog("step7=Click Add icon");
			clickXpath(Constant.XPATH_ADD_EMOTION_FIRST);
			assertTrue("Click Add icon", waitForTextExists(Constant.TXT_CAMERA_FILM, 3000));
			
			printLog("step8=Select one picture");
			clickXpath(Constant.XPATH_FIRST_PICTURE);
			assertTrue("The Ui Element not exist: " + Constant.TXT_COMPLETE, waitForTextExists(Constant.TXT_COMPLETE, 3000));
			clickText(Constant.TXT_COMPLETE, true);
			assertTrue("Back emtion plus failed.", waitForTextExists(Constant.TXT_MY_FAVORITE_EMOTION, 5000));
			name = getElementByXpath(Constant.XPATH_ADD_EMOTION_FIRST).getText();
			assertTrue("Click emtion plus failed.", !name.equals(Constant.NAME_EMOTION_PLUS));
		}
		
		printLog("step9=Delete the emotion");
		clickText(Constant.TXT_EDIT, true);
		clickXpath(Constant.XPATH_ADD_EMOTION_FIRST);
		sleep(1000);
		clickText(Constant.TXT_DELETE_ONE, true);
		clickText(Constant.TXT_CONFIRM, true);
		String name2 = getElementByXpath(Constant.XPATH_ADD_EMOTION_FIRST).getText();
		assertTrue("Delete emtion failed.", name2.equals(Constant.NAME_EMOTION_PLUS));
	}
	
	@Test
	public void testFunction_Msg_LinkCard_ReviewCard() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		String link = "http://www.lanxin.cn";
		sendMsg_Text(link);
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_COPY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_COPY, true);
		assertTrue("Select copy failed.", waitForTextGone(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step6=Click the plus button in toolbar");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("The link card not exist.", waitForNameExists(Constant.NAME_LINK_BUBBLES, 3000));
	}

	@Test
	public void testFunction_Msg_LinkCard_SendCard() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		String link = "http://www.163.com";
		sendMsg_Text(link);
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_COPY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_COPY, true);
		assertTrue("Select copy failed.", waitForTextGone(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step6=Click the plus button in toolbar");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("The link card not exist.", waitForNameExists(Constant.NAME_LINK_BUBBLES, 3000));

		printLog("step7=Click the link Card");
		List<WebElement> list = getElementsByName(Constant.NAME_LINK_BUBBLES);
		if (list.size() > 0) {
			clickCenterPoint(list.get(list.size() - 1));
		}
		assertTrue("Click yes failed.", waitForNameGone(Constant.NAME_LINK_BUBBLES, 3000));

		printLog("step8=click the link");
		clickCenterPoint(getLastMsgInChat());
		assertTrue("The UI Element not exist: " + Constant.TXT_LINK_NETEAST_DETAIL,
				waitForTextExists(Constant.TXT_LINK_NETEAST_DETAIL, 3000));
	}

	@Test
	public void testFunction_Msg_LinkCard_SameLink() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		String link = "http://www.163.com";
		sendMsg_Text(link);
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_COPY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_COPY, true);
		assertTrue("Select copy failed.", waitForTextGone(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step6=Click the plus button in toolbar");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("The link card exist.", waitForNameGone(Constant.NAME_LINK_BUBBLES, 3000));
	}

	@Test
	public void testFunction_Msg_LinkCard_NotLink() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		sendMsg_Text();
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_COPY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_COPY, true);
		assertTrue("Select copy failed.", waitForTextGone(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step6=Click the plus button in toolbar");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("The link card exist.", waitForNameGone(Constant.NAME_LINK_BUBBLES, 3000));
	}

	@Test
	public void testFunction_Msg_LinkCard_InvalidLink() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		String link = "aaaaaaaaa";
		sendMsg_Text(link);
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_COPY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_COPY, true);
		assertTrue("Select copy failed.", waitForTextGone(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step6=Click the plus button in toolbar");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("The link card exist.", waitForNameGone(Constant.NAME_LINK_BUBBLES, 3000));
	}

	@Test
	public void testFunction_Msg_LinkCard_NotPlusButton() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		String link = "http://www.lanxin.cn";
		sendMsg_Text(link);
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_COPY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_COPY, true);
		assertTrue("Select copy failed.", waitForTextGone(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step6=Click the emotion button in toolbar");
		clickName(Constant.NAME_MSG_EMOTION_BTN);
		assertTrue("The link card exist.", waitForNameGone(Constant.NAME_LINK_BUBBLES, 3000));
	}

	@Test
	public void testFunction_Msg_LinkCard_SendLink() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		String link = "http://www.163.com";
		sendMsg_Text(link);
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_COPY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_COPY, true);
		assertTrue("Select copy failed.", waitForTextGone(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step6=Click the plus button in toolbar");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("The link card not exist.", waitForNameExists(Constant.NAME_LINK_BUBBLES, 3000));

		printLog("step7=Click " + Constant.TXT_CHAT_TOOL_LINK);
		clickText(Constant.NAME_MSG_LINK_BTN, true);
		clickText(Constant.NAME_MSG_LINK_BTN, true);
		assertTrue("The text not exist: " + Constant.TXT_LINK_TITLE, waitForTextExists(Constant.TXT_LINK_TITLE, 5000));

		printLog("step8=Verify UI Element");
		assertTrue("The text not exist: " + Constant.TXT_LINK_NETEAST,
				waitForTextExists(Constant.TXT_LINK_NETEAST, 5000));
	}

	@Test
	public void testFunction_Msg_LinkCard_SendCard_WorkGroup() {
		String groupName = getValue("workgroup_name");
		createWorkGroupByPlusButton(phoneNum, password, groupName);
		DeleteGroup = groupName;
		
		clickXpath(Constant.XPATH_BACK);
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		String link = "http://www.lanxin.cn";
		sendMsg_Text(link);
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_COPY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_COPY, true);
		assertTrue("Select copy failed.", waitForTextGone(Constant.TXT_SELECT_DIALOG_LIST_COPY, 3000));

		printLog("step6=Back to Msg list");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back Msg list failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step7=Enter workgroup");
		clickText(groupName, true);
		assertTrue("Enter work group failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step8=Click the plus button in toolbar");
		clickName(Constant.NAME_CHAT_MENU_TOOL);
		assertTrue("The link card not exist.", waitForNameExists(Constant.NAME_LINK_BUBBLES, 3000));

		printLog("step9=Click the link Card");
		List<WebElement> list = getElementsByName(Constant.NAME_LINK_BUBBLES);
		if (list.size() > 0) {
			clickCenterPoint(list.get(list.size() - 1));
		}
		assertTrue("Click yes failed.", waitForNameGone(Constant.NAME_LINK_BUBBLES, 3000));

		printLog("step10=click the link");
		clickCenterPoint(getLastMsgInChat());
		assertTrue("The UI Element not exist: " + Constant.TXT_LINK_LANXIN_DETAIL,
				waitForTextExists(Constant.TXT_LINK_LANXIN_DETAIL, 15000));
	}
	
	@Test
	public void testFunction_Msg_BatchDealMsg_SelectMore() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step5=Long click a message");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
		
		clickName(Constant.TXT_MORE_OPTION);
		assertTrue("Click more failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_MORE, 3000));

		printLog("step6=Select More in options list");
		clickText(Constant.TXT_SELECT_DIALOG_LIST_MORE, true);
		assertTrue("Select More in option list failed", waitForNameExists(Constant.NAME_CHAT_EDIT_BTN_SHARE, 3000));
	}

	@Test
	public void testFunction_Msg_BatchDealMsg_MsgRelay() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send some msg");
		String longtime = sendMsg_Text();

		printLog("step5=Long click a message");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
		
		clickName(Constant.TXT_MORE_OPTION);
		assertTrue("Click more failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_MORE, 3000));

		printLog("step6=Select More in options list");
		clickText(Constant.TXT_SELECT_DIALOG_LIST_MORE, true);
		assertTrue("Select More in option list failed", waitForNameExists(Constant.NAME_CHAT_EDIT_BTN_SHARE, 3000));

		printLog("step7=Select share");
		clickName(Constant.NAME_CHAT_EDIT_BTN_SHARE);
		assertTrue("Click the relay to enter option failed.", waitForTextExists(Constant.TXT_RELAY_TITLE, 3000));
		assertTrue("The UI Element not exist: " + Constant.TXT_RELAY_NEW_CHAT,
				waitForTextExists(Constant.TXT_RELAY_NEW_CHAT, 2000));

		printLog("step8=Click one current chat");
		clickText(chatName, true);
		assertTrue("Clickt the current chat failed.", waitForTextExists(Constant.TXT_CONFIRM, 3000));
		clickText(Constant.TXT_CONFIRM, true);

		clickXpath(Constant.XPATH_BACK);
		assertTrue("Relay the msg failed.", waitForTextExists(longtime, 3000));
	}

	@Test
	public void testFunction_Msg_BatchDealMsg_MsgFavorite() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send some msg");
		String longtime = sendMsg_Text();

		printLog("step5=Long click a message");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
		
		clickName(Constant.TXT_MORE_OPTION);
		assertTrue("Click more failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_MORE, 3000));

		printLog("step6=Select More in options list");
		clickText(Constant.TXT_SELECT_DIALOG_LIST_MORE, true);
		assertTrue("Select More in option list failed", waitForNameExists(Constant.NAME_CHAT_EDIT_BTN_COLLECT, 3000));

		printLog("step6=Select Collect");
		clickText(Constant.NAME_CHAT_EDIT_BTN_COLLECT, true);

		printLog("step7=Click back");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back to Msg failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step8=Click " + Constant.TXT_TOOLBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click the More failed.", waitForTextExists(Constant.TXT_MORE_MY_FAVORITE, 3000));

		printLog("step9=Click " + Constant.TXT_MORE_MY_FAVORITE);
		clickText(Constant.TXT_MORE_MY_FAVORITE, true);
	}

	@Test
	public void testFunction_Msg_BatchDealMsg_MsgDelete() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Send some msg");
		String longtime = sendMsg_Text();

		printLog("step5=Long click a message");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
		
		clickName(Constant.TXT_MORE_OPTION);
		assertTrue("Click more failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_MORE, 3000));

		printLog("step6=Select More in options list");
		clickText(Constant.TXT_SELECT_DIALOG_LIST_MORE, true);
		assertTrue("Select More in option list failed", waitForNameExists(Constant.NAME_CHAT_EDIT_BTN_DELETE, 3000));

		printLog("step6=Select Delete");
		clickText(Constant.NAME_CHAT_EDIT_BTN_DELETE, true);
		assertTrue("Select delete failed.", waitForNameExists(Constant.TXT_DELETE, 3000));
		
		clickText(Constant.TXT_DELETE, true);
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Delete the msg failed.", waitForTextGone(longtime, 3000));
	}

	@Test
	public void testFunction_Msg_BatchDealMsg_Cancel() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step5=Long click a message");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_MORE_OPTION, 3000));
		
		clickName(Constant.TXT_MORE_OPTION);
		assertTrue("Click more failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_MORE, 3000));

		printLog("step6=Select More in options list");
		clickText(Constant.TXT_SELECT_DIALOG_LIST_MORE, true);
		assertTrue("Select More in option list failed", waitForNameExists(Constant.NAME_CHAT_EDIT_BTN_DELETE, 3000));

		printLog("step7=Cancel batch deal");
		clickText(Constant.TXT_CANCEL, true);
		assertTrue("Long click message failed", waitForNameGone(Constant.NAME_CHAT_EDIT_BTN_DELETE, 3000));
	}
	
	@Test
	public void testFunction_Address_Org_DragSwitchButton() {
		startLanxinApp(phoneNum, password);
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		assertTrue("Ui Element not exist: ID_TOOLBAR_MSG", waitForXpathExists(Constant.XPATH_TABBAR_MSG, 3000));

		printLog("step2=Click " + Constant.TXT_TOOLBAR_ADDRESS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue("Click Address failed.", waitForTextExists(Constant.TXT_ADDRESS_ORG, 3000));

		printLog("step3=Drag switch button");
		Point p1 = getElementCenterPointByName(Constant.NAME_GROUP_TOP);
		printLog("p1" + p1);
		drag(p1.x, p1.y, p1.x, p1.y - 200, 1000);
		Point p2 = getElementCenterPointByName(Constant.NAME_GROUP_TOP);
		printLog("p2" + p2);
		assertTrue("The swithc button can be drag", p1.equals(p2));
	}
	
	@Test
	public void testFunction_Address_Contacts_TopContacts_Index() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_ADS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue("Click Address failed.", waitForNameExists(Constant.TXT_ADDRESS_ORG, 3000));
		
		printLog("step3=Click Contacts");
		clickName(Constant.TXT_ADDRESS_CONTACT);
		assertTrue("Click Address failed.", waitForTextExists(Constant.TXT_ADDRESS_NEW_CONTACT, 3000));
		
		printLog("step4=Verify the Index display");
		assertTrue("The UI Element not exist: " + Constant.TXT_ADDRESS_TOPCONTACT, waitForTextExists(Constant.TXT_ADDRESS_TOPCONTACT, 3000));
		assertTrue("The UI Element not exist: " + Constant.TXT_CONTACT_INDEX, waitForTextExists(Constant.TXT_CONTACT_INDEX, 3000));
	}
	
	@Test
	public void testFunction_Address_Contacts_Search_ByNumber() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_ADS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue("Click Address failed.", waitForNameExists(Constant.TXT_ADDRESS_ORG, 3000));
		
		printLog("step3=Click Contacts");
		clickName(Constant.TXT_ADDRESS_CONTACT);
		assertTrue("Cick the search icon failed", waitForXpathExists(Constant.XPATH_CONTACTS_SEARCH, 3000));

		printLog("step4=Enter number to search contact");
		clickXpath(Constant.XPATH_CONTACTS_SEARCH);
		enterTextByKeyBoard(phoneNum);
		assertTrue("Search by number failed.", waitForTextExists(Constant.TXT_MY_NAME, 3000));
	}
	
	@Test
	public void testFunction_Address_Contacts_Search_ByNumber_ContactDetails() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_ADS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue("Click Address failed.", waitForNameExists(Constant.TXT_ADDRESS_ORG, 3000));
		
		printLog("step3=Click Contacts");
		clickName(Constant.TXT_ADDRESS_CONTACT);
		assertTrue("Cick the search icon failed", waitForXpathExists(Constant.XPATH_CONTACTS_SEARCH, 3000));

		printLog("step4=Enter number to search contact");
		clickXpath(Constant.XPATH_CONTACTS_SEARCH);
		enterTextByKeyBoard(phoneNum);
		assertTrue("Search by number failed.", waitForTextExists(Constant.TXT_MY_NAME, 3000));
		
		printLog("step5=Click the search result to check contact details");
		clickXpath(Constant.XPATH_CONTACTS_SEARCH_RESULT);
		assertTrue("Enter name card failed.", waitForTextExists(Constant.TXT_NAME_CARD, 3000));
	}

	@Test
	public void testFunction_Address_GroupChat_Apply() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_ADS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue("Click Address failed.", waitForNameExists(Constant.TXT_ADDRESS_ORG, 3000));
		
		printLog("step3=Click " + Constant.TXT_ADDRESS_CONTACT);
		clickText(Constant.TXT_ADDRESS_CONTACT, true);
		assertTrue(Constant.TXT_ADDRESS_CONTACT + " not exist.", waitForTextExists(Constant.TXT_INTEREST_CHAT_GROUP, 5000));
		
		printLog("step4=Click " + Constant.TXT_INTEREST_CHAT_GROUP);
		clickText(Constant.TXT_INTEREST_CHAT_GROUP, true);
		assertTrue("Click group chat failed.", waitForTextExists(Constant.TXT_INTEREST_CHAT_GROUP, 3000));

		String groupName = getElementByXpath(Constant.XPATH_SELECT_LIST_FIRST).getText();
		printLog("step5=Click a group");
		clickText(groupName, true);
		assertTrue("Click a group failed.", waitForTextExists(Constant.TXT_GROUP_INFO, 2000));

		printLog("step5=Apply for group");
		if (waitForTextExists(Constant.TXT_GROUP_JOIN, 3000)) {
			clickText(Constant.TXT_GROUP_JOIN, true);
		}
		assertTrue("Click Apply for group failed.", waitForTextExists(Constant.TXT_GROUP_APPLYED, 3000));
	}

	@Test
	public void testFunction_Address_GroupChat_Search() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_ADS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue(Constant.TXT_ADDRESS_CONTACT + " not exist.", waitForTextExists(Constant.TXT_ADDRESS_CONTACT, 5000));
		
		printLog("step3=Click " + Constant.TXT_ADDRESS_CONTACT);
		clickText(Constant.TXT_ADDRESS_CONTACT, true);
		assertTrue(Constant.TXT_ADDRESS_CONTACT + " not exist.", waitForTextExists(Constant.TXT_INTEREST_CHAT_GROUP, 5000));
		
		printLog("step4=Click " + Constant.TXT_INTEREST_CHAT_GROUP);
		clickText(Constant.TXT_INTEREST_CHAT_GROUP, true);
		assertTrue("Click group chat failed.", waitForTextExists(Constant.TXT_INTEREST_CHAT_GROUP, 3000));

		printLog("step5=Click the search edittext");
		clickXpath(Constant.XPATH_SEARCH_BAR);
		String groupName = "est";
		enterNumbersByKeyboard(groupName);
		clickText(Constant.TXT_SEARCH, true);
		clickText(Constant.TXT_SEARCH, true);
		assertTrue("Search failed.", waitForXpathExists(Constant.XPATH_SELECT_LIST_FIRST, 3000));
	}

	@Test
	public void testFunction_Address_RssAccount_RssTop() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_ADS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue(Constant.TXT_ADDRESS_CONTACT + " not exist.", waitForTextExists(Constant.TXT_ADDRESS_CONTACT, 5000));
		
		printLog("step3=Click " + Constant.TXT_ADDRESS_CONTACT);
		clickText(Constant.TXT_ADDRESS_CONTACT, true);
		assertTrue(Constant.TXT_ADDRESS_CONTACT + " not exist.", waitForTextExists(Constant.TXT_INTEREST_RSS_ACCOUNT, 5000));

		printLog("step3=Click " + Constant.TXT_INTEREST_RSS_ACCOUNT);
		clickText(Constant.TXT_INTEREST_RSS_ACCOUNT, true);
		assertTrue("Click Rss failed.", waitForTextExists(Constant.TXT_INTEREST_RSS_ACCOUNT, 3000));
		assertTrue("The list has no rss accont.", waitForXpathExists(Constant.XPATH_SELECT_LIST_FIRST, 3000));

		printLog("step4=Click a account");
		String acctountName = getElementByXpath(Constant.XPATH_SELECT_LIST_FIRST).getText();
		clickText(acctountName, true);
		assertTrue("Click the account failed.", waitForTextExists(acctountName, 3000));

		printLog("step5=Click " + Constant.TXT_RSS_TOP);
		if (waitForTextExists(Constant.txt_RSS_CANCEL, 3000)) {
			clickText(Constant.txt_RSS_CANCEL, true);
			List<WebElement> list = getElementsByName(Constant.txt_RSS_CANCEL);
			clickCenterPoint(list.get(list.size() - 1));
			assertTrue("Click " + Constant.txt_RSS_CANCEL + " failed.", waitForTextExists(Constant.TXT_RSS_TOP, 3000));
		} else {
			clickText(Constant.TXT_RSS_TOP, true);
			assertTrue("Click " + Constant.TXT_RSS_TOP + " failed.", waitForTextExists(Constant.txt_RSS_CANCEL, 3000));
		}
	}

	@Test
	public void testFunction_Address_RssAccount_NoAccount() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_ADS);
		clickXpath(Constant.XPATH_TABBAR_ADS);
		assertTrue(Constant.TXT_ADDRESS_CONTACT + " not exist.", waitForTextExists(Constant.TXT_ADDRESS_CONTACT, 5000));
		
		printLog("step3=Click " + Constant.TXT_ADDRESS_CONTACT);
		clickText(Constant.TXT_ADDRESS_CONTACT, true);
		assertTrue(Constant.TXT_ADDRESS_CONTACT + " not exist.", waitForTextExists(Constant.TXT_INTEREST_RSS_ACCOUNT, 5000));

		printLog("step3=Click " + Constant.TXT_INTEREST_RSS_ACCOUNT);
		clickText(Constant.TXT_INTEREST_RSS_ACCOUNT, true);
		assertTrue("Click Rss failed.", waitForTextExists(Constant.TXT_INTEREST_RSS_ACCOUNT, 3000));
		
		assertTrue("The list has no rss accont.", waitForXpathExists(Constant.XPATH_SELECT_LIST_FIRST, 3000));

		printLog("step5=Verify no any rss account");
		if (waitForXpathExists(Constant.XPATH_SELECT_LIST_FIRST, 3000)) {
			printLog("The list has rss accont.");
		} else {
			printLog("The list no rss accont.");
		}
	}

	@Test
	public void testFunction_More_Person_BasicInfo_SetHeadIcon_ByPhoto() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click Person info");
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Enter person info failed.", waitForTextExists(Constant.TXT_MORE_PERSON_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MORE_PERSON_HEAD);
		clickText(Constant.TXT_MORE_PERSON_HEAD, true);
		assertTrue("Click set Head icon failed.", waitForTextExists(Constant.TXT_HEAD_MODIFY, 3000));

		printLog("step5=Click " + Constant.TXT_DIALOG_SETHEAD_PHOTO);
		setHeadIcon_Photo();
	}

	@Test
	public void testFunction_More_Person_BasicInfo_SetHeadIcon_ByPicture() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click Person info");
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Enter person info failed.", waitForTextExists(Constant.TXT_MORE_PERSON_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MORE_PERSON_HEAD);
		clickText(Constant.TXT_MORE_PERSON_HEAD, true);
		assertTrue("Click set Head icon failed.", waitForTextExists(Constant.TXT_HEAD_MODIFY, 3000));

		printLog("step5=Click " + Constant.TXT_DIALOG_SETHEAD_PICTURE);
		setHeadIcon_Picture();
	}
	
	@Test
	public void testFunction_More_Person_BasicInfo_SetSex() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click Person info");
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Enter person info failed.", waitForTextExists(Constant.TXT_MORE_PERSON_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MORE_PERSON_SEX);
		clickText(Constant.TXT_MORE_PERSON_SEX, true);
		assertTrue("Click set sex  failed.", waitForTextExists(Constant.TXT_SET_SAVE, 3000));

		printLog("step5=Change sex and save");
		clickText(Constant.TXT_SET_SEX_FEMALE, true);
		clickText(Constant.TXT_SET_SAVE, true);
		assertTrue("Set sex failed", waitForTextExists(Constant.TXT_SET_SEX_FEMALE, 3000));

		printLog("step6=Change sex and saveagain");
		clickText(Constant.TXT_MORE_PERSON_SEX, true);
		assertTrue("Click set sex  failed.", waitForTextExists(Constant.TXT_SET_SAVE, 3000));
		clickText(Constant.TXT_SET_SEX_MALE, true);
		clickText(Constant.TXT_SET_SAVE, true);
		assertTrue("Set sex failed", waitForTextExists(Constant.TXT_SET_SEX_MALE, 3000));
	}

	@Test
	public void testFunction_More_Person_BasicInfo_SetSign() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click Person info");
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Enter person info failed.", waitForTextExists(Constant.TXT_MORE_PERSON_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MORE_PERSON_SIGN);
		clickText(Constant.TXT_MORE_PERSON_SIGN, true);
		assertTrue("Click set sign  failed.", waitForTextExists(Constant.TXT_SET_SAVE, 3000));

		printLog("step5=Change sign and save");
		clickXpath(Constant.XPATH_SIGN);
		getElementByXpath(Constant.XPATH_SIGN).clear();
		Date d = new Date();
		String longtime = String.valueOf(d.getTime());
		enterTextByKeyBoard(longtime);
		clickText(Constant.TXT_SET_SAVE, true);
		assertTrue("Set sign failed", waitForTextExists(longtime, 3000));
	}

	@Test
	public void testFunction_More_Person_BasicInfo_SetIntroduction() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click Person info");
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Enter person info failed.", waitForTextExists(Constant.TXT_MORE_PERSON_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MORE_PERSON_INTRODUCE);
		clickText(Constant.TXT_MORE_PERSON_INTRODUCE, true);
		assertTrue("Click set sign  failed.", waitForTextExists(Constant.TXT_SET_SAVE, 3000));

		printLog("step5=Change introduce and save");
		clickXpath(Constant.XPATH_INTRODUCTION);
		getElementByXpath(Constant.XPATH_INTRODUCTION).clear();
		Date d = new Date();
		String longtime = String.valueOf(d.getTime());
		enterTextByKeyBoard(longtime);
		clickText(Constant.TXT_SET_SAVE, true);
		assertTrue("Set sign failed", waitForTextExists(longtime, 3000));
	}

	@Test
	public void testFunction_More_Person_BasicInfo_SetJob() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click Person info");
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Enter person info failed.", waitForTextExists(Constant.TXT_MORE_PERSON_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MORE_PERSON_JOB);
		clickText(Constant.TXT_MORE_PERSON_JOB, true);
		assertTrue("Click set job  failed.", waitForTextExists(Constant.TXT_SET_SAVE, 3000));

		while (waitForNameExists(Constant.NAME_BTN_DELETE, 2000)) {
			clickText(Constant.NAME_BTN_DELETE, true);
		}
		
		printLog("step5=add work");
		clickText(Constant.TXT_WORK_ADD, true);
		clickText(Constant.TXT_WORK_PLACE, true);
		String s1 = "workplace";
		enterTextByKeyBoard(s1);
		assertTrue("Set workplace failed", waitForTextExists(s1, 3000));
		
		closeKeyboard();

		clickText(Constant.TXT_WORK_POSITION, true);
		String s2 = "workposition";
		enterTextByKeyBoard(s2);
		assertTrue("Set work position failed", waitForTextExists(s2, 3000));
		
		closeKeyboard();

		clickText(Constant.TXT_WORK_TIME, true);
		clickText(Constant.TXT_CONFIRM, true);

		sleep(2000);
		printLog("step6=add edu");
		clickText(Constant.TXT_EDU_ADD, true);
		
		while (!waitForTextExists(Constant.TXT_EDU_PLACE, 1000)) {
			clickText(Constant.TXT_EDU_ADD, true);
		}
		
		clickText(Constant.TXT_EDU_PLACE, true);
		String s3 = "school";
		enterTextByKeyBoard(s3);
		assertTrue("Set school failed", waitForTextExists(s3, 3000));
		
		closeKeyboard();

		clickText(Constant.TXT_EDU_POSITION, true);
		String s4 = "class";
		enterTextByKeyBoard(s4);
		assertTrue("Set class failed", waitForTextExists(s4, 3000));
		
		closeKeyboard();

		clickText(Constant.TXT_WORK_TIME, true);
		List<WebElement> list = getElementsByName(Constant.TXT_WORK_TIME);
		clickCenterPoint(list.get(list.size() - 1));
		clickText(Constant.TXT_CONFIRM, true);

		printLog("step7=Save edit and check");
		clickText(Constant.TXT_SET_SAVE, true);
		while (waitForTextExists(Constant.TXT_SET_SAVE, 1000)) {
			clickText(Constant.TXT_SET_SAVE, true);
		}
		assertTrue("Set workplace failed", waitForTextExists(s1, 3000));
		assertTrue("Set work position failed", waitForTextExists(s2, 3000));
		assertTrue("Set school failed", waitForTextExists(s3, 3000));
		assertTrue("Set class failed", waitForTextExists(s4, 3000));
	}

	@Test
	public void testFunction_More_Person_BasicInfo_NotEdit() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click Person info");
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Enter person info failed.", waitForTextExists(Constant.TXT_MORE_PERSON_TITLE, 3000));

		printLog("step4=Click " + Constant.TXT_MORE_PERSON_INTRODUCE);
		clickText(Constant.TXT_MORE_PERSON_INTRODUCE, true);
		assertTrue("Click set sign  failed.", waitForTextExists(Constant.TXT_SET_SAVE, 3000));

		printLog("step5=Change introduce and save");
		clickXpath(Constant.XPATH_INTRODUCTION);
		getElementByXpath(Constant.XPATH_INTRODUCTION).clear();
		clickText(Constant.TXT_SET_SAVE, true);
		assertTrue("Set sign failed", waitForTextExists(Constant.TXT_NOT_EDIT, 3000));
	}

	@Test
	public void testFunction_More_Person_QRCode_Relay() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click Person info");
		clickName(Constant.TXT_MY_NAME);
		assertTrue("Enter person info failed.", waitForTextExists(Constant.TXT_MORE_PERSON_TITLE, 3000));

		printLog("step4=click QR code");
		clickText(Constant.TXT_MORE_PERSON_QRCODE, true);
		assertTrue("Enter QR code failed.", waitForTextExists(Constant.TXT_MORE_PERSON_QRCODE, 3000));

		printLog("step5=Click the More icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("UI Element not exist: " + Constant.TXT_SELECT_DIALOG_LIST_RELAY,
				waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_RELAY, 2000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_RELAY);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_RELAY, true);
		assertTrue("Click the relay to enter option failed.", waitForTextExists(Constant.TXT_RELAY_TITLE, 3000));
		assertTrue("CLick relay failed.", waitForTextExists(Constant.TXT_RELAY_NEW_CHAT, 3000));

		printLog("step6=Click one current chat");
		clickText(chatName, true);
		assertTrue("Clickt the current chat failed.", waitForTextExists(Constant.TXT_CONFIRM, 3000));

		printLog("step7=Click the confirm");
		clickText(Constant.TXT_CONFIRM, true);

		printLog("step8=Back to Msg list and check send successful");
		clickXpath(Constant.XPATH_BACK);
		clickXpath(Constant.XPATH_BACK);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 3000));

		assertTrue("Save QRcode failed.", waitForTextExists(Constant.TXT_IMAGE, 3000));
	}

	@Test
	public void testFunction_More_MyDocument_DocumentDetail() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_MY_DOCUMENT);
		clickText(Constant.TXT_MORE_MY_DOCUMENT, true);
		assertTrue("Enter My Document failed.",waitForTextExists(Constant.TXT_MORE_MY_DOCUMENT, 3000));

		printLog("step4=Select one document");
		String file = getElementByXpath(Constant.XPATH_SELECT_LIST_FIRST).getText();
		clickText(file, true);
		assertTrue("UI Element not exist: " + Constant.TXT_FILE_REVIEW,
				waitForTextExists(Constant.TXT_FILE_REVIEW, 3000) || waitForTextExists(file, 3000));

		printLog("step5=Click share icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("UI Element not exist: " + Constant.TXT_SHARE_TO_GROUP,
				waitForTextExists(Constant.TXT_SHARE_TO_GROUP, 2000));
		assertTrue("UI Element not exist: " + Constant.TXT_SAVE_TO_MY_DOCUMENT,
				waitForTextExists(Constant.TXT_SAVE_TO_MY_DOCUMENT, 2000));

		printLog("step6=Select " + Constant.TXT_SHARE_TO_GROUP);
		clickText(Constant.TXT_SHARE_TO_GROUP, true);
		assertTrue("Click the relay to enter option failed.", waitForTextExists(Constant.TXT_RELAY_TITLE, 3000));
		assertTrue("The UI Element not exist: " + Constant.TXT_RELAY_NEW_CHAT,
				waitForTextExists(Constant.TXT_RELAY_NEW_CHAT, 2000));

		printLog("step7=Click one current chat");
		clickText(chatName, true);
		assertTrue("Clickt the current chat failed.", waitForTextExists(Constant.TXT_CONFIRM, 3000));

		printLog("step8=Click the confirm");
		clickText(Constant.TXT_CONFIRM, true);
		waitForWindowUpdate(3000);

		clickXpath(Constant.XPATH_BACK);
		clickXpath(Constant.XPATH_BACK);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 3000));

		printLog("step10=click the dialog to check share");
		assertTrue("Relay the msg failed.", waitForTextExists(Constant.TXT_DOCUMENT, 3000));
	}
	
	@Test
	public void testFunction_More_MyDocument_DocumentDelete() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_MY_DOCUMENT);
		clickText(Constant.TXT_MORE_MY_DOCUMENT, true);
		assertTrue("Enter My Document failed.",waitForTextExists(Constant.TXT_MORE_MY_DOCUMENT, 3000));

		printLog("step4=Select one document");
		String file = getElementByXpath(Constant.XPATH_SELECT_LIST_FIRST).getText();
		while (waitForTextExists(file, 3000)) {
			swipeByText(file);
			assertTrue("Long click the document failed.", waitForTextExists(Constant.TXT_DELETE, 3000));

			printLog("Step5=Delete the document and verify");
			clickText(Constant.TXT_DELETE, true);
		}
		assertTrue("Delete the document failed.", waitForTextGone(file, 5000));
	}

	@Test
	public void testFunction_More_MyFavorite_Favorite() {
		String chatName = createPrivateChatBySelectContactInContacts(phoneNum, password);
		
		printLog("step2=Click " + Constant.TXT_TOOLBAR_MSG);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step3=Click The private chat");
		clickText(chatName, true);
		assertTrue("Enter Private chat failed.", waitForNameExists(Constant.NAME_CHAT_MENU_TOOL, 3000));

		printLog("step4=Long click the msg");
		longClickElementBySwipe(getLastMsgInChat());
		assertTrue("Long click the msg failed.", waitForTextExists(Constant.TXT_SELECT_DIALOG_LIST_FAVORITE, 3000));

		printLog("step5=Select " + Constant.TXT_SELECT_DIALOG_LIST_FAVORITE);
		clickText(Constant.TXT_SELECT_DIALOG_LIST_FAVORITE, true);

		printLog("step6=Click back");
		clickXpath(Constant.XPATH_BACK);
		assertTrue("Back to Msg failed.", waitForXpathExists(Constant.XPATH_MSG_LIST, 5000));

		printLog("step7=Click " + Constant.TXT_TOOLBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click the More failed.", waitForTextExists(Constant.TXT_MORE_MY_FAVORITE, 3000));

		printLog("step8=Click " + Constant.TXT_MORE_MY_FAVORITE);
		clickText(Constant.TXT_MORE_MY_FAVORITE, true);
		assertTrue("Msg favorite failed.", waitForTextExists(Constant.TXT_PRIVATE_MSG, 3000));
	}

	@Test
	public void testFunction_More_MyFavorite_ExportToDocument() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_MY_FAVORITE);
		clickText(Constant.TXT_MORE_MY_FAVORITE, true);
		assertTrue("Enter My Favorite failed.",waitForTextExists(Constant.TXT_MORE_MY_FAVORITE, 3000));

		printLog("step4=Click Settings btn");
		clickText(Constant.NAME_GROUP_SETTING_BTN, true);
		clickText(Constant.TXT_EXPORT_TO_FILE, true);
		assertTrue("Click the export failed.", waitForNameExists(Constant.NAME_CHECKBOX, 3000));

		printLog("step5=Select some msg");
		clickName(Constant.NAME_CHECKBOX);

		printLog("step6=Click " + Constant.TXT_CONFIRM);
		clickText(Constant.TXT_CONFIRM, true);
		assertTrue("Enter edit file name", waitForTextExists(Constant.TXT_EDIT_FILE_NAME, 3000));

		printLog("step7=Enter file name and save");
		clickXpath(Constant.XPATH_TF_FILE_NAME);
		String fileName = "favorite";
		enterTextByXpath(Constant.XPATH_TF_FILE_NAME, fileName);
		clickText(Constant.TXT_CONFIRM, true);
		assertTrue("Enter to My Document failed.", waitForTextExists(Constant.TXT_EXPORT_SUCESSFUL, 5000));
		
		clickText(Constant.TXT_I_KNOW, true);
		assertTrue("Export favorite to document failed.", waitForTextExists(fileName, 5000));
	}

	@Test
	public void testFunction_More_MyFavorite_LongClick_CancelFavorite() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_MY_FAVORITE);
		clickText(Constant.TXT_MORE_MY_FAVORITE, true);
		assertTrue("Enter My Favorite failed.",waitForTextExists(Constant.TXT_MORE_MY_FAVORITE, 3000));

		printLog("step4=Long click the msg");
		String msg = getElementByXpath(Constant.XPATH_COLLECTION_FIRST_TIME).getText();
		longClickElementBySwipe(getElementByXpath(Constant.XPATH_COLLECTION_FIRST_MSG));
		assertTrue("The UI Element not exist: " + Constant.TXT_COPY, waitForTextExists(Constant.TXT_COPY, 2000));
		assertTrue("The UI Element not exist: " + Constant.TXT_RELAY, waitForTextExists(Constant.TXT_RELAY, 2000));
		assertTrue("The UI Element not exist: " + Constant.TXT_CANCEL_FAVORITE,
				waitForTextExists(Constant.TXT_CANCEL_FAVORITE, 2000));

		printLog("step5=Click " + Constant.TXT_CANCEL_FAVORITE);
		clickText(Constant.TXT_CANCEL_FAVORITE, true);
		assertTrue("Cancel favorite msg failed.", waitForTextGone(msg, 3000));
	}
	
	@Test
	public void testFunction_More_Settings_SoundNotify_OpenOrClose() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_SETTINGS);
		clickText(Constant.TXT_MORE_SETTINGS, true);
		assertTrue("Enter Settings failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS_LOGOUT, 3000));

		printLog("step4=Click the button to open sound notify");
		String value = getElementByXpath(Constant.XPATH_SETTING_SOUND).getAttribute("value");
		if (value.equals("0")) {
			clickXpath(Constant.XPATH_SETTING_SOUND);
			sleep(1000);
			value = getElementByXpath(Constant.XPATH_SETTING_SOUND).getAttribute("value");
			assertTrue("Open sound notify failed.", value.equals("1"));
		} else {
			clickXpath(Constant.XPATH_SETTING_SOUND);
			sleep(1000);
			value = getElementByXpath(Constant.XPATH_SETTING_SOUND).getAttribute("value");
			assertTrue("Close sound notify failed.", value.equals("0"));
		}
	}

	@Test
	public void testFunction_More_Settings_HandSet_OpenOrClose() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_SETTINGS);
		clickText(Constant.TXT_MORE_SETTINGS, true);
		assertTrue("Enter Settings failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS_LOGOUT, 3000));

		printLog("step4=Click the button to open HandSet");
		String value = getElementByXpath(Constant.XPATH_SETTING_HANDSET).getAttribute("value");
		if (value.equals("0")) {
			clickXpath(Constant.XPATH_SETTING_HANDSET);
			sleep(1000);
			value = getElementByXpath(Constant.XPATH_SETTING_HANDSET).getAttribute("value");
			assertTrue("Open HandSet failed.", value.equals("1"));
		} else {
			clickXpath(Constant.XPATH_SETTING_HANDSET);
			sleep(1000);
			value = getElementByXpath(Constant.XPATH_SETTING_HANDSET).getAttribute("value");
			assertTrue("Close HandSet failed.", value.equals("0"));
		}
	}

	@Test
	public void testFunction_More_Settings_ShakeNotify_OpenOrClose() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_SETTINGS);
		clickText(Constant.TXT_MORE_SETTINGS, true);
		assertTrue("Enter Settings failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS_LOGOUT, 3000));

		printLog("step4=Click the button to open Shake notify");
		String value = getElementByXpath(Constant.XPATH_SETTING_SHAKE).getAttribute("value");
		if (value.equals("0")) {
			clickXpath(Constant.XPATH_SETTING_SHAKE);
			sleep(1000);
			value = getElementByXpath(Constant.XPATH_SETTING_SHAKE).getAttribute("value");
			assertTrue("Open Shake notify failed.", value.equals("1"));
		} else {
			clickXpath(Constant.XPATH_SETTING_SHAKE);
			sleep(1000);
			value = getElementByXpath(Constant.XPATH_SETTING_SHAKE).getAttribute("value");
			assertTrue("Close Shake notify failed.", value.equals("0"));
		}
	}
	
	@Test
	public void testFunction_More_Settings_ScreenLock_Default() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_SETTINGS);
		clickText(Constant.TXT_MORE_SETTINGS, true);
		assertTrue("Enter Settings failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS_LOGOUT, 3000));

		printLog("step4=Click the button to open screen lock");
		String value = getElementByXpath(Constant.XPATH_SETTING_SCREEN_LOCK).getAttribute("value");
		if (value.equals("0")) {
			clickXpath(Constant.XPATH_SETTING_SCREEN_LOCK);
			assertTrue("Click sreen lock failed.", waitForTextExists(Constant.TXT_SET_SCREEN_LOCK, 3000));
		}
	}

	@Test
	public void testFunction_More_Settings_ChangePassword_Enter() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_SETTINGS);
		clickText(Constant.TXT_MORE_SETTINGS, true);
		assertTrue("Enter Settings failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS_LOGOUT, 3000));

		printLog("step4=Click the button to open modify password");
		clickName(Constant.TXT_MORE_SETTINGS_PASSWORD);
		assertTrue("Enter Modify password failed.", waitForTextExists(Constant.TXT_CURRENT_PASSWORD, 3000));
	}

	@Test
	public void testFunction_More_Settings_ChangePassword_Element() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_SETTINGS);
		clickText(Constant.TXT_MORE_SETTINGS, true);
		assertTrue("Enter Settings failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS_LOGOUT, 3000));

		printLog("step4=Click the button to open modify password");
		clickName(Constant.TXT_MORE_SETTINGS_PASSWORD);
		assertTrue("Enter Modify password failed.", waitForTextExists(Constant.TXT_CURRENT_PASSWORD, 3000));

		printLog("step5=Verify the UI Element");
		assertTrue("Tips not exist", waitForTextExists(Constant.TXT_PASSWORD_TIPS, 3000));
		assertTrue("Current psd not exist", waitForXpathExists(Constant.XPATH_CURRENT_PSD, 3000));
		assertTrue("New psd not exist", waitForXpathExists(Constant.XPATH_NEW_PSD, 3000));
		assertTrue("Re-new psd not exist", waitForXpathExists(Constant.XPATH_RE_NEW_PSD, 3000));
	}

	@Test
	public void testFunction_More_Settings_ChangePassword_WrongOldPassword() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_SETTINGS);
		clickText(Constant.TXT_MORE_SETTINGS, true);
		assertTrue("Enter Settings failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS_LOGOUT, 3000));

		printLog("step4=Click the button to open modify password");
		clickName(Constant.TXT_MORE_SETTINGS_PASSWORD);
		assertTrue("Enter Modify password failed.", waitForTextExists(Constant.TXT_CURRENT_PASSWORD, 3000));

		printLog("step5=Change password and enter wrong old password");
		String wrong = "111111";
		String newPwd = "111aaa";
		clickXpath(Constant.XPATH_CURRENT_PSD);
		enterTextByXpath(Constant.XPATH_CURRENT_PSD, wrong);
		clickXpath(Constant.XPATH_NEW_PSD);
		enterTextByXpath(Constant.XPATH_NEW_PSD, newPwd);
		clickXpath(Constant.XPATH_RE_NEW_PSD);
		enterTextByXpath(Constant.XPATH_RE_NEW_PSD, newPwd);
		clickText(Constant.TXT_CONFIRM, true);
		assertTrue("Wrong old password tips not exist.", waitForTextExists(Constant.TXT_WRONG_PSD_TIPS, 5000));
	}

	@Test
	public void testFunction_More_Settings_ChangeNumber_Enter() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_SETTINGS);
		clickText(Constant.TXT_MORE_SETTINGS, true);
		assertTrue("Enter Settings failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS_LOGOUT, 3000));

		printLog("step4=Click the change number button");
		clickText(Constant.TXT_MORE_SETTINGS_NUMBER, true);
		assertTrue("The UI Element not exist: " + Constant.TXT_NEXT, waitForTextExists(Constant.TXT_NEXT, 5000));

		printLog("step6=Verify the Change number page Element");
		assertTrue("The UI Element not exist: " + Constant.TXT_CHANGE_NUMBER_INTRO,
				waitForTextExists(Constant.TXT_CHANGE_NUMBER_INTRO, 5000));
	}
	
	@Test
	public void testFunction_More_Settings_LogOut() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_SETTINGS);
		clickText(Constant.TXT_MORE_SETTINGS, true);
		assertTrue("Enter Settings failed.", waitForTextExists(Constant.TXT_LOGOUT, 3000));

		printLog("step4=Click Logout");
		clickText(Constant.TXT_LOGOUT, true);
		assertTrue("Logout not exist", waitForTextExists(Constant.TXT_LOGOUT, 3000));
		assertTrue("Cancel not exist", waitForTextExists(Constant.TXT_CANCEL, 3000));

		List<WebElement> list = getElementsByName(Constant.TXT_LOGOUT);
		clickCenterPoint(list.get(list.size() - 1));
		assertTrue("Logout failed.", waitForNameExists(Constant.TXT_NEXT_STEP, 3000));
	}
	
	@Test
	public void testFunction_More_Help_Share_NewChat() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_HELP);
		clickText(Constant.TXT_MORE_HELP, true);
		if (waitForTextExists(Constant.TXT_NO, 3000)) {
			clickText(Constant.TXT_NO, true);
		}
		assertTrue("Enter Help failed.", waitForTextExists(Constant.TXT_MORE_HELP_TITLE, 3000));

		printLog("step4=Click the Share icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the settings btn failed.", waitForTextExists(Constant.TXT_SHARE_TO_GROUP, 3000));
		
		clickName(Constant.TXT_SHARE_TO_GROUP);
		assertTrue("Click the relay to enter option failed.", waitForTextExists(Constant.TXT_RELAY_NEW_CHAT, 3000));

		printLog("step5=Click " + Constant.TXT_RELAY_NEW_CHAT);
		clickText(Constant.TXT_RELAY_NEW_CHAT, true);
		assertTrue("Click the relay new chat failed.", waitForTextExists(Constant.TXT_SELECT_BY_CONTACTS, 3000));

		printLog("step6=Click confirm button");
		clickText(Constant.TXT_MY_NAME, true);
		clickXpath(Constant.XPATH_NAVBAR_CONFIRM);
		assertTrue("Select contacts failed.", waitForTextExists(Constant.TXT_CONFIRM, 3000));
		
		clickName(Constant.TXT_CONFIRM);

		printLog("step8=Back to msg list to check result of share");
		clickXpath(Constant.XPATH_BACK);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 3000));

		assertTrue("The ui element not exist: " + Constant.TXT_LINK,
				waitForTextExists(Constant.TXT_LINK, 3000));
	}

	@Test
	public void testFunction_More_Help_Share_RecentChat() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Click " + Constant.TXT_TABBAR_MORE);
		clickXpath(Constant.XPATH_TABBAR_MORE);
		assertTrue("Click More failed.", waitForTextExists(Constant.TXT_MORE_SETTINGS, 3000));

		printLog("step3=click " + Constant.TXT_MORE_HELP);
		clickText(Constant.TXT_MORE_HELP, true);
		if (waitForTextExists(Constant.TXT_NO, 3000)) {
			clickText(Constant.TXT_NO, true);
		}
		assertTrue("Enter Help failed.", waitForTextExists(Constant.TXT_MORE_HELP_TITLE, 3000));

		printLog("step4=Click the Share icon");
		clickName(Constant.NAME_GROUP_SETTING_BTN);
		assertTrue("Click the settings btn failed.", waitForTextExists(Constant.TXT_SHARE_TO_GROUP, 3000));
		
		clickName(Constant.TXT_SHARE_TO_GROUP);
		assertTrue("Click the relay to enter option failed.", waitForTextExists(Constant.TXT_RELAY_NEW_CHAT, 3000));

		printLog("step5=Click " + Constant.TXT_MY_NAME);
		clickText(Constant.TXT_MY_NAME, true);
		assertTrue("Select contacts failed.", waitForTextExists(Constant.TXT_CONFIRM, 3000));
		
		clickName(Constant.TXT_CONFIRM);

		printLog("step6=Back to msg list to check result of share");
		clickXpath(Constant.XPATH_BACK);
		clickXpath(Constant.XPATH_TABBAR_MSG);
		assertTrue("Msg list not exist", waitForXpathExists(Constant.XPATH_MSG_LIST, 3000));

		assertTrue("The ui element not exist: " + Constant.TXT_LINK,
				waitForTextExists(Constant.TXT_LINK, 3000));
	}

	@Test
	public void testFunction_LaunchApp_LoginAgain() {
		startLanxinApp(phoneNum, password);

		printLog("step2=Back to home shut down app");
		driver.closeApp();

		printLog("step3=Launch app again");
		driver.launchApp();
		startLanxinApp(phoneNum, password);
	}
}

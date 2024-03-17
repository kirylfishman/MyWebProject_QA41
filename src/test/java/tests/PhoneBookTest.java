package tests;

import config.BaseTest;
import helpers.*;
import io.qameta.allure.Allure;
import jdk.jfr.Description;
import model.Contact;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.LoginPage;
import pages.MainPage;

import java.io.IOException;

public class PhoneBookTest extends BaseTest {

    @Test(description = "The test checks the empty field warning declaration.")
    @Parameters("browser")
    public void registrationWithoutPassword(@Optional("chrome") String browser) throws InterruptedException {
        Allure.description("User already exist. Login and add contact.!");

        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Click by Login button");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Click by Reg button");
        String expectedString = "Wrong";

        Alert alert= loginPage.fillEmailField("myemail@mail.com").clickByRegistartionBUtton();
        boolean isAlertHandled = AlertHandler.handleAlert(alert, expectedString);
        Assert.assertTrue(isAlertHandled);

    }
    @Test
    @Description("User already exist. Login and add contact.")
    public void loginOfAnExistingUserAddContact() throws InterruptedException {
        Allure.description("User already exist. Login and add contact.!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        Allure.step("Step 3");
        MainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());
        Contact newContact = new Contact(NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(10,5,3),
                AddressGenerator.generateAddress(),
                "new description");
        newContact.toString();
        addPage.fillFormAndSave(newContact);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.getDataFromContactList(newContact));
        TakeScreen.takeScreenshot("screen");
        Thread.sleep(3000);

    }
    @Test
    @Description("Successful Registration")
    public void successfulRegistration(){
        Allure.description("Successful Registration test.");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Open LOGIN menu");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        lpage.fillEmailField(EmailGenerator.generateEmail(5,5,3))
                .fillPasswordField(PasswordStringGenerator.generateString());
        Alert alert =  lpage.clickByRegistartionBUtton();
        if (alert==null){
            ContactsPage contactsPage = new ContactsPage(getDriver());
            Assert.assertTrue( contactsPage. isElementPersist(getDriver()
                    .findElement(By.xpath("//button[contains(text(),'Sign Out')]"))));
        }else {
            TakeScreen.takeScreenshot("Successful Registration");}
    }
    @Test
    public void deleteContact() throws InterruptedException {
        Allure.description("User already exist. Delete contact by phone number!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertNotEquals(contactsPage.deleteContactByPhoneNumberOrName("2101225254138"),
                contactsPage.getContactsListSize(),"Contact lists are different");

    }

    @Test
    public void deleteContactApproachTwo() throws IOException {
        String filename = "contactDataFile.ser";
        MainPage mainPage = new MainPage(getDriver());
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        MainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());
        Contact newContact = new Contact(NameAndLastNameGenerator.generateName(),NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(10,5,3),
                AddressGenerator.generateAddress(), "Test description");
        addPage.fillFormAndSave(newContact);
        Contact.serializeContact(newContact, filename);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Contact deserContact = Contact.desiarializeContact(filename);
        Assert.assertNotEquals(contactsPage.deleteContactByPhoneNumberOrName(deserContact.getPhone()),
                contactsPage.getContactsListSize());

    }

@Test
    public void  registrationOfAnAlreadyRegisteredUser(){
  Allure.description("User is already exist");
    MainPage mainPage = new MainPage(getDriver());
    Allure.step("Step 1: User navigate on Login page");
    LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
    Allure.step("Step 2: fill all fields for registration in existing data");
    Allure.step("Step 3: the user cannot register again with the same data");
    String expectedString = "User already exist";
    Alert alert= lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
            .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
            .clickByRegistartionBUtton();
    boolean isAlertHandled = AlertHandler.handleAlert(alert, expectedString);
    Assert.assertTrue(isAlertHandled);
}

}
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
    public void successfulRegistration (){
        MainPage mainPage = new MainPage(getDriver());
        Allure.description("User entering valid data for Registration");
        MainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.fillEmailField(EmailGenerator.generateEmail(4,4,4));
        loginPage.fillPasswordField(PasswordStringGenerator.generateString());
        loginPage.clickByRegistartionBUtton();
Assert.assertTrue(mainPage.isTextEqual(By.xpath("//a[@class='active']"),"CONTACTS"));
    }
    @Test
    @Description("Creating a new contact.Deleting this contact and checking that it is no longer in the contact list")
    public void deletingCreatedContact() throws InterruptedException {
       MainPage mainPage = new MainPage(getDriver());
       MainPage.openTopMenu(TopMenuItem.LOGIN.toString());
       LoginPage loginPage = new LoginPage(getDriver());
       loginPage.fillEmailField(EmailGenerator.generateEmail(4,4,4));
       loginPage.fillPasswordField(PasswordStringGenerator.generateString());
       loginPage.clickByRegistartionBUtton();
       Assert.assertTrue(mainPage.isTextEqual(By.xpath("//a[@class='active']"),"CONTACTS"));
Thread.sleep(2000);
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
        MainPage.openTopMenu(TopMenuItem.CONTACTS.toString());
 contactsPage = new ContactsPage(getDriver());
Thread.sleep(2000);
contactsPage.removeContact(newContact);
Thread.sleep(2000);
        Assert.assertTrue(contactsPage.isTextEqual(By.xpath("//h1[normalize-space()='No Contacts here!']"),"No Contacts here!"));


//No Contacts here!
//h1[normalize-space()='No Contacts here!']

    }
}


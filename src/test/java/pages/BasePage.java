package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// BasePage служит базовым классом для всех страниц тестового приложения
public class BasePage {

    protected static WebDriver driver;
    public static void setDriver(WebDriver webDriver){ // Метод устанавливает значение поля driver в переданный экземпляр веб-драйвера.
        driver=webDriver; //Эта строка присваивает переданный экземпляр веб-драйвера переменной driver, что позволяет другим классам иметь доступ к этому веб-драйверу через метод getDriver()
    }
    
    public String getTextBase(By locator){
        WebElement element = findElementBase(locator);
        return element.getText().trim().toUpperCase();
    }

    private WebElement findElementBase(By locator) {
        return driver.findElement(locator);
    }

    public boolean isTextEqual(By locator, String expectedResult){
        String actualResult = getTextBase(locator);
        expectedResult = expectedResult.toUpperCase();

        if (expectedResult.equals(actualResult)){
            return true;
        } else{
            System.out.println("expected result: " + expectedResult + "actual result: " + actualResult);
            return false;
        }
    }
}
package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WindowAndTabTests {
    public static void main(String[] args) throws InterruptedException {
       // switchTab();
        sliderTest();
    }
@Test
    public static void sliderTest() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        driver .get("https://demoqa.com/slider");
        driver.manage().window().maximize();
    WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
    Actions actions = new Actions(driver) ;
    actions.dragAndDropBy(slider, 30,0).build().perform();
    Thread.sleep(3000);

//        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
//        slider.sendKeys(Keys.ARROW_RIGHT);Thread.sleep(1000);
//        slider.sendKeys(Keys.ARROW_RIGHT);Thread.sleep(1000);
//        slider.sendKeys(Keys.ARROW_RIGHT);Thread.sleep(1000);
        driver.quit();
    }
    @Test
    public static void dragAndDropTest() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        driver .get("https://demoqa.com/drag_and_drop");
        driver.manage().window().maximize();
        WebElement elementToDrop = driver.findElement(By.xpath("//div[@id= 'column-a']"));
        WebElement targetElement = driver.findElement(By.xpath("//div[@id= 'column-b']"));

        Actions actions = new Actions(driver);
        actions.dragAndDrop(elementToDrop,targetElement).build().perform();
        Thread.sleep(5000);
        driver.quit();

    }
    public static void switchTab(){
        WebDriver driver = new FirefoxDriver();
        driver .get("https://demoqa.com/browser-windows");
        driver.manage().window().maximize();
        String mainWindowHandler = driver.getWindowHandle();
        WebElement newButton = driver.findElement(By.xpath("//button[@id='tabButton']"));
        newButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> allWindowHandles = driver.getWindowHandles();
        String newWindowHandler = "";
        for (String windowHandle: allWindowHandles){
            if (!windowHandle.equals(mainWindowHandler)){
                newWindowHandler = windowHandle;
                break;

            }
        }
        driver.switchTo().window(newWindowHandler);
        WebElement newPageElement = driver.findElement(By.xpath("//h1"));
        newPageElement.click();
        driver.quit();
    }

    @Test

    public static void waitForAnElement(){
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        WebElement buttonStart = driver.findElement(By.xpath("//button"));
        buttonStart.click();



        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement testElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id= ''finish]")));
        testElement.click();
        driver.quit();
    }

    @Test
    public static String findRowByValue(String valueToFInd){

        WebDriver driver = new FirefoxDriver();

        try {
            driver.manage().window().maximize();
            driver.get("http://the-internet.herokuapp.com/tables");
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
            WebElement tableElement = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.tagName("table")));
            List<WebElement> rows = tableElement.findElements (By.tagName("tr"));
            for (WebElement row: rows){
                List <WebElement> cells = row.findElements(By.tagName("td"));
                for (WebElement cell : cells){
                    if(cell.getText().equals(valueToFInd)){
                        return row.getText();

                    }
                }
            }return null;
        }finally {
            driver.quit();
        }
    }


    @Test
    public static String findRowByValueLambda(String valueToFInd){

        WebDriver driver = new FirefoxDriver();

        try {
            driver.manage().window().maximize();
            driver.get("http://the-internet.herokuapp.com/tables");
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
            WebElement tableElement = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.tagName("table")));
            List<WebElement> rows = tableElement.findElements (By.tagName("tr"));
            Optional <WebElement> optionalRow = rows.stream().filter(row->row
                    .findElements(By.tagName("td"))
                    .stream().allMatch(cell->cell.getText().equals(valueToFInd))).findFirst();
            return optionalRow.map(WebElement::getText).orElse(null);
        }finally {
            driver.quit();
        }
    }
@Test
    public void rightMouseClick() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://the-internet.herokuapp.com/");
        WebElement element = driver.findElement(By.xpath("//a[contains(text(), 'Testing')]"));
        Actions actions = new Actions(driver);
        actions.contextClick(element).perform();

        Thread.sleep(3000);
        driver.quit();
    }
}

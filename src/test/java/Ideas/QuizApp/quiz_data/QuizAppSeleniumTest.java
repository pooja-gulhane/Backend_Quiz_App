package Ideas.QuizApp.quiz_data;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class QuizAppSeleniumTest {

    private WebDriver driver;
    private String authToken;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/driver/chromedriver.exe");

        driver = new ChromeDriver();

        driver.get("http://localhost:4200/signin");
    }

    @Test
    public void testLogin() throws InterruptedException {
        driver.findElement(By.id("email")).sendKeys("jack@gmail.com");

        driver.findElement(By.id("password")).sendKeys("Jack@12345");

        driver.findElement(By.id("signin")).click();

        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("http://localhost:4200/explore-quiz"));

        String expectedUrl = "http://localhost:4200/explore-quiz";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);
    }



    @Test
    public void startQuiz() throws InterruptedException {
        testLogin();
        driver.get("http://localhost:4200/quiz/rules/53");
        System.out.println(driver.getCurrentUrl());

        driver.findElement(By.id("agree")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for option 1 to be visible before clicking
        WebElement option1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("option1")));
        option1.click();

        Thread.sleep(2000);
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("next")));
        nextButton.click();

        Thread.sleep(2000);
        WebElement option2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("option2")));
        option2.click();
        Thread.sleep(2000);
        nextButton.click();

        Thread.sleep(2000);
        option2.click();
        Thread.sleep(2000);
        nextButton.click();

        Thread.sleep(2000);
        option2.click();
        Thread.sleep(2000);
        nextButton.click();


        WebElement previousButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("previous")));
        previousButton.click();

        option2.click();

        Thread.sleep(2000);
        WebElement quizSubmit = wait.until(ExpectedConditions.elementToBeClickable(By.id("quizSubmit")));
        quizSubmit.click();

        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        confirmButton.click();

        // Wait for the URL to change to the score page
        wait.until(ExpectedConditions.urlToBe("http://localhost:4200/score/53"));

        // Verify the redirection to the score page
        String expectedUrl = "http://localhost:4200/score/53";
        String actualUrl = driver.getCurrentUrl();
        System.out.println("Redirected URL: " + actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl);
    }

    @Test
    public void quizHistoryTest() throws InterruptedException
    {
        startQuiz();
        driver.get("http://localhost:4200/quiz-history");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Thread.sleep(2000);
        // Wait for the "Submit Quiz" button to be clickable
        WebElement quizHistory = wait.until(ExpectedConditions.elementToBeClickable(By.id("quizHistory")));
        quizHistory.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:4200/user-response-history/52/602"));
        Thread.sleep(2000);

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("scroll(0, 280)");

        Thread.sleep(2000);
        // Verify the redirection to the score page
        String expectedUrl = "http://localhost:4200/user-response-history/52/602";
        String actualUrl = driver.getCurrentUrl();
        System.out.println("Redirected URL: " + actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl);
    }


    @AfterMethod
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }

}


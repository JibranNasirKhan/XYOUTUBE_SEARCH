package demo;

import java.util.logging.Level;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import demo.utils.ExcelDataProvider;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;
        //    private ChromeDriver driver;
        //  private WebDriver driver;
         private WebDriverWait wait;
         private Wrappers wrapperMethod;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();
                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");
                driver = new ChromeDriver(options);
                driver.manage().window().maximize();
                wrapperMethod = new Wrappers(driver);
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();
        }

        @Test
        public void testCase01(){
                System.out.println("Start testCase01 :Go to Youtube.com assert URL. print About message on the screen");
                String URL = "https://www.youtube.com/";
                wrapperMethod.navigateTOURL(URL);
                System.out.println("End of testCase01(navigateTOURL)");
        }
        //System.out.println("testCase02: Go to the 'Films' tab and in the 'Top Selling' section, scroll to the extreme right. Apply a Soft Assert on whether the movie is marked 'A' for Mature or not. Apply a Soft assert on whether the movie is either 'Comedy' or 'Animation'.");
        @Test
        public void testCase02() throws InterruptedException{
                System.out.println("testCase02: Go to the 'Films' tab and in the 'Top Selling' section, scroll to the extreme right. Apply a Soft Assert on whether the movie is marked 'A' for Mature or not. Apply a Soft assert on whether the movie is either 'Comedy' or 'Animation'.");
                String URL = "https://www.youtube.com/";
                wrapperMethod.TopSellingSection(URL);
                System.out.println();
                System.out.println("End of testCase01(TopSellingSection)");
        }
        @Test
        public void testCase03() throws InterruptedException{
                System.out.println("Go to the Music tab and in the 1st section, scroll to the extreme right. Print the name of the playlist. Soft Assert on whether the number of tracks listed is less than or equal to 50.");
                String URL = "https://www.youtube.com/";
                wrapperMethod.printTheLastPlaylist(URL);
                System.out.println("End of testCase03('Printing the last playlist')");
        }
        @Test
        public void testCase04() throws InterruptedException{
                System.out.println("testCase04: Go to the \"News\" tab and print the title and body of the 1st 3 “Latest News Posts” along with the sum of the number of likes on all 3 of them. No likes given means 0.");
                String URL = "https://www.youtube.com/";
                wrapperMethod.printtilteofTheLatestNewsPost(URL);
                System.out.println("End of testCase03('printtilteofTheLatestNewsPost')");
        }
        @Test
        public void testCase05() throws InterruptedException, java.io.IOException{
                System.out.println("testCase05: Search for each of the items given in the stubs: src/test/resources/data.xlsx, and keep scrolling till the sum of each video’s views reach 10 Cr. ");
                String URL = "https://www.youtube.com";
                
                wrapperMethod.searchUntilViewsAre10CR(URL);
                
                System.out.println("End of testCase05('searchUntilViewsAre10CR')");
        }
}
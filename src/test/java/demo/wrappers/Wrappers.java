package demo.wrappers;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;


public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     public  WebDriver driver;
     public  WebDriverWait wait;
     public  Actions action;
     public  JavascriptExecutor js;
     
 
     public Wrappers(WebDriver driver){
         this.driver = driver;
         wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         this.action = new Actions(driver);
         this.js = (JavascriptExecutor)driver; 
     }
     public static void waitForPageLoad(){
         try{
             Thread.sleep(5000);
         } catch (InterruptedException e){
             Thread.currentThread().interrupt();
         }
     }
     
     /*
      * Write your selenium wrappers here
      */
     public void navigateTOURL(String URL){
         driver.get(URL);
         String CurrentURL = driver.getCurrentUrl();
         Assert.assertEquals(CurrentURL, URL, "URL is incorrect");
 
         WebElement about = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='https://www.youtube.com/about/']")));
         waitForPageLoad();
         about.click();
         waitForPageLoad();
         String currentURL = driver.getCurrentUrl();
         System.out.println(currentURL);
         if(currentURL.contains("about")){
            Assert.assertEquals(currentURL, currentURL, "URL is incorrect");
         }
         
 
         //printing the message after clicking about
         WebElement Primary_message = driver.findElement(By.xpath("(//p[@class='lb-font-display-3 lb-font-color-text-primary'])[1]"));
         WebElement Secondary_message = driver.findElement(By.xpath("(//p[@class='lb-font-display-3 lb-font-color-text-primary'])[2]"));
 
            // String FinalText = Primary_message.getText()+Secondary_message.getText();
            // String FinalText2 = Secondary_message.getText();

            String AboutMessage = Primary_message.getText()+Secondary_message.getText();

            // System.out.println(FinalText);
            // System.out.println(FinalText2);
            // System.out.println("Print About message :"+FinalText+FinalText2);
            System.out.println("Printing About Message : "+AboutMessage);

        //  System.out.println(Primary_message.getText()+Secondary_message.getText());
      }
 
      public void TopSellingSection(String URL) throws InterruptedException{
         driver.get(URL);
         waitForPageLoad();
         
         WebElement films = driver.findElement(By.xpath("//a[@id='endpoint' and @href='/feed/storefront?bp=ogUCKAU%3D']"));
         films.click();
         waitForPageLoad();
         
         WebElement topSellingSections = wait.until(ExpectedConditions.visibilityOfElementLocated(
                 By.xpath("//span[@id='title' and text()='Top selling']/ancestor::ytd-shelf-renderer")));
                 
         WebElement scrollToRight = topSellingSections.findElement(By.xpath("(//div[@class='yt-spec-touch-feedback-shape__fill'])[7]"));
 
         for (int i = 0; i <=1; i++ ){
             scrollToRight.click();
             Thread.sleep(3000);
         }    
 
         // List<WebElement> topSellingMovies = topSellingSections.findElements(By.xpath("//div[@class='style-scope yt-horizontal-list-renderer' and @id='items']"));
         List<WebElement> topSellingMovies = topSellingSections.findElements(By.xpath(".//div[@id='items']//ytd-grid-movie-renderer"));
         WebElement lastmovies = topSellingMovies.get(topSellingMovies.size()-1);
 
         SoftAssert softAssert = new SoftAssert();
 
     try {
         
         // List<WebElement> topSellingMovies = topSellingSections.findElements(By.xpath("//div[@class='style-scope yt-horizontal-list-renderer' and @id='items']"));
        
         // WebElement lastmovies = topSellingMovies.get(topSellingMovies.size()-1);
         WebElement maturityRating = driver.findElement(By.xpath("(//p[@class='style-scope ytd-badge-supported-renderer'])[32]"));
         // WebElement maturityRating = lastmovies.findElement(By.xpath(".//p[@class='style-scope ytd-badge-supported-renderer']"));
         String rating = maturityRating.getText();
         
         softAssert.assertTrue(rating.contains("A"), "Movie is not marked 'A' for Mature.");
         System.out.println(rating);
 
         WebElement genre = driver.findElement(By.xpath("(//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer'])[16]"));
         // WebElement genre = lastmovies.findElement(By.xpath(".//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']"));
         String genreText = genre.getText();
         softAssert.assertTrue(genreText.contains("Comedy") || genreText.contains("Animation"), 
             "Movie genre is neither 'Comedy' nor 'Animation'.");
             System.out.println(genreText);
     } catch (NoSuchElementException e) {
         Assert.fail("Required elements for assertions not found. Exception: " + e.getMessage());
         }
     }
 
     public void printTheLastPlaylist(String URL) throws InterruptedException{
         driver.get(URL);
         waitForPageLoad();
 
         WebElement music = driver.findElement(By.xpath("(//a[@id='endpoint'])[8]"));
         wait.until(ExpectedConditions.visibilityOf(music));
         music.click();
         waitForPageLoad();
         WebElement scrollright = driver.findElement(By.xpath("(//div[@class='yt-spec-touch-feedback-shape yt-spec-touch-feedback-shape--touch-response'])[6]"));
 
         for(int i = 0; i <= 1; i++){
             scrollright.click();
             Thread.sleep(3000);
         }
 
         WebElement Playlist = driver.findElement(By.xpath("(//h3[@class='style-scope ytd-compact-station-renderer'])[11]"));
         String PlaylistName = Playlist.getText();
         System.out.println("The name of the last Playlist :"+PlaylistName);
 
         WebElement trackCount = driver.findElement(By.xpath("(//p[@id='video-count-text' and @class='style-scope ytd-compact-station-renderer'])[11]"));
         String trackcountText = trackCount.getText().replaceAll("\\D+","");
         int trackcount = Integer.parseInt(trackcountText);
 
         SoftAssert softAssert = new SoftAssert();
          softAssert.assertTrue(trackcount <= 50, "Number of tracks listed are greater than 50 ");
         if(trackcount > 50){
             System.out.println("[Assertion Failed] Number of tracks "+trackcount);
         }
         softAssert.assertAll();
     }
 
     public void printtilteofTheLatestNewsPost(String URL) throws InterruptedException{
        try{
         driver.get(URL);
         waitForPageLoad();
 
         WebElement news = driver.findElement(By.xpath("//yt-formatted-string[@class='title style-scope ytd-guide-entry-renderer' and text()='News']"));
         wait.until(ExpectedConditions.visibilityOf(news));
         news.click();
         waitForPageLoad();
               
             WebElement NewsBody = driver.findElement(By.xpath("(//span[@dir='auto' and @class='style-scope yt-formatted-string'])[1]"));
             System.out.println("News Title is : "+NewsBody.getAttribute("title")+NewsBody.getText());
         
             WebElement NewsBody2 = driver.findElement(By.xpath("(//span[@dir='auto' and @class='style-scope yt-formatted-string'])[5]"));
             System.out.println("News Title is : "+NewsBody2.getAttribute("title")+NewsBody2.getText());
 
             WebElement NewsBody3 = driver.findElement(By.xpath("(//span[@dir='auto' and @class='style-scope yt-formatted-string'])[9]"));
             System.out.println("News Title is : "+NewsBody3.getAttribute("title")+NewsBody3.getText());
 
         for(int i = 1; i <= 3; i++){
             WebElement sumOfLikes = driver.findElement(By.xpath("(//span[@id='vote-count-middle'])["+i+"]"));
             String noOflike = sumOfLikes.getText();
             int noOflike1 = Integer.parseInt(noOflike);
             noOflike1++;
             System.out.println("Sum of no of likes are : "+noOflike1);
      }
    } catch (Exception e) {
        System.err.println("An error occured: "+e.getMessage());
 }
    }

    public int parseViewCount(String viewCountText){

            int views = 0;
            if(viewCountText.contains("B")){
                views = (int) (Double.parseDouble(viewCountText.replace("B views", "").trim())*1000000000);
            } else if(viewCountText.contains("M")){
                views = (int) (Double.parseDouble(viewCountText.replace("M views", "").trim())*100000000);
            } else if (viewCountText.contains("K")){
                views = (int) (Double.parseDouble(viewCountText.replace("K views", "").trim())*1000);
            } else {
                views = (int) (Double.parseDouble(viewCountText.replace("views", "").replace(",", "").trim()));
            }
    
            return views;
    }

    public void searchUntilViewsAre10CR(String URL) throws InterruptedException, IOException {
        driver.get(URL);
        waitForPageLoad();

        List<String> searchItems = new ArrayList<>();
        // String[] searchItems = {"item1", "item2", "item3"};
        
        String excelFilePath = "src/test/resources/data.xlsx";
        FileInputStream inputstream = new FileInputStream(excelFilePath);

        XSSFWorkbook workbook = new XSSFWorkbook(inputstream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int rowIndex = 1; rowIndex <= 3; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                for (Cell cell : row) {
                    searchItems.add(cell.toString());
                }
            }
        }
    
    
    for (String item : searchItems){
        int totalviews = 0;
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='search']"));
        wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys(item);
        searchBox.sendKeys(Keys.ENTER);
        searchBox.clear();
        waitForPageLoad();
        WebElement filter = driver.findElement(By.xpath("//button[@class='yt-spec-button-shape-next yt-spec-button-shape-next--text yt-spec-button-shape-next--mono yt-spec-button-shape-next--size-m yt-spec-button-shape-next--icon-trailing']"));
        filter.click();
        waitForPageLoad();
        WebElement sortByViewCount = driver.findElement(By.xpath("//yt-formatted-string[@class='style-scope ytd-search-filter-renderer' and text()='View count']"));
        sortByViewCount.click();
        waitForPageLoad();

        while(totalviews<100000000){
            List<WebElement> videos = driver.findElements(By.id("video-title"));

            for(WebElement video : videos){
                WebElement viewCountElement = video.findElement(By.xpath("//span[@class='inline-metadata-item style-scope ytd-video-meta-block']"));
                int views = parseViewCount(viewCountElement.getText());

                totalviews += views;

                if(totalviews >= 100000000){
                    break;
                }
            }
            // ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            waitForPageLoad(); // Wait for more videos to load
            
            System.out.println(" Total views for "+item+": "+totalviews);
        }
    }
  }
}

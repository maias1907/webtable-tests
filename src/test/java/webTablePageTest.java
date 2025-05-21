
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.recordsTable.webTablePage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class webTablePageTest {

    private WebDriver driver;
    private webTablePage webPage;
    private static final String baseURL = "https://demoqa.com/webtables";

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        webPage = new webTablePage(driver);
        driver.manage().window().maximize();
        driver.get(baseURL);
    }



    @Test
    @Order(1)
    public void addNewRecordTest() {
        webPage.addNewRecord("Maias", "Omar", "25", "100", "maias9018@gmail.com", "Math");
        webPage.searchRecord("maias9018@gmail.com");
        assertTrue(webPage.isEmailPresent("maias9018@gmail.com"));
    }

    @Test
    @Order(2)
    public void editSalaryTest() {
        webPage.searchRecord("kierra@example.com");
        webPage.editSalary("kierra@example.com", "99999");
        String updatedSalary = webPage.getSalaryByEmail("kierra@example.com");
        assertEquals("99999", updatedSalary);
    }

    @Test
    @Order(3)
    public void deleteRecordTest() {
        webPage.deleteRecord("kierra@example.com");
        webPage.searchRecord("kierra@example.com");
        assertFalse(webPage.isEmailPresent("kierra@example.com"));
    }

    @Test
    @Order(4)
    public void searchFilterTest() {
        boolean res=webPage.searchRecord("Cierra");
        assertTrue(res);
    }
    @AfterEach
    public void teardown() {
        driver.quit();
    }
}


package UITests;

import org.example.Utils.DriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import org.recordsTableLocators.WebTablePage;


import static org.example.Utils.DriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebTablesSmokeTest {
    private WebDriver driver;
    private WebTablePage page;
    private static final String baseURL = "https://demoqa.com/webtables";


    @BeforeEach
    public void setUp() {
        driver= getDriver();
        page = new WebTablePage(driver);
        driver.manage().window().maximize();
        driver.get(baseURL);

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    public void testPageLoads() {
        assertTrue(driver.getTitle().contains("ToolsQA") || driver.getCurrentUrl().contains("webtables"));
    }

    @Test
    @Order(2)
    public void testAddNewRecordFormOpens() {
        assertDoesNotThrow(() -> page.addNewRecord("Test", "User", "29", "2900", "smoke@test.com", "Smoke"));
        assertTrue(page.isEmailPresent("smoke@test.com"));
    }

    @Test
    @Order(3)
    public void testSearchByName() {
        boolean found=page.searchRecord("Cierra");
        assertTrue(found);
    }

    @Test
    @Order(4)
    public void testDeleteRecord() {
        page.deleteRecord("kierra@example.com");
        page.searchRecord("kierra@example.com");
        assertFalse(page.isEmailPresent("kierra@example.com"));
    }
}

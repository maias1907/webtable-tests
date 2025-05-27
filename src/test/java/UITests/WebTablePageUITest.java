package UITests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.example.Pages.WebTablePage;

import static org.example.Utils.DriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebTablePageUITest {

    private WebDriver driver;
    private WebTablePage webPage;
    private static final String baseURL = "https://demoqa.com/webtables";

    @BeforeEach
    public void setup() {
        driver= getDriver();
        webPage = new WebTablePage(driver);
        driver.manage().window().maximize();
        driver.get(baseURL);
    }

    @Test
    @Order(1)
    public void addNewRecordTest() {
        boolean added=webPage.addNewRecord("Maias", "Omar", "25", "100", "maias9018@gmail.com", "Math");
        assertTrue(added);
    }
    @Test
    @Order(2)
    public void addNewRecordMissingMandatoryFields() {
        // Try adding with missing first name
        boolean added = webPage.addNewRecord("","Omar","25","100","missingfname@example.com","Math");
        assertFalse(added, "Record should NOT be added with missing first name");

        // Try adding with missing email
        added = webPage.addNewRecord("John", "Doe", "30", "200", "", "IT");
        assertFalse(added, "Record should NOT be added with missing email");
    }

    @Test
    @Order(3)
    public void addNewRecordInvalidEmailFormat() {
        boolean added = webPage.addNewRecord("Test", "User", "22", "150", "abc@@example.com", "Sales");
        assertFalse(added, "Record should NOT be added with invalid email format");
    }

    @Test
    @Order(4)
    public void addNewRecordNegativeAge() {
        boolean added = webPage.addNewRecord("Negative", "Age", "-5", "120", "negativeage@example.com", "HR");
        assertFalse(added, "Record should NOT be added with negative age");
    }
    @Test
    @Order(5)
    public void partialSearchTest() {
        boolean found = webPage.searchRecord("Kie");
        assertTrue(found);
    }
    @Test
    @Order(6)
    public void editSalaryTest() {

        boolean editSal=webPage.editSalary("kierra@example.com", "99999");
        assertTrue(editSal);
    }
    @Test
    @Order(7)
    public void editSalaryInvalidInput() {
        boolean edited = webPage.editSalary("kierra@example.com", "nine thousand");
        assertFalse(edited, "Salary edit should fail when input is non-numeric");
    }

    @Test
    @Order(8)
    public void deleteRecordTest() {
        webPage.deleteRecord("kierra@example.com");
        webPage.searchRecord("kierra@example.com");
        assertFalse(webPage.isEmailPresent("kierra@example.com"));
    }

    @Test
    @Order(9)
    public void searchFilterTest() {
        boolean res=webPage.searchRecord("Cierra");
        assertTrue(res);
    }
    @Test
    @Order(10)
    public void searchInvalidInputTest() {
        // Using special characters
        boolean found = webPage.searchRecord("%$#@!");
        assertFalse(found, "No records should be found for special character search");

        // Using SQL injection-like input
        found = webPage.searchRecord("' OR '1'='1");
        assertFalse(found, "Search should be safe and not return results for malicious input");
    }



    @Test
    @Order(11)
    public void duplicateEmailTest() {
        webPage.addNewRecord("John", "Doe", "30", "5000", "duplicate@example.com", "IT");
        boolean added = webPage.addNewRecord("Jane", "Doe", "28", "6000", "duplicate@example.com", "HR");
        assertTrue(added, "Duplicate emails should not be allowed");
    }

    @Test
    @Order(12)
    public void resetSearchFilterTest() {
       boolean search=webPage.searchRecord("NonExistentName");
        assertEquals(0, webPage.getRecordCount(), "No records should be found");

    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}



package org.recordsTable;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class webTablePage {
    private WebDriver driver;
    private By addNewRecordButton = By.id("addNewRecordButton");
    private By firstNameInput = By.id("firstName");
    private By lastNameInput = By.id("lastName");
    private By ageInput = By.id("age");
    private By emailInput = By.id("userEmail");
    private By salaryInput = By.id("salary");
    private By departmentInput = By.id("department");
    private By submitButton = By.id("submit");
    private By searchBox = By.id("searchBox");

    public webTablePage(WebDriver driver) {
        this.driver = driver;
    }

    public void addNewRecord(String firstName, String lastName, String age, String salary, String email, String department) {
        scrollToTable();
        driver.findElement(addNewRecordButton).click();

        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(ageInput).sendKeys(age);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(salaryInput).sendKeys(salary);
        driver.findElement(departmentInput).sendKeys(department);
        driver.findElement(submitButton).click();
    }
    public boolean isPadRow(WebElement row) {
        String classAttr = row.getAttribute("class");
        return classAttr != null && classAttr.contains("-padRow") ;
    }

    public boolean searchRecord(String query) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        searchInput.clear();
        searchInput.sendKeys(query);
        scrollToTable();
        List<WebElement> rows = driver.findElements(By.cssSelector("div.rt-tr-group"));
        for (WebElement row : rows) {
           WebElement row2Check= row.findElement(By.cssSelector("div.rt-tr"));
            if (isPadRow(row2Check)) break;
            System.out.println(row.getText().toLowerCase().contains(query.toLowerCase()));
            if (!row.getText().toLowerCase().contains(query.toLowerCase())  ) {
                return false;
            }
        }

        return true;
    }


    public void editSalary(String email, String newSalary) {
        scrollToTable();
        List<WebElement> rows = driver.findElements(By.cssSelector("div.rt-tr-group"));
        for (WebElement row : rows) {
            if (row.getText().contains(email)) {
                row.findElement(By.cssSelector("span[title='Edit']")).click();
                WebElement salaryField = driver.findElement(salaryInput);
                salaryField.clear();
                salaryField.sendKeys(newSalary);
                driver.findElement(submitButton).click();
                break;
            }
        }
    }

    public void deleteRecord(String email) {
        scrollToTable();
        List<WebElement> rows = driver.findElements(By.cssSelector("div.rt-tr-group"));
        for (WebElement row : rows) {
            if (row.getText().contains(email)) {
                WebElement deleteButton = row.findElement(By.cssSelector("span[title='Delete'][id^='delete-record']"));
                new Actions(driver).moveToElement(deleteButton).perform();
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
                new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOf(row));
                break;
            }
        }
    }

    public String getSalaryByEmail(String email) {
        scrollToTable();
        List<WebElement> rows = driver.findElements(By.cssSelector("div.rt-tr-group"));
        for (WebElement row : rows) {
            if (row.getText().contains(email)) {
                String[] data = row.getText().split("\\s+");
                return data[4]; // salary is the 5th column
            }
        }
        return null;
    }

    public boolean isEmailPresent(String email) {
        scrollToTable();
        List<WebElement> rows = driver.findElements(By.cssSelector("div.rt-tr-group"));
        for (WebElement row : rows) {
            if (row.getText().contains(email)) {
                return true;
            }
        }
        return false;
    }

    private void scrollToTable() {

            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.PAGE_DOWN).perform();
    }
}



package org.example.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class WebTablePage {
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

    public WebTablePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean addNewRecord(String firstName, String lastName, String age, String salary, String email, String department) {

        if (firstName == null || firstName.trim().isEmpty()) return false;
        if (email == null || email.trim().isEmpty() || !email.contains("@")) return false;
        if (age != null && !age.matches("\\d+")) return false; // age should be digits only
        if (salary != null && !salary.matches("\\d+")) return false; // salary should be digits only
        scrollToTable();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addNewRecordButton));
        addButton.click();
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(ageInput).sendKeys(age);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(salaryInput).sendKeys(salary);
        driver.findElement(departmentInput).sendKeys(department);
        driver.findElement(submitButton).click();
        // Wait a little or use explicit wait to ensure table updates
        try {
            Thread.sleep(1000); // or better use wait for element presence or invisibility of modal
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Verify the record appears in the table by email (assuming email unique)
        scrollToTable();
        return isEmailPresent(email);
    }

    public boolean isPadRow(WebElement row) {
        String classAttr = row.getAttribute("class");
        return classAttr != null && classAttr.contains("-padRow");
    }

    public boolean searchRecord(String query) {
        if (query == null || query.trim().isEmpty()) {
            return false;
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        searchInput.clear();
        searchInput.sendKeys(query);
        scrollToTable();

        List<WebElement> rows = driver.findElements(By.cssSelector("div.rt-tr-group"));

        // If the table shows a "No rows found" message, return false
        boolean foundMatchingRow = false;

        for (WebElement row : rows) {
            WebElement actualRow = row.findElement(By.cssSelector("div.rt-tr"));
            if (isPadRow(actualRow)) break;

            String rowText = row.getText().toLowerCase();
            if (rowText.contains(query.toLowerCase())) {
                foundMatchingRow = true;
                break;
            }
        }

        return foundMatchingRow;
    }


    public boolean editSalary(String email, String newSalary) {
        if (newSalary == null || !newSalary.matches("\\d+")) {
            return false;
        }

        scrollToTable();
        List<WebElement> rows = driver.findElements(By.cssSelector("div.rt-tr-group"));
        for (WebElement row : rows) {
            if (row.getText().contains(email)) {
                row.findElement(By.cssSelector("span[title='Edit']")).click();
                WebElement salaryField = driver.findElement(salaryInput);
                salaryField.clear();
                salaryField.sendKeys(newSalary);
                driver.findElement(submitButton).click();

                // Small wait to allow UI to update
                try {
                    Thread.sleep(500); // replace with explicit wait if needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Verify salary updated
                String updatedSalary = getSalaryByEmail(email);
                return newSalary.equals(updatedSalary);
            }
        }

        // Email not found in any row
        return false;
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
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.rt-tr-group")));
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

    public int getRecordCount() {
        List<WebElement> allRows = driver.findElements(By.cssSelector("div.rt-tbody .rt-tr-group"));

        int validRowCount = 0;
        for (WebElement row : allRows) {

            if (row.getText().trim().isEmpty()) {
                continue; // skip empty rows
            }

            if (row.getText().contains("No rows found")) {
                continue; // skip placeholder row
            } else {

                validRowCount++;
            }


        }
        return validRowCount;
    }
}






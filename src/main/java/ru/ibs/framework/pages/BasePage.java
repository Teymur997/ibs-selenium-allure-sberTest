package ru.ibs.framework.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ibs.framework.managers.DriverManager;
import ru.ibs.framework.managers.PageManager;

public class BasePage {
    protected final DriverManager driverManager = DriverManager.getInstance();
    protected final PageManager pageManager = PageManager.getInstance();
    protected final WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 5, 500);
    protected final Actions actions = new Actions(driverManager.getDriver());
    protected final JavascriptExecutor executor = (JavascriptExecutor) driverManager.getDriver();

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }


    protected WebElement scrollToElementJs(WebElement element) {
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
        waitUntilElementIsVisible(element);
        return element;
    }
    protected WebElement waitUntilElementIsClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    protected WebElement waitUntilElementIsVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    protected boolean waitUntilElementDisappear(WebElement element) {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }
    protected void fillInputField(WebElement field, String value) {
        scrollToElementJs(field);
        waitUntilElementIsClickable(field);
        field.sendKeys(value);
    }
    protected Integer priceValueHandler(WebElement element) {
        waitUntilElementIsVisible(element);
        return Integer.parseInt(element.getText().replaceAll("\\D", ""));
    }
    protected String articleValueHandler(WebElement element) {
        waitUntilElementIsVisible(element);
        return element.getText().replaceAll("\\D", "");
    }
    protected String indicatorTextHandler(String guarantee) {
        return guarantee.replaceAll("[^\\d%,]+", "");
    }
    protected String valueTextHandler(String guarantee) {
        return guarantee.replaceAll("\\D", "");
    }

}

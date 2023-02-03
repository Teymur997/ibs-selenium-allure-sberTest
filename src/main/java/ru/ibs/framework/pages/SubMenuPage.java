package ru.ibs.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SubMenuPage extends BasePage {

    @FindBy(xpath = "//button[@class=\"kitt-cookie-warning__close\"]")
    private WebElement cookieCloseButton;
    @FindBy(xpath = "//button[@class=\"kitt-region__accept\"]")
    private WebElement regionSelectionCloseButton;
    @FindBy(xpath = "//iframe[@id=\"iFrameResizer0\"]")
    private WebElement calculatorFrame;
    @FindBy(xpath = "//div[@class=\"ugyMAENZwRVFZMfAVn6H6\"]")
    private WebElement calculatorWindow;
    @FindBy(xpath = "//h2[@class=\"t-header-big\"]")
    private List<WebElement> calculatorWindowButton;
    @FindBy(xpath = "//div[@class=\"inpt-root__input-container-6-3-4-beta-1-for-calculator\"]")
    private List<WebElement> inputField;
    @FindBy(xpath = "//div[@class=\"_2RwsL_oKFrEK9oh3oTaIT0\"]")
    private List<WebElement> checkboxList;
    @FindBy(xpath = "//ul[@class=\"_3LcGGKRN57Ugk1KsivpOBl\"]")
    private List<WebElement> calculationIndicators;


    @Step("Закрытие Cookie")
    public SubMenuPage closeCookie() {
        wait.until(ExpectedConditions.visibilityOf(cookieCloseButton));
        cookieCloseButton.click();
        return pageManager.getSubMenuPage();
    }
    @Step("Закрытие окна подтверждения региона")
    public SubMenuPage closeRegionSelectionWindow() {
        try {
            wait.until(ExpectedConditions.visibilityOf(regionSelectionCloseButton));
            regionSelectionCloseButton.click();
        } catch (NoSuchElementException | TimeoutException ignore) {
        }
        return pageManager.getSubMenuPage();
    }
    @Step("Проверка корректности открытия страницы \"{pageName}\"")
    public SubMenuPage checkIfPageIsOpened(String pageName) {
        Assertions.assertTrue(driverManager.getDriver()
                .getTitle()
                .contains(pageName), "Страница не открыта");
        /*
        проверка не проходит, так как название в контекстном меню - Ипотека на вторичное жильё,
        отличается от названия страницы - Ипотека на вторичное жилье
         */
        return pageManager.getSubMenuPage();
    }
    @Step("Прокрутка до окна расчета ипотеки")
    public SubMenuPage scrollToCalculatorWindow() {
        driverManager.getDriver()
                .switchTo()
                .frame(calculatorFrame);
        scrollToElementJs(calculatorWindow);
        waitUntilElementIsVisible(calculatorWindow);
        return pageManager.getSubMenuPage();
    }
    @Step("Внесение данных в поле \"{fieldName}\"")
    public SubMenuPage fieldFilling(String fieldName, String value) {
        WebElement field = getInputField(fieldName);

        field.click();
        field.sendKeys(Keys.CONTROL + "a");
        field.sendKeys(Keys.BACK_SPACE);
        sendLetters(field, value);
        waitForStability(5000, 250);

        Assertions.assertEquals(value, valueTextHandler(field.getAttribute("value")), "Актуальное значение поля не соответствует ожидаемому");
        return pageManager.getSubMenuPage();
    }
    @Step("Отключение чекбокса \"{checkBoxName}\"")
    public SubMenuPage checkBoxClicking(String checkBoxName) {
        WebElement checkBox = getCheckbox(checkBoxName);
        actions.moveToElement(checkBox);
        checkBox.click();
        return pageManager.getSubMenuPage();
    }
    @Step("Проверка корректности индикатора \"{indicatorName}\"")
    public SubMenuPage checkIndicatorValue(String indicatorName, String value) {
        waitForStability(5000, 250);
        Assertions.assertEquals(
                value,
                getIndicator(indicatorName).getText(),
                "Показатели расчета не равны");
        return pageManager.getSubMenuPage();
    }
    @Step("Проверка корректности индикатора \"{indicatorName}\"")
    public SubMenuPage checkProfitIndicatorValue(String indicatorName, String value) {
        waitForStability(5000, 250);
        Assertions.assertEquals(
                value,
                getProfitIndicator(indicatorName).getText(),
                "Показатели расчета не равны");
        return pageManager.getSubMenuPage();
    }
    @Step("Прокрутка до окна расчета ипотеки")
    public SubMenuPage scrollToCalculatorWindowBottom() {
        driverManager.getDriver().switchTo().defaultContent();
        WebElement el = calculatorWindowButton.stream()
                .filter(element -> element
                        .getText()
                        .contains("Рассчитайте ипотеку"))
                .findAny()
                .get();
        scrollToElementJs(el);
        waitUntilElementIsVisible(el);
        driverManager.getDriver()
                .switchTo()
                .frame(calculatorFrame);
        return pageManager.getSubMenuPage();
    }
    private WebElement getInputField(String fieldName) {
        return  inputField.stream()
                .filter(element -> element.findElement(By.xpath(".//label"))
                        .getText().contains(fieldName))
                .map(element -> element.findElement(By.xpath(".//input")))
                .findAny()
                .get();
    }
    private WebElement getCheckbox(String checkBoxName) {
        return  checkboxList.stream()
                .filter(element -> element.findElement(By.xpath("./span"))
                        .getText()
                        .contains(checkBoxName))
                .map(element -> element.findElement(By.xpath(".//input")))
                .findAny()
                .get();
    }
    private WebElement getIndicator(String indicatorName) {
        return calculationIndicators
                .get(1)
                .findElements(By.xpath(".//li"))
                .stream()
                .filter(element -> element.findElement(By.xpath(".//span[@class=\"_2K-vC4nTzrGG1TQHQ2HGL\"]")).getText()
                        .contains(indicatorName))
                .map(element -> element.findElement(By.xpath(".//span[contains(@class, '_19zitcoxuphOm2IGRCrUgj')]")))
                .findAny()
                .get();
    }
    private WebElement getProfitIndicator(String indicatorName) {
        return calculationIndicators
                .get(2)
                .findElements(By.xpath(".//li"))
                .stream()
                .filter(element -> element.findElement(By.xpath(".//span[@class=\"_2K-vC4nTzrGG1TQHQ2HGL\"]")).getText()
                        .contains(indicatorName))
                .map(element -> element.findElement(By.xpath(".//span[contains(@class, '_19zitcoxuphOm2IGRCrUgj')]")))
                .findAny()
                .get();
    }
    private void sendLetters(WebElement element, String value) {
        String[] letters = value.split("");
        for (String letter : letters) {
            actions.moveToElement(element).sendKeys(letter).pause(100).build().perform();
        }
    }
    private void waitForStability(int maxWaitMillis, int pollDelimiter) {
        double start = System.currentTimeMillis();
        while (System.currentTimeMillis() < start + maxWaitMillis) {
            String prevState = driverManager.getDriver().getPageSource();
            try {
                Thread.sleep(pollDelimiter);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (prevState.equals(driverManager.getDriver().getPageSource())) {
                return;
            }
        }
    }
}

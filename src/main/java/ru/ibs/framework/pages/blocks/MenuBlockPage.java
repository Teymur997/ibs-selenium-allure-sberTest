package ru.ibs.framework.pages.blocks;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ibs.framework.pages.BasePage;
import ru.ibs.framework.pages.SubMenuPage;
import ru.ibs.framework.utils.PropsConst;

import java.util.List;

public class MenuBlockPage extends BasePage {
    @FindBy(xpath = "//img[@class=\"header__logo-image\"]")
    private WebElement logo;
    @FindBy(xpath = "//li[contains(@class, 'kitt-top-menu__item_first')]//a[@role=\"button\"]")
    private List<WebElement> topMenu;
    @FindBy(xpath = "//a[@data-cga_click_top_menu]")
    private List<WebElement> subMenu;
    @Step("Проверка корректности открытия нужной страницы")
    public MenuBlockPage checkIfPageIsOpened() {
        wait.until(ExpectedConditions.visibilityOf(logo));
        Assertions.assertTrue(driverManager.getDriver()
                .getTitle()
                .contains("СберБанк для физических лиц"), "Страница не открыта");
        return pageManager.getMenuBlockPage();
    }
    @Step("Выбор меню \"{menuName}\"")
    public MenuBlockPage chooseMenu(String menuName) {
        topMenu.stream()
                .filter(element -> element.getText()
                        .contains(menuName)).findAny()
                .get()
                .click();
        return pageManager.getMenuBlockPage();
    }
    @Step("Выбор подменю \"{subMenuName}\"")
    public SubMenuPage chooseSubMenu(String subMenuName) {
        subMenu.stream().filter(element -> element.getAttribute("innerText")
                .contains(subMenuName))
                .findAny()
                .get()
                .click();
        return pageManager.getSubMenuPage();
    }
}

package ru.ibs.framework.managers;


import ru.ibs.framework.pages.SubMenuPage;
import ru.ibs.framework.pages.blocks.MenuBlockPage;

public class PageManager {
    private static PageManager INSTANCE;
    private TestPropertiesManager testPropertiesManager;
    MenuBlockPage menuBlockPage;
    SubMenuPage subMenuPage;


    public static PageManager getInstance () {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }
    public MenuBlockPage getMenuBlockPage() {
        if (menuBlockPage == null) {
            menuBlockPage = new MenuBlockPage();
        }
        return menuBlockPage;
    }

    public SubMenuPage getSubMenuPage() {
        if (subMenuPage == null) {
            subMenuPage = new SubMenuPage();
        }
        return subMenuPage;
    }

}

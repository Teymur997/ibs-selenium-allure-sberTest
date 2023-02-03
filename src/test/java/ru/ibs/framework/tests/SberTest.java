package ru.ibs.framework.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ibs.framework.base.BaseTests;
import ru.ibs.framework.utils.AllureListener;

@ExtendWith(AllureListener.class)
public class SberTest extends BaseTests {
    @Test
    @DisplayName("Тест")
    public void test() {
        pageManager.getMenuBlockPage()
                .checkIfPageIsOpened()
                .chooseMenu("Ипотека")
                .chooseSubMenu("Ипотека на вторичное жильё")
                .closeCookie()
                .closeRegionSelectionWindow()
                .checkIfPageIsOpened("Ипотека на вторичное жилье")
                .scrollToCalculatorWindow()
                .fieldFilling("Стоимость недвижимости", "5180000")
                .fieldFilling("Первоначальный взнос", "3058000")
                .fieldFilling("Срок кредита", "30")
                .checkBoxClicking("Страхование жизни и здоровья")
                .scrollToCalculatorWindowBottom()
                .checkIndicatorValue("Сумма кредита", "2 122 000 ₽")
                .checkIndicatorValue("Ежемесячный платеж", "21 664 ₽")
                .checkProfitIndicatorValue("Необходимый доход", "36 829 ₽")
                .checkIndicatorValue("Процентная ставка", "11%");
    }


}

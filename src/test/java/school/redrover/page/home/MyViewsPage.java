package school.redrover.page.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

public class MyViewsPage extends BasePage {

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButton;

    @FindBy(css = ".h4")
    private WebElement folderText;

    public MyViewsPage(WebDriver driver) {
        super(driver);
    }

    private void selectMenuFromItemDropdown(String itemName, String menuName) {
        TestUtils.moveAndClickWithJS(getDriver(), getDriver().findElement(
                By.xpath("//td/a/span[text() = '%s']/../button".formatted(itemName))));

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='jenkins-dropdown__item__icon']/parent::*[contains(., '%s')]"
                        .formatted(menuName)))).click();
    }

    public MyViewsPage selectDeleteFromItemMenuAndClickYes(String itemName) {
        selectMenuFromItemDropdown(itemName, "Удалить Multi-configuration project");
        getWait5().until(ExpectedConditions.visibilityOf(yesButton)).click();

        return this;
    }

    public String getTextEmptyFolder() {
        return getWait5().until(ExpectedConditions.visibilityOf(folderText)).getText();
    }
}

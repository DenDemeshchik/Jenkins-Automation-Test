package school.redrover.page.freestyle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;


public class FreestyleProjectPage extends BaseProjectPage<FreestyleProjectPage, FreestyleConfigPage, FreestyleRenamePage> {

    @FindBy(tagName = "h1")
    private WebElement projectName;

    @FindBy(xpath = "//a[@data-build-success='Сборка запланирована']")
    private WebElement buildNowSidebar;

    @FindBy(xpath = "//span[text()='Сборочная директория']/..")
    private WebElement workspaceSidebar;

    @FindBy(xpath = "//*[@id='breadcrumbs']/li[5]")
    private WebElement breadCrumbs;

    @FindBy(name = "Submit")
    private WebElement enableButton;

    @FindBy(id = "enable-project")
    private WebElement projectStatusText;

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public FreestyleConfigPage createProjectConfigPage() {
        return new FreestyleConfigPage(getDriver());
    }

    @Override
    public FreestyleRenamePage createProjectRenamePage() {
        return new FreestyleRenamePage(getDriver());
    }

    public String getProjectName() {
        return projectName.getText();
    }

    public FreestyleProjectPage clickWorkspaceSidebar() {
        getWait10().until(ExpectedConditions.visibilityOf(workspaceSidebar)).click();

        return this;
    }

    public FreestyleProjectPage clickBuildNowSidebar() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildNowSidebar)).click();

        return this;
    }

    public FreestyleConfigPage changeEnablingStateViaIndicator() {
        getWait10().until(ExpectedConditions.visibilityOf(enableButton)).click();

        return new FreestyleConfigPage(getDriver());
    }

    public String getBreadCrumb() {
        return breadCrumbs.getText();
    }

    public String getDisabledProjectIndicator() {
        return getWait10().until(ExpectedConditions.visibilityOf(projectStatusText)).getText();
    }
}

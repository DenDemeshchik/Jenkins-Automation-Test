package school.redrover.page.multiConfiguration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;

public class MultiConfigurationProjectPage extends BaseProjectPage<MultiConfigurationProjectPage, MultiConfigurationConfigPage, MultiConfigurationRenamePage> {

    @FindBy(xpath = "//div[@id='side-panel']//span[text()='Удалить Multi-configuration project']")
    private WebElement deleteProjectButton;

    @FindBy(xpath = "//footer/following-sibling::dialog")
    private WebElement deletionPopup;

    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public MultiConfigurationConfigPage createProjectConfigPage() {
        return new MultiConfigurationConfigPage(getDriver());
    }

    @Override
    public MultiConfigurationRenamePage createProjectRenamePage() {
        return new MultiConfigurationRenamePage(getDriver());
    }

    public MultiConfigurationProjectPage clickDeleteProject() {
        deleteProjectButton.click();

        return this;
    }

    public WebElement getDeletionPopup() {
        return getWait5().until(ExpectedConditions.visibilityOf(deletionPopup));
    }
 }

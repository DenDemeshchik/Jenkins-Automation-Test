package school.redrover.page.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;
import school.redrover.page.home.HomePage;

public class UserConfigPage extends BasePage {

    @FindBy (xpath = "//a[@data-title='Удалить']")
    private WebElement deleteUserSidebarButton;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement okToDeleteButton;

    public UserConfigPage(WebDriver driver) {
        super(driver);
    }

    public HomePage deleteUserFromConfigureUserPage() {
        deleteUserSidebarButton.click();
        okToDeleteButton.click();
        return new HomePage(getDriver());
    }

}

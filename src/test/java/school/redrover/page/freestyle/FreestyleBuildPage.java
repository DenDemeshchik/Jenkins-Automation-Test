package school.redrover.page.freestyle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

public class FreestyleBuildPage extends BasePage {


    @FindBy(name = "Submit")
    private WebElement saveButton;

    public FreestyleBuildPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleBuildPage clickSaveButton() {
        saveButton.click();

        return this;
    }
}

package school.redrover.page.pipeline;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.List;

public class PipelineBuildPage extends BasePage {

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//button[normalize-space(text())=\"Don't keep this build forever\"]")
    private WebElement keepThisBuildForeverButton;

    @FindBy(xpath = "//div[@id='tasks']/div")
    private List<WebElement> sidebarTaskList;

    public PipelineBuildPage(WebDriver driver) {
        super(driver);
    }
}

package school.redrover.page.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.List;

public class BuildHistoryPage extends BasePage {

    @FindBy(xpath = "//a[@class='jenkins-table__link model-link']/span")
    private WebElement lastProjectName;

    @FindBy(xpath = "//*[@id='projectStatus']/tbody/tr/td[4]")
    private List<WebElement> statusList;

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return lastProjectName.getText();
    }

    public List<String> getListOfStatuses() {

        return statusList.stream().map(WebElement::getText).toList();
    }
}

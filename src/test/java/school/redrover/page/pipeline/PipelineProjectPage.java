package school.redrover.page.pipeline;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BaseProjectPage;


public class PipelineProjectPage extends BaseProjectPage<PipelineProjectPage, PipelineConfigurePage, PipelineRenamePage> {

    @FindBy(xpath = "//div[@class='jenkins-app-bar']//h1")
    private WebElement projectPageTitle;

    @FindBy(xpath = "//ol[@id='breadcrumbs']/li[@class='jenkins-breadcrumbs__list-item'][2]")
    private WebElement projectNameBreadcrumbs;

    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public PipelineConfigurePage createProjectConfigPage() {
        return new PipelineConfigurePage(getDriver());
    }

    @Override
    public PipelineRenamePage createProjectRenamePage() {
        return new PipelineRenamePage(getDriver());
    }

    public String getTitle() {
        return projectPageTitle.getText();
    }

    public String getProjectNameBreadcrumb() {
        return projectNameBreadcrumbs.getText();
    }

    public PipelineSyntaxPage gotoPipelineSyntaxPageFromLeftPanel(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/job/%s/pipeline-syntax']".formatted(projectName))).click();

        return new PipelineSyntaxPage(getDriver());
    }
}

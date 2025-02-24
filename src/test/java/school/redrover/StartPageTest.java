package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class StartPageTest extends BaseTest {

    private static final String NEW_FOLDER_NAME = "FirstFolder";

    @Test
    public void testStartPageMainPanelContent() {
        List<String> startPageMainContent = new HomePage(getDriver())
                .getContentBlock();

        Assert.assertEquals(startPageMainContent.size(), 4);
        Assert.assertEquals(
                startPageMainContent,
                List.of("Create a job", "Set up an agent", "Configure a cloud", "Learn more about distributed builds"));
    }

    @Test
    public void testStartPageSidePanelTaskContent() {
        List<String> startPageSideContent = new HomePage(getDriver())
                .getSideContent();

        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(startPageSideContent, List.of("Создать Item", "История сборок", "Настроить Jenkins", "My Views"));
    }

    @Test
    public void testCheckLinksSidePanel() {
        List<String> startPageSideContent = new HomePage(getDriver())
                .getSideContentAttribute();

        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(
                startPageSideContent,
                List.of("http://localhost:8080/view/all/newJob", "http://localhost:8080/view/all/builds",
                        "http://localhost:8080/manage", "http://localhost:8080/me/my-views"));
    }

    @Test
    public void testCreateNewFolder() {
        TestUtils.createFolder(getDriver(), NEW_FOLDER_NAME);

        List<String> projectList = new HomePage(getDriver())
                .getItemList();

        Assert.assertListContainsObject(
                projectList,
                NEW_FOLDER_NAME,
                "Folder is not created");
    }
}

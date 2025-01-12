package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.multiConfiguration.MultiConfigurationConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.List;

public class MultiConfigurationProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MultiConfigurationProject";

    @Test(description = "Create project without descriptions")
    public void testCreateProjectWithoutDescription() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(itemList.size(), 1);
        Assert.assertEquals(itemList.get(0), PROJECT_NAME);
    }

    @Test
    public void testCreateProjectWithoutName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectMultiConfigurationProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» Поле не может быть пустым, введите имя");
        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());
    }

    @Test
    public void testSelectTimePeriodThrottleBuilds() {
        MultiConfigurationConfigPage multiConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .clickThrottleBuildsCheckbox();

        List<String> selectItems = multiConfigPage.getAllDurationItemsFromSelect();

        for (String item : selectItems) {
            multiConfigPage.selectDurationItem(item);

            Assert.assertEquals(multiConfigPage.getSelectedDurationItemText(), item);
        }
    }

    @Test
    public void testDeletionPopupAppearsOnProjectPage() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        WebElement deletionPopup = new HomePage(getDriver())
                .openMultiConfigurationProject(PROJECT_NAME)
                .clickDeleteProject()
                .getDeletionPopup();

        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test(dependsOnMethods = "testDeletionPopupAppearsOnProjectPage")
    public void testDeletionPopupAppearsOnMainPage() {
        WebElement deletionPopup = new HomePage(getDriver())
                .selectDeleteFromItemMenu(PROJECT_NAME)
                .getDeletionPopup();

        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test
    public void testDeleteProjectFromProjectPage() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .openMultiConfigurationProject(PROJECT_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getWelcomeTitle();

        Assert.assertEquals(welcomeText, "Добро пожаловать в Jenkins!");
    }

    @Test
    public void testDeleteViaDropDownMenu() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(PROJECT_NAME)
                .getWelcomeTitle();

        Assert.assertEquals(welcomeText, "Добро пожаловать в Jenkins!");
    }

    @Test
    public void testDeleteFromMyViewsPage() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .goToMyViews()
                .selectDeleteFromItemMenuAndClickYes(PROJECT_NAME)
                .getTextEmptyFolder();

        Assert.assertEquals(welcomeText, "This folder is empty");
    }
}
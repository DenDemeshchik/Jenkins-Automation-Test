package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.pipeline.PipelineConfigurePage;
import school.redrover.page.pipeline.PipelineProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.List;

public class PipelineProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "PipelineName";
    private static final String NEW_PROJECT_NAME = "NewPipelineName";

    @DataProvider
    public Object[][] providerUnsafeCharacters() {

        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };
    }

    @Test
    public void testCreateProjectWithValidNameViaSidebar() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(
                itemList,
                PROJECT_NAME,
                "Project is not created");
    }

    @Test(dependsOnMethods = "testCreateProjectWithValidNameViaSidebar")
    public void testVerifySidebarOptionsOnProjectPage() {
        List<String> actualSidebarOptionList = new HomePage(getDriver())
                .openPipelineProject(PROJECT_NAME)
                .getSidebarOptionList();

        Assert.assertEquals(
                actualSidebarOptionList,
                List.of("Status", "Changes", "Собрать сейчас", "Настройки", "Удалить Pipeline", "Stages", "Переименовать", "Pipeline Syntax"),
                "Sidebar options on Project page do not match expected list.");
    }

    @Test()
    public void testRename() {
        PipelineProjectPage projectPage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openPipelineProject(PROJECT_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(NEW_PROJECT_NAME)
                .clickRenameButton();

        Assert.assertEquals(projectPage.getTitle(), NEW_PROJECT_NAME);
        Assert.assertEquals(projectPage.getProjectNameBreadcrumb(), NEW_PROJECT_NAME);
    }

    @Test
    public void testWarningMessageOnRenameProjectPage() {
        String actualWarningMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openPipelineProject(PROJECT_NAME)
                .clickRenameSidebarButton()
                .getWarningMessage();

        Assert.assertEquals(actualWarningMessage, "Новое имя совпадает с прежним.");
    }

    @Test
    public void testRenameByChevronDashboard() {
        PipelineProjectPage projectPage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .goToPipelineRenamePageViaDropdown(PROJECT_NAME)
                .clearInputFieldAndTypeName(NEW_PROJECT_NAME)
                .clickRenameButton();

        Assert.assertEquals(projectPage.getTitle(), NEW_PROJECT_NAME);
        Assert.assertEquals(projectPage.getProjectNameBreadcrumb(), NEW_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testRenameByChevronDashboard")
    public void testRenameByChevronDisplayedOnHomePageWithCorrectName() {
        boolean isDisplayed = new HomePage(getDriver())
                .getItemList()
                .contains(NEW_PROJECT_NAME);

        Assert.assertTrue(isDisplayed);
    }

    @Test
    public void testDeleteByChevronBreadcrumb() {
        String welcomeTitle = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .openPipelineProject(PROJECT_NAME)
                .openBreadcrumbDropdown()
                .clickDeleteBreadcrumbDropdownAndConfirm()
                .getWelcomeTitle();

        Assert.assertEquals(welcomeTitle, "Добро пожаловать в Jenkins!");
    }

    @Test
    public void testPipelineDisabledTooltipOnHomePage() {
        String tooltipValue = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickToggleToDisableOrEnableProject()
                .clickSaveButton()
                .gotoHomePage()
                .getStatusBuild(PROJECT_NAME);

        Assert.assertEquals(tooltipValue, "Приостановлено");
    }

    @Test
    public void testBuildWithValidPipelineScript() {
        final String validPipelineScriptFile = """
                pipeline {
                    agent any
                    stages {
                        stage('Checkout') {
                            steps {echo 'Step: Checkout code from repository'}
                        }
                     }
                }
                """;

        String statusBuild = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .enterScriptFromFile(validPipelineScriptFile)
                .clickSaveButton()
                .gotoHomePage()
                .selectBuildNowFromItemMenu(PROJECT_NAME)
                .refreshAfterBuild()
                .getStatusBuild(PROJECT_NAME);

        Assert.assertEquals(statusBuild, "Успешно");
    }

    @Test
    public void testBuildWithInvalidPipelineScript() {
        final String invalidPipelineScriptFile = """
                error_pipeline {{{
                    agent any
                    stages {
                        stage('Checkout') {
                            steps {echo 'Step: Checkout code from repository'}
                        }
                     }
                }
                """;

        String statusBuild = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .enterScriptFromFile(invalidPipelineScriptFile)
                .clickSaveButton()
                .gotoHomePage()
                .selectBuildNowFromItemMenu(PROJECT_NAME)
                .refreshAfterBuild()
                .getStatusBuild(PROJECT_NAME);

        Assert.assertEquals(statusBuild, "Провалилось");
    }

    @Test
    public void testVerifyListOfActionsOnSidebar() {
        TestUtils.createPipelineProject(getDriver(), PROJECT_NAME);

        List<String> actualListActions = new HomePage(getDriver())
                .openPipelineProject(PROJECT_NAME)
                .getSidebarOptionList();

        Assert.assertEquals(actualListActions,
                List.of("Status", "Changes", "Собрать сейчас", "Настройки", "Удалить Pipeline", "Stages", "Переименовать", "Pipeline Syntax"));
    }

    @Test
    public void testCreatePipelineFromExistingOne() {
        final String secondProjectName = "Second" + PROJECT_NAME;
        TestUtils.createPipelineProject(getDriver(), PROJECT_NAME);

        List<String> itemNameList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(secondProjectName)
                .enterName(PROJECT_NAME)
                .clickOkLeadingToCofigPageOfCopiedProject(new PipelineConfigurePage(getDriver()))
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(itemNameList.contains(secondProjectName));
    }
}

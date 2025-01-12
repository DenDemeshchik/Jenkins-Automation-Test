package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.page.freestyle.FreestyleConfigPage;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.List;

public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MyFreestyleProject";
    private static final String NEW_PROJECT_NAME = "NewFreestyleProjectName";
    private static final String DESCRIPTION = "FreestyleDescription";

    @DataProvider
    public Object[][] providerUnsafeCharacters() {

        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };
    }

    @Test
    public void testCreateFreestyleProjectWithEmptyName() {
        String emptyNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("")
                .selectFreestyleProject()
                .getEmptyNameMessage();

        Assert.assertEquals(emptyNameMessage, "» Поле не может быть пустым, введите имя");
    }

    @Test
    public void testCreateFreestyleProjectWithDuplicateName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProject()
                .getErrorMessage();

        Assert.assertTrue(errorMessage.contains("уже существует"));
    }

    @Test
    public void testCreateProjectViaContentBlockButton() {
        List<String> actualProjectsList = new HomePage(getDriver())
                .clickNewItemContentBlock()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(actualProjectsList.size(), 1);
        Assert.assertEquals(actualProjectsList.get(0), PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithDuplicateName")
    public void testRenameViaBreadcrumbDropdown() {
        String renamedProject = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .openBreadcrumbDropdown()
                .clickRenameBreadcrumbDropdown()
                .clearInputFieldAndTypeName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(renamedProject, NEW_PROJECT_NAME);
    }

    @Test
    public void testCreateProjectViaSidebarMenu() {
        List<String> actualProjectsList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(actualProjectsList.size(), 1);
        Assert.assertEquals(actualProjectsList.get(0), PROJECT_NAME);
    }

    @Test
    public void testCreateFreestyleProjectFromMyViews() {
        List<String> projectName = new HomePage(getDriver())
                .clickMyViewsButton()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(projectName.size(), 1);
        Assert.assertEquals(projectName.get(0), PROJECT_NAME);
    }

    @Test
    public void testCreateFreestyleProjectWithDurationCheckbox() {
        final String durationPeriod = "minute";

        String periodCheckbox = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .selectDurationCheckbox(durationPeriod)
                .clickSaveButton()
                .gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getTimePeriod();

        Assert.assertEquals(periodCheckbox, durationPeriod);
    }

    @Test(dependsOnMethods = "testCreateProjectViaContentBlockButton")
    public void testAddDescription() {
        String description = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .editDescription(DESCRIPTION)
                .clickSubmitButton()
                .getDescription();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test
    public void testRenameProjectViaDropdown() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        final String newName = "New " + PROJECT_NAME;

        String actualProjectName = new HomePage(getDriver())
                .clickRenameInProjectDropdown(PROJECT_NAME)
                .clearInputFieldAndTypeName(newName)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, newName);
    }

    @Test
    public void testCheckSidebarMenuItemsOnProjectPage() {
        final List<String> templateSidebarMenu = List.of(
                "Статус", "Изменения", "Сборочная директория", "Собрать сейчас", "Настройки", "Удалить Проект", "Переименовать");

        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> actualSidebarMenu = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .getSidebarOptionList();

        Assert.assertEquals(actualSidebarMenu, templateSidebarMenu);
    }

    @Test
    public void testConfigureProjectAddBuildStepsExecuteShellCommand() {
        final String testCommand = "echo \"TEST! Hello Jenkins!\"";

        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String extractedText = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .clickAddBuildStep()
                .selectExecuteShellBuildStep()
                .addExecuteShellCommand(testCommand)
                .clickSaveButton()
                .clickSidebarConfigButton()
                .getTextExecuteShellTextArea();

        Assert.assertEquals(extractedText, testCommand);
    }

    @Test
    public void testDeleteProjectViaDropdown() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(PROJECT_NAME)
                .getWelcomeTitle();

        Assert.assertEquals(welcomeText, "Добро пожаловать в Jenkins!");
    }

    @Test(dataProvider = "providerUnsafeCharacters")
    public void testErrorMessageDisplayedForInvalidCharactersInProjectName(String unsafeCharacter) {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(unsafeCharacter)
                .selectFreestyleProject()
                .getInvalidNameMessage();

        Assert.assertTrue(errorMessage.contains("небезопасный символ"));
    }

    @Test
    public void testFreestyleProjectDescriptionPreview() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String descriptionPreview = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .editDescription(DESCRIPTION)
                .clickPreview()
                .getPreviewDescriptionText();

        Assert.assertEquals(descriptionPreview, DESCRIPTION);
    }

    @Test
    public void testJobNameSorting() {
        List<String> projectNames = List.of("aaa", "bbb", "aabb");
        projectNames.forEach(name -> TestUtils.createFreestyleProject(getDriver(), name));

        Boolean isSorted = new HomePage(getDriver())
                .isInAlphabeticalOrder();

        Assert.assertTrue(isSorted, "Projects is not sorted alphabetically");
    }

    @Test
    public void testNotificationBarAppears() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String notificationBar = new HomePage(getDriver())
                .selectBuildNowFromItemMenu(PROJECT_NAME)
                .getNotificationBarStatus();

        Assert.assertEquals(notificationBar, "Собрать сейчас: Done.");
    }

    @Test(dependsOnMethods = "testNotificationBarAppears")
    public void testCounterOfRunsIncrease() {
        String progressBar = new HomePage(getDriver())
                .selectBuildNowFromItemMenu(PROJECT_NAME)
                .refreshAfterBuild()
                .getNumberOfRuns();

        Assert.assertEquals(progressBar, "#2");
    }

    @Test
    public void testBuildHistoryIsEmpty() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> emptyHistory = new HomePage(getDriver())
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(emptyHistory.size(), 0);
    }

    @Test
    public void testUpdateAfterExecutingBuild() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> oneExecution = new HomePage(getDriver())
                .clickScheduleBuild(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(oneExecution.get(0), "stable");
        Assert.assertEquals(oneExecution.size(), 1);
    }

    @Test(dependsOnMethods = "testUpdateAfterExecutingBuild")
    public void testUpdateAfterChangingConfig() {
        List<String> changeConfig = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .addBuildStep("Run with timeout")
                .gotoHomePage()
                .clickScheduleBuild(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(changeConfig.size(), 2);
    }

    @Test
    public void testWorkspaceIsOpened() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String workspace = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebar()
                .clickWorkspaceSidebar()
                .getBreadCrumb();

        Assert.assertEquals(workspace, "Сборочная директория " + PROJECT_NAME + " на мастер");
    }

    @Test(dependsOnMethods = "testBuildHistoryIsEmpty")
    public void testDeleteViaBreadcrumbDropdown() {
        List<String> projectList = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .openBreadcrumbDropdown()
                .clickDeleteBreadcrumbDropdownAndConfirm()
                .getItemList();

        Assert.assertListNotContainsObject(projectList, PROJECT_NAME, "Project is not deleted.");
    }

    @Test
    public void testCreateFreestyleProjectFromExistingOne() {
        String secondProjectName = "Second" + PROJECT_NAME;
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> itemNameList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(secondProjectName)
                .enterName(PROJECT_NAME)
                .clickOkLeadingToCofigPageOfCopiedProject(new FreestyleConfigPage(getDriver()))
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(itemNameList.contains(secondProjectName));
    }
}
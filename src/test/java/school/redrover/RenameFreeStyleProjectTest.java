package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class RenameFreeStyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "FreeStyleProjectTest";
    private static final String PROJECT_NAME_EDITED = "FreeStyleProjectTestEdited";

    @Test
    public void testCorrectName() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String renamingResult = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .renameItem(PROJECT_NAME_EDITED)
                .getProjectName();

        Assert.assertEquals(renamingResult, PROJECT_NAME_EDITED);
    }

    @Test(dependsOnMethods = "testCorrectName")
    public void testTheSameName() {
        String theSameNameWarning = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME_EDITED)
                .renameItem(PROJECT_NAME_EDITED)
                .getRenameWarningMessage();

        Assert.assertEquals(theSameNameWarning, "Новое имя совпадает с прежним.");
    }

    @Test(dependsOnMethods = "testTheSameName")
    public void testEmptyName() {
        String emptyNameWarning = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME_EDITED)
                .renameItem("")
                .getRenameWarningMessage();

        Assert.assertEquals(emptyNameWarning, "Имя не указано");
    }
}

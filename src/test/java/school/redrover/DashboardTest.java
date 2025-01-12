package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.Comparator;
import java.util.List;

public class DashboardTest extends BaseTest {

    private static final List<String> PROJECT_NAMES = List.of("FPipelineProject", "APipelineProject", "ZPipelineProject");

    @Test
    public void testVerifyProjectOrderByNameASCByDefault() {
        PROJECT_NAMES.forEach(jobName -> TestUtils.createFreestyleProject(getDriver(), jobName));
        final List<String> expectedList = PROJECT_NAMES.stream().sorted(Comparator.naturalOrder()).toList();

        List<String> projectNameList = new HomePage(getDriver())
                .getItemList();

        Assert.assertEquals(projectNameList.size(), 3);
        Assert.assertEquals(projectNameList, expectedList);
    }

    @Test(dependsOnMethods = "testVerifyDisplayIconDownArrowNextToNameByDefault")
    public void testVerifyProjectOrderByNameDesc() {
        final List<String> expectedList = PROJECT_NAMES.stream().sorted(Comparator.reverseOrder()).toList();

        List<String> actualList = new HomePage(getDriver())
                .clickNameTableHeaderChangeOrder()
                .getItemList();

        Assert.assertEquals(actualList, expectedList);
    }

    @Test(dependsOnMethods = "testVerifyProjectOrderByNameASCByDefault")
    public void testVerifyDisplayIconDownArrowNextToNameByDefault() {
        String titleTableHeader = new HomePage(getDriver())
                .getTitleTableHeaderWithDownArrow();

        Assert.assertEquals(titleTableHeader, "Name");
    }

    @Test(dependsOnMethods = "testVerifyDisplayIconDownArrowNextToNameByDefault")
    public void testDisplayDownArrowOnSelectedColumnName() {

        String titleTableHeader = new HomePage(getDriver())
                .clickStatusTableHeaderChangeOrder()
                .getTitleTableHeaderWithUpArrow();

        Assert.assertEquals(titleTableHeader, "S");
    }

    @Test
    public void testLogOut() {
        String signInTitle = new HomePage(getDriver())
                .clickLogOut()
                .getSignInTitle();

        Assert.assertEquals(signInTitle, "Sign in to Jenkins");
    }

    @Test
    public void testNavigateCredentialsMenu() {
        String pageTitleText = new HomePage(getDriver())
                .openAdminDropdownMenu()
                .clickCredentialsAdminDropdownMenu()
                .getPageTitleText();

        Assert.assertEquals(pageTitleText, "Credentials");
    }


    @Test
    public void testAddDomainArrow() {
        String user = new HomePage(getDriver())
                .openAdminDropdownMenu()
                .clickCredentialsAdminDropdownMenu()
                .getUserName();

        Assert.assertFalse(user.isEmpty());
    }

    @Test
    public void testisDisplayedDomainElementDropdown() {
        boolean itemMenuDisplayed = new HomePage(getDriver())
                .openAdminDropdownMenu()
                .clickCredentialsAdminDropdownMenu()
                .clickDropdownMenu()
                .getDisplayedItemMenu();

        Assert.assertTrue(itemMenuDisplayed);
    }
}

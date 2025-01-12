package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.NewViewPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.List;

public class ViewTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";
    private static final String VIEW_NAME = "ViewName";

    @Test
    public void testCreateListViewForSpecificJob() {
        TestUtils.createPipelineProject(getDriver(), PIPELINE_NAME);

        List<String> viewList = new HomePage(getDriver())
                .clickCreateNewViewButton()
                .typeNameIntoInputField(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .selectJobCheckBoxByName(PIPELINE_NAME)
                .clickOkButton()
                .gotoHomePage()
                .getViewList();

        Assert.assertListContainsObject(
                viewList,
                VIEW_NAME,
                "New List View is displayed");
    }

    @Test
    public void testCreateNewViewForm() {
        TestUtils.createFolder(getDriver(), "NewFolder");

        NewViewPage newViewPage = new HomePage(getDriver())
                .gotoHomePage()
                .clickCreateNewViewButton();

        Assert.assertTrue(newViewPage.getInputFromNameField().isEmpty(), "Input field should be empty.");
        Assert.assertFalse(newViewPage.isRadioButtonListViewSelected(), "ListView radio button should not be selected.");
        Assert.assertFalse(newViewPage.isRadioButtonMyViewSelected(), "MyView radio button should not be selected.");
        Assert.assertFalse(newViewPage.isCreateButtonEnabled(), "Create button should be disabled.");
    }
}
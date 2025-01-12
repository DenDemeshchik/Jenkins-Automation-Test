package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class CreateNewItem1Test extends BaseTest {

    private static final String ITEM_NAME = "CreateNewItem";
    private static final String PIPELINE = "Pipeline";

    @Test
    public void testWithButton() {
        List<String> items = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ITEM_NAME)
                .selectFreestyleProjectAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(items.size(), 1);
        Assert.assertEquals(items.get(0), ITEM_NAME);
    }

    @Test
    public void testWithLinkInSidebar() {
        List<String> items = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PIPELINE)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(items.size(), 1);
        Assert.assertEquals(items.get(0), PIPELINE);
    }

    @Test(dependsOnMethods = "testWithLinkInSidebar")
    public void testCheckUniqueItemName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PIPELINE)
                .selectFreestyleProject()
                .getInvalidNameMessage();

        Assert.assertTrue(errorMessage.contains("» Задача с именем "));
    }

    @Test
    public void testCheckInvalidName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("<{]_  -&")
                .selectFreestyleProject()
                .getErrorMessage();

        Assert.assertTrue(errorMessage.contains("небезопасный символ"));
    }
}

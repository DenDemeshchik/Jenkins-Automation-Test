package school.redrover.page.manage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.manage.node.NodesPage;
import school.redrover.page.systemConfiguration.CloudsPage;
import school.redrover.page.CredentialsConfigurePage;
import school.redrover.page.systemConfiguration.PluginsPage;
import school.redrover.page.base.BasePage;
import school.redrover.page.user.UsersPage;

import java.util.List;

public class ManageJenkinsPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//a[@href='pluginManager']")
    private WebElement pluginsButton;

    @FindBy(xpath = "//a[@href='securityRealm/']")
    private WebElement usersButton;

    @FindBy(xpath = "//h2[@class='jenkins-section__title']")
    private List<WebElement> sections;

    @FindBy(xpath = "(//div[@class='jenkins-section__items'])[1]//dt")
    private List<WebElement> systemConfigItems;

    @FindBy(css = "input[id=settings-search-bar]")
    private WebElement searchInputField;

    @FindBy(css = "[class$='no-results-label']")
    private WebElement noResultLabel;

    @FindBy(css = "input[id=settings-search-bar]")
    private WebElement searchFieldForPressEnter;

    @FindBy(css = "a[href$='configureCredentials']")
    private WebElement configureCredentialsItem;

    @FindBy(className = "jenkins-search__results-item--selected")
    private WebElement selectedSearchResultItem;

    @FindBy(xpath = "//a[@href='appearance']")
    private WebElement appearanceButton;

    @FindBy(xpath = "//dt[.='Nodes']")
    private WebElement nodesButton;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return title.getText();
    }

    public AppearancePage clickAppearanceButton() {
        appearanceButton.click();

        return new AppearancePage(getDriver());
    }

    public NodesPage clickNodesButton() {
        nodesButton.click();

        return new NodesPage(getDriver());
    }

    public PluginsPage openPluginsPage() {
        pluginsButton.click();

        return new PluginsPage(getDriver());
    }

    public UsersPage openUsersPage() {
        usersButton.click();

        return new UsersPage(getDriver());
    }

    public List<String> getAllSections() {
        return sections.stream().map(WebElement::getText).toList();
    }

    public List<String> getSystemConfigurationItems() {
        return systemConfigItems.stream().map(WebElement::getText).toList();
    }

    public ManageJenkinsPage typeSearchInputField(String text) {
        getWait10().until(ExpectedConditions.visibilityOf(searchInputField)).sendKeys(text);

        return this;
    }

    public String getNoResultLabelText() {
        return getWait10().until(ExpectedConditions.visibilityOf(noResultLabel)).getText();
    }

    public CloudsPage pressEnterAfterInput(String text) {
        searchFieldForPressEnter.sendKeys(text);
        getWait5().until(ExpectedConditions.visibilityOf(selectedSearchResultItem));
        searchFieldForPressEnter.sendKeys(Keys.ENTER);

        return new CloudsPage(getDriver());
    }

    public ManageJenkinsPage switchFocusToSearchFieldAndTypeText(String text) {
         new Actions(getDriver()).sendKeys(Keys.chord("/", text)).perform();

        return this;
    }

    public ManageJenkinsPage clickOnSearchField() {
        searchInputField.click();

        return this;
    }

    public String getInputText() {
        return searchInputField.getAttribute("value");
    }

    public boolean isSearchSettingFieldDisplayed() {
        return searchInputField.isDisplayed();
    }

    public CredentialsConfigurePage clickConfigureCredentialsItem() {
        configureCredentialsItem.click();

        return new CredentialsConfigurePage(getDriver());
    }

    public String getAttribute(String attributeName) {
        return searchInputField.getAttribute(attributeName);
    }
}
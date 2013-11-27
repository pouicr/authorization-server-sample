package net.atos.contest.test.arquillian.cukespace;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.arquillian.ArquillianCucumber;
import cucumber.runtime.arquillian.api.Features;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.net.URL;

/**
 */
@RunWith(ArquillianCucumber.class)
@Features("src/test/resources/features/front-range-test.feature")
public class CukeRangeCreationTe_st {

    @Deployment (testable = false)
    public static Archive<?> buildArchive() {
        WebArchive webArchive = ShrinkWrap.create(MavenImporter.class)
                .loadPomFromFile("pom.xml")
                .importBuildOutput()
                .as(WebArchive.class)
                .addAsResource(new FileAsset(new File("src/test/resources/cards.csv")), "cards.csv")
                .addAsResource(new FileAsset(new File("src/test/resources/merchants.csv")), "merchants.csv")
                .addAsResource(new FileAsset(new File("src/test/resources/bin-ranges.csv")), "bin-ranges.csv")
                .addAsResource(new FileAsset(new File("src/test/resources/blacklist.csv")), "blacklist.csv");
        webArchive.toString(true);
        return webArchive;
    }

    @Drone
    private WebDriver browser;

    @ArquillianResource
    private URL deploymentUrl;

    private String userName;

    @FindBy(id = "login-form:login")
    private WebElement login;

    @FindBy(id = "login-form:password")
    private WebElement password;

    @FindBy(id = "login-form:submit-login")
    private WebElement loginButton;

    @FindByJQuery("span[id$='user-id']")
    private WebElement userField;


    @Given("^I visit login page$")
    public void I_visit_login_page() throws Throwable {
        browser.get(deploymentUrl + "login.xhtml");
    }

    @And("^I type \"([^\"]*)\" as login$")
    public void I_type_as_login(String arg1) throws Throwable {
        login.clear();
        login.sendKeys(arg1);
        userName = arg1;
    }

    @And("^\"([^\"]*)\" as password$")
    public void as_password(String arg1) throws Throwable {
        password.sendKeys(arg1);
    }

    @When("^I click on the login button$")
    public void click() throws Throwable {
        Graphene.guardHttp(loginButton).click();
    }

    @Then("^I should be login$")
    public void I_should_be_login() throws Throwable {
        Assert.assertTrue("User should be logged in!", userField.getText().contains(userName));
    }

    @Then("^I should not be login$")
    public void I_should_not_be_login() throws Throwable {
        Assert.assertTrue("User should be logged in!", userField.isDisplayed());
    }
}

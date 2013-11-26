package net.atos.contest.test.arquillian.webclient;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.net.URL;

/**
 */
@RunWith(Arquillian.class)
public class LoginTest {

    private static final String WEBAPP_SRC = "src/main/webapp";

    @Deployment(testable = false)
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
    WebDriver browser;

    @ArquillianResource
    URL deploymentUrl;

    @FindBy(id = "login-form:login")
    WebElement login;

    @FindBy(id = "login-form:password")
    WebElement password;

    @FindBy(id = "login-form:submit-login")
    private WebElement loginButton;

    @FindByJQuery("span[id$='user-id']")
    private WebElement userField;


    @Test
    public void should_login_successfully() {
        browser.get(deploymentUrl + "login.xhtml");

        login.sendKeys("pouic");
        password.sendKeys("bla");

        Graphene.guardHttp(loginButton).click();

        Assert.assertTrue("User should be logged in!",
                userField.getText().contains("pouic"));
    }
}

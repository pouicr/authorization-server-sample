package net.atos.contest.test.arquillian.warp;

import net.atos.xa.contest.dto.front.User;
import org.apache.deltaspike.jsf.api.listener.phase.AfterPhase;
import org.apache.deltaspike.jsf.api.listener.phase.JsfPhaseId;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.warp.Activity;
import org.jboss.arquillian.warp.Inspection;
import org.jboss.arquillian.warp.Warp;
import org.jboss.arquillian.warp.WarpTest;
import org.jboss.arquillian.warp.servlet.BeforeServlet;
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

import javax.inject.Inject;
import java.io.File;
import java.net.URL;

/**
 */
@RunWith(Arquillian.class)
@WarpTest
@RunAsClient
public class WarpLoginTest {

    @Deployment
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

    @Test
    public void should_login_successfully() {

        Warp
                .initiate(new Activity() {
                    public void perform() {
                        browser.get(deploymentUrl + "login.xhtml");
                        login.sendKeys("pouic");
                        password.sendKeys("bla");

                        Graphene.guardHttp(loginButton).click();
                    }
                })
                .inspect(new Inspection() {
                    private static final long serialVersionUID = 1L;

                    @Inject
                    User userField;

                    @BeforeServlet
                    public void beforeAction() {
                        Assert.assertEquals("User should be unknown!", "UNKNOWN", userField.getName());
                    }

                    @AfterPhase(JsfPhaseId.RENDER_RESPONSE)
                    public void afterEvent() {
                        Assert.assertEquals("User should be logged in!", "pouic", userField.getName());
                    }
                }
                );
    }
}

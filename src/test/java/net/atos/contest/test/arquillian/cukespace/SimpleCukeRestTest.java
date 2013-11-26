package net.atos.contest.test.arquillian.cukespace;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.arquillian.ArquillianCucumber;
import cucumber.runtime.arquillian.api.Features;
import org.apache.cxf.jaxrs.client.WebClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.runner.RunWith;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.Arrays;

/**
 */
@RunWith(ArquillianCucumber.class)
@Features("src/test/resources/features/rest-api.feature")
public class SimpleCukeRestTest {

    public static String SERVICE_URL = "http://localhost:8080/contest";


    @Deployment(testable = false)
    public static WebArchive buildArchive(){
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "contest.war")
                .addPackages(true, "net.atos.xa.contest")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource(new FileAsset(new File("src/test/resources/cards.csv")), "cards.csv")
                .addAsResource(new FileAsset(new File("src/test/resources/merchants.csv")), "merchants.csv")
                .addAsResource(new FileAsset(new File("src/test/resources/bin-ranges.csv")), "bin-ranges.csv")
                .addAsResource(new FileAsset(new File("src/test/resources/blacklist.csv")), "blacklist.csv")
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/persistence.xml")), "persistence.xml")
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/resources.xml")), "resources.xml")
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/openejb-jar.xml")), "openejb-jar.xml")
                .addAsLibraries(Maven.resolver()
                        .loadPomFromFile("pom.xml")
                        .importRuntimeDependencies()
                        .resolve()
                        .withTransitivity()
                        .asFile());

        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    WebClient client;

    Response r;


    @When("^I create a \"([^\"]*)\" request$")
    public void get(String uri){
        client = WebClient.create(SERVICE_URL+"/rest/"+uri, Arrays.asList(new JacksonJsonProvider()));
    }

    @Given("^I send the request$")
    public void ping() {
        client.type(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN);
        r = client.get();
    }

    @Then("^I should get \"([^\"]*)\"$")
    public void res(String expected){
        String ret = r.readEntity(String.class);
        r.close();
        Assert.assertEquals(expected,ret);
    }


}

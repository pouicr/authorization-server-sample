/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.contest.test.arquillian;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import junit.framework.Assert;
import net.atos.xa.contest.dto.AuthorizationRequest;
import net.atos.xa.contest.dto.AuthorizationResponse;
import org.apache.cxf.jaxrs.client.WebClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

@RunWith(Arquillian.class)
public class RestServerTest {

    public static String SERVICE_URL = "http://localhost:8080/contest";

    @Deployment (testable = false)
    public static Archive<?> buildArchive(){
        return ShrinkWrap.create(MavenImporter.class)
                .loadPomFromFile("pom.xml")
                .importBuildOutput()
                .as(WebArchive.class);
    }

    /*@Deployment (testable = false)
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
    }*/

    @Test
    public void ping(){
        WebClient client = WebClient.create(SERVICE_URL+"/rest/ping", Arrays.asList(new JacksonJsonProvider()));
        client.type(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN);
        Response r = client.get();
        String ret = r.readEntity(String.class);
        r.close();
        Assert.assertEquals(Response.Status.OK.getReasonPhrase(),r.getStatusInfo().getReasonPhrase());
        Assert.assertEquals("pong",ret);
    }

    @Test
    public void rest(){
        WebClient client = WebClient.create(SERVICE_URL+"/rest/reset", Arrays.asList(new JacksonJsonProvider()));
        client.type(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN);
        Response r = client.get();
        String ret = r.readEntity(String.class);
        Assert.assertEquals(Response.Status.OK.getReasonPhrase(),r.getStatusInfo().getReasonPhrase());
        Assert.assertEquals("ok",ret);
    }


    @Test
    public void authorizedFromFile() throws IOException, ParseException {
        WebClient client = WebClient.create(SERVICE_URL+"/rest/authorized", Arrays.asList(new JacksonJsonProvider())).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);

        String sCurrentLine;
        BufferedReader br = new BufferedReader(new FileReader(Thread.currentThread().getContextClassLoader().getResource("authorizationTest.csv").getFile()));

        while ((sCurrentLine = br.readLine()) != null) {
            String[] data = sCurrentLine.split(",");
            AuthorizationRequest authorizationRequest = new AuthorizationRequest(data[0],data[1],Integer.valueOf(data[2]),Integer.valueOf(data[3]), new StdDateFormat().parse(data[4]),data[5]);
            Response r = client.post(authorizationRequest);
            Assert.assertEquals(Response.Status.OK.getReasonPhrase(),r.getStatusInfo().getReasonPhrase());
            AuthorizationResponse authorizationResponse = r.readEntity(AuthorizationResponse.class);
            Assert.assertEquals(data[6], authorizationResponse.getResponseCode());
        }
    }
}

package net.atos.contest.test.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;

import java.io.File;

/**
 */
@RunWith(Arquillian.class)
public class LoginTest {

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


}

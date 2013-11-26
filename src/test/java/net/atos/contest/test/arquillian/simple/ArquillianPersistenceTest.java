package net.atos.contest.test.arquillian.simple;


import net.atos.xa.contest.domain.Card;
import net.atos.xa.contest.repository.CardRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.DataSeedStrategy;
import org.jboss.arquillian.persistence.SeedDataUsing;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

/**
 */
@RunWith(Arquillian.class)
public class ArquillianPersistenceTest {

    @Deployment
    public static Archive<?> buildArchive(){
        return ShrinkWrap.create(WebArchive.class, "contest-persist.war")
                .addPackages(true, "net.atos.xa.contest.domain")
                .addClass("net.atos.xa.contest.repository.CardRepository")
                .addClass("net.atos.xa.contest.repository.EmP")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/persistence.xml")), "persistence.xml")
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/resources.xml")), "resources.xml")
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/openejb-jar.xml")), "openejb-jar.xml")
                .addAsLibraries(Maven.resolver()
                        .loadPomFromFile("pom.xml")
                        .importRuntimeDependencies()
                        .resolve()
                        .withTransitivity()
                        .asFile());
    }

    @Inject
    CardRepository cardRepository;

    @Test
    @UsingDataSet("cards.yml")
    public void checkDb(){
        List<Card> list = cardRepository.findAll();
        displayList(list);
        Assert.assertEquals(5, list.size());
    }

    @Test
    @SeedDataUsing(DataSeedStrategy.CLEAN_INSERT)
    @UsingDataSet("cards.yml")
    @ShouldMatchDataSet(value = "cards.yml", orderBy = { "cardNumber"})
    public void checkAssertDb(){
        System.out.println("assert===============");
        displayList(cardRepository.findAll());
    }


    private void displayList(List<Card> list) {
        for (Card card : list) {
            System.out.println("########");
            System.out.println(card);
        }
    }
}

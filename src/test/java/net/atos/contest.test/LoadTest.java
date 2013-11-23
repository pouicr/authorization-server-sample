/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.contest.test;

import junit.framework.Assert;
import net.atos.xa.contest.repository.BinRangeRepository;
import net.atos.xa.contest.repository.BlacklistRepository;
import net.atos.xa.contest.repository.CardRepository;
import net.atos.xa.contest.repository.MerchantRepository;
import org.apache.openejb.core.LocalInitialContextFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;

public class LoadTest {

    @Inject
    CardRepository cardRepository;

    @Inject
    MerchantRepository merchantRepository;

    @Inject
    BinRangeRepository binRangeRepository;

    @Inject
    BlacklistRepository blacklist;

    private static EJBContainer container;

    @BeforeClass
    public static void init() throws NamingException {
        System.setProperty("cards",Thread.currentThread().getContextClassLoader().getResource("cards.csv").getPath());
        System.setProperty("merchants",Thread.currentThread().getContextClassLoader().getResource("merchants.csv").getPath());
        System.setProperty("bin-ranges",Thread.currentThread().getContextClassLoader().getResource("bin-ranges.csv").getPath());
        System.setProperty("blacklist",Thread.currentThread().getContextClassLoader().getResource("blacklist.csv").getPath());
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, LocalInitialContextFactory.class.getName());
        container = EJBContainer.createEJBContainer();
    }

    @Before
    public void inject() throws NamingException {
        container.getContext().bind("inject", this);
    }

    @Test
    public void loadTest(){
        System.out.println("should be done :)");

        Assert.assertNotNull(cardRepository.findAll());
        Assert.assertNotNull(merchantRepository.findAll());
        Assert.assertNotNull(binRangeRepository.findAll());
        Assert.assertNotNull(blacklist.getBlackList());
    }



    @AfterClass
    public static void close(){
        container.close();
    }
}

/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.contest.test.embedded;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import junit.framework.Assert;
import net.atos.xa.contest.business.AuthorizationManager;
import net.atos.xa.contest.dto.AuthorizationRequest;
import org.apache.openejb.core.LocalInitialContextFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

public class CryptoTest {

    @Inject
    AuthorizationManager authorizationManager;

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
    public void cryptoTest() throws IOException, ParseException {
        String sCurrentLine;
        BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("authorizationTest.csv")));
        System.out.println("###############");
        while ((sCurrentLine = br.readLine()) != null) {
            String[] data = sCurrentLine.split(",");
            AuthorizationRequest authorizationRequest = new AuthorizationRequest(data[0],data[1],Integer.valueOf(data[2]),Integer.valueOf(data[3]), new StdDateFormat().parse(data[4]),data[5]);
            StringBuilder sb = new StringBuilder();
            sb.append(authorizationRequest.getCardNumber());
            sb.append(authorizationRequest.getExpiryDate());
            sb.append(authorizationRequest.getAmount());
            sb.append(authorizationRequest.getMerchantId());

            StringBuilder sb0 = new StringBuilder();
            sb0.append(data[0]);
            sb0.append(data[1]);
            sb0.append(data[2]);
            sb0.append(data[3]);

            String cryptogram = authorizationManager.crypt(sb.toString());
            String cryptogram0 = authorizationManager.crypt(sb0.toString());

            Assert.assertEquals(cryptogram,cryptogram0);

            System.out.println("Author n°"+data[7]+" = "+sb.toString()+" => "+ cryptogram);
            System.out.println("###############");
        }
    }



    @AfterClass
    public static void close(){
        container.close();
    }
}

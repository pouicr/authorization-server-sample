/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.contest.test;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import junit.framework.Assert;
import net.atos.xa.contest.business.AuthorizationManager;
import net.atos.xa.contest.dto.AuthorizationRequest;
import net.atos.xa.contest.dto.AuthorizationResponse;
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
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LocalAuthorizationTest {

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
    public void authorizedFromFile() throws IOException, ParseException {
        String sCurrentLine;
        BufferedReader br = new BufferedReader(new FileReader(Thread.currentThread().getContextClassLoader().getResource("authorizationTest.csv").getFile()));
        List<Integer> previousAN = new ArrayList<Integer>();
        while ((sCurrentLine = br.readLine()) != null) {
            String[] data = sCurrentLine.split(",");
            AuthorizationRequest authorizationRequest = new AuthorizationRequest(data[0],data[1],Integer.valueOf(data[2]),Integer.valueOf(data[3]), new StdDateFormat().parse(data[4]),data[5]);
            System.out.println("Bean side request = "+authorizationRequest);
            AuthorizationResponse authorizationResponse = authorizationManager.authorized(authorizationRequest);
            System.out.println("Bean side response = " + authorizationResponse);
            Assert.assertEquals("unexpected responseCode : ",data[6],authorizationResponse.getResponseCode());
            Assert.assertFalse("AuthorizationNumber should be different : ",previousAN.contains(authorizationResponse.getAuthorizationNumber()));
            if( authorizationResponse.getResponseCode().equals("000")){
                previousAN.add(authorizationResponse.getAuthorizationNumber());
            } else {
                Assert.assertEquals("When responseCode != 000 (error occured), authorizationNumber should be '0'",new Integer(0),authorizationResponse.getAuthorizationNumber());
            }
        }
    }




    @AfterClass
    public static void close(){
        container.close();
    }
}

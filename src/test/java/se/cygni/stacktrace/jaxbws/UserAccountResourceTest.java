package se.cygni.stacktrace.jaxbws;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;


public class UserAccountResourceTest { 

    private static SelectorThread server;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("com.sun.jersey.config.property.packages", "se.cygni.stacktrace.jaxbws");
        server = GrizzlyWebContainerFactory.create(new URI("http://localhost:8080/"), params);
    }

    @AfterClass
    public static void afterClass() {
        server.stopEndpoint();
    }
        
    @Test
    public void testXML() throws Exception {
        Client client = Client.create();
        WebResource resource = client.resource("http://localhost:8080/accounts");
        UserAccounts accounts = resource.accept("application/xml").get(UserAccounts.class);
        
        assertAccounts(accounts);
    }
    
    @Test
    public void testJSON() throws Exception {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        Client client = Client.create(config);
        WebResource resource = client.resource("http://localhost:8080/accounts");
        UserAccounts accounts = resource.accept("application/json").get(UserAccounts.class);
        
        assertAccounts(accounts);
    }
    
    public static void assertAccounts(UserAccounts accounts) {
        Assert.assertNotNull(accounts);
        Assert.assertFalse(accounts.getUserAccount().isEmpty());
        Assert.assertEquals(2, accounts.getUserAccount().size());

        UserAccount account1 = accounts.getUserAccount().get(0);
        Assert.assertEquals(1, account1.getId());
        Assert.assertEquals("account1@localhost", account1.getEmail());
        Assert.assertEquals(2, account1.getServices().getService().size());
        Assert.assertTrue(account1.getServices().getService().contains("music"));        
    }
}

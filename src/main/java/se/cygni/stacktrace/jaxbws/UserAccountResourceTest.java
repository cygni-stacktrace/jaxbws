package se.cygni.stacktrace.jaxbws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import com.sun.jersey.json.impl.provider.entity.JSONObjectProvider;
import com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider;


public class UserAccountResourceTest {
  
    private static SelectorThread threadSelector;

    @BeforeClass
    public static void beforeClass() throws IOException, URISyntaxException {
        final Map<String, String> initParams = new HashMap<String, String>();

        initParams.put("com.sun.jersey.config.property.packages",
                "se.cygni.stacktrace.jaxbws");

        threadSelector = GrizzlyWebContainerFactory.create(new URI("http://localhost:8080/"), initParams);
    }

    @AfterClass
    public static void afterClass() {
        threadSelector.stopEndpoint();
    }
        
    @Test
    public void testXML() throws Exception {
        Client client = Client.create();
        WebResource resource = client.resource("http://localhost:8080/accounts");
        UserAccounts accountsAfterRoundtrip = resource.accept("application/xml").get(UserAccounts.class);
        
        Assert.assertNotNull(accountsAfterRoundtrip);
        Assert.assertFalse(accountsAfterRoundtrip.getUserAccount().isEmpty());
        Assert.assertEquals(2, accountsAfterRoundtrip.getUserAccount().size());

        final UserAccount accountAfterRoundtrip1 = accountsAfterRoundtrip.getUserAccount().get(0);
        Assert.assertEquals(1, accountAfterRoundtrip1.getId());
        Assert.assertEquals("account1@localhost", accountAfterRoundtrip1.getEmail());
        Assert.assertEquals(2, accountAfterRoundtrip1.getServices().getService()
                .size());
        Assert.assertTrue(accountAfterRoundtrip1.getServices().getService().contains("music"));
    }

    
    @Test
    public void testJSON() throws Exception {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        Client client = Client.create(config);
        WebResource resource = client.resource("http://localhost:8080/accounts");
        UserAccounts accountsAfterRoundtrip = resource.accept("application/json").get(UserAccounts.class);
        
        Assert.assertNotNull(accountsAfterRoundtrip);
        Assert.assertFalse(accountsAfterRoundtrip.getUserAccount().isEmpty());
        Assert.assertEquals(2, accountsAfterRoundtrip.getUserAccount().size());

        final UserAccount accountAfterRoundtrip1 = accountsAfterRoundtrip.getUserAccount().get(0);
        Assert.assertEquals(1, accountAfterRoundtrip1.getId());
        Assert.assertEquals("account1@localhost", accountAfterRoundtrip1.getEmail());
        Assert.assertEquals(2, accountAfterRoundtrip1.getServices().getService()
                .size());
        Assert.assertTrue(accountAfterRoundtrip1.getServices().getService().contains("music"));
    }

}

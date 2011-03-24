package se.cygni.stacktrace.jaxbws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("com.sun.jersey.config.property.packages", "se.cygni.stacktrace.jaxbws");
        SelectorThread ws = GrizzlyWebContainerFactory.create(new URI("http://localhost:8080/"), params);
        System.out.println("Tryck return för att avsluta Jersey ...");
        System.in.read();
        ws.stopEndpoint();
    }
}

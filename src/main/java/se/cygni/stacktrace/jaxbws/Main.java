package se.cygni.stacktrace.jaxbws;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.ServerSocketChannel;
import java.util.HashMap;
import java.util.Map;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("com.sun.jersey.config.property.packages", "se.cygni.stacktrace.jaxbws");
        SelectorThread threadSelector = GrizzlyWebContainerFactory.create(new URI("http://localhost:8080/"), initParams);
        System.out.println("Tryck return för att avsluta Jersey servern ...");
        System.in.read();
        threadSelector.stopEndpoint();
    }
}

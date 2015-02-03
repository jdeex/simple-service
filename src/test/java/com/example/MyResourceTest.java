package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    
    
    
    
    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void sayPlainTextHello() {
        String responseMsg = target.path("myresource").request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
        assertEquals("Hello Jersey", responseMsg);
    }
    
    
    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void sayXMLHello() {
        String responseMsg = target.path("myresource").request(javax.ws.rs.core.MediaType.TEXT_XML).get(String.class);
        assertEquals("<?xml version=\"1.0\"?><hello> Hello Jersey</hello>", responseMsg);
    }
    
    
    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void sayHtmlHello() {
        String responseMsg = target.path("myresource").request().get(String.class);
        assertEquals("<html><title>Hello Jersey</title><body><h1>Hello Jersey</body></h1></html>", responseMsg);
    }
    
    
    @Test
    public void getCountPlainText(){
        int responseMsg = target.path("myresource/task/count").request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(Integer.class);
        assertNotNull(responseMsg);
    }
    
    
    @Test
    public void getAllTasks(){
        Collection<Task> tasks = target.path("myresource/task/all").request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(Collection.class);
        assertNotNull(tasks);
    }   
    
    
    @Test 
    public void getTaskInfoByID(){
        Task responseMsg = target.path("myresource/task/1").request(MediaType.APPLICATION_XML).get(Task.class);
        assertNull(responseMsg);
        
        Task t = new Task();
        t.setId("1");
        t.setDescripcion("Tarea de Prueba");        
        
        Response responsePost = target.path("myresource/task").request(MediaType.APPLICATION_XML).post(Entity.entity(t, MediaType.APPLICATION_XML),Response.class);
        assertNotNull(responsePost);
        
        responseMsg = target.path("myresource/task/1").request(MediaType.APPLICATION_XML).get(Task.class);
        assertNotNull(responseMsg);
                
    }
    
    
    @Test
    public void updateTask(){
        Task responseTask = target.path("myresource/task/1").request(MediaType.APPLICATION_XML).get(Task.class);
        assertNotNull(responseTask);
        assertEquals("1",responseTask.getId());
        
        responseTask.setAsignadoA("Anakin Skywalker");
        
        Response responsePut = target.path("myresource/task").request(MediaType.APPLICATION_XML).put(Entity.entity(responseTask, MediaType.APPLICATION_XML),Response.class);
        assertNotNull(responsePut);
        
        Task updatedTask = target.path("myresource/task/1").request(MediaType.APPLICATION_XML).get(Task.class);
        assertEquals("Anakin Skywalker", updatedTask.getAsignadoA());        
    }
    
    
    @Test
    public void deleteTask(){
        Response responseDel = target.path("myresource/task/1").request(MediaType.TEXT_PLAIN).delete(Response.class);
        assertEquals(200, responseDel.getStatus());
    }
}

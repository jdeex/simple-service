package com.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource
{

    @Context
    UriInfo uriInfo;
    
    private static Map<String,Task> hmTasks = new HashMap<String, Task>();
    
    // /**
    // * Method handling HTTP GET requests. The returned object will be sent
    // * to the client as "text/plain" media type.
    // *
    // * @return String that will be returned as a text/plain response.
    // */
    // @GET
    // @Produces(MediaType.TEXT_PLAIN)
    // public String getIt() {
    // return "Got it!";
    // }

    // This method is called if TEXT_PLAIN is request
    @GET
    @Produces (MediaType.TEXT_PLAIN)
    public String sayPlainTextHello()
    {
        return "Hello Jersey";
    }
    

    // This method is called if XML is request
    @GET
    @Produces (MediaType.TEXT_XML)
    public String sayXMLHello()
    {
        return "<?xml version=\"1.0\"?><hello> Hello Jersey</hello>";
    }

    
    // This method is called if HTML is request
    @GET
    @Produces (MediaType.TEXT_HTML)
    public String sayHtmlHello()
    {
        return "<html><title>Hello Jersey</title><body><h1>Hello Jersey</body></h1></html>";
    }
    
    
    @GET
    @Path ("task/count")
    @Produces (MediaType.TEXT_PLAIN)
    public int getCountPlainText()
    {
        return hmTasks.size();
    }
    
    
    @GET
    @Path ("task/all")
    @Produces (MediaType.APPLICATION_XML)
    public Collection<Task> getAllTasks()
    {

        return hmTasks.values();
    }    
    
    
    @GET
    @Path ("task/{id}")
    @Produces (MediaType.APPLICATION_XML)
    public Task getTaskInfoByID(@PathParam("id") String id)
    {
        if (id == null || "".equals(id.trim()) || !hmTasks.containsKey(id))
        {
            System.out.println("[getTaskInfoByID] No se encontro la tarea solicitada ID :" + id);
            return null;
        }
        else
        {
            return hmTasks.get(id);
        }
    }
      
    
    @POST
    @Path("task")
    @Consumes(MediaType.APPLICATION_XML)
    public Response newTask(Task nt)
    {
        Response resp;

        System.out.println("[newTask] Se va a crear una nueva tarea");

        String keyNt = nt.getId();
        if (keyNt == null || "".equals(keyNt.trim()) || hmTasks.containsKey(keyNt))
        {
            resp = Response.notModified().build();
        }
        else
        {
            hmTasks.put(keyNt, nt);
            System.out.println("[newTask] Creada una nueva tarea con id: " + keyNt);
            // resp = Response.ok(nt).build();
            resp = Response.created(uriInfo.getAbsolutePath()).build(); // FIXME como devolver la URI al nuevo recurso recien creado??
        }

        return resp;
    }
    
    
//    @PUT
//    @Path("task/{id}")
//    @Consumes(MediaType.TEXT_PLAIN)
//    public Response updateTask(@PathParam ("id") String id, @PathParam("assign") String assingTo)
//    {
//        Response resp = Response.notModified().build();
//        
//        if (id != null && !"".equals(id.trim()) && hmTasks.containsKey(id))
//        {         
//            Task t = hmTasks.get(id);
//            
//            t.setAsignadoA(assingTo);
//            t.setEstado("Assigned");
//            
//            hmTasks.remove(id);
//            hmTasks.put(id, t);
//            
//            System.out.println("[newTask] Actualizada tarea con id: " + id);
//
//            resp = Response.ok(t).build();
//        }        
//        
//        return resp;
//    }
    
    
    @PUT
    @Path("task")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateTask(Task ut)
    {
        Response resp = Response.notModified().build();
        
        String id = ut.getId();

        if (id != null && !"".equals(id.trim()) && hmTasks.containsKey(id))
        {
            ut.setEstado("Assigned");

            hmTasks.remove(id);
            hmTasks.put(id, ut);

            System.out.println("[newTask] Actualizada tarea con id: " + id);

            resp = Response.ok(ut).build();
        }

        return resp;
    }  
    
    
    @DELETE
    @Path("task/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteTask(@PathParam("id") String id){
        Response resp = Response.noContent().build();
        
        if (id != null && !"".equals(id.trim()) && hmTasks.containsKey(id))
        {
            hmTasks.remove(id);
            System.out.println("[newTask] Eliminada tarea con id: " + id);

            resp = Response.ok().build();
        }
        
        return resp;
    }
    

}

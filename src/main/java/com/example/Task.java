package com.example;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Task")
public class Task implements Serializable
{
    private String id;
    private String descripcion;
    private String asignadoA;
    private String estado = "Not Assigned";
    
    
    /**
     * 
     * Constructor.
     *
     */
    public Task()
    {
        
    }
    
    /**
     * 
     * Constructor.
     * 
     * @param id
     * @param descripcion
     */
    public Task(String id, String desc)
    {
        this.id = id;
        this.descripcion = desc;        
    }
    
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public String getAsignadoA()
    {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA)
    {
        this.asignadoA = asignadoA;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }
}

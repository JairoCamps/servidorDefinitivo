/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity.service;

import app.entity.Categoria;
import app.entity.Serie;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ofviak
 */
@Stateless
@Path("app.entity.categoria")
public class CategoriaFacadeREST extends AbstractFacade<Categoria> {

    @PersistenceContext(unitName = "ServerTestingPU")
    private EntityManager em;

    public CategoriaFacadeREST() {
        super(Categoria.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Categoria entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Categoria entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Categoria find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoria> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoria> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    //Servicio para actualizar la tabla dependiendo del valor que tenga el selectOneMenu
    @POST
    @Path("categoriaByNombre/{nombre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Categoria createCategoriaByNombre(@PathParam("nombre") String nombre){
        Categoria c=new Categoria();
        c.setNombre(nombre);
        try{
            this.create(c);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return c;
    }
    
    //Servicio para encontrar una categoria por nombre
    @POST
    @Path("findCategoriaByName/{nombre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Categoria findCategoriaByNombre(@PathParam("nombre") String nombre){
        Categoria c = null;
        Query q = em.createNamedQuery("Categoria.findByNombre");
        q.setParameter("nombre", nombre);
        try{
            c = (Categoria) q.getSingleResult();
        }catch(Exception e){e.getMessage();}
        
        return c;
    }
    
    //Servicio para encontrar el id de una categoria por nombre
    @POST
    @Path("findIdCategoriaByName/{nombre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Integer findIdCategoriaByNombre(@PathParam("nombre") String nombre){
        Categoria c = null;
        Query q = em.createNamedQuery("Categoria.findByNombre");
        q.setParameter("nombre", nombre);
        try{
            c = (Categoria) q.getSingleResult();
        }catch(NoResultException e){return null;}
        
        return c.getIdCategoria();
    }
    
    //Servicio para encontrar las listas asociadas a una categoria por nombre
    @POST
    @Path("findSeriesByCategoriaName/{nombre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Serie> findSeriesByCategoriaName(@PathParam("nombre") String nombre){
        Categoria categoria = findCategoriaByNombre(nombre);
        
        Query q = em.createQuery("SELECT s FROM Serie s JOIN s.categoriaserieCollection csc, csc.categoriaidCategoria c WHERE c.idCategoria = :idCategoria ORDER BY s.fecha ASC");
        q.setParameter("idCategoria",categoria.getIdCategoria());
        
        return q.getResultList();
    }
    
    //Servicio para borrar la categoria por el nombre
    @POST
    @Path("deleteCategoriaByName/{nombre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Boolean deleteCategoriaByName(@PathParam("nombre") String nombre){
        try{
            Categoria c = findCategoriaByNombre(nombre);
            remove(c);
        }catch(Exception e){return false;}
        
        return true;
    }
    
    //Servicio que devuelve una lista de categorias ordenadas por orden
    @POST
    @Path("selectCategoriaOrdenado")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoria> selectCategoriaOrdenado(){
        Query q = em.createQuery("SELECT c FROM Categoria c ORDER BY c.nombre ASC");
        
        return q.getResultList();
    }
    
    //Servicio para buscar categoria por idSerie
    @POST
    @Path("selectCategoriaByIdSerie/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoria> selectCategoriaByIdSerie(@PathParam("id") Integer id){
        Query q = em.createQuery("SELECT c FROM Categoria c JOIN c.categoriaserieCollection csc, csc.serieidSerie s WHERE s.idSerie = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }
    
    //Servicio para buscar una categoria por idCategoria
    @POST
    @Path("findCategoriaByIdCategoria/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Categoria findCategoriaByIdCategoria(@PathParam("id") Integer id){
        Query q = em.createQuery("SELECT c FROM Categoria c WHERE c.idCategoria = :id");
        q.setParameter("id", id);
        
        Categoria c = (Categoria) q.getSingleResult();
        return c;
    }
    
}

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
@Path("app.entity.serie")
public class SerieFacadeREST extends AbstractFacade<Serie> {

    @PersistenceContext(unitName = "ServerTestingPU")
    private EntityManager em;

    public SerieFacadeREST() {
        super(Serie.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Serie entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Serie entity) {
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
    public Serie find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Serie> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Serie> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    
    //----------------------------- NUESTROS SERVICIOS ----------------------------------------------------------
    
    //Servicio para actualizar la tabla dependiendo del valor que tenga el selectOneMenu
    @GET
    @Path("seriesByIdCategoria/{idCategoria}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Serie> findSeriesByIdCategoria(@PathParam("idCategoria") Integer idCategoria){
        List<Serie> listaSerie;
        Query q = this.em.createQuery("SELECT s FROM Serie s JOIN s.categoriaCollection c WHERE c.idCategoria=:idCategoria");
        q.setParameter("idCategoria", idCategoria);
        try{
            listaSerie = (List<Serie>) q.getResultList();
        }catch (NoResultException e){
            listaSerie = null;
        }
        
        return listaSerie;
    }
    
    //Servicio para encontrar una serie por su nombre
    @GET
    @Path("serieByNombre/{nombre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Serie findSerieByNombre (@PathParam("nombre") String nombre){
        Serie s;
        Query q = this.em.createQuery("SELECT s FROM Serie s WHERE s.nombre = :nombre");
        q.setParameter("nombre", nombre);
        try{
            s = (Serie) q.getSingleResult();
        }catch (NoResultException e){
            s = null;
        }       
        return s;
    }
    
    //Servicio para encontrar las categorias de una serie
    @GET
    @Path("categoriasByIdSerie/{idSerie}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoria> findCategoriasByIdSerie (@PathParam("idSerie") Integer idSerie){
        List<Categoria> listaCategorias;
        Query q = this.em.createQuery("SELECT c FROM Categoria c JOIN c.serieCollection s WHERE s.idSerie = :idSerie");
        q.setParameter("idSerie", idSerie);
        try{
            listaCategorias = (List<Categoria>) q.getResultList();
        }catch (NoResultException e){
            listaCategorias = null;
        }
        
        return listaCategorias;
    }
    
}

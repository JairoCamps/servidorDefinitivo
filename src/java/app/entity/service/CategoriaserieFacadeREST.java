/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity.service;

import app.entity.Categoria;
import app.entity.Categoriaserie;
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
@Path("app.entity.categoriaserie")
public class CategoriaserieFacadeREST extends AbstractFacade<Categoriaserie> {

    @PersistenceContext(unitName = "ServerTestingPU")
    private EntityManager em;

    public CategoriaserieFacadeREST() {
        super(Categoriaserie.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Categoriaserie entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Categoriaserie entity) {
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
    public Categoriaserie find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoriaserie> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoriaserie> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //===========ENCONTRAR LAS CATEGORIAS DE UNA SERIE
    
    @GET
    @Path("categoriasByIdSerieIntermedio/{idSerie}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoria> findCategoriasByIdSerieIntermedio(@PathParam("idSerie") Integer idSerie){
        List<Categoria> listaCategorias;
        Query q = this.em.createQuery("SELECT cs.categoriaidCategoria FROM Categoriaserie cs WHERE cs.serieidSerie.idSerie = :idSerie");
        q.setParameter("idSerie", idSerie);
        try{
            listaCategorias = (List<Categoria>) q.getResultList();
        }catch (NoResultException e){
            listaCategorias = null;
        }
        
        return listaCategorias;
    }
    
    //=============ENCONTRAR LAS SERIES DE UNA CATEGORIA
    @GET
    @Path("seriesByIdCategoriaIntermedio/{idCategoria}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Serie> findSeriesByIdCategoriaIntermedio(@PathParam("idCategoria") Integer idCategoria){
        List<Serie> listaSeries;
        Query q = this.em.createQuery("SELECT cs.serieidSerie FROM Categoriaserie cs WHERE cs.categoriaidCategoria.idCategoria = :idCategoria");
        q.setParameter("idCategoria", idCategoria);
        try{
            listaSeries = (List<Serie>) q.getResultList();
        }catch (NoResultException e){
            listaSeries = null;
        }
        
        return listaSeries;
    }
    //===========ENCONTRAR LAS CATEGORIASERIE DE ESA SERIE
    
    @GET
    @Path("intermediaByIdSerie/{idSerie}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoriaserie> findIntermediaByIdSerie(@PathParam("idSerie") Integer idSerie){
        List<Categoriaserie> listaCs;
        Query q = this.em.createQuery("SELECT cs FROM Categoriaserie cs WHERE cs.serieidSerie.idSerie = :idSerie");
        q.setParameter("idSerie", idSerie);
        try{
            listaCs = (List<Categoriaserie>) q.getResultList();
        }catch (NoResultException e){
            listaCs = null;
        }
        
        return listaCs;
    }
    
}

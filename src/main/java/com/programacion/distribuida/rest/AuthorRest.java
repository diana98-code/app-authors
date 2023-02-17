package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Authors;
import com.programacion.distribuida.servicios.AuthorRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorRest {

    @GET
    @Path("/{id}")
    @Operation(summary = "Devuelve Autor por id")
    @APIResponse(responseCode = "404", description = "NOT FOUND")
    @APIResponse(responseCode = "200",description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Authors.class)))
    public Authors findById(@Parameter(description = "Id Autor buscado", required = true) @PathParam("id") Integer id) {
        return repository.findById(id);
    }

    @Inject AuthorRepository repository;

    @GET
    @Operation(summary = "Lista de Autores")
    @APIResponse(responseCode = "404", description = "NOT FOUND")
    @APIResponse(responseCode = "200",description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Authors.class,
                    type = SchemaType.ARRAY)))
    public List<Authors> findAll() {
        return repository
                .findAll()
                .list();
    }

    @POST
    @Operation(summary = "Crea Autor", description = "Crea un Autor")
    public void insert(@RequestBody(description = "Autor a crear", required = true,
                                    content = @Content(schema = @Schema(implementation = Authors.class))) Authors obj) {
        repository.persist(obj);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualiza Autor", description = "Actualiza un Autor por id")
    public void update(@RequestBody(description = "Autor a actualizar", required = true,
                                    content = @Content(schema = @Schema(implementation = Authors.class))) Authors obj,
                       @Parameter(description = "Id Autor a actualizar", required = true) @PathParam("id") Integer id) {

        var author = repository.findById(id);

        author.setFirtName(obj.getFirtName());
        author.setLastName(obj.getLastName());
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Elimina Autor", description = "Elimina Autor por id")
    public void delete(@Parameter(description = "Id Autor a eliminar", required = true) @PathParam("id") Integer id ) {
        repository.deleteById(id);
    }
}

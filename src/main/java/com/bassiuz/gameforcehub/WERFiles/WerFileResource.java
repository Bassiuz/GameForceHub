package com.bassiuz.gameforcehub.WERFiles;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.jaxrs.PathParam;


@Path("/WerFiles")
public class WerFileResource {

    private final WerFileRepository werFileRepository;

    public WerFileResource(WerFileRepository werFileRepository) {
        this.werFileRepository = werFileRepository;
    }

    @GET
    @Produces("application/json")
    public Iterable<WerFile> findAll() {
        return werFileRepository.findAll();
    }

    @POST
    @Path("/name/{name}")
    @Produces("application/json")
    public WerFile create(@PathParam String name) {
        return werFileRepository.save(new WerFile(name));
    }

}

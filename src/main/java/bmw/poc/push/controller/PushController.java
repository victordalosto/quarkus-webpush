package bmw.poc.push.controller;

import bmw.poc.push.service.PushService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/push")
@ApplicationScoped
public class PushController {

    @Inject
    private PushService pushService;

    @GET
    @Path("/{id}")
    public void push(@PathParam("id") String id) {
        pushService.pushMessage(id);
    }


}

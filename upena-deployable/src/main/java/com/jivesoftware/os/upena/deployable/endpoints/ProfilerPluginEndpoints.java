package com.jivesoftware.os.upena.deployable.endpoints;

import com.jivesoftware.os.mlogger.core.MetricLogger;
import com.jivesoftware.os.mlogger.core.MetricLoggerFactory;
import com.jivesoftware.os.upena.deployable.profiler.visualize.VStrategies;
import com.jivesoftware.os.upena.deployable.region.ProfilerPluginRegion;
import com.jivesoftware.os.upena.deployable.region.ProfilerPluginRegion.ProfilerPluginRegionInput;
import com.jivesoftware.os.upena.deployable.soy.SoyService;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 */
@Singleton
@Path("/ui/profiler")
public class ProfilerPluginEndpoints {

    private static final MetricLogger LOG = MetricLoggerFactory.getLogger();

    private final SoyService soyService;
    private final ProfilerPluginRegion pluginRegion;

    public ProfilerPluginEndpoints(@Context SoyService soyService,
        @Context ProfilerPluginRegion pluginRegion) {
        this.soyService = soyService;
        this.pluginRegion = pluginRegion;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response profiler(@Context HttpServletRequest httpRequest) {
        try {

            String rendered = soyService.renderPlugin(httpRequest.getRemoteUser(), pluginRegion,
                new ProfilerPluginRegionInput(true,
                    "",
                    800,
                    VStrategies.ValueStrat.constant.name(),
                    VStrategies.StackStrat.constant.name(),
                    VStrategies.BarStrat.calledBy.name(),
                    VStrategies.ClassNameStrat.none.name(),
                    VStrategies.Colorings.heat.name(),
                    VStrategies.Background.alpha.name(),
                    VStrategies.StackOrder.ascending.name(),
                    0,
                    0));
            return Response.ok(rendered).build();
        } catch (Exception e) {
            LOG.error("profiler GET", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response action(@Context HttpServletRequest httpRequest,
        @FormParam("enabled") @DefaultValue("false") boolean enabled,
        @FormParam("serviceName") @DefaultValue("") String serviceName,
        @FormParam("height") @DefaultValue("800") int height,
        @FormParam("valueStrategy") @DefaultValue("constant") String valueStrategy,
        @FormParam("stackStrategy") @DefaultValue("constant") String stackStrategy,
        @FormParam("barStrategy") @DefaultValue("calledBy") String barStrategy,
        @FormParam("classNameStrategy") @DefaultValue("none") String classNameStrategy,
        @FormParam("coloring") @DefaultValue("heat") String coloring,
        @FormParam("background") @DefaultValue("alpha") String background,
        @FormParam("stackOrder") @DefaultValue("ascending") String stackOrder,
        @FormParam("x") @DefaultValue("0") int mouseX,
        @FormParam("y") @DefaultValue("0") int mouseY) {
        try {
            String rendered = soyService.renderPlugin(httpRequest.getRemoteUser(), pluginRegion,
                new ProfilerPluginRegionInput(enabled,
                    serviceName,
                    height,
                    valueStrategy,
                    stackStrategy,
                    barStrategy,
                    classNameStrategy,
                    coloring,
                    background,
                    stackOrder,
                    mouseX,
                    mouseY));
            return Response.ok(rendered).build();
        } catch (Exception e) {
            LOG.error("profiler POST", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}

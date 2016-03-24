package nowait.service.cache.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import nowait.service.cache.common.DataResult;
import nowait.service.cache.model.ClientIdCache;
import nowait.service.cache.model.SimpleCache;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by yangjunming on 3/23/16.
 * author: yangjunming@huijiame.com
 */
@Path("cache")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public interface CacheRestService {

    @GET
    @Path("ping")
    DataResult<String> ping();

    @POST
    @Path("setCache")
    DataResult<String> setCache(SimpleCache simpleCache);

    @GET
    @Path("getCache/{key}")
    DataResult<String> getCache(@PathParam("key") @NotNull(message = "key不能为空") String key);

    @POST
    @Path("setCacheWithClientId")
    DataResult<String> setCacheWithClientId(ClientIdCache clientIdCache);

    @GET
    @Path("getCacheWithClientId/{clientId}/{key}")
    DataResult<String> getCacheWithClientId(@PathParam("clientId") @NotNull(message = "clientId不能为空") String clientId,
                                @PathParam("key") @NotNull(message = "key不能为空") String key);
}

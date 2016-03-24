package nowait.service.cache.consumer;

import nowait.service.cache.api.CacheService;
import nowait.service.cache.common.utils.ApplicationContextUtil;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class CacheServiceConsumer {

    private static Logger logger = null;

    private static String baseUrl = "";

    private final static Client client = ClientBuilder.newClient();

    public static void main(String[] args) throws IOException, TException {

        ConfigurableApplicationContext context = ApplicationContextUtil.getInstance().getContext();

        logger = LoggerFactory.getLogger(CacheServiceConsumer.class);
        baseUrl = context.getBean(RestServiceConfig.class).getBaseUrl();

        CacheService.Iface cacheService = context.getBean(CacheService.Iface.class, "cacheService");
        System.out.println("cacheService ping:" + cacheService.ping());
        System.out.println(cacheService.setCache("jimmy", "杨俊明"));
        System.out.println(cacheService.getCache("jimmy"));

        performanceTest(cacheService);

        //restErrorTest();
    }

//    private static void restErrorTest() {
//        post(baseUrl + "register", getUser(0L), MediaType.APPLICATION_JSON_TYPE);
//    }

//    private static void restTest() {
//        logger.info("\nREST test begin ...");
//        get(baseUrl + "0");
//        get(baseUrl + "2");
//        post(baseUrl + "delete", 2L, MediaType.APPLICATION_JSON_TYPE);
//        post(baseUrl + "post", "", MediaType.APPLICATION_JSON_TYPE);
//        post(baseUrl + "register", getUser(3L), MediaType.APPLICATION_JSON_TYPE);
//    }


//    private static void rpcTest(UserService userService) {
//        logger.info("rpc test begin ...");
//        logger.info("getUserById test => ");
//        logger.info(userService.getUserById(1L).toString());
//        logger.info(userService.ping());
//        logger.info(userService.deleteUserById(2L).toString());
//        logger.info(userService.updatePassword(1L, "12345678", "abcdefgh").toString());
//        logger.info(userService.registerUser(getUser(5L)).toString());
//    }

    private static void performanceTest(CacheService.Iface thriftService) throws TException {

        int max = 100000;
        long start;
        long end;
        long elapsedMilliseconds;

        logger.warn("CacheService RPC testing => ");

        start = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            thriftService.setCache("key" + i, i + "");
        }
        end = System.currentTimeMillis();
        elapsedMilliseconds = end - start;

        logger.warn(String.format("%d次redis write,共耗时%d毫秒,平均%f/秒", max, elapsedMilliseconds, max / (elapsedMilliseconds / 1000.0F)));

        start = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            thriftService.getCache("key" + i);
        }
        end = System.currentTimeMillis();
        elapsedMilliseconds = end - start;

        logger.warn(String.format("%d次redis read,共耗时%d毫秒,平均%f/秒", max, elapsedMilliseconds, max / (elapsedMilliseconds / 1000.0F)));


//        logger.warn("REST testing => ");
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < max; i++) {
//            get(baseUrl + "ping");
//        }
//        end = System.currentTimeMillis();
//        elapsedMilliseconds = end - start;
//
//        logger.warn(String.format("%d次REST调用,共耗时%d毫秒,平均%f/秒", max, elapsedMilliseconds, max / (elapsedMilliseconds / 1000.0F)));
    }

    private static void get(String url) {
        logger.info(url);
        WebTarget target = client.target(url);
        Response response = target.request().get();
        output(response, client);
    }


//    private static <T> void post(String url, T t, MediaType mediaType) {
//        //logger.info(url);
//        WebTarget target = client.target(url);
//        Response response = target.request().buildPost(Entity.entity(t, mediaType)).invoke();
//        output(response, client);
//    }

    private static void output(Response response, Client client) {
        try {
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
            }
            logger.debug(response.readEntity(String.class));
        } finally {
            response.close();
            //client.close();
        }
    }

}
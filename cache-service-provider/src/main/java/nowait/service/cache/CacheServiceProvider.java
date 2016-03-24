package nowait.service.cache;


import nowait.service.cache.common.utils.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by yangjunming on 3/4/16.
 * author: yangjunming@huijiame.com
 */
public class CacheServiceProvider {

    public static void main(String[] args) throws IOException {
        ApplicationContextUtil.getInstance().getContext();
        Logger log = LoggerFactory.getLogger(CacheServiceProvider.class);
        log.info("缓存服务已启动!");
        System.out.println("cache servicve has been started ...");
        System.in.read();
    }
}

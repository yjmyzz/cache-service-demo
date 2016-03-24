package nowait.service.cache.config;

import lombok.Data;

/**
 * Created by yangjunming on 2/24/16.
 * author: yangjunming@huijiame.com
 */
@Data
public class ServiceConfig {

    /**
     * service provider发生错误时,是否抛出异常
     */
    private boolean throwException;
}

package nowait.service.cache.impl;

import nowait.service.cache.api.CacheRestService;
import nowait.service.cache.common.DataResult;
import nowait.service.cache.common.consts.ServiceConst;
import nowait.service.cache.model.ClientIdCache;
import nowait.service.cache.model.SimpleCache;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * Created by yangjunming on 3/23/16.
 * author: yangjunming@huijiame.com
 */
@Service("cacheRestService")
public class CacheRestServiceImpl implements CacheRestService {

    private static final Logger logger = LoggerFactory.getLogger(CacheRestServiceImpl.class);

    @Autowired
    CacheServiceImpl cacheService;

    @Override
    public DataResult<String> ping() {
        DataResult<String> result = new DataResult<String>();
        try {
            result.setData(cacheService.ping());
            result.setSuccess(true);
        } catch (TException e) {
            logger.error("ping error:", e);
            result.setErrorCode(ServiceConst.ErrorCode.THRIFT_ERROR);
            result.setErrorDesc(e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public DataResult<String> setCache(SimpleCache simpleCache) {
        DataResult<String> result = new DataResult<String>();
        try {
            result.setData(cacheService.setCache(simpleCache.getKey(), simpleCache.getValue()));
            result.setSuccess(true);
        } catch (TException e) {
            logger.error("setCache error:", e);
            result.setErrorCode(ServiceConst.ErrorCode.THRIFT_ERROR);
            result.setErrorDesc(e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public DataResult<String> getCache(@NotNull(message = "key不能为空") String key) {
        DataResult<String> result = new DataResult<String>();
        try {
            result.setData(cacheService.getCache(key));
            result.setSuccess(true);
        } catch (TException e) {
            logger.error("getCache error:", e);
            result.setErrorCode(ServiceConst.ErrorCode.THRIFT_ERROR);
            result.setErrorDesc(e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public DataResult<String> setCacheWithClientId(ClientIdCache clientIdCache) {
        DataResult<String> result = new DataResult<String>();
        try {
            result.setData(cacheService.setCacheWithClientId(clientIdCache.getClientId(), clientIdCache.getKey(), clientIdCache.getValue()));
            result.setSuccess(true);
        } catch (TException e) {
            logger.error("setCacheWithClientId error:", e);
            result.setErrorCode(ServiceConst.ErrorCode.THRIFT_ERROR);
            result.setErrorDesc(e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public DataResult<String> getCacheWithClientId(@NotNull(message = "clientId不能为空") String clientId, @NotNull(message = "key不能为空") String key) {
        DataResult<String> result = new DataResult<String>();
        try {
            result.setData(cacheService.getCacheWithClientId(clientId, key));
            result.setSuccess(true);
        } catch (TException e) {
            logger.error("getCacheWithClientId error:", e);
            result.setErrorCode(ServiceConst.ErrorCode.THRIFT_ERROR);
            result.setErrorDesc(e.getLocalizedMessage());
        }
        return result;
    }
}

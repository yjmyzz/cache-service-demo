package nowait.service.cache.filter;

import com.alibaba.dubbo.rpc.*;
import nowait.service.cache.utils.ParamValidateUtils;
import lombok.Setter;
import nowait.service.cache.common.DataResult;
import nowait.service.cache.common.consts.ServiceConst;
import nowait.service.cache.common.exception.ParamException;
import nowait.service.cache.common.exception.ServiceException;
import nowait.service.cache.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yangjunming on 2/22/16.
 * author: yangjunming@huijiame.com
 */
@Component("serviceFilter")
public class ServiceFilter implements Filter {

    final static Logger logger = LoggerFactory.getLogger(ServiceFilter.class);

    //坑1:不要用@Autowired注入,拿不到对象,改用setter
    @Setter
    ServiceConfig serviceConfig;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        long start = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();

        try {

            //1. log params info
            sb.append(invocation.toString());

            //2. param validate
            for (Object arg : invocation.getArguments()) {
                Map<String, ArrayList<String>> validateResult = ParamValidateUtils.validator(arg);
                if (validateResult != null) {
                    for (Map.Entry<String, ArrayList<String>> entry : validateResult.entrySet()) {
                        sb.append(" , 参数:" + arg + " , " + entry.getKey() + " 校验失败 , 原因: " + entry.getValue());
                        long end = System.currentTimeMillis();
                        long elapsedMilliseconds = end - start;
                        sb.append(" , 执行耗时: " + elapsedMilliseconds + " 毫秒");
                        DataResult dataResult = new DataResult(ServiceConst.ErrorCode.PARAM_VALID_ERROR, entry.
                                getKey() + ":"
                                + entry.getValue(), elapsedMilliseconds);
                        RpcResult result = new RpcResult(dataResult);
                        result.setAttachments(invocation.getAttachments());
                        if (serviceConfig.isThrowException()) {
                            result.setException(new ParamException(ServiceConst.ErrorCode.PARAM_VALID_ERROR));
                        }

                        logger.error(sb.toString());
                        return result;
                    }
                }
            }

            //3. invoke
            Result result = invoker.invoke(invocation);

            //4. log result
            sb.append(" , 调用结果: " + result.toString());
            long end = System.currentTimeMillis();
            long elapsedMilliseconds = end - start;
            sb.append(" , 执行耗时: " + elapsedMilliseconds + " 毫秒");

            if (result.getValue() != null && result.getValue() instanceof DataResult) {
                ((DataResult) result.getValue()).setElapsedMilliseconds(elapsedMilliseconds);
            }

            logger.info(sb.toString());

            return result;

        } catch (Exception e) {
            DataResult dataResult = new DataResult(ServiceConst.ErrorCode.UNKNOWN, e.getMessage());
            RpcResult result = new RpcResult(dataResult);
            result.setAttachments(invocation.getAttachments());
            if (serviceConfig.isThrowException()) {
                result.setException(new ServiceException(ServiceConst.ErrorCode.UNKNOWN, e.getMessage()));
            }
            sb.append(" , 调用结果: " + result.toString());

            long end = System.currentTimeMillis();
            long elapsedMilliseconds = end - start;

            sb.append(" , 执行耗时: " + elapsedMilliseconds + " 毫秒");
            dataResult.setElapsedMilliseconds(elapsedMilliseconds);
            logger.error(sb.toString());

            return result;
        }


    }
}

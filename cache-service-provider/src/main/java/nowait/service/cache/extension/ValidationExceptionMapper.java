package nowait.service.cache.extension;


import com.alibaba.dubbo.rpc.protocol.rest.RpcExceptionMapper;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import nowait.service.cache.common.DataResult;
import nowait.service.cache.common.consts.ServiceConst;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

/**
 * Created by yangjunming on 2/26/16.
 * author: yangjunming@huijiame.com
 */
public class ValidationExceptionMapper extends RpcExceptionMapper {

    protected Response handleConstraintViolationException(ConstraintViolationException cve) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation cv : cve.getConstraintViolations()) {
            sb.append(String.format("arg:%s,value:%s,message:%s", cv.getPropertyPath().toString(),
                    (cv.getInvalidValue() == null ? "null" : cv.getInvalidValue().toString()),
                    cv.getMessage()));
        }

        DataResult<String> dataResult = new DataResult<String>(ServiceConst.ErrorCode.PARAM_VALID_ERROR, sb.toString());
        //class cls = Request.class.
        // Request
        return Response.status(Response.Status.OK).entity(dataResult).
                type(ContentType.APPLICATION_JSON_UTF_8).build();
    }
}

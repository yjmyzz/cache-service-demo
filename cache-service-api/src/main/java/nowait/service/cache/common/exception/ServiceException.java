package nowait.service.cache.common.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yangjunming on 2/24/16.
 * author: yangjunming@huijiame.com
 */
@Setter
@Getter
@ToString
public class ServiceException extends Exception {

    private String errCode;

    private String errMsg;

    public ServiceException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ServiceException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }
}

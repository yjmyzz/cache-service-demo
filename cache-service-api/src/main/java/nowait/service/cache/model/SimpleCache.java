package nowait.service.cache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by yangjunming on 3/23/16.
 * author: yangjunming@huijiame.com
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement
public class SimpleCache implements Serializable {

    private static final long serialVersionUID = -8133490495371404223L;

    @NotNull(message = "key不能为空")
    private String key;

    @NotNull(message = "value不能为空")
    private String value;
}

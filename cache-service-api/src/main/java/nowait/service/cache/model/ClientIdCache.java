package nowait.service.cache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement
@EqualsAndHashCode(callSuper = false)
public class ClientIdCache extends SimpleCache implements Serializable {

    private static final long serialVersionUID = -1173658437595884399L;

    @NotNull(message = "clientId不能为空")
    private String clientId;
}

package <%= packageName %>.dto.edit;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class <%= entityName %>Edit implements Serializable {

    private String name;
}

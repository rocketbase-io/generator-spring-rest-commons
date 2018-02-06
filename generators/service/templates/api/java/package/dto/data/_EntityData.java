package <%= packageName %>.dto.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class <%= entityName %>Data implements Serializable {

    private String id;

    private String name;
}

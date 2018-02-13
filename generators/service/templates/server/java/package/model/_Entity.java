package <%= packageName %>.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class <%= entityName %> implements Serializable {

    @Id
    private String id;

    private String name;
}

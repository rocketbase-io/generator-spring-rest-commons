package <%= packageName %>.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<%_ if (mongoDb) { _%>
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
<%_ } else { _%>
import javax.persistence.*;
<%_ } _%>

import java.io.Serializable;

<%_ if (mongoDb) { _%>
@Document(collection = "<%= entityCamelCase %>")
<%_ } else { _%>
@Entity
@Table(name =  "<%= entitySnakeCase %>")
<%_ } _%>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class <%= entityName %>Entity implements Serializable {

    <%_ if (mongoDb) { _%>
    @Id
    <%_ } else { _%>
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    <%_ } _%>
    private <%= idClass %> id;

    private String name;
}

package <%= packageName %>.dto.<%= entityFolder %>;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class <%= entityName %>Write implements Serializable {

    private String name;
}

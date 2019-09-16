<%_ if (isDto) { _%>
package <%= packageName %>.dto;
<%_ } else { _%>
package <%= packageName %>.dto.<%= entityFolder %>;
<%_ } _%>

<%_ if (obfuscated) { _%>
import io.rocketbase.commons.obfuscated.ObfuscatedId;
<%_ } _%>
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class <%= entityNameRead %> implements Serializable {

<%_ if (obfuscated) { _%>
    private ObfuscatedId id;
<%_ } else { _%>
    private <%= idClass %> id;
<%_ } _%>

    private String name;
}

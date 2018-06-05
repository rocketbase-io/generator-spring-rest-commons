package <%= packageName %>.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.rocketbase.commons.dto.PageableResult;
import io.rocketbase.commons.resource.AbstractCrudChildRestResource;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Read;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Write;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class <%= entityName %>Resource extends AbstractCrudChildRestResource<<%= entityName %>Read, <%= entityName %>Write, <%= idClass %>> {

    private String baseApiUrl;

    public <%= entityName %>Resource(@Value("${resource.base.api.url}") String baseApiUrl, ObjectMapper objectMapper) {
        super(objectMapper);
        this.baseApiUrl = baseApiUrl;
    }


    @Override
    protected String getBaseParentApiUrl() {
        return baseApiUrl + "/api/<%= parentKebabCase %>";
    }

    @Override
    protected String getChildPath() {
        return "<%= entityKebabCase %>";
    }

    @Override
    protected TypeReference<PageableResult<<%= entityName %>Read>> createPagedTypeReference() {
        return new TypeReference<PageableResult<<%= entityName %>Read>>() {
        };
    }
}

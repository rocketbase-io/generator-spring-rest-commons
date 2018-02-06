package <%= packageName %>.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.rocketbase.commons.dto.PageableResult;
import io.rocketbase.commons.resource.AbstractCrudRestResource;
import <%= packageName %>.dto.data.<%= entityName %>Data;
import <%= packageName %>.dto.edit.<%= entityName %>Edit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class <%= entityName %>Resource extends AbstractCrudRestResource<<%= entityName %>Data, <%= entityName %>Edit, String> {

    @Value("${resource.base.api.url}")
    private String baseApiUrl;

    @Autowired
    public <%= entityName %>Resource(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected String getBaseApiUrl() {
        return baseApiUrl + "/api/<%= entityVariable %>";
    }

    @Override
    protected TypeReference<PageableResult<<%= entityName %>Data>> createPagedTypeReference() {
        return new TypeReference<PageableResult<<%= entityName %>Data>>() {
        };
    }
}

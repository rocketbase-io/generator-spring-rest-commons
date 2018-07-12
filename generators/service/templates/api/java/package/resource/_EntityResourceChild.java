package <%= packageName %>.resource;

import io.rocketbase.commons.dto.PageableResult;
import io.rocketbase.commons.resource.AbstractCrudChildRestResource;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Read;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Write;
import org.springframework.util.Assert;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;


public class <%= entityName %>Resource extends AbstractCrudChildRestResource<<%= entityName %>Read, <%= entityName %>Write, <%= idClass %>> {

    protected String baseUrl;

    public <%= entityName %>Resource(String baseUrl) {
        this(baseUrl, null);
    }

    public <%= entityName %>Resource(String baseUrl, RestTemplate restTemplate) {
        Assert.hasText(baseUrl, "baseUrl is required");
        this.baseUrl = baseUrl;
        setRestTemplate(restTemplate);
    }


    @Override
    protected String getBaseParentApiUrl() {
        return baseUrl + "/api/<%= parentKebabCase %>";
    }

    @Override
    protected String getChildPath() {
        return "<%= entityKebabCase %>";
    }

    @Override
    protected ParameterizedTypeReference<PageableResult<<%= entityName %>Read>> createPagedTypeReference() {
        return new ParameterizedTypeReference<PageableResult<<%= entityName %>Read>>() {
        };
    }
}

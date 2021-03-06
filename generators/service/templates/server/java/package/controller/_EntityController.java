package <%= packageName %>.controller;

<%_ if (obfuscated) { _%>
import io.rocketbase.commons.controller.AbstractCrudObfuscatedController;
<%_ } else { _%>
import io.rocketbase.commons.controller.AbstractCrudController;
<%_ } _%>
import <%= packageName %>.converter.<%= entityName %>Converter;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityNameRead %>;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityNameWrite %>;
import <%= packageName %>.model.<%= entityName %>Entity;
import <%= packageName %>.repository.<%= entityName %>Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/<%= entityKebabCase %>")
public class <%= entityName %>Controller extends <%_ if (obfuscated) { _%>AbstractCrudObfuscatedController<%_ } else { _%>AbstractCrudController<%_ } _%><<%= entityName %>Entity, <%= entityNameRead %>, <%= entityNameWrite %><%_ if (!obfuscated) { _%>, <%= idClass %><%_ } _%>, <%= entityName %>Converter> {

    @Autowired
    public <%= entityName %>Controller(<%= entityName %>Repository repository, <%= entityName %>Converter converter) {
        super(repository, converter);
    }

    @Override
    protected <%= entityName %>Repository getRepository() {
        return (<%= entityName %>Repository) super.getRepository();
    }

    @Override
    protected Sort getDefaultSort() {
        return Sort.by("id");
    }

}

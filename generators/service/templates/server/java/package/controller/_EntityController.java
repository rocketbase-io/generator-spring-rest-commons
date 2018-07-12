package <%= packageName %>.controller;

import io.rocketbase.commons.controller.AbstractCrudController;
import <%= packageName %>.converter.<%= entityName %>Converter;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Read;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Write;
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
public class <%= entityName %>Controller extends AbstractCrudController<<%= entityName %>Entity, <%= entityName %>Read, <%= entityName %>Write, <%= idClass %>, <%= entityName %>Converter> {

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

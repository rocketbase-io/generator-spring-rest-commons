package <%= packageName %>.controller;

import io.rocketbase.commons.controller.AbstractCrudController;
import <%= packageName %>.converter.<%= entityName %>Converter;
import <%= packageName %>.dto.data.<%= entityName %>Read;
import <%= packageName %>.dto.edit.<%= entityName %>Write;
import <%= packageName %>.model.<%= entityName %>;
import <%= packageName %>.repository.<%= entityName %>Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/<%= entityVariable %>")
public class <%= entityName %>Controller extends AbstractCrudController<<%= entityName %>, <%= entityName %>Read, <%= entityName %>Write, String, <%= entityName %>Converter> {

    @Autowired
    public <%= entityName %>Controller(<%= entityName %>Repository repository, <%= entityName %>Converter converter) {
        super(repository, converter);
    }

}

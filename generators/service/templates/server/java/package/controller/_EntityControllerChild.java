package <%= packageName %>.controller;

import io.rocketbase.commons.controller.AbstractCrudChildController;
import <%= packageName %>.converter.<%= entityName %>Converter;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Read;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Write;
import <%= packageName %>.model.<%= entityName %>Entity;
import <%= packageName %>.model.<%= parentName %>Entity;
import <%= packageName %>.repository.<%= entityName %>Repository;
import <%= packageName %>.repository.<%= parentName %>Repository;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.rocketbase.commons.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/<%= parentKebabCase %>/{parentId}/<%= entityKebabCase %>")
public class <%= entityName %>Controller extends AbstractCrudChildController<<%= entityName %>Entity, <%= entityName %>Read, <%= entityName %>Write, <%= idClass %>, <%= entityName %>Converter> {

    @Resource
    private <%= parentName %>Repository <%= parentCamelCase %>Repository;

    @Autowired
    public <%= entityName %>Controller(<%= entityName %>Repository repository, <%= entityName %>Converter converter) {
        super(repository, converter);
    }

    @Override
    protected <%= entityName %>Entity getEntity(String parentId, String id) {
        // todo: need to add correct implementation
        // return getRepository().findOneBy<%= parentName %>IdAndId(parentId, id);
        return null;
    }

    @Override
    protected Page<<%= entityName %>Entity> findAllByParentId(String parentId, PageRequest pageRequest) {
        // todo: need to add correct implementation
        // return getRepository().findAllBy<%= parentName %>Id(parentId, pageRequest);
        return null;
    }

    @Override
    protected <%= entityName %>Entity newEntity(String parentId, <%= entityName %>Write <%= entityCamelCase %>Write) {
        Optional<<%= parentName %>Entity> optional = <%= parentCamelCase %>Repository.findById(parentId);
        if (!optional.isPresent()) {
            throw new NotFoundException();
        }
        <%= entityName %>Entity <%= entityCamelCase %>Entity = getConverter().newEntity(<%= entityCamelCase %>Write);
        // todo: set parent to entity
        // <%= entityCamelCase %>Entity.setParent(optional.get());
        return <%= entityCamelCase %>Entity;
    }

    @Override
    protected <%= entityName %>Repository getRepository() {
        return (<%= entityName %>Repository) super.getRepository();
    }

}

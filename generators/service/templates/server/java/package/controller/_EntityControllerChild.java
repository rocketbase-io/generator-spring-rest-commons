package <%= packageName %>.controller;

<%_ if (obfuscated) { _%>
import io.rocketbase.commons.controller.AbstractCrudChildController;
<%_ } else { _%>
import io.rocketbase.commons.controller.AbstractCrudChildObfuscatedController;
import io.rocketbase.commons.obfuscated.ObfuscatedId;
<%_ } _%>
import <%= packageName %>.converter.<%= entityName %>Converter;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityNameRead %>;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityNameWrite %>;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/<%= parentKebabCase %>/{parentId}/<%= entityKebabCase %>")
public class <%= entityName %>Controller extends <%_ if (obfuscated) { _%>AbstractCrudChildObfuscatedController<%_ } else { _%>AbstractCrudChildController<%_ } _%><<%= entityName %>Entity, <%= entityNameRead %>, <%= entityNameWrite %><%_ if (!obfuscated) { _%>, <%= idClass %><%_ } _%>, <%= entityName %>Converter> {

    @Resource
    private <%= parentName %>Repository <%= parentCamelCase %>Repository;

    @Autowired
    public <%= entityName %>Controller(<%= entityName %>Repository repository, <%= entityName %>Converter converter) {
        super(repository, converter);
    }

    @Override
    protected <%= entityName %>Entity getEntity(<%= idClass %> parentId, <%= idClass %> id) {
        // todo: need to add correct implementation
        // return getRepository().findOneBy<%= parentName %>IdAndId(parentId, id);
        return null;
    }

    @Override
    protected Page<<%= entityName %>Entity> findAllByParentId(<%= idClass %> parentId, Pageable pageable) {
        // todo: need to add correct implementation
        // return getRepository().findAllBy<%= parentName %>Id(parentId, pageable);
        return null;
    }

    @Override
    protected <%= entityName %>Entity newEntity(<%= idClass %> parentId, <%= entityNameWrite %> <%= entityCamelCase %>Write) {
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

    @Override
    protected Sort getDefaultSort() {
        return Sort.by("id");
    }
}

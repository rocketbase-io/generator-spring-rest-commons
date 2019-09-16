package <%= packageName %>.controller;

import io.rocketbase.commons.dto.PageableResult;
import io.rocketbase.commons.exception.NotFoundException;
import io.rocketbase.commons.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import <%= packageName %>.converter.<%= entityName %>Converter;
<%_ if (isDto) { _%>
import <%= packageName %>.dto.<%= entityNameRead %>;
<%_ } else { _%>
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityNameRead %>;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityNameWrite %>;
<%_ } _%>
import <%= packageName %>.model.<%= entityName %>Entity;
import <%= packageName %>.model.<%= parentName %>Entity;
import <%= packageName %>.repository.<%= entityName %>Repository;
import <%= packageName %>.repository.<%= parentName %>Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api/<%= parentKebabCase %>/{<%= parentCamelCase %>Id}/<%= entityKebabCase %>")
@RequiredArgsConstructor
public class <%= entityName %>Controller implements BaseController {

    private final <%= entityName %>Repository <%= entityCamelCase %>Repository;

    private final <%= parentName %>Repository <%= parentCamelCase %>Repository;

    private final <%= entityName %>Converter converter;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageableResult<<%= entityNameRead %>> find(@PathVariable("<%= parentCamelCase %>Id") <%= idClass %> parentId, @RequestParam(required = false) MultiValueMap<String, String> params) {
        Page<<%= entityName %>Entity> entities = findAllByParentId(parentId, parsePageRequest(params, getDefaultSort()));
        return PageableResult.contentPage(converter.fromEntities(entities.getContent()), entities);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @ResponseBody
    public <%= entityNameRead %> getById(@PathVariable("<%= parentCamelCase %>Id") <%= idClass %> parentId, @PathVariable("id") <%= idClass %> id) {
        <%= entityName %>Entity entity = getEntity(parentId, id);
        return converter.fromEntity(entity);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public <%= entityNameRead %> create(@PathVariable("<%= parentCamelCase %>") <%= idClass %> parentId, @RequestBody @NotNull @Validated <%= entityNameWrite %> dto) {
        <%= entityName %>Entity entity = <%= entityCamelCase %>Repository.save(newEntity(parentId, dto));
        return converter.fromEntity(entity);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public <%= entityNameRead %> update(@PathVariable("<%= parentCamelCase %>Id") <%= idClass %> parentId, @PathVariable <%= idClass %> id, @RequestBody @NotNull @Validated <%= entityNameWrite %> dto) {
        <%= entityName %>Entity entity = getEntity(parentId, id);
        converter.updateEntityFromEdit(dto, entity);
        <%= entityCamelCase %>Repository.save(entity);
        return converter.fromEntity(entity);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void delete(@PathVariable("<%= parentCamelCase %>Id") <%= idClass %> parentId, @PathVariable("id") <%= idClass %> id) {
        <%= entityName %>Entity entity = getEntity(parentId, id);
        <%= entityCamelCase %>Repository.delete(entity);
    }

    protected <%= entityName %>Entity getEntity(<%= idClass %> parentId, <%= idClass %> id) {
        // todo: need query with parentId!
        return <%= entityCamelCase %>Repository.findById(id)
            .orElseThrow(() -> new NotFoundException());
    }

    protected Sort getDefaultSort() {
        return Sort.by("id");
    }

    protected Page<<%= entityName %>Entity> findAllByParentId(<%= idClass %> parentId, Pageable pageable) {
        // todo: need query with parentId!
        return <%= entityCamelCase %>Repository.findAll(pageable);
    }

    protected <%= entityName %>Entity newEntity(<%= idClass %> parentId, <%= entityNameWrite %> dto) {
        <%= parentName %>Entity parent = <%= parentCamelCase %>Repository.findById(parentId)
            .orElseThrow(() -> new NotFoundException());

        <%= entityName %>Entity entity = converter.newEntity(dto);
        // todo: need implementation
        // entity.setParent(parent.getId());
        return entity;
    }

}

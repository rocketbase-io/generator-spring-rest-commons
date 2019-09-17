package <%= packageName %>.controller;

import io.rocketbase.commons.dto.PageableResult;
import io.rocketbase.commons.exception.NotFoundException;
import io.rocketbase.commons.controller.BaseController;
<%_ if (obfuscated) { _%>
import io.rocketbase.commons.obfuscated.ObfuscatedId;
<%_ } _%>
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
    public PageableResult<<%= entityNameRead %>> find(@PathVariable("<%= parentCamelCase %>Id") <%= idClassObfuscated %> <%= parentCamelCase %>Id, @RequestParam(required = false) MultiValueMap<String, String> params) {
        Page<<%= entityName %>Entity> entities = findAllBy<%= parentName %>Id(<%= parentCamelCase %>Id, parsePageRequest(params, getDefaultSort()));
        return PageableResult.contentPage(converter.fromEntities(entities.getContent()), entities);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @ResponseBody
    public <%= entityNameRead %> getById(@PathVariable("<%= parentCamelCase %>Id") <%= idClassObfuscated %> <%= parentCamelCase %>Id, @PathVariable("id") <%= idClassObfuscated %> id) {
        <%= entityName %>Entity entity = getEntity(<%= parentCamelCase %>Id, id);
        return converter.fromEntity(entity);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public <%= entityNameRead %> create(@PathVariable("<%= parentCamelCase %>Id") <%= idClassObfuscated %> <%= parentCamelCase %>Id, @RequestBody @NotNull @Validated <%= entityNameWrite %> dto) {
        <%= entityName %>Entity entity = <%= entityCamelCase %>Repository.save(newEntity(<%= parentCamelCase %>Id, dto));
        return converter.fromEntity(entity);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public <%= entityNameRead %> update(@PathVariable("<%= parentCamelCase %>Id") <%= idClassObfuscated %> <%= parentCamelCase %>Id, @PathVariable <%= idClassObfuscated %> id, @RequestBody @NotNull @Validated <%= entityNameWrite %> dto) {
        <%= entityName %>Entity entity = getEntity(<%= parentCamelCase %>Id, id);
        converter.updateEntityFromEdit(dto, entity);
        <%= entityCamelCase %>Repository.save(entity);
        return converter.fromEntity(entity);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void delete(@PathVariable("<%= parentCamelCase %>Id") <%= idClassObfuscated %> <%= parentCamelCase %>Id, @PathVariable("id") <%= idClassObfuscated %> id) {
        <%= entityName %>Entity entity = getEntity(<%= parentCamelCase %>Id, id);
        <%= entityCamelCase %>Repository.delete(entity);
    }

    protected <%= entityName %>Entity getEntity(<%= idClassObfuscated %> <%= parentCamelCase %>Id, <%= idClassObfuscated %> id) {
        // todo: need query with <%= parentCamelCase %>Id!
        return <%= entityCamelCase %>Repository.findById(id<%_ if (obfuscated) { _%>.getId()<%_ } _%>)
            .orElseThrow(() -> new NotFoundException());
    }

    protected Sort getDefaultSort() {
        return Sort.by("id");
    }

    protected Page<<%= entityName %>Entity> findAllBy<%= parentName %>Id(<%= idClassObfuscated %> <%= parentCamelCase %>Id, Pageable pageable) {
        // todo: need query with <%= parentCamelCase %>Id!
        return <%= entityCamelCase %>Repository.findAll(pageable);
    }

    protected <%= entityName %>Entity newEntity(<%= idClassObfuscated %> <%= parentCamelCase %>Id, <%= entityNameWrite %> dto) {
        <%= parentName %>Entity parent = <%= parentCamelCase %>Repository.findById(<%= parentCamelCase %>Id<%_ if (obfuscated) { _%>.getId()<%_ } _%>)
            .orElseThrow(() -> new NotFoundException());

        <%= entityName %>Entity entity = converter.newEntity(dto);
        // todo: need implementation
        // entity.setParent(parent.getId());
        return entity;
    }

}

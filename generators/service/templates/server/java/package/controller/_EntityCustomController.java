package <%= packageName %>.controller;

import io.rocketbase.commons.dto.PageableResult;
import io.rocketbase.commons.exception.NotFoundException;
import io.rocketbase.commons.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import <%= packageName %>.converter.<%= entityName %>Converter;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Read;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Write;
import <%= packageName %>.model.<%= entityName %>Entity;
import <%= packageName %>.repository.<%= entityName %>Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api/<%= entityKebabCase %>")
@RequiredArgsConstructor
public class <%= entityName %>Controller implements BaseController {

    private final <%= entityName %>Repository repository;

    private final <%= entityName %>Converter converter;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageableResult<<%= entityName %>Read> find(@RequestParam(required = false) MultiValueMap<String, String> params) {
        Page<<%= entityName %>Entity> entities = repository.findAll(parsePageRequest(params, getDefaultSort()));
        return PageableResult.contentPage(converter.fromEntities(entities.getContent()), entities);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @ResponseBody
    public <%= entityName %>Read getById(@PathVariable("id") <%= idClass %> id) {
        <%= entityName %>Entity entity = getEntity(id);
        return converter.fromEntity(entity);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public <%= entityName %>Read create(@RequestBody @NotNull @Validated <%= entityName %>Write write) {
        <%= entityName %>Entity entity = repository.save(converter.newEntity(write));
        return converter.fromEntity(entity);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public <%= entityName %>Read update(@PathVariable <%= idClass %> id, @RequestBody @NotNull @Validated <%= entityName %>Write write) {
        <%= entityName %>Entity entity = getEntity(id);
        converter.updateEntityFromEdit(write, entity);
        repository.save(entity);
        return converter.fromEntity(entity);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void delete(@PathVariable("id") <%= idClass %> id) {
        <%= entityName %>Entity entity = getEntity(id);
        repository.delete(entity);
    }

    protected <%= entityName %>Entity getEntity(<%= idClass %> id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }

    protected Sort getDefaultSort() {
        return Sort.by("id");
    }

}
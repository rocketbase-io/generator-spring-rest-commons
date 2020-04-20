package <%= packageName %>.repository;

import <%= packageName %>.model.<%= entityName %>Entity;
<%_ if (mongoDb) { _%>
import org.springframework.data.mongodb.repository.MongoRepository;
<%_ } else { _%>
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
<%_ } _%>

public interface <%= entityName %>Repository extends <% if (mongoDb) { %>MongoRepository<% } else { %>PagingAndSortingRepository<% } %><<%= entityName %>Entity, <%= idClass %>> <% if (!mongoDb) {%>, JpaSpecificationExecutor<<%= entityName %>Entity><%}%> {

}

package <%= packageName %>.repository;

import <%= packageName %>.model.<%= entityName %>;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface <%= entityName %>Repository extends MongoRepository<<%= entityName %>Entity, String> {

}

package tech.abc.edoc.edoc.esrepository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import tech.abc.edoc.edoc.entity.Document;


/**
 *
 * @author wqliu
 * @date 2021-4-4 17:44
 **/
@Repository
public interface DocumentRepository extends ElasticsearchRepository<Document,String> {
}

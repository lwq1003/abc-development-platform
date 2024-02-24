package tech.abc.edoc.edoc.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

/**
 * @author wqliu
 * @date 2021-4-7 17:17
 **/
@Data
public class PopSearchScrollHits {
    private String scrollId;
    private List<SearchHit<Document>> data;

}

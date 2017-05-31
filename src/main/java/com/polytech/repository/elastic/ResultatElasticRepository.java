package com.polytech.repository.elastic;

import com.polytech.models.Requete;
import com.polytech.models.Result;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by E.Marouane on 28/05/2017.
 */
public interface ResultatElasticRepository extends ElasticsearchRepository<Result, String> {

    public List<Result> findResultsByTitleLike(String title, Sort sort);

}

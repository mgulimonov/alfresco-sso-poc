package com.max.alfboot.dao;

import java.util.List;

import com.max.alfboot.domain.Reference;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "reference", path = "reference")
public interface ReferenceRepository extends PagingAndSortingRepository<Reference, Long> {

    List<Reference> findByCode(@Param("code") String code);

}

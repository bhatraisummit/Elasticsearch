/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.knittech.webapp.repository;

import com.knittech.webapp.entity.Book;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sumit
 */
@Repository
public interface BookRepository extends ElasticsearchCrudRepository<Book,String >{
    
    List<Book> findByName(String name);
    
    List<Book> findByPrice (Integer price);
    
}

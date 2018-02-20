/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.knittech.webapp.controller.rest;

import com.knittech.webapp.entity.Book;
import com.knittech.webapp.entity.Grade;
import com.knittech.webapp.repository.BookRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import net._01001111.text.LoremIpsum;
import org.apache.commons.lang.math.RandomUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sumit
 */
@RestController
@RequestMapping(path = "/book/rest")
public class BookRestController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    public void init() {
        elasticsearchTemplate.deleteIndex(Book.class);
        elasticsearchTemplate.createIndex(Book.class);
        elasticsearchTemplate.putMapping(Book.class);
        elasticsearchTemplate.refresh(Book.class);
    }


    @PostMapping(path = "/populate/{id}")
    public String populateSingle(@PathVariable String id) {
        LoremIpsum lorem = new LoremIpsum();
        Book book = new Book(id, RandomUtils.nextInt(), lorem.randomWord(), lorem.randomWord(), lorem.words(5));
        List<Grade> gradeList = new ArrayList<>();
        for (int i = 1; i<=10; i++){
            Grade grade = new Grade(i, RandomUtils.nextInt());
            gradeList.add(grade);
        }
        book.setGrade(gradeList);
        if (bookRepository.save(book) == null) {
            return "Data Not Populated";
        } else {
            return "Single Data Populated";
        }
    }

    @PostMapping(path = "/populatebulk")
    public String populateBulk() {
        List<IndexQuery> indexQueries = new ArrayList<>();
        LoremIpsum lorem = new LoremIpsum();
        for (int i = 2; i < 10; i++) {
            IndexQuery indexQuery = new IndexQuery();
            Book book = new Book(Integer.toString(i), RandomUtils.nextInt(), lorem.randomWord(), lorem.randomWord(), lorem.words(5));
            indexQuery.setId(Integer.toString(i));
            indexQuery.setObject(book);
            indexQueries.add(indexQuery);
        }
        elasticsearchTemplate.bulkIndex(indexQueries);
        elasticsearchTemplate.refresh(Book.class);
        return String.valueOf(bookRepository.count());
    }

    @GetMapping("/book/{id}")
    public Book book(@PathVariable("id") String id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.get();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") String id) {
        bookRepository.deleteById(id);
        if (bookRepository.findById(id).isPresent()) {
            return "unsuccessful";
        } else {
            return "Deleted";
        }
    }

    @PutMapping("/update/{id}")
    public String updateById(@PathVariable("id") String id) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.id(id);
        updateRequest.doc("price", 950);
        UpdateQuery query = new UpdateQueryBuilder().withId(id).withClass(Book.class).withUpdateRequest(updateRequest).build();
        UpdateResponse updateResponse = elasticsearchTemplate.update(query);
        return updateResponse.getResult().toString();
    }

    @PutMapping("/updatebulk")
    public void updateBulk() throws IOException {
        List<UpdateQuery> updateQueries = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.id(String.valueOf(i));
            updateRequest.doc("price", 950);
            UpdateQuery query = new UpdateQueryBuilder().withId(String.valueOf(i)).withClass(Book.class).withUpdateRequest(updateRequest).build();
            updateQueries.add(query);
        }
        elasticsearchTemplate.bulkUpdate(updateQueries);
        
    }

    @GetMapping("/book/all")
    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }
    
    @GetMapping("/book/price/{price}")
    public List<Book> getBookByPrice(@PathVariable("price") int price){
        return bookRepository.findByPrice(price);
    }
}

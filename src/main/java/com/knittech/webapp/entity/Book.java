/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.knittech.webapp.entity;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;



/**
 *
 * @author Sumit
 */
@Document(indexName = "book", type = "info")
public class Book {
    
    @Id 
    public String id;
    
    @Field(type = FieldType.Integer)
    public Integer price;
    
    @Field(type = FieldType.keyword)
    public String name;
    
    @Field(type = FieldType.keyword)
    public String author;
    
    @Field(type = FieldType.keyword)
    public String publication;
    

    @Field(type = FieldType.Nested)
    public List<Grade> grade;
   
    public Book() {
    }

    public Book(String id, Integer price, String name, String author, String publication) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.author = author;
        this.publication = publication;
    }

    public void setGrade(List<Grade> grade) {
        this.grade = grade;
    }

    public List<Grade> getGrade() {
        return grade;
    }
    
    

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }
    
    
    
}

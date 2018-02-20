/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.knittech.webapp.entity;

/**
 *
 * @author Sumit
 */
public class Grade {
    
    private int grade;
    private int numbers;

    public Grade() {
    }

    public Grade(int grade, int numbers) {
        this.grade = grade;
        this.numbers = numbers;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }
    
}

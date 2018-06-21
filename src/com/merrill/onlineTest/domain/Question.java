package com.merrill.onlineTest.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
public class Question {
    private Long id;
    private Sort sort;
    private Selection selection;
    private String stem;
    private String answer;
//    private String showAnswer;
    private List<Option> options = new ArrayList<>();
}

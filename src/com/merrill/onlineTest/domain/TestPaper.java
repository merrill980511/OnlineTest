package com.merrill.onlineTest.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class TestPaper {
    private Long id;
    private String name;
    private Timestamp startTime;
    private Timestamp endTime;
    private Sort sort;
    private int time;
    private List<Question> questions = new ArrayList<>();
}

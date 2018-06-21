package com.merrill.onlineTest.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TestResult {
    private Long id;
    private User user;
    private TestPaper testPaper;
    private BigDecimal mark;
}

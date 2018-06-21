package com.merrill.onlineTest.query;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class QueryTestResult {
    private BigDecimal max = new BigDecimal(100);
    private BigDecimal min = new BigDecimal(0);
    private Long userId;
    private Long testId;
    private String userName;
    private String testName;
    private Long sort = -1L;
    private int start;
    private int end;
    private List<Object> parameters = new ArrayList<>();
    private List<String> conditions = new ArrayList<>();

    boolean isCount = false;

    public String getQuery() {
        StringBuilder sql = new StringBuilder(80);
        parameters = new ArrayList<>();
        conditions = new ArrayList<>();
        if (userId != null) {
            conditions.add("u.id LIKE ? ");
            parameters.add("%" + userId + "%");
        }
        if (testId != null) {
            conditions.add("t.id LIKE ? ");
            parameters.add("%" + testId + "%");
        }
        if (StringUtils.isNotBlank(userName)) {
            conditions.add("u.name LIKE ? ");
            parameters.add("%" + userName + "%");
        }
        if (StringUtils.isNotBlank(testName)) {
            conditions.add("t.name LIKE ? ");
            parameters.add("%" + testName + "%");
        }
        if (sort != -1L){
            conditions.add("t.s_id = ? ");
            parameters.add(sort);
        }

        if (!max.equals(100)){
            conditions.add("tr.mark <= ? ");
            parameters.add(max);
        }
        if (!min.equals(0)){
            conditions.add("tr.mark >= ? ");
            parameters.add(min);
        }
        System.out.println(parameters + "    " + isCount);
        if (!isCount) {
            parameters.add(start);
            parameters.add(end);
        }
        isCount = false;
        if (conditions.size() == 0) {
            return "";
        }
        System.out.println(parameters);
        String queryString = StringUtils.join(conditions, " AND ");
        return sql.append("WHERE ").append(queryString).toString();
    }

}

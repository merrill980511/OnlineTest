package com.merrill.onlineTest.query;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryQuestion {
    private Long id;
    private String name;
    private Long sort = -1L; //按学科分类查询
    private Long selection = -1L; //单选还是多选
    private int start;
    private int end;
    private List<Object> parameters = new ArrayList<>();
    private List<String> conditions = new ArrayList<>();

    boolean isCount = false;

    public String getQuery(){
        StringBuilder sql = new StringBuilder(80);
        parameters = new ArrayList<>();
        conditions = new ArrayList<>();
        if (id != null){
            conditions.add("q.id LIKE ? ");
            parameters.add("%"+id+"%");
        }
        if (StringUtils.isNotBlank(name)){
            conditions.add("q.stem LIKE ? ");
            parameters.add("%"+name+"%");
        }
        if (sort != -1L){
            conditions.add("s.id = ? ");
            parameters.add(sort);
        }
        if (selection != -1L){
            conditions.add("se.id = ? ");
            parameters.add(selection);
        }
        System.out.println(parameters+ "    "+isCount);
        if (!isCount){
            if (start < 0){
                start = 0;
            }
            parameters.add(start);
            parameters.add(end);
        }
        isCount = false;
        if (conditions.size() == 0){
            return "";
        }
        System.out.println(parameters);
        String queryString = StringUtils.join(conditions, " AND ");
        return sql.append("WHERE ").append(queryString).toString();
    }
}

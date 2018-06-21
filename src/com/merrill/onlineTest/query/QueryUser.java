package com.merrill.onlineTest.query;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryUser {
    private Long id;
    private String name;
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
            conditions.add("id LIKE ? ");
            parameters.add("%"+id+"%");
        }
        if (StringUtils.isNotBlank(name)){
            conditions.add("name LIKE ? ");
            parameters.add("%"+name+"%");
        }
        System.out.println(parameters+ "    "+isCount);
        if (!isCount){
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

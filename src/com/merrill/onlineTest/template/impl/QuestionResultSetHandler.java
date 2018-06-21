package com.merrill.onlineTest.template.impl;

import com.merrill.onlineTest.domain.Option;
import com.merrill.onlineTest.domain.Question;
import com.merrill.onlineTest.domain.Selection;
import com.merrill.onlineTest.domain.Sort;
import com.merrill.onlineTest.template.IResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QuestionResultSetHandler implements IResultSetHandler<List<Question>> {
    @Override
    public List<Question> handler(ResultSet rs) throws SQLException {
        List<Question> list = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        Long id = new Long("-1");
        while (rs.next()){
            if (rs.getLong("q_id") != id){
                Question question = new Question();
                list.add(question);
                question.setId(rs.getLong("id"));
                question.setStem(rs.getString("stem").trim());

                Selection selection = new Selection();
                selection.setId(rs.getLong("se.id"));
                selection.setName(rs.getString("se.name").trim());
                question.setSelection(selection);

                Sort sort = new Sort();
                sort.setId(rs.getLong("s.id"));
                sort.setName(rs.getString("s.name").trim());
                question.setSort(sort);

                question.setAnswer(rs.getString("answer").trim());
                Option option = new Option();
                options = new ArrayList<>();
                question.setOptions(options);
                id = rs.getLong("id");
                option.setId(rs.getLong("o.id"));
                option.setItem(rs.getString("item").trim());
                option.setContent(rs.getString("content").trim());
                options.add(option);
            } else {
                Option option = new Option();
                id = rs.getLong("id");
                option.setId(rs.getLong("o.id"));
                option.setItem(rs.getString("item").trim());
                option.setContent(rs.getString("content").trim());
                options.add(option);
            }

        }
        for (Iterator it = list.iterator(); it.hasNext(); ){
            Question question = (Question) it.next();
            List<Option> options1 = question.getOptions();
            Collections.sort(options1, new Comparator<Option>() {
                @Override
                public int compare(Option o1, Option o2) {
                    if (o1.getItem().compareTo(o2.getItem()) > 0){
                        return 1;
                    }
                    if (o1.getItem().compareTo(o2.getItem()) == 0){
                        return 0;
                    }
                    return -1;
                }
            });
        }
        return list;
    }
}
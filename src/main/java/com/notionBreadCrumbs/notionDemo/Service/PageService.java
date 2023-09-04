package com.notionBreadCrumbs.notionDemo.Service;

import com.notionBreadCrumbs.notionDemo.entity.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> getPageInfo(int pageId) {

        String query = "SELECT * FROM pages WHERE id = " + pageId;
        Page page = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Page.class));

        Map<String, Object> result = new HashMap<>();

        result.put("pageId", page.getId());
        result.put("title", page.getTitle());
        result.put("content", page.getContent());
        result.put("subPages", getSubPages(pageId));
        result.put("breadcrumbs", getBreadcrumbs(pageId));


        return result;
    }

    // 경로 브레드크럼
    private List<Integer> getBreadcrumbs(int pageId) {
        List<Integer> breadcrumbs = new ArrayList<>(Arrays.asList(pageId)); // 현재 노션 페이지 Id 저장
        while (pageId > 0) {

            String query = "SELECT parent_id FROM pages WHERE id = " + pageId;
            Page page = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Page.class));

            if(page.getParentId() != null) { // 부모 페이지가 있는 경우
                breadcrumbs.add(page.getParentId());
                pageId = page.getParentId();
            }
            else // 부모 페이지가 없는 경우
                break;

        }
        Collections.reverse(breadcrumbs); // 최상위 페이지 출력을 위해 역순서
        return breadcrumbs;
    }

    // 하위 페이지 확인
    private List<Integer> getSubPages(int pageId) {

        String query = "SELECT id FROM pages WHERE parent_id = " + pageId;
        List<Page> pageList= jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Page.class));

        // id값만 추출
        List<Integer> idList = new ArrayList<>();
        for(int i = 0; i < pageList.size(); i++)
            idList.add(pageList.get(i).getId());

        return idList;
    }
}


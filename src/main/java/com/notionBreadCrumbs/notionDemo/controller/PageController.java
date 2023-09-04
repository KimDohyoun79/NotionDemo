package com.notionBreadCrumbs.notionDemo.controller;

import com.notionBreadCrumbs.notionDemo.Service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/pages")
public class PageController {

    @Autowired
    private PageService pageService;

    @GetMapping("/{pageId}")
    public ResponseEntity<Map<String, Object>> getPageInfo(@PathVariable int pageId) {
        return ResponseEntity.ok(pageService.getPageInfo(pageId));
    }
}

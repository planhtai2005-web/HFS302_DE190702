package com.de190702.mvc.service;

import com.de190702.mvc.entity.Category;

import java.util.List;

public interface CategoryService {
    // Q3.1 / Q5.2.9 - Category dropdown must be populated from the database
    List<Category> getAllCategories();
}
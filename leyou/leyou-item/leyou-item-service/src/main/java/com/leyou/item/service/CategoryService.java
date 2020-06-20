package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> queryCategoriesByPid(Long pid);

    List<String> queryNamesByIds(List<Long> ids);

    void addCategory(Category category);

    void deleteCategory(Long id);

    void updateCategory(Category category);

    List<Category> queryByBrandId(Long bid);
}

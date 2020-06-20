package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

public interface BrandService {

    PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    void saveBrand(Brand brand, List<Long> cid);

    List<Brand> queryBrandsByCid(Long cid);

    Brand queryBrandById(Long id);

    void updateBrand(Brand brand, List<Long> cids);

    void deleteBrand(Long bid);
}

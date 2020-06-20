package com.leyou.item.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    /**
     * 根据查询条件分页并排序查询品牌信息
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @Override
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        // 初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        // 根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        // 添加分页条件
        PageHelper.startPage(page, rows);
        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        List<Brand> brands = brandMapper.selectByExample(example);
        // 包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        // 包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增品牌
     * @param brand
     * @param cid
     */
    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //1.新增brand
        brandMapper.insertSelective(brand);

        //2.更新中间表
        cids.forEach(cid -> {
            brandMapper.insertBrandAndCategory(cid, brand.getId());
        });
    }
    /**
     * 根据分类Id查询品牌列表
     * @param cid
     * @return
     */
    @Override
    public List<Brand> queryBrandsByCid(Long cid) {
        return this.brandMapper.selectBrandByCid(cid);
    }
    /**
     * 根据品牌Id查询品牌
     * @param cid
     * @return
     */
    @Override
    public Brand queryBrandById(Long id) {
        Brand brand = this.brandMapper.selectByPrimaryKey(id);
        return brand;
    }

    /**
     * 修改品牌
     * @param brand
     * @param cids
     */
    @Override
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        //1.修改brand
        brandMapper.updateByPrimaryKeySelective(brand);

        //2.更新中间表
        //1)先把旧的删了
        brandMapper.deleteBrandAndCategory(brand.getId());
        //2)再添加新的分类
        cids.forEach(cid -> {
            brandMapper.insertBrandAndCategory(cid, brand.getId());
        });
    }

    /**
     * 删除品牌
     * @param bid
     */
    @Override
    @Transactional
    public void deleteBrand(Long bid) {
        //1.删除品牌信息
        this.brandMapper.deleteByPrimaryKey(bid);
        //2.维护中间表
        this.brandMapper.deleteBrandAndCategory(bid);
    }
}

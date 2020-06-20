package com.leyou.item.service.Impl;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 根据parentId查询子类目
     * @param pid
     * @return
     */
    @Override
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        List<Category> select = categoryMapper.select(record);
        return select;
    }
    /**
     * 根据多个商品分类id，查询商品分类名称
     * @param ids
     * @return
     */
    public List<String> queryNamesByIds(List<Long> ids) {
        List<Category> list = this.categoryMapper.selectByIdList(ids);
        List<String> names = new ArrayList<>();
        for (Category category : list) {
            names.add(category.getName());
        }
        return names;
        // return list.stream().map(category -> category.getName()).collect(Collectors.toList());
    }

    /**
     * 新增分类
     * @param category
     */
    @Override
    public void addCategory(Category category) {
        /**
         * 将本节点插入到数据库中
         * 将此category的父节点的isParent设为true
         */
        //1.首先设置id为null
        category.setId(null);
        //2.保存到数据库
        this.categoryMapper.insert(category);
        //3.修改父节点
        Category parent = new Category();
        parent.setId(category.getParentId());
        parent.setIsParent(true);
        this.categoryMapper.updateByPrimaryKeySelective(parent);

    }

    /**
     * 根据id删除分类
     * @param id
     */
    @Override
    public void deleteCategory(Long id) {
        /**
         * 分情况删除：
         *   一、如果是子节点
         *      看看有没有兄弟节点，如果有兄弟节点，则直接删除该子节点
         *      如果没有兄弟节点，则除了删除该节点外，还需要修改父节点的isParent的值为false，然后维护中间表
         *   二、如果是父节点
         *      1.删除该节点及其所有子孙节点，然后要维护中间表
         */
        Category category=this.categoryMapper.selectByPrimaryKey(id);
        //如果是子节点
        if(!category.getIsParent()){
            //查询此节点的父亲节点的孩子个数 ===> 查询还有几个兄弟
            List<Category> categories = this.queryCategoriesByPid(category.getParentId());
            //如果还有兄弟节点
            if(categories.size()>1){
                //有兄弟,直接删除自己
                this.categoryMapper.deleteByPrimaryKey(id);
                //维护中间表
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(id);
            }else{
                //没有兄弟节点，先删除自己
                this.categoryMapper.deleteByPrimaryKey(id);
                //修改父节点的isParant的值为false
                Category parent = new Category();
                parent.setId(category.getParentId());
                parent.setIsParent(false);
                this.categoryMapper.updateByPrimaryKeySelective(parent);
                //维护中间表
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(id);
            }
        }else{
            //如果是父亲节点
            //首先要删除该父节点以及其所有子孙节点
            //1.查找其所有子节点
            ArrayList<Category> list = new ArrayList<>();
            queryAllChildrens(category,list);
            //2.查找其所有叶子节点，用来更新中间表，
            //中间表：tb_category_brand，是用来维护分类和品牌的对应关系的，
            //分类和品牌是多对多的关系，
            //所以中间表中保存的数据就是category_id和brand_id，
            //而且category_id中保存的是最底一层的类目id，也就是分类的叶子节点id。
            ArrayList<Category> list2 = new ArrayList<>();
            queryAllLeafNode(category,list2);
            //3.删除节点本身以及其所有子孙节点，也就是list集合中的所有元素
            for (Category category1 : list) {
                this.categoryMapper.delete(category1);
            }

            //4.维护中间表
            for (Category category1 : list2) {
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(category1.getId());
            }

        }
    }

    /**
     * 查询category节点的所有叶子节点，并放入list2集合
     * @param category
     * @param list2
     */
    private void queryAllLeafNode(Category category, ArrayList<Category> list2) {
        //首先其所有子孙节点，得到arrayList集合
        ArrayList<Category> arrayList = new ArrayList<>();
        this.queryAllChildrens(category,arrayList);
        //遍历arrayList集合，如果该元素不是父亲节点，则就是叶子节点，加入到list2集合
        for (Category category1 : arrayList) {
           if(!category1.getIsParent()){
               list2.add(category1);
           }
        }

    }

    /**
     * 查询category节点的所有子节点，并放入list集合
     * @param category
     * @param list
     */
    private void queryAllChildrens(Category category, ArrayList<Category> list) {
        //先把当前父节点加入到list集合
        list.add(category);
        //根据父节点查询其所有子节点
        List<Category> categories = this.queryCategoriesByPid(category.getId());
        //此处使用递归，因为根据父节点查询到的子节点有可能也是其他子节点的父节点，并且现在并没有把所有的
        //该父节点下的子节点加入到list集合
        for (Category category1 : categories) {
            queryAllChildrens(category1,list);
        }
    }

    /**
     * 修改分类
     * @param category
     */
    @Override
    public void updateCategory(Category category) {
        this.categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public List<Category> queryByBrandId(Long bid) {
        return this.categoryMapper.queryByBrandId(bid);
    }
}

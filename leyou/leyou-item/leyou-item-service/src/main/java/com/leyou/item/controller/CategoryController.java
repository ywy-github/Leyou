package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.Impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
      @Autowired
      private CategoryServiceImpl categoryServiceImpl;
       /**
      * 根据父id查询子节点
      * @param pid
       */
      @GetMapping("list")
      public ResponseEntity<List <Category>> queryCategoriesByPid(@RequestParam(value ="pid",defaultValue ="0") Long pid){
          if(pid==null||pid<0)
          {
              // 响应400，相当于ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
              return ResponseEntity.badRequest().build();
          }
          List<Category> categories = categoryServiceImpl.queryCategoriesByPid(pid);
          if(CollectionUtils.isEmpty(categories))
          {
              // 响应404
              return ResponseEntity.notFound().build();
          }
          return ResponseEntity.ok(categories);
      }

    /**
     * 新增分类
     * @param category
     * @return
     */
      @PostMapping
      public ResponseEntity<Void> addCategary(Category category){
          System.out.println(category);
          this.categoryServiceImpl.addCategory(category);
          return ResponseEntity.status(HttpStatus.CREATED).build();

      }
      /**
     * 删除分类
     * @return
     */
    @DeleteMapping("cid/{cid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("cid") Long id){
        this.categoryServiceImpl.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改分类
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCategory(Category category){
        this.categoryServiceImpl.updateCategory(category);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 根据多个商品分类id，查询商品分类名称
     * @param ids
     * @return
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){

        List<String> names = this.categoryServiceImpl.queryNamesByIds(ids);
        if (CollectionUtils.isEmpty(names)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }
    /**
     * 通过品牌id查询商品分类
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long bid) {
        List<Category> list = this.categoryServiceImpl.queryByBrandId(bid);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}

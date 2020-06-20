package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.Impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {
      @Autowired
      private BrandServiceImpl brandService;

      /**
       * 根据查询条件分页并排序查询品牌信息
       * @param key
       * @param page
       * @param rows
       * @param sortBy
       * @param desc
       * @return
       */
      @GetMapping("page")
      public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
              @RequestParam(value = "key",required = false) String key,
              @RequestParam(value = "page",defaultValue = "1") Integer page,
              @RequestParam(value = "rows",defaultValue = "5") Integer rows,
              @RequestParam(value = "sortBy",required = false) String sortBy,
              @RequestParam(value = "desc",required = false) Boolean desc

      ){
              PageResult<Brand> result = brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
              if(CollectionUtils.isEmpty(result.getItems())){
                    return ResponseEntity.notFound().build();
              }

            return ResponseEntity.ok(result);
      }

    /**
     * 新增品牌
     * @param brand
     * @param cid
     * @return
     */
      @PostMapping
      public ResponseEntity<Void> saveBrand(Brand brand,@RequestParam("cids") List<Long> cids){

          brandService.saveBrand(brand,cids);
          return ResponseEntity.status(HttpStatus.CREATED).build();

      }

    /**
     * 修改品牌
     * @param brand
     * @param cids
     * @return
     */
     @PutMapping
     public ResponseEntity<Void> updateBrand(Brand brand,@RequestParam("cids") List<Long> cids){
          brandService.updateBrand(brand,cids);
          return ResponseEntity.status(HttpStatus.OK).build();
     }

    /**
     * 删除品牌
     * @param bid
     * @return
     */
     @DeleteMapping("bid/{bid}")
     public ResponseEntity<Void> deleteBrand(@PathVariable("bid") Long bid){
          this.brandService.deleteBrand(bid);
          return ResponseEntity.ok().build();
     }
    /**
     * 根据分类Id查询品牌列表
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid")Long cid){
        List<Brand> brands = this.brandService.queryBrandsByCid(cid);
        if (CollectionUtils.isEmpty(brands)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }
    /**
     * 根据品牌Id查询品牌
     * @param cid
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        Brand brand = this.brandService.queryBrandById(id);
        if(brand==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }

}

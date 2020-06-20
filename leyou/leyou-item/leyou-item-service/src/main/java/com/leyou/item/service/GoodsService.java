package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

public interface GoodsService {

    PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows);

    void saveGoods(SpuBo spuBo);

    SpuDetail querySpuDetailBySpuId(Long spuId);

    void updateGoods(SpuBo spu);

    Spu querySpuById(Long id);

    void deleteGoods(Long spuId);

    void goodsSoldOut(Long id);

    Sku querySkuById(Long id);
}

package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

public interface SpecificationService {
    List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching);

    List<SpecGroup> queryGroupsByCid(Long cid);

    List<SpecGroup> querySpecsByCid(Long cid);

    void addSpecGroup(SpecGroup group);

    void updateSpecGroup(SpecGroup group);

    void deleteSpecGroup(Long id);

    void addSpecParam(SpecParam specParam);

    void updateSpecParam(SpecParam specParam);

    void deleteSpecParam(Long id);
}

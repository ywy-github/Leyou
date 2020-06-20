package com.leyou.item.service.Impl;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin.dom.html.HTMLInputElement;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecParamMapper paramMapper;
    @Autowired
    private SpecGroupMapper groupMapper;


    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.groupMapper.select(specGroup);
    }

    @Override
    public List<SpecGroup> querySpecsByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> groups = this.queryGroupsByCid(cid);
        groups.forEach(g -> {
            // 查询组内参数
            g.setParams(this.queryParams(g.getId(), null, null, null));
        });
        return groups;
    }


    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        return this.paramMapper.select(record);
    }

    /**
     * 新增规格参数组
     * @param group
     */
    @Override
    @Transactional
    public void addSpecGroup(SpecGroup group) {

        this.groupMapper.insertSelective(group);
    }

    /**
     * 修改规格参数组
     * @param group
     */
    @Override
    @Transactional
    public void updateSpecGroup(SpecGroup group) {
        this.groupMapper.updateByPrimaryKeySelective(group);
    }

    /**
     * 删除规格参数组
     * @param id
     */
    @Override
    @Transactional
    public void deleteSpecGroup(Long id){
        //1.先根据组Id删除规格参数
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(id);
        this.paramMapper.delete(specParam);
        //2.再根据组Id删除规格参数组
        this.groupMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增规格参数
     * @param specParam
     */
    @Override
    public void addSpecParam(SpecParam specParam) {
        this.paramMapper.insertSelective(specParam);
    }

    /**
     * 修改规格参数
     * @param specParam
     */
    @Override
    public void updateSpecParam(SpecParam specParam) {
        this.paramMapper.updateByPrimaryKeySelective(specParam);
    }
    /**
     * 删除规格参数
     * @param id
     * @return
     */
    @Override
    public void deleteSpecParam(Long id) {
        this.paramMapper.deleteByPrimaryKey(id);
    }

}

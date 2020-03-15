package com.qingcheng.dao;

import com.qingcheng.pojo.goods.Spec;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpecMapper extends Mapper<Spec> {
    /**
     * =查询所有规格列表
     * @return
     */
    @Select("SELECT DISTINCT b.name categoryName,a.name specName,a.options specOptions FROM tb_spec a LEFT JOIN tb_category b ON a.template_id=b.template_id ORDER BY a.seq")
    public List<Map> findAllSpecList();
}

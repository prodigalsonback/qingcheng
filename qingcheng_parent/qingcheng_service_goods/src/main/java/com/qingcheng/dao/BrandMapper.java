package com.qingcheng.dao;

import com.qingcheng.pojo.goods.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends Mapper<Brand> {

    /**
     * 查询所有品牌列表
     * @return
     */
    @Select("SELECT a.name categoryName,b.name brandName,b.image image FROM tb_category a  " +
            " JOIN tb_category_brand c ON a.id=c.category_id  " +
            " JOIN tb_brand b ON b.id = c.brand_id ORDER BY b.seq ")
    public List<Map> findAllBrandList();
}

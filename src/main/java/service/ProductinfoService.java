package service;

import entity.Productinfo;

import java.util.List;

public interface ProductinfoService {
    public List<Productinfo> selectAllProductsByP_type(String p_type,Integer page);

    List<String> selectAllP_type();

    List<Productinfo> selectAllProductsP_type(String p_type);

    List<Productinfo> selectAll();

    int selectTotal(String p_type);

    int deleteByPrimaryKey(Integer pId);

    int insert(Productinfo record);

    int insertSelective(Productinfo record);

    Productinfo selectByPrimaryKey(Integer pId);

    int updateByPrimaryKeySelective(Productinfo record);

    int updateByPrimaryKey(Productinfo record);
}

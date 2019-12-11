package service;

import entity.Orderinfo;

public interface OrderInfoService {

    int deleteByPrimaryKey(Integer oId);

    int insert(Orderinfo record);

    int insertSelective(Orderinfo record);

    Orderinfo selectByPrimaryKey(Integer oId);

    int updateByPrimaryKeySelective(Orderinfo record);

    int updateByPrimaryKey(Orderinfo record);
}

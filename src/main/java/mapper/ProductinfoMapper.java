package mapper;

import entity.Productinfo;

import java.util.List;

public interface ProductinfoMapper {
    //数组不好用,用list
    List<String> selectAllP_type();

    int deleteByPrimaryKey(Integer pId);

    int insert(Productinfo record);

    int insertSelective(Productinfo record);

    Productinfo selectByPrimaryKey(Integer pId);

    int updateByPrimaryKeySelective(Productinfo record);

    int updateByPrimaryKey(Productinfo record);
}
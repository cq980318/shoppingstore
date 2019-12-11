package mapper;

import entity.Productinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductinfoMapper {

    List<Productinfo> selectAllProductsByP_type(@Param(value = "p_type2") String p_type, @Param(value = "page2") Integer page);
    //数组不好用,用list
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
package service;

import entity.Userinfo;

public interface UserService {
    Userinfo selectByUsername(String username);
    String selectEmailByUsername(String username);
    int selectUidByUsername(String username);

    int deleteByPrimaryKey(Integer uId);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    Userinfo selectByPrimaryKey(Integer uId);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKey(Userinfo record);
}

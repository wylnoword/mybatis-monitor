package com.github.mybatis.monitor.dao;

import java.util.List;

import com.github.mybatis.monitor.entity.TUser;
import org.apache.ibatis.annotations.Param;


public interface TUserMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert1(TUser record);
    
    int insert2(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    List<TUser> selectUserHealthReport();

    List<TUser> selectByEmailAndSex2(@Param("email")String email,@Param("sex")Byte sex);

    List<TUser> selectIfandWhereOper(@Param("email")String email,@Param("sex")Byte sex);
    
    List<TUser> selectChooseOper(@Param("email")String email,@Param("sex")Byte sex);
    
    int updateIfOper(TUser record);
    
    int updateIfAndSetOper(TUser record);
    
    int insertIfOper(TUser record);
    
    List<TUser> selectForeach4In(String[] names);
    
    int insertForeach4Batch(List<TUser> users);
    
}
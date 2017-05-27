package com.beardream.dao;

import com.beardream.model.Dish;
import com.beardream.model.DishDishBusinessTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DishMapper {
    int deleteByPrimaryKey(Integer dishId);

    int insert(Dish record);

    int insertSelective(Dish record);

    List<Dish> findBySelective(Dish dish);

    Dish selectByPrimaryKey(Integer dishId);

    int updateByPrimaryKeySelective(Dish record);

    int updateByPrimaryKeyWithBLOBs(Dish record);

    int updateByPrimaryKey(Dish record);

    //连接查询菜品、菜品商家和标签
    List<DishDishBusinessTag> findDishDishBusinessTagSelective(DishDishBusinessTag dishDishBusinessTag);

}
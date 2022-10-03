package com.ajie.service;


import com.ajie.entity.Dish;
import com.ajie.entity.dto.DishDto;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应得口味数据 ------ dish / dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //更新菜品，同时更新菜品对应的口味数据
    public void updateWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);
}

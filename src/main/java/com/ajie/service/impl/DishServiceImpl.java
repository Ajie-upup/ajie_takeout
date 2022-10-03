package com.ajie.service.impl;

import com.ajie.entity.Dish;
import com.ajie.entity.DishFlavor;
import com.ajie.entity.dto.DishDto;
import com.ajie.mapper.DishMapper;
import com.ajie.service.DishFlavorService;
import com.ajie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应得口味数据
     *
     * @param dishDto
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本数据到菜品表dish
        this.save(dishDto);

        //菜品口味id
        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        //将集合中的每个元素的id设置值
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表 dishFlavor
        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品的基本信息，从dish表查询
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        //查询当前菜品对应的口味信息，从dish_favor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表的基本信息
        this.updateById(dishDto);

        //清理当前菜品对应的口味数据  ------ delete
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        //添加当前提交的口味信息 -----  insert
        List<DishFlavor> flavors = dishDto.getFlavors();
        //将集合中的每个元素的id设置值
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表 dishFlavor
        dishFlavorService.saveBatch(flavors);
    }
}

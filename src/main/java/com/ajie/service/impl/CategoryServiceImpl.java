package com.ajie.service.impl;

import com.ajie.common.CustomException;
import com.ajie.entity.Category;
import com.ajie.entity.Dish;
import com.ajie.entity.Setmeal;
import com.ajie.mapper.CategoryMapper;
import com.ajie.service.CategoryService;
import com.ajie.service.DishService;
import com.ajie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:hyj
 * @date:2022/10/2
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除之前需要进行判断
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据id进行查询
        dishQueryWrapper.eq(Dish::getCategoryId, id);

        int count1 = dishService.count(dishQueryWrapper);

        //查询当前分类是否关联了菜品，如果已经关联了，抛出一个业务异常
        if (count1 > 0) {
            //已经关联了，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId, id);

        int count2 = setmealService.count(setmealQueryWrapper);

        //查询当前分类是否关联了套餐，如果已经关联了，抛出一个业务异常
        if (count2 > 0) {
            //已经关联了套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除
        super.removeById(id);
    }
}

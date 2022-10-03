package com.ajie.entity.dto;

import com.ajie.entity.Dish;
import com.ajie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:hyj
 * @date:2022/10/3
 */
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}

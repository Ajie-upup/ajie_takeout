package com.ajie.service;

import com.ajie.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author:hyj
 * @date:2022/10/2
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}

package com.clz.web.material.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.material.entity.Material;
import com.clz.web.material.mapper.MaterialMapper;
import com.clz.web.material.service.MaterialService;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {
}

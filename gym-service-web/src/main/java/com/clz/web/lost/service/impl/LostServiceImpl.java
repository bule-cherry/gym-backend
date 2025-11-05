package com.clz.web.lost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.lost.entity.Lost;
import com.clz.web.lost.mapper.LostMapper;
import com.clz.web.lost.service.LostService;
import org.springframework.stereotype.Service;

@Service
public class LostServiceImpl extends ServiceImpl<LostMapper, Lost> implements LostService {
}

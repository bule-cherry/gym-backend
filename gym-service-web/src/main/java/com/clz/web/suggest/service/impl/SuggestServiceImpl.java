package com.clz.web.suggest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.suggest.entity.Suggest;
import com.clz.web.suggest.mapper.SuggestMapper;
import com.clz.web.suggest.service.SuggestService;
import org.springframework.stereotype.Service;

@Service
public class SuggestServiceImpl extends ServiceImpl<SuggestMapper, Suggest> implements SuggestService {
}

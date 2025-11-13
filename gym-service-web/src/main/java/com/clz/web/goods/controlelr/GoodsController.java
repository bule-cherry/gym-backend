package com.clz.web.goods.controlelr;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.goods.entity.Goods;
import com.clz.web.goods.entity.GoodsParam;
import com.clz.web.goods.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/goods")
@Api("商品模块")
public class GoodsController {

    @Resource
    private GoodsService service;

    @PostMapping
    @ApiOperation("添加商品")
    public ResultVo add(@RequestBody Goods goods) {
        if (service.save(goods)) {
            return ResultUtils.success("添加成功");
        } else {
            return ResultUtils.error("添加失败");
        }
    }

    @PutMapping
    @ApiOperation("修改商品")
    public ResultVo edit(@RequestBody Goods goods) {
        if (service.updateById(goods)) {
            return ResultUtils.success("修改成功");
        } else {
            return ResultUtils.error("修改失败");
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除商品")
    public ResultVo delete(@PathVariable("id") Long id) {
        if (service.removeById(id)) {
            return ResultUtils.success("删除成功");
        } else {
            return ResultUtils.error("删除失败");
        }
    }

    @GetMapping("/list")
    @ApiOperation("分页查询商品")
    public ResultVo list(GoodsParam param) {
        /*Page<Goods> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        LambdaQueryWrapper<Goods> query = new LambdaQueryWrapper<Goods>()
                .like(StringUtils.isNotEmpty(param.getName()), Goods::getName, param.getName());*/
        Page<Goods> list = service.selectGoodsPage(param);
        return ResultUtils.success("查询成功", list);
    }

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("{id}")
    public ResultVo getById(@PathVariable("id") Long id) {
        String s = stringRedisTemplate.opsForValue().get("goods:" + id);
        Goods goods = JSON.parseObject(s, Goods.class);
        if (goods != null) {
            return ResultUtils.success("查询成功", goods);
        }
        goods = service.getById(id);
        if (goods == null) {
            return ResultUtils.error("商品不存在");
        }
        String jsonString = JSON.toJSONString(goods);
        stringRedisTemplate.opsForValue().set("goods:" + id, jsonString);
        return ResultUtils.success("查询成功",goods);
    }
}

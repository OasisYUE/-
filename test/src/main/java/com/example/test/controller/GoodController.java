package com.example.test.controller;

import com.example.test.entity.Good;
import com.example.test.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoodController {
    @Autowired
    private GoodService goodService;

    // 获取所有商品
    @GetMapping("/goods")
    public List<Good> getAllGoods() {
        return goodService.getAllGoods();
    }

    // 根据ID获取商品
    @GetMapping("/goods/{id}")
    public Good getGoodById(@PathVariable Long id) {
        return goodService.getGoodById(id).orElseThrow(() -> new RuntimeException("Good not found with ID: " + id));
    }

    // 创建新商品
    @PostMapping("/goods")
    public Good createGood(@RequestBody Good good) {
        return goodService.createGood(good);
    }

    // 更新商品状态
    @PutMapping("/goods/{id}/status")
    public Good updateGoodStatus(@PathVariable Long id, @RequestParam String status) {
        return goodService.updateGoodStatus(id, status);
    }

    // 删除商品
    @DeleteMapping("/goods/{id}")
    public void deleteGood(@PathVariable Long id) {
        goodService.deleteGood(id);
    }

}

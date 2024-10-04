package com.example.test.service;

import com.example.test.entity.Good;
import com.example.test.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodService {
    @Autowired
    private GoodRepository goodRepository;

    // 获取所有商品
    public List<Good> getAllGoods() {
        return goodRepository.findAll();
    }

    // 根据ID获取商品
    public Optional<Good> getGoodById(Long id) {
        return goodRepository.findById(id);
    }

    // 创建新商品
    public Good createGood(Good good) {
        good.setStatus("在售"); // 默认新商品为"在售"状态
        return goodRepository.save(good);
    }

    // 更新商品状态
    public Good updateGoodStatus(Long id, String status) {
        Optional<Good> existingGood = goodRepository.findById(id);

        if (existingGood.isPresent()) {
            Good good = existingGood.get();
            good.setStatus(status);
            return goodRepository.save(good); // 保存更新后的商品
        } else {
            throw new RuntimeException("Good with ID " + id + " not found");
        }
    }

    // 删除商品
    public void deleteGood(Long id) {
        goodRepository.deleteById(id);
    }

}

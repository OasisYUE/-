package com.example.test.controller;

import com.example.test.entity.Buyer;
import com.example.test.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    // 获取所有买家
    @GetMapping
    public ResponseEntity<List<Buyer>> getAllBuyers() {
        List<Buyer> buyers = buyerService.getAllBuyers();
        return new ResponseEntity<>(buyers, HttpStatus.OK);
    }

    // 创建新的买家
    @PostMapping
    public ResponseEntity<Buyer> saveBuyer(@RequestBody Buyer buyer) {
        Buyer createdBuyer = buyerService.saveBuyer(buyer);
        return new ResponseEntity<>(createdBuyer, HttpStatus.CREATED);
    }

    // 删除买家
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long id) {
        buyerService.deleteBuyer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 更新买家信息
    @PutMapping("/{id}")
    public ResponseEntity<Buyer> updateBuyerById(@PathVariable Long id, @RequestBody Buyer updatedBuyer) {
        Optional<Buyer> existingBuyerOptional = buyerService.getBuyerById(id);

        if (existingBuyerOptional.isPresent()) {
            Buyer existingBuyer = existingBuyerOptional.get();

            // 更新字段
            existingBuyer.setName(updatedBuyer.getName());
            existingBuyer.setTradeTime(updatedBuyer.getTradeTime());
            existingBuyer.setTradeAddr(updatedBuyer.getTradeAddr());
            existingBuyer.setStatus(updatedBuyer.getStatus());

            // 保存修改后的记录
            Buyer updated = buyerService.saveBuyer(existingBuyer);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            throw new RuntimeException("Buyer with ID " + id + " not found");
        }
    }
}

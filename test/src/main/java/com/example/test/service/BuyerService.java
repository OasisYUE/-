package com.example.test.service;

import com.example.test.entity.Buyer;
import com.example.test.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyerService {
    @Autowired
    private BuyerRepository buyerRepository;

    public List<Buyer> getAllBuyers() {
        return buyerRepository.findAll();
    }

    public Buyer saveBuyer(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public void deleteBuyer(Long id) {
        buyerRepository.deleteById(id);
    }

    public Optional<Buyer> getBuyerById(Long id) {
        return buyerRepository.findById(id);
    }

    // 根据ID查找并修改买家记录
    public Buyer updateBuyerById(Long id, Buyer updatedBuyer) {
        Optional<Buyer> existingBuyerOptional = buyerRepository.findById(id);

        if (existingBuyerOptional.isPresent()) {
            Buyer existingBuyer = existingBuyerOptional.get();

            // 更新指定字段
            existingBuyer.setName(updatedBuyer.getName());
            existingBuyer.setTradeTime(updatedBuyer.getTradeTime());
            existingBuyer.setTradeAddr(updatedBuyer.getTradeAddr());
            existingBuyer.setStatus(updatedBuyer.getStatus());

            // 保存修改后的记录
            return buyerRepository.save(existingBuyer);
        } else {
            // 如果ID对应的记录不存在，可以抛出异常或返回 null
            throw new RuntimeException("Buyer with ID " + id + " not found");
        }
    }




}

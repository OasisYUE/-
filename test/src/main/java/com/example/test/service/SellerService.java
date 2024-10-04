package com.example.test.service;

import com.example.test.entity.Seller;
import com.example.test.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    // 创建新卖家
    public Seller createSeller(Seller seller) {
        if (sellerRepository.existsById(seller.getAccount())) {
            throw new RuntimeException("Account already exists");
        }
        return sellerRepository.save(seller);
    }

    // 删除卖家
    public void deleteSeller(String account) {
        if (!sellerRepository.existsById(account)) {
            throw new RuntimeException("Seller with account " + account + " not found");
        }
        sellerRepository.deleteById(account);
    }

    // 根据账户更新密码
    public Seller updatePassword(String account, String newPassword) {
        Optional<Seller> sellerOptional = sellerRepository.findById(account);
        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            seller.setPassword(newPassword);
            return sellerRepository.save(seller);
        } else {
            throw new RuntimeException("Seller with account " + account + " not found");
        }
    }
}

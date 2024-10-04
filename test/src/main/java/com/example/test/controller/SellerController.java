package com.example.test.controller;

import com.example.test.entity.Seller;
import com.example.test.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // 创建新卖家
    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        try {
            Seller createdSeller = sellerService.createSeller(seller);
            return new ResponseEntity<>(createdSeller, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    // 删除卖家
    @DeleteMapping("/{account}")
    public ResponseEntity<Void> deleteSeller(@PathVariable String account) {
        try {
            sellerService.deleteSeller(account);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 更新密码
    @PutMapping("/{account}/password")
    public ResponseEntity<Seller> updatePassword(@PathVariable String account, @RequestParam String newPassword) {
        try {
            Seller updatedSeller = sellerService.updatePassword(account, newPassword);
            return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}

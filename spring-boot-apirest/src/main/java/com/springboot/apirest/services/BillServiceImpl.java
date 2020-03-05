package com.springboot.apirest.services;

import com.springboot.apirest.entity.Bill;
import com.springboot.apirest.exceptions.BillNotFoundException;
import com.springboot.apirest.repository.IBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillServiceImpl implements IBillService {

    @Autowired
    private IBillRepository billRepository;

    @Override
    @Transactional(readOnly = true)
    public Bill findById(Integer id) {

        return billRepository.findById(id)
                .orElseThrow(BillNotFoundException::new);
    }

    @Override
    @Transactional
    public Bill save(Bill bill) {

        return billRepository.save(bill);
    }

    @Override
    @Transactional
    public Bill deleteById(Integer id) {

        Bill bill = findById(id);
        billRepository.deleteById(id);

        return bill;
    }
}

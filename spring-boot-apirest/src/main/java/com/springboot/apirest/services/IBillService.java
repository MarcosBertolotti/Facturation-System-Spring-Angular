package com.springboot.apirest.services;

import com.springboot.apirest.entity.Bill;

public interface IBillService {

    Bill findById(Integer id);

    Bill save(Bill bill);

    Bill deleteById(Integer id);
}

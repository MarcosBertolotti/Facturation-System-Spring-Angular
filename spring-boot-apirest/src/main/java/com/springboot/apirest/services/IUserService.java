package com.springboot.apirest.services;

import com.springboot.apirest.entity.User;

public interface IUserService {

    User findByUsername(String username);
}

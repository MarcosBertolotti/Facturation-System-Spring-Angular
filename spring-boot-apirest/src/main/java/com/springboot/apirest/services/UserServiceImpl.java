package com.springboot.apirest.services;


import com.springboot.apirest.entity.User;
import com.springboot.apirest.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {    // interfaz de spring security

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if(user == null){
            logger.error("Login Error: user '" + username + "' doesn't exists!");
            throw  new UsernameNotFoundException("Login Error: user '" + username + "' doesn't exists!");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .peek(authority -> logger.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), user.getEnabled(), true ,true, true, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }
}

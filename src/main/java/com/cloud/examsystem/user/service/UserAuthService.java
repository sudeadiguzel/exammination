package com.cloud.examsystem.user.service;

import com.cloud.examsystem.authentication.common.UserPrincipal;
import com.cloud.examsystem.user.entity.User;
import com.cloud.examsystem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserAuthService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getByUsername(s);
        if (user.isEmpty()){
            throw new UsernameNotFoundException(s);
        }
        return UserPrincipal.of(user.get());
    }


    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object pricipal = auth.getPrincipal();
        try{
            User user =  ((UserPrincipal)pricipal).getUser();
            return user;
        }catch (Exception e){
            return new User();
        }
    }
}

package com.redbus.backend_redbus.service;


import com.redbus.backend_redbus.model.UserTable;
import com.redbus.backend_redbus.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;@Service
public class UsersServiceImplementar implements UserDetailsService {
    @Autowired
    UserTableRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("Username" +
                " is" + s);
        UserTable users = (userRepository.findByName(s));
        if (users == null) {
            System.out.println("No User Found");
            throw new UsernameNotFoundException("Not Found!!");
        } else {
            System.out.println("is user");
            return new UserPrincipal(users);
        }
    }
}

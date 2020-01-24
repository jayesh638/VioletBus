package com.redbus.backend_redbus.config;

import com.redbus.backend_redbus.model.GoogleOAuth2UserInfo;
import com.redbus.backend_redbus.model.Role;
import com.redbus.backend_redbus.model.Ticket;
import com.redbus.backend_redbus.model.UserTable;
import com.redbus.backend_redbus.repository.RoleRepository;
import com.redbus.backend_redbus.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomOidcService extends OidcUserService {
    @Autowired
    private UserTableRepository userTableRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map attributes = oidcUser.getAttributes();
        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setUserId((String) attributes.get("sub"));
        userInfo.setUsername((String) attributes.get("name"));
        updateUser(userInfo);
        return oidcUser;
    }

    private void updateUser(GoogleOAuth2UserInfo userInfo) {
        UserTable users = userTableRepository.findByEmail(userInfo.getEmail());
        if (users == null) {
            users = new UserTable();
            users.setUniqueId(userInfo.getUserId());
            users.setEmail(userInfo.getEmail());
            users.setName(userInfo.getUsername());
            users.setCreatedDate(new Date());
            users.setEnable(true);
            Role role = new Role();
            role.setUserRole("user");
            roleRepository.save(role);
            users.setRoleId(role);
            users.setPassword("123");
            List<Ticket> tickets = new ArrayList<>();
            users.setTickets(tickets);
            userTableRepository.save(users);
        }
    }
}
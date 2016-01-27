package com.saintdan.framework.config.custom;

import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.component.LogHelper;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.tools.LogUtils;
import com.saintdan.framework.tools.SpringSecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Custom authentication provider.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 12/9/15
 * @since JDK1.8
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // Find user.
        String username = token.getName();
        User user = userRepository.findByUsr(username);
        if (user == null) { //If user does not exists, throw UsernameNotFoundException.
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }

        // Compare password and credentials of authentication.
        if (!customPasswordEncoder.matches(token.getCredentials().toString(), user.getPwd())) {
            throw new BadCredentialsException("Invalid username/password");
        }
        UserRepositoryUserDetails userDetails = new UserRepositoryUserDetails(user);

        // Get client ip address.
        String ip = SpringSecurityUtils.getCurrentUserIp();

        // Save user login info.
        user.setLastLoginIP(ip);
        user.setLastLoginTime(new Date());

        if (!userDetails.isEnabled()) {
            throw new DisabledException("User has been disabled.");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("Account has been expired.");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("Account has been locked.");
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new LockedException("Credentials has been expired.");
        }
        userRepository.save(user);

        // Save to log.
        try {
            logHelper.logUsersOperations(LogType.LOGIN, "login", user);
        } catch (Exception e) {
            LogUtils.traceError(logger, e, "Log user login failed.");
        }

        //Authorize.
        return new UsernamePasswordAuthenticationToken(userDetails, user.getPwd(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private LogHelper logHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final static class UserRepositoryUserDetails extends User implements UserDetails {

        private static final long serialVersionUID = -2502869413772228006L;

        private UserRepositoryUserDetails(User user) {
            super(user);
        }

        /**
         * Get the authorities.
         *
         * @return      GrantedAuthorities
         */
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            Set<Role> roles = getRoles();
            for (Role role : roles) {
                Set<Group> groups = role.getGroups();
                for (Group group : groups) {
                    Set<Resource> resources = group.getResources();
                    for (Resource resource : resources) {
                        GrantedAuthority authority = new SimpleGrantedAuthority(resource.getName());
                        authorities.add(authority);
                    }
                }
            }
            return authorities;
        }

        @Override
        public String getUsername() {
            return getUsr();
        }

        @Override
        public String getPassword() {
            return getPwd();
        }

        @Override
        public boolean isAccountNonExpired() {
            return isAccountNonExpiredAlias();
        }

        @Override
        public boolean isAccountNonLocked() {
            return isAccountNonLockedAlias();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return isCredentialsNonExpiredAlias();
        }

        @Override
        public boolean isEnabled() {
            return isEnabledAlias();
        }

    }
}

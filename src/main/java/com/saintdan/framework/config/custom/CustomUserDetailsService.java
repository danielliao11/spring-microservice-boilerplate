package com.saintdan.framework.config.custom;

import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.po.*;
import com.saintdan.framework.repo.LogRepository;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.tools.SpringSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Custom user detail service.
 * Implements {@link UserDetailsService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

    @Autowired
    private LogRepository logRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usr) throws UsernameNotFoundException {
		User user = userRepository.findByUsr(usr);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s does not exist!", usr));
		}
        // Get client ip address.
        String ip = SpringSecurityUtils.getCurrentUserIp();

        // Save user login info.
        user.setLastLoginIP(ip);
        user.setLastLoginTime(new Date());
        userRepository.save(user);

        // Save to log.
        Log log = new Log(ip, user.getId(), user.getUsr(), LogType.LOGIN);
        logRepository.save(log);
        return new UserRepositoryUserDetails(user);
	}

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
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

	}

}

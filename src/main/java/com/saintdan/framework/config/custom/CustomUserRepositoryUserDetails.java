package com.saintdan.framework.config.custom;

import com.saintdan.framework.po.User;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom user repository user details.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 4/22/16
 * @since JDK1.8
 */
public class CustomUserRepositoryUserDetails extends User implements UserDetails {

  private static final long serialVersionUID = -2502869413772228006L;

  public CustomUserRepositoryUserDetails(User user) {
    super(user);
  }

  /**
   * Get the authorities.
   *
   * @return GrantedAuthorities
   */
  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    getRoles()
        .forEach(role -> role.getResources()
            .forEach(resource -> authorities.add(new SimpleGrantedAuthority(resource.getName()))));
    return authorities;
  }

  @Override public String getUsername() {
    return getUsr();
  }

  @Override public String getPassword() {
    return getPwd();
  }

  @Override public boolean isAccountNonExpired() {
    return isAccountNonExpiredAlias();
  }

  @Override public boolean isAccountNonLocked() {
    return isAccountNonLockedAlias();
  }

  @Override public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpiredAlias();
  }

  @Override public boolean isEnabled() {
    return isEnabledAlias();
  }

}

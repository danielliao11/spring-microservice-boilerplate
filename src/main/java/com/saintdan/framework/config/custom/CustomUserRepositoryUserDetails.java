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
public class CustomUserRepositoryUserDetails implements UserDetails {

  private static final long serialVersionUID = -2502869413772228006L;

  private User user;

  public CustomUserRepositoryUserDetails(User user) {
    this.user = user;
  }

  /**
   * Get the authorities.
   *
   * @return GrantedAuthorities
   */
  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    user.getRoles()
        .forEach(role -> role.getResources()
            .forEach(resource -> authorities.add(new SimpleGrantedAuthority(resource.getName()))));
    return authorities;
  }

  @Override public String getUsername() {
    return user.getUsr();
  }

  @Override public String getPassword() {
    return user.getPwd();
  }

  @Override public boolean isAccountNonExpired() {
    return user.isAccountNonExpiredAlias();
  }

  @Override public boolean isAccountNonLocked() {
    return user.isAccountNonLockedAlias();
  }

  @Override public boolean isCredentialsNonExpired() {
    return user.isCredentialsNonExpiredAlias();
  }

  @Override public boolean isEnabled() {
    return user.isEnabledAlias();
  }

}

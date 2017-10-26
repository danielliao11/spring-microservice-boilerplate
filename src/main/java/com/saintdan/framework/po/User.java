package com.saintdan.framework.po;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.listener.PersistentListener;
import com.saintdan.framework.listener.ValidFlagListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Authorized users, provide for spring security oauth2.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/23/15
 * @see {@link org.springframework.security.core.userdetails.UserDetails}
 * @since JDK1.8
 */
@Entity
@Table(name = "users")
@EntityListeners({PersistentListener.class, ValidFlagListener.class})
@NamedEntityGraph(name = "User.roles", attributeNodes = @NamedAttributeNode("roles"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"roles", "accounts"})
@ToString(exclude = {"roles", "accounts"})
public class User implements UserDetails {

  private static final long serialVersionUID = 2680591198337929454L;

  @GenericGenerator(
      name = "userSequenceGenerator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "users_seq"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "1")
      }
  )
  @Id
  @GeneratedValue(generator = "userSequenceGenerator")
  @Column(updatable = false)
  private long id;

  @Column(length = 50)
  private String name;

  @Column(nullable = false, length = 20)
  private String usr;

  @Column(nullable = false, length = 200)
  private String pwd;

  @Column(nullable = false)
  @Builder.Default
  private boolean isAccountNonExpiredAlias = true;

  @Column(nullable = false)
  @Builder.Default
  private boolean isAccountNonLockedAlias = true;

  @Column(nullable = false)
  @Builder.Default
  private boolean isCredentialsNonExpiredAlias = true;

  @Column(nullable = false)
  @Builder.Default
  private boolean isEnabledAlias = true;

  @Column(nullable = false)
  @Builder.Default
  private ValidFlag validFlag = ValidFlag.VALID;

  @Column(columnDefinition = "TEXT")
  private String description;

  // Last login time
  @Builder.Default
  private long lastLoginAt = System.currentTimeMillis();

  // Last login IP address
  private String ip;

  @Column(nullable = false, updatable = false)
  private long createdAt;

  @Column(nullable = false)
  private long createdBy;

  @Column(nullable = false)
  private long lastModifiedAt;

  @Column(nullable = false)
  private long lastModifiedBy;

  @Version
  @Column(nullable = false)
  private int version;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
  @JoinTable(name = "users_has_roles",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private Set<Role> roles;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
  private Set<Account> accounts;

  public User(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.usr = user.getUsr();
    this.pwd = user.getPwd();
    this.roles = user.getRoles();
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

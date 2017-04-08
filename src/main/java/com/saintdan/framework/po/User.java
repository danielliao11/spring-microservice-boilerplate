package com.saintdan.framework.po;

import com.saintdan.framework.enums.ValidFlag;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Authorized users, provide for spring security oauth2.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/23/15
 * @see {@link org.springframework.security.core.userdetails.UserDetails}
 * @since JDK1.8
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "users")
@NamedEntityGraph(name = "User.roles", attributeNodes = @NamedAttributeNode("roles"))
public class User implements Serializable {

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
  private Long id;

  @NotEmpty
  @Column(length = 50)
  private String name;

  @NotEmpty
  @Column(nullable = false, length = 20)
  private String usr;

  @NotEmpty
  @Column(nullable = false, length = 200)
  private String pwd;

  @Column(nullable = false)
  private boolean isAccountNonExpiredAlias = Boolean.TRUE;

  @Column(nullable = false)
  private boolean isAccountNonLockedAlias = Boolean.TRUE;

  @Column(nullable = false)
  private boolean isCredentialsNonExpiredAlias = Boolean.TRUE;

  @Column(nullable = false)
  private boolean isEnabledAlias = Boolean.TRUE;

  @Column(nullable = false)
  private ValidFlag validFlag = ValidFlag.VALID;

  @Column(columnDefinition = "TEXT")
  private String description;

  // Last login time
  private LocalDateTime lastLoginTime = LocalDateTime.now();

  // Last login IP address
  private String ip;

  @CreatedDate
  @Column(nullable = false)
  private LocalDateTime createdDate = LocalDateTime.now();

  @CreatedBy
  @Column(nullable = false)
  private Long createdBy;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime lastModifiedDate = LocalDateTime.now();

  @LastModifiedBy
  @Column(nullable = false)
  private Long lastModifiedBy;

  @Version
  @Column(nullable = false)
  private int version;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
  @JoinTable(name = "users_has_roles",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private Set<Role> roles = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REFRESH)
  private Set<Account> accounts = new HashSet<>();

  public User() {}

  public User(Long id, String name, String usr, String pwd) {
    this.id = id;
    this.name = name;
    this.usr = usr;
    this.pwd = pwd;
  }

  public User(User user) {
    super();
    this.id = user.getId();
    this.name = user.getName();
    this.usr = user.getUsr();
    this.pwd = user.getPwd();
    this.roles = user.getRoles();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsr() {
    return usr;
  }

  public void setUsr(String usr) {
    this.usr = usr;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public boolean isAccountNonExpiredAlias() {
    return isAccountNonExpiredAlias;
  }

  public void setAccountNonExpiredAlias(boolean accountNonExpiredAlias) {
    isAccountNonExpiredAlias = accountNonExpiredAlias;
  }

  public boolean isAccountNonLockedAlias() {
    return isAccountNonLockedAlias;
  }

  public void setAccountNonLockedAlias(boolean accountNonLockedAlias) {
    isAccountNonLockedAlias = accountNonLockedAlias;
  }

  public boolean isCredentialsNonExpiredAlias() {
    return isCredentialsNonExpiredAlias;
  }

  public void setCredentialsNonExpiredAlias(boolean credentialsNonExpiredAlias) {
    isCredentialsNonExpiredAlias = credentialsNonExpiredAlias;
  }

  public boolean isEnabledAlias() {
    return isEnabledAlias;
  }

  public void setEnabledAlias(boolean enabledAlias) {
    isEnabledAlias = enabledAlias;
  }

  public ValidFlag getValidFlag() {
    return validFlag;
  }

  public void setValidFlag(ValidFlag validFlag) {
    this.validFlag = validFlag;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(LocalDateTime lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public Long getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public Long getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(Long lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}

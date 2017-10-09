package com.saintdan.framework.po;

import com.saintdan.framework.enums.ValidFlag;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

/**
 * Authorized resources,provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Entity @EntityListeners({AuditingEntityListener.class}) @Table(name = "resources")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Resource implements GrantedAuthority, Serializable {

  private static final long serialVersionUID = 6298843159549723556L;

  @GenericGenerator(
      name = "resourceSequenceGenerator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "resources_seq"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "1")
      }
  )
  @Id
  @GeneratedValue(generator = "resourceSequenceGenerator")
  @Column(updatable = false)
  private long id;

  @NotEmpty
  @Column(unique = true, nullable = false, length = 20)
  private String name;

  @Column(length = 500)
  private String description;

  @Column(nullable = false)
  private ValidFlag validFlag = ValidFlag.VALID;

  @Column(updatable = false)
  private long createdAt = System.currentTimeMillis();

  @Column(nullable = false, updatable = false)
  private long createdBy;

  private long lastModifiedAt = System.currentTimeMillis();

  @Column(nullable = false)
  private long lastModifiedBy;

  @Version
  @Column(nullable = false)
  private int version;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources", cascade = CascadeType.REFRESH)
  private Set<Role> roles = new HashSet<>();

  @PreRemove
  private void removeResourcesFromRoles() {
    roles.forEach(role -> role.getResources().remove(this));
  }

  public Resource(Long id) {
    this.id = id;
  }

  public Resource(String name, String description) {
    this.name = name;
    this.description = description;
  }

  @Override
  public String getAuthority() {
    return name;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("description", description)
        .append("validFlag", validFlag.code())
        .append("createdAt", createdAt)
        .append("createdBy", createdBy)
        .append("lastModifiedAt", lastModifiedAt)
        .append("lastModifiedBy", lastModifiedBy)
        .append("version", version)
        .toString();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ValidFlag getValidFlag() {
    return validFlag;
  }

  public void setValidFlag(ValidFlag validFlag) {
    this.validFlag = validFlag;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public long getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
  }

  public long getLastModifiedAt() {
    return lastModifiedAt;
  }

  public void setLastModifiedAt(long lastModifiedAt) {
    this.lastModifiedAt = lastModifiedAt;
  }

  public long getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(long lastModifiedBy) {
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

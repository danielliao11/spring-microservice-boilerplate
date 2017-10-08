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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Authorized roles, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/23/15
 * @since JDK1.8
 */
@Entity @EntityListeners({AuditingEntityListener.class}) @Table(name = "roles")
@NamedEntityGraph(name = "Role.resources", attributeNodes = @NamedAttributeNode("resources"))
public class Role implements Serializable {

  private static final long serialVersionUID = -5193344128221526323L;

  @GenericGenerator(
      name = "roleSequenceGenerator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "roles_seq"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "1")
      }
  )
  @Id
  @GeneratedValue(generator = "roleSequenceGenerator")
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

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = CascadeType.REFRESH)
  private Set<User> users = new HashSet<>();

  @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH })
  @JoinTable(name = "roles_has_resources",
      joinColumns = { @JoinColumn(name = "role_id") },
      inverseJoinColumns = { @JoinColumn(name = "resource_id") })
  private Set<Resource> resources = new HashSet<>();

  @PreRemove
  private void removeRolesFromUsers() {
    users.forEach(user -> user.getRoles().remove(this));
  }


  public Role() {}

  public Role(String name, String description) {
    this.name = name;
    this.description = description;
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

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Set<Resource> getResources() {
    return resources;
  }

  public void setResources(Set<Resource> resources) {
    this.resources = resources;
  }
}

package com.saintdan.framework.po;

import com.saintdan.framework.enums.ValidFlag;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Authorized resources,provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "resources")
public class Resource implements GrantedAuthority, Serializable {

  private static final long serialVersionUID = 6298843159549723556L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "SERIAL")
  private Long id;

  @NotEmpty
  @Column(unique = true, nullable = false, length = 20)
  private String name;

  /**
   * The resource path
   * <p><b>NOTE: Using ANT path mode</b></p>
   */
  @NotEmpty
  @Column(unique = true, length = 1024, nullable = false)
  private String path;

  /**
   * The priority. the smaller the description the higher the priority.
   */
  @NotNull
  @Column(nullable = false)
  private Integer priority;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private ValidFlag validFlag = ValidFlag.VALID;

  @CreatedDate
  @Column(nullable = false)
  private Date createdDate = new Date();

  @CreatedBy
  @Column(nullable = false)
  private Long createdBy;

  @LastModifiedDate
  @Column(nullable = false)
  private Date lastModifiedDate = new Date();

  @LastModifiedBy
  @Column(nullable = false)
  private Long lastModifiedBy;

  @Version
  @Column(nullable = false)
  private int version;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources", cascade = {CascadeType.REFRESH})
  private Set<Group> groups = new HashSet<>();

  public Resource() {

  }

  public Resource(String name, String path, Integer priority, String description) {
    this.name = name;
    this.path = path;
    this.priority = priority;
    this.description = description;
  }

  @Override
  public String getAuthority() {
    return name;
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

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
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

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Long getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
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

  public Set<Group> getGroups() {
    return groups;
  }

  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  }
}

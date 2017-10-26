package com.saintdan.framework.po;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.listener.PersistentListener;
import com.saintdan.framework.listener.ValidFlagListener;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.PreRemove;
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

/**
 * Authorized resources,provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Entity
@Table(name = "resources")
@EntityListeners({PersistentListener.class, ValidFlagListener.class})
@NamedEntityGraph(name = "Resource.roles", attributeNodes = @NamedAttributeNode("roles"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")
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

  @Column(unique = true, nullable = false, length = 20)
  private String name;

  @Column(length = 500)
  private String description;

  @Column(nullable = false)
  @Builder.Default
  private ValidFlag validFlag = ValidFlag.VALID;

  @Column(nullable = false, updatable = false)
  private long createdAt;

  @Column(nullable = false, updatable = false)
  private long createdBy;

  @Column(nullable = false)
  private long lastModifiedAt;

  @Column(nullable = false)
  private long lastModifiedBy;

  @Version
  @Column(nullable = false)
  private int version;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources", cascade = CascadeType.REFRESH)
  private Set<Role> roles;

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
}

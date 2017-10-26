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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

/**
 * Authorized roles, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/23/15
 * @since JDK1.8
 */
@Entity
@Table(name = "roles")
@EntityListeners({PersistentListener.class, ValidFlagListener.class})
@NamedEntityGraph(name = "Role.resources", attributeNodes = @NamedAttributeNode("resources"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
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

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = CascadeType.REFRESH)
  private Set<User> users;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
  @JoinTable(name = "roles_has_resources",
      joinColumns = {@JoinColumn(name = "role_id")},
      inverseJoinColumns = {@JoinColumn(name = "resource_id")})
  private Set<Resource> resources;

  @PreRemove
  private void removeRolesFromUsers() {
    users.forEach(user -> user.getRoles().remove(this));
  }

  public Role(String name, String description) {
    this.name = name;
    this.description = description;
  }
}

package com.saintdan.framework.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
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
@Table(name = "resources")
public class Resource implements Serializable {

    private static final long serialVersionUID = 6298843159549723556L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    @Column(nullable = false, length = 20)
    private String name;

    /**
     * The resource path
     * <p><b>NOTE: Using ANT path mode</b></p>
     */
    @NotEmpty
    @Column(length = 1024, nullable = false)
    private String path;

    /**
     * The priority. the smaller the value the higher the priority.
     */
    @NotEmpty
    @Column(nullable = false)
    private Integer priority;

    @Column(length = 500)
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources")
    private Set<Group> groups = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}

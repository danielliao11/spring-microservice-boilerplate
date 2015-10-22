package com.saintdan.framework.po;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class User implements Serializable {

    private static final long serialVersionUID = 2680591198337929454L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(length = 50)
    private String name;

    @NotEmpty
    @Column(unique = true, nullable = false, length = 20)
    private String usr;

    @NotEmpty
    @Column(nullable = false, length = 100)
    private String pwd;

    @Column(length = 500)
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "users_has_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    public User() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

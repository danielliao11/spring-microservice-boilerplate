package com.saintdan.framework.po;

import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.AccountSourceType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Account of user.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 08/02/2017
 * @since JDK1.8
 */
@Entity @EntityListeners({ AuditingEntityListener.class }) @Table(name = "accounts")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Account implements Serializable {

  private static final long serialVersionUID = -6004454109313475045L;

  @GenericGenerator(
      name = "accountSequenceGenerator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "accounts_seq"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "1")
      }
  )
  @Id
  @GeneratedValue(generator = "accountSequenceGenerator")
  @Column(updatable = false)
  private long id;

  private String account;

  private AccountSourceType accountSourceType;

  @Column(updatable = false)
  private long createdAt = System.currentTimeMillis();

  @Column(nullable = false, updatable = false)
  private long createdBy = 0;

  private long lastModifiedAt = System.currentTimeMillis();

  @Column(nullable = false)
  private long lastModifiedBy = 0;

  @Version
  @Column(nullable = false)
  private int version;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("account", account)
        .append("accountSourceType", accountSourceType == null ? CommonsConstant.BLANK : accountSourceType.code())
        .append("createdAt", createdAt)
        .append("createdBy", createdBy)
        .append("lastModifiedAt", lastModifiedAt)
        .append("lastModifiedBy", lastModifiedBy)
        .append("version", version)
        .append("user", user == null ? CommonsConstant.BLANK : user.getId())
        .toString();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public AccountSourceType getAccountSourceType() {
    return accountSourceType;
  }

  public void setAccountSourceType(AccountSourceType accountSourceType) {
    this.accountSourceType = accountSourceType;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}

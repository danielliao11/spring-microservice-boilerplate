package com.saintdan.framework.po;

import com.saintdan.framework.enums.AccountSourceType;
import java.io.Serializable;
import java.time.LocalDateTime;
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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Account of user.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 08/02/2017
 * @since JDK1.8
 */
@Entity
@EntityListeners({ AuditingEntityListener.class })
@Table(name = "accounts")
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
  private Long id;

  private String account;

  private AccountSourceType accountSourceType;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate = LocalDateTime.now();

  @CreatedBy
  @Column(nullable = false, updatable = false)
  private Long createdBy;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime lastModifiedDate = LocalDateTime.now();

  @LastModifiedBy
  @Column(nullable = false)
  private Long lastModifiedBy;

  @Version
  @Column(nullable = false)
  private Integer version;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @Override public String toString() {
    final StringBuffer sb = new StringBuffer("Account{");
    sb.append("id=").append(id);
    sb.append(", account='").append(account).append('\'');
    sb.append(", accountSourceType=").append(accountSourceType);
    sb.append(", createdDate=").append(createdDate);
    sb.append(", createdBy=").append(createdBy);
    sb.append(", lastModifiedDate=").append(lastModifiedDate);
    sb.append(", lastModifiedBy=").append(lastModifiedBy);
    sb.append(", version=").append(version);
    sb.append('}');
    return sb.toString();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}

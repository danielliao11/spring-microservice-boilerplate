package com.saintdan.framework.spec;

import java.text.ParseException;
import java.time.LocalDateTime;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.utils.Converter;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 22/03/2017
 * @since JDK1.8
 */
public class LocalDateTimeBetween<T> extends DateSpecification<T> {

  private LocalDateTime after;
  private LocalDateTime before;

  public LocalDateTimeBetween(String path, String[] args, Converter converter)
      throws ParseException {
    super(path, args, converter);
    if (args == null || args.length != 2) {
      throw new IllegalArgumentException(
          "expected 2 http params (date boundaries), but was: " + args);
    }
    String afterDateStr = args[0];
    String beforeDateStr = args[1];
    this.after = covertToLocalDateTime(afterDateStr);
    this.before = covertToLocalDateTime(beforeDateStr);
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    return cb.between(this.path(root), after, before);
  }
}

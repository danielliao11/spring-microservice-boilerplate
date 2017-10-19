package com.saintdan.framework.spec;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.utils.Converter;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 26/03/2017
 * @since JDK1.8
 */
public class LocalDateTimeBefore<T> extends DateSpecification<T> {

  private LocalDateTime date;

  public LocalDateTimeBefore(String path, String[] args, Converter converter)
      throws ParseException {
    super(path, args, converter);
    if (args == null || args.length != 1) {
      throw new IllegalArgumentException(
          "expected a single http-param, but was: " + Arrays.toString(args));
    }
    String dateStr = args[0];
    this.date = covertToLocalDateTime(dateStr);
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    return cb.lessThanOrEqualTo(this.path(root), date);
  }
}

package com.github.manuelp.todoEvents;

import fj.Function;
import fj.P2;
import fj.Semigroup;
import fj.Unit;
import fj.data.List;
import fj.data.NonEmptyList;
import fj.data.Validation;

import static fj.Unit.unit;

public class Validations {
  /**
   * Returns a {@link Validation} that can be used to validate a non-nullable value.
   *
   * @param name  Name of the value
   * @param value Non-nullable value
   * @param <T>   Type of the non-nullable value
   * @return Validation to check the non-nullability
   */
  public static <T> Validation<String, Unit> notNull(String name, T value) {
    return Validation.condition(value != null, name + " value cannot be null!", unit());
  }

  public static <T> Validation<String, Unit> notNull(P2<String, T> x) {
    return notNull(x._1(), x._2());
  }

  public static Validation<NonEmptyList<String>, Unit> notNull(List<P2<String, ?>> values) {
    return Validation.sequence(Semigroup.nonEmptyListSemigroup(), values.map(Validations::notNull).map(Validation::nel))
                     .map(
                         Function.constant(unit()));
  }

  public static void mustNotBeNull(List<P2<String, ?>> values) {
    Validation<NonEmptyList<String>, Unit> v = notNull(values);
    if (v.isFail()) {
      String message = v.fail().toList().intersperse(" ").foldLeft1((a, b) -> a + b);
      throw new IllegalArgumentException(message);
    }
  }
}

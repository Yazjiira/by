package com.brodieyazaki.models.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.brodieyazaki.injectors.InjectorConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotation;

/**
 * InheritableChild annotation for use in sling models. Used in conjuction
 * with @ResourcePath injector annotation to expose a child resource, within
 * Resource adaptables.
 *
 * use ex:
 * @InhertiableChild
 * @ResourcePath("mychild")
 * Resource _myChild
 *
 * yeilds:
 * _myChild.getName() => mychild;
 * _myChild.getPath() => path to current resource or ancestor depending on
 *                       existance in inheritance hierarchy.
 *
 * @author Brodie Yazaki
 */
@Target({FIELD, METHOD})
@Retention(RUNTIME)
@InjectAnnotation
@Source(InjectorConstants.INHERITABLE_CHILD)
public @interface InheritableChild {
  /**
   * Set the default injection strategy for this type to optional. If AEM is unable
   * to find an inheritable type, this is fine, as it could mean that this is in
   * the ancestor resource definition.
   *
   * Default value = OPTIONAL
   */
  public InjectionStrategy injectionStrategy() default InjectionStrategy.OPTIONAL;
}

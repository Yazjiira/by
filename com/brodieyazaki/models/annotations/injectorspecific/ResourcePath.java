package com.brodieyazaki.models.annotations.injectorspecific;

import com.brodieyazaki.models.injectors.InjectorConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import org.apache.sling.models.annotations.Source;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Value annotation for use in Injector Annotations
 * 
 * @author Brodie Yazaki
 */
@Target({FIELD, METHOD})
@Retention(RUNTIME)
@Qualifier
@Source(InjectorConstants.RESOURCE_PATH)
public @interface ResourcePath {

  /**
   * Value of the ResourcePath annotation
   *
   * @return
   *   returns the value provided via annotation
   */
  String value();
}
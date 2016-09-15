package com.brodieyazaki.models.injectors;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Base class for the rest of the custom Injectors. Allows
 * us to define common usage in a single place.
 *
 * @author Brodie Yazaki
 */
abstract class AbstractInjector {
  /**
   * Attempts to extract a resourceResolver based on adaptable type.
   *
   * @param adaptable
   *          The current Object being adapted from.
   * @return
   *     The resourceResolver from the target adapatable.
   */
  protected ResourceResolver getResourceResolver(Object adaptable) {
    ResourceResolver rr = null;

    if (adaptable instanceof Resource) {
      rr = ((Resource) adaptable).getResourceResolver();
    } else if (adaptable instanceof SlingHttpServletRequest) {
      rr = ((SlingHttpServletRequest) adaptable).getResourceResolver();
    }

    return rr;
  }
}
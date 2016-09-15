package com.brodieyazaki.models.components.navigation;

import com.brodieyazaki.models.annotations.InheritableChild;
import com.brodieyazaki.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import javax.inject.Named;

import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;

import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sampe model to show usage examples
 *
 */
@Model(adaptables = { Resource.class})
public class SampleModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(SampleModel.class);
  
  @InheritableChild
  @ResourcePath("pathToChild")
  private Resource _myChild;

  @SlingObject
  @Named("resource")
  private Resource _resource;

  @PostConstruct
  public void init() {
  	// the current resource being adapted from
  	LOGGER.info(_resource.getPath());

    // this should show the path to the closest
    // child in the node hierarchy.
  	LOGGER.info(_myChild.getPath());
  	LOGGER.info(_myChild.getName());
  }
}
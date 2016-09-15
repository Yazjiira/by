package com.brodieyazaki.models.injectors;

import java.lang.reflect.Type;
import java.lang.reflect.AnnotatedElement;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.brodieyazaki.models.annotations.injectorspecific.ResourcePath;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.osgi.framework.Constants;

/**
 * InheritableChildInjector offers a simple and easy way to access child resources
 * and their ancestors, for inheritance patterns within our sling models.
 *
 * @author Brodie Yazaki
 */
@Component
@Service
@Property(name = Constants.SERVICE_RANKING, intValue = 5000)
public class InhertiableChildInjector extends AbstractInjector implements Injector {
  @Override
  public String getName() {
    return InjectorConstants.INHERITABLE_CHILD;
  }

  /**
   * Uses the current adaptable and a provided ResourcePath to find a child or it's ancestor in
   * the current page Hierarchy.
   * 
   * @param adaptable
   *          @see Injector#getValue(Object, String, Type, AnnotatedElement, DisposalCallbackRegistry)
   * @param name
   *          @see Injector#getValue(Object, String, Type, AnnotatedElement, DisposalCallbackRegistry)
   * @param type
   *          @see Injector#getValue(Object, String, Type, AnnotatedElement, DisposalCallbackRegistry)
   * @param element
   *          @see Injector#getValue(Object, String, Type, AnnotatedElement, DisposalCallbackRegistry)
   * @param callbackRegistry
   *          @see Injector#getValue(Object, String, Type, AnnotatedElement, DisposalCallbackRegistry)
   * @return
   *     Returns the resource provided view the @ResourcePath annotation.
   */
  @Override
  public Object getValue(Object adaptable, String name, Type type, AnnotatedElement element,
      DisposalCallbackRegistry callbackRegistry) {
    Resource inhertiableTarget = null;
    ResourcePath childPathValue = element.getAnnotation(ResourcePath.class);

    // because this injector is for use in component resources, we do not
    // want to return anything if the adaptable is not a resource.
    if (adaptable instanceof Resource && childPathValue != null) {
      inhertiableTarget = getInheritableChild((Resource) adaptable, childPathValue.value());
    }

    return inhertiableTarget;
  }
  
  /**
   * Method to find the inheritable child resource at the provided
   * path. If a child resource is not found, search up the page
   * tree until one is found.
   * 
   * @param top
   *          starting resource
   *          
   * @param childPath
   *          target child relative path
   *          
   * @return
   *     If found, return the resource at the child path or at the childPath of an ancestor resource.
   *     Else return null.
   */
  private Resource getInheritableChild(Resource top, String childPath) {
    Resource child = null;
    
    if (top != null && StringUtils.isNotBlank(childPath)) {
      child = top.getChild(childPath);
      
      if (child == null) {
        String pageContentPath = top.getName() + "/" + childPath;
        PageManager pageManager = top.getResourceResolver().adaptTo(PageManager.class);
        Page parentPage = pageManager.getContainingPage(top).getParent();

        while (parentPage != null) {
          child = parentPage.getContentResource(pageContentPath);
          
          if (child != null) {
            break;
          }
          
          parentPage = parentPage.getParent();
        }
      }
    }
    
    return child;
  }
}

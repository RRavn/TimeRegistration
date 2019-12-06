package dk.rr.services.timeregistrationservice.controllers.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import dk.rr.services.timeregistrationservice.controllers.TimeRegistrationController;
import dk.rr.services.timeregistrationservice.models.TimeCategory;

@Component
public class TimeCategoryResourceAssembler implements ResourceAssembler<TimeCategory, Resource<TimeCategory>> {

  @Override
  public Resource<TimeCategory> toResource(TimeCategory timeCategory) {

    return new Resource<>(timeCategory,
      linkTo(methodOn(TimeRegistrationController.class).getTimeCategoryById(timeCategory.getId())).withSelfRel(),
      linkTo(methodOn(TimeRegistrationController.class).getTimeCategorys()).withRel("timecategory"));
  }
}
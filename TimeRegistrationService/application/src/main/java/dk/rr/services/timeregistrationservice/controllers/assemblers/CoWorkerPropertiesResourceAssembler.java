package dk.rr.services.timeregistrationservice.controllers.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import dk.rr.services.timeregistrationservice.controllers.TimeRegistrationController;
import dk.rr.services.timeregistrationservice.models.CoWorkerProperties;

@Component
public class CoWorkerPropertiesResourceAssembler implements ResourceAssembler<CoWorkerProperties, Resource<CoWorkerProperties>> {

  @Override
  public Resource<CoWorkerProperties> toResource(CoWorkerProperties coWorkerProperties) {

    return new Resource<>(coWorkerProperties,
      linkTo(methodOn(TimeRegistrationController.class).getCoWorkerPropertiesById(coWorkerProperties.getId())).withSelfRel(),
      linkTo(methodOn(TimeRegistrationController.class).getCoWorkerProperties()).withRel("coworkerproperties"));
  }
}
package dk.rr.services.timeregistrationservice.controllers.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import dk.rr.services.timeregistrationservice.controllers.TimeRegistrationController;
import dk.rr.services.timeregistrationservice.models.Registration;

@Component
public class RegistrationResourceAssembler implements ResourceAssembler<Registration, Resource<Registration>> {

  @Override
  public Resource<Registration> toResource(Registration registration) {

    return new Resource<>(registration,
      linkTo(methodOn(TimeRegistrationController.class).getRegistrationById(registration.getId())).withSelfRel(),
      linkTo(methodOn(TimeRegistrationController.class).getRegistrations()).withRel("registration"));
  }
}
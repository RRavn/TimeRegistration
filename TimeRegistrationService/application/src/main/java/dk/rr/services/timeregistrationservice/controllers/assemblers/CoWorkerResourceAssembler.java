package dk.rr.services.timeregistrationservice.controllers.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import dk.rr.services.timeregistrationservice.controllers.TimeRegistrationController;
import dk.rr.services.timeregistrationservice.models.CoWorker;

@Component
public class CoWorkerResourceAssembler implements ResourceAssembler<CoWorker, Resource<CoWorker>> {

  @Override
  public Resource<CoWorker> toResource(CoWorker coWorker) {

    return new Resource<>(coWorker,
      linkTo(methodOn(TimeRegistrationController.class).getCoWorkerById(coWorker.getId())).withSelfRel(),
      linkTo(methodOn(TimeRegistrationController.class).getCoWorkers()).withRel("coworker"));
  }
}
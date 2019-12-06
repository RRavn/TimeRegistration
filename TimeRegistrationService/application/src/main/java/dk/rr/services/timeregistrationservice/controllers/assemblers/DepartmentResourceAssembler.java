package dk.rr.services.timeregistrationservice.controllers.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import dk.rr.services.timeregistrationservice.controllers.TimeRegistrationController;
import dk.rr.services.timeregistrationservice.models.Department;

@Component
public class DepartmentResourceAssembler implements ResourceAssembler<Department, Resource<Department>> {

  @Override
  public Resource<Department> toResource(Department department) {

    return new Resource<>(department,
      linkTo(methodOn(TimeRegistrationController.class).getDepartmentById(department.getId())).withSelfRel(),
      linkTo(methodOn(TimeRegistrationController.class).getDepartments()).withRel("departments"));
  }
}
package dk.rr.services.timeregistrationservice.controllers;

import dk.rr.services.timeregistrationservice.models.*;
import dk.rr.services.timeregistrationservice.controllers.assemblers.CoWorkerPropertiesResourceAssembler;
import dk.rr.services.timeregistrationservice.controllers.assemblers.CoWorkerResourceAssembler;
import dk.rr.services.timeregistrationservice.controllers.assemblers.DepartmentResourceAssembler;
import dk.rr.services.timeregistrationservice.controllers.assemblers.RegistrationResourceAssembler;
import dk.rr.services.timeregistrationservice.controllers.assemblers.TimeCategoryResourceAssembler;
import dk.rr.services.timeregistrationservice.services.CoWorkerPropertiesService;
import dk.rr.services.timeregistrationservice.services.CoWorkerService;
import dk.rr.services.timeregistrationservice.services.DepartmentService;
import dk.rr.services.timeregistrationservice.services.RegistrationService;
import dk.rr.services.timeregistrationservice.services.TimeCategoryService;
import dk.rr.services.timeregistrationservice.exceptions.NotFoundException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@Slf4j
@Api(tags = "timeregistration")
public class TimeRegistrationController {
    private DepartmentService departmentService;
    private DepartmentResourceAssembler departmentResourceAssembler;
    private TimeCategoryService timeCategoryService;
    private TimeCategoryResourceAssembler timeCategoryResourceAssembler;
    private CoWorkerPropertiesService coWorkerPropertiesService;
    private CoWorkerPropertiesResourceAssembler coWorkerPropertiesResourceAssembler;
    private CoWorkerService coWorkerService;
    private CoWorkerResourceAssembler coWorkerResourceAssembler;
    private RegistrationService registrationService;
    private RegistrationResourceAssembler registrationResourceAssembler;

    public TimeRegistrationController(DepartmentService departmentService, DepartmentResourceAssembler departmentResourceAssembler, TimeCategoryService timeCategoryService, TimeCategoryResourceAssembler timeCategoryResourceAssembler, CoWorkerPropertiesService coWorkerPropertiesService, CoWorkerPropertiesResourceAssembler coWorkerPropertiesResourceAssembler, CoWorkerService coWorkerService, CoWorkerResourceAssembler coWorkerResourceAssembler, RegistrationService registrationService, RegistrationResourceAssembler registrationResourceAssembler) {
        this.departmentService = departmentService;
        this.departmentResourceAssembler = departmentResourceAssembler;
        this.timeCategoryService = timeCategoryService;
        this.timeCategoryResourceAssembler = timeCategoryResourceAssembler;
        this.coWorkerPropertiesService = coWorkerPropertiesService;
        this.coWorkerPropertiesResourceAssembler = coWorkerPropertiesResourceAssembler;
        this.coWorkerService = coWorkerService;
        this.coWorkerResourceAssembler = coWorkerResourceAssembler;
        this.registrationService = registrationService;
        this.registrationResourceAssembler = registrationResourceAssembler;
    }

    @GetMapping(value = "/Department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Department>> getDepartments() {
        List<Resource<Department>> departments = departmentService.getAll().stream()
                .map(departmentResourceAssembler::toResource)
                .collect(Collectors.toList());

        log.info("GET /Department FOUND {}", departments.size(), this);
        return new Resources<>(departments, linkTo(methodOn(TimeRegistrationController.class).getDepartments()).withSelfRel());
    }

    @PostMapping(value = "/Department", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDepartment(@NotNull @RequestBody Department department) throws URISyntaxException {
        if (department.getName() == null) {
            log.error("GET /Department. Name is null!", this);
            return ResponseEntity.badRequest().build();
        }
        Resource<Department> resource = departmentResourceAssembler.toResource(departmentService.save(department));
        log.info("POST /Department CREATED", department, this);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping(value = "/Department/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Department> getDepartmentById(@PathVariable Long id) {
        log.info("GET /Department/id/{}", id, this);
        return departmentResourceAssembler.toResource(departmentService.getById(id)
                .orElseThrow(() -> new NotFoundException(id)));
    }

    @PutMapping(value = "/Department/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDepartmentById(@NotNull @RequestBody Department department, @PathVariable Long id) throws URISyntaxException {
        if (department.getName() == null || departmentService.getById(id) == null){
            log.error("PUT /Department/{}. Name is null or id not found!", id, this);
            return ResponseEntity.badRequest().build();
        }
        if (department.getId() == null) {
            department.setId(id);
        }

        Resource<Department> resource = departmentResourceAssembler.toResource(departmentService.save(department));
        log.info("PUT /Department/{} REPLACED", id, this);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping(value = "/Department/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteDepartmentById(@PathVariable Long id) {
        departmentService.delete(id);
        log.info("DELETE /Department/{} DELETED", id, this);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/TimeCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<TimeCategory>> getTimeCategorys() {
        List<Resource<TimeCategory>> timeCategorys = timeCategoryService.getAll().stream()
                .map(timeCategoryResourceAssembler::toResource)
                .collect(Collectors.toList());

        log.info("GET /TimeCategory FOUND {}", timeCategorys.size(), this);
        return new Resources<>(timeCategorys, linkTo(methodOn(TimeRegistrationController.class).getTimeCategorys()).withSelfRel());
    }

    @PostMapping(value = "/TimeCategory", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTimeCategory(@NotNull @RequestBody TimeCategory timeCategory) throws URISyntaxException {
        if (timeCategory.getName() == null) {
            log.error("GET /TimeCategory. Name is null!", this);
            return ResponseEntity.badRequest().build();
        }
        Resource<TimeCategory> resource = timeCategoryResourceAssembler.toResource(timeCategoryService.save(timeCategory));

        log.info("POST /TimeCategory CREATED {}", timeCategory.getName(), this);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping(value = "/TimeCategory/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<TimeCategory> getTimeCategoryById(@PathVariable Long id) {
        log.info("GET /TimeCategory/id/{}", id, this);
        return timeCategoryResourceAssembler.toResource(timeCategoryService.getById(id)
                .orElseThrow(() -> new NotFoundException(id)));
    }

    @PutMapping(value = "/TimeCategory/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTimeCategoryById(@NotNull @RequestBody TimeCategory timeCategory, @PathVariable Long id) throws URISyntaxException {
        if (timeCategory.getName() == null || timeCategoryService.getById(id) == null) {
            log.error("PUT /TimeCategory/{}. Name is null or id not found!", id, this);
            return ResponseEntity.badRequest().build();
        }
        if (timeCategory.getId() == null) {
            timeCategory.setId(id);
        }

        Resource<TimeCategory> resource = timeCategoryResourceAssembler.toResource(timeCategoryService.save(timeCategory));
        log.info("PUT /TimeCategory/{} REPLACED", id, this);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping(value = "/TimeCategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTimeCategoryById(@PathVariable Long id) {
        timeCategoryService.delete(id);
        log.info("DELETE /TimeCategory/{} DELETED", id, this);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/CoWorker", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<CoWorker>> getCoWorkers() {
        List<Resource<CoWorker>> coWorkers = coWorkerService.getAll().stream()
                .map(coWorkerResourceAssembler::toResource)
                .collect(Collectors.toList());

        log.info("GET /CoWorker FOUND {}", coWorkers.size(), this);
        return new Resources<>(coWorkers, linkTo(methodOn(TimeRegistrationController.class).getCoWorkers()).withSelfRel());
    }

    @PostMapping(value = "/CoWorker", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCoWorker(@NotNull @RequestBody CoWorker coWorker) throws URISyntaxException {
        if (coWorker.getName() == null || coWorker.getDepartment() == null || coWorker.getDepartment().getId() == null) {
            log.error("GET /CoWorker. Name, department or department id is null!", this);
            return ResponseEntity.badRequest().build();
        }
        Resource<CoWorker> resource = coWorkerResourceAssembler.toResource(coWorkerService.save(coWorker));

        log.info("POST /CoWorker CREATED {}", coWorker.getName(), this);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping(value = "/CoWorker/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<CoWorker> getCoWorkerById(@PathVariable Long id) {
        log.info("GET /CoWorker/id/{}", id, this);
        return coWorkerResourceAssembler.toResource(coWorkerService.getById(id)
                .orElseThrow(() -> new NotFoundException(id)));
    }

    @GetMapping(value = "/CoWorker/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<CoWorker> getCoWorkerByName(@PathVariable String name) {
        log.info("GET /CoWorker/name/{}", name, this);
        return coWorkerResourceAssembler.toResource(coWorkerService.getByName(name)
                .orElseThrow(() -> new NotFoundException(name)));
    }

    @PutMapping(value = "/CoWorker/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCoWorkerById(@NotNull @RequestBody CoWorker coWorker, @PathVariable Long id) throws URISyntaxException {
        if (coWorker.getName() == null || coWorker.getDepartment() == null || coWorker.getDepartment().getId() == null || coWorkerService.getById(id) == null) {
            log.error("PUT /CoWorker/{}. Name, department, department id is null or id not found!", id, this);
            return ResponseEntity.badRequest().build();
        }
        if (coWorker.getId() == null) {
            coWorker.setId(id);
        }

        Resource<CoWorker> resource = coWorkerResourceAssembler.toResource(coWorkerService.save(coWorker));
        log.info("PUT /CoWorker/{} REPLACED", id, this);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping(value = "/CoWorker/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteCoWorkerById(@PathVariable Long id) {
        coWorkerService.delete(id);
        log.info("DELETE /CoWorker/{} DELETED", id, this);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/CoWorkerProperties", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<CoWorkerProperties>> getCoWorkerProperties() {
        List<Resource<CoWorkerProperties>> coWorkerProperties = coWorkerPropertiesService.getAll().stream()
                .map(coWorkerPropertiesResourceAssembler::toResource)
                .collect(Collectors.toList());

        log.info("GET /CoWorkerProperties FOUND {}", coWorkerProperties.size(), this);
        return new Resources<>(coWorkerProperties, linkTo(methodOn(TimeRegistrationController.class).getCoWorkerProperties()).withSelfRel());
    }

    @PostMapping(value = "/CoWorkerProperties", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCoWorkerProperties(@NotNull @RequestBody CoWorkerProperties coWorkerProperties) throws URISyntaxException {
        if (coWorkerProperties.getFixedArriveTime() == null || coWorkerProperties.getFixedLeaveTime() == null || coWorkerProperties.getCoworker() == null) {
            log.error("GET /CoWorkerProperties. FixedArriveTime, fixedLeaveTime or coWorker is null!", this);
            return ResponseEntity.badRequest().build();
        }
        Resource<CoWorkerProperties> resource = coWorkerPropertiesResourceAssembler.toResource(coWorkerPropertiesService.save(coWorkerProperties));

        log.info("POST /CoWorkerProperties CREATED for coWorker {}", coWorkerProperties.getCoworker().getName(), this);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping(value = "/CoWorkerProperties/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<CoWorkerProperties> getCoWorkerPropertiesById(@PathVariable Long id) {
        log.info("GET /CoWorkerProperties/id/{}", id, this);
        return coWorkerPropertiesResourceAssembler.toResource(coWorkerPropertiesService.getById(id)
                .orElseThrow(() -> new NotFoundException(id)));
    }

    @GetMapping(value = "/CoWorkerProperties/coworkerid/{coWorkerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<CoWorkerProperties> getCoWorkerByCoWorkerId(@PathVariable Long coWorkerId) {
        return coWorkerPropertiesResourceAssembler.toResource(coWorkerPropertiesService.getByCoWorkerId(coWorkerId)
                .orElseThrow(() -> new NotFoundException(coWorkerId)));
    }

    @PutMapping(value = "/CoWorkerProperties/{coWorkerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCoWorkerPropertiesByCoWorkerId(@NotNull @RequestBody CoWorkerProperties coWorkerProperties, @PathVariable Long coWorkerId) throws URISyntaxException {
        if (coWorkerProperties.getFixedArriveTime() == null || coWorkerProperties.getFixedLeaveTime() == null || coWorkerPropertiesService.getByCoWorkerId(coWorkerId) == null) {
            log.error("PUT /CoWorkerProperties/{}. FixedArriveTime, fixedLeaveTime or coWorkerId not found!", coWorkerId, this);
            return ResponseEntity.badRequest().build();
        }
        if (coWorkerProperties.getCoworker() == null) {
            coWorkerProperties.setCoworker(coWorkerService.getById(coWorkerId).get());
        }

        Resource<CoWorkerProperties> resource = coWorkerPropertiesResourceAssembler.toResource(coWorkerPropertiesService.save(coWorkerProperties));
        log.info("PUT /CoWorkerProperties/{} REPLACED", coWorkerId, this);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping(value = "/CoWorkerProperties/{coWorkerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCoWorkerPropertiesByCoWorkerId(@PathVariable Long coWorkerId) {
        coWorkerPropertiesService.delete(coWorkerId);
        log.info("DELETE /CoWorkerProperties/{} DELETED", coWorkerId, this);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/Registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Registration>> getRegistrations() {
        List<Resource<Registration>> registrations = registrationService.getAll().stream()
                .map(registrationResourceAssembler::toResource)
                .collect(Collectors.toList());

        log.info("GET /Registration FOUND {}", registrations.size(), this);
        return new Resources<>(registrations, linkTo(methodOn(TimeRegistrationController.class).getRegistrations()).withSelfRel());
    }

    @PostMapping(value = "/Registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRegistration(@NotNull @RequestBody Registration registration) throws URISyntaxException {
        if (registration.getCoworker() == null || registration.getArriveTime() == null || registration.getLeaveTime() == null || registration.getTimeCategory() == null) {
            log.error("GET /Registration. CoWorker, arriveTime, leaveTime or timeCategory is null!", this);
            return ResponseEntity.badRequest().build();
        }
        Resource<Registration> resource = registrationResourceAssembler.toResource(registrationService.save(registration));

        log.info("POST /Registration CREATED for coWorker {}", registration.getCoworker().getName(), this);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping(value = "/Registration/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Registration> getRegistrationById(@PathVariable Long id) {
        log.info("GET /Registration/id/{}", id, this);
        return registrationResourceAssembler.toResource(registrationService.getById(id)
                .orElseThrow(() -> new NotFoundException(id)));
    }

    @GetMapping(value = "/Registration/coworkerid/{coWorkerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Registration>> getRegistrationsByCoWorkerId(@PathVariable Long coWorkerId) {
        List<Resource<Registration>> registrations = registrationService.getByCoWorkerId(coWorkerId).stream()
                .map(registrationResourceAssembler::toResource)
                .collect(Collectors.toList());

        log.info("GET /Registration/coworkerid/{} FOUND {}", coWorkerId, registrations.size(), this);
        return new Resources<>(registrations, linkTo(methodOn(TimeRegistrationController.class).getRegistrationsByCoWorkerId(coWorkerId)).withSelfRel());
    }

    @GetMapping(value = "/Registration/timecategoryid/{timeCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Registration>> getRegistrationsByTimeCategoryId(@PathVariable Long timeCategoryId) {
        List<Resource<Registration>> registrations = registrationService.getByTimeCategory(timeCategoryId).stream()
                .map(registrationResourceAssembler::toResource)
                .collect(Collectors.toList());

        log.info("GET /Registration/timecategoryid/{} FOUND {}", timeCategoryId, registrations.size(), this);
        return new Resources<>(registrations, linkTo(methodOn(TimeRegistrationController.class).getRegistrationsByTimeCategoryId(timeCategoryId)).withSelfRel());
    }

    @GetMapping(value = "/Registration/timeinterval/{startTime}/{endTime}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Registration>> getRegistrationsByTimeInterval(@PathVariable Timestamp startTime, @PathVariable Timestamp endTime) {
        List<Resource<Registration>> registrations = registrationService.getByTimeInterval(startTime, endTime).stream()
                .map(registrationResourceAssembler::toResource)
                .collect(Collectors.toList());

        log.info("GET /Registration/timeinterval/{}/{} FOUND {}", startTime, endTime, registrations.size(), this);
        return new Resources<>(registrations, linkTo(methodOn(TimeRegistrationController.class).getRegistrationsByTimeInterval(startTime, endTime)).withSelfRel());
    }

    @GetMapping(value = "/Registration/coworkerid_timeinterval/{coWorkerId}/{startTime}/{endTime}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Registration>> getRegistrationsByCoworkerAndArrivetimeAndLeavetime(@PathVariable Long coWorkerId, @PathVariable Timestamp startTime, @PathVariable Timestamp endTime) {
        List<Resource<Registration>> registrations = registrationService.getByCoworkerAndTimeInterval(coWorkerId, startTime, endTime).stream()
                .map(registrationResourceAssembler::toResource)
                .collect(Collectors.toList());

        log.info("GET /Registration/coworkerid_timeinterval/{}/{}/{} FOUND {}", coWorkerId, startTime, endTime, registrations.size(), this);
        return new Resources<>(registrations, linkTo(methodOn(TimeRegistrationController.class).getRegistrationsByCoworkerAndArrivetimeAndLeavetime(coWorkerId, startTime, endTime)).withSelfRel());
    }

    @PutMapping(value = "/Registration/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRegistrationById(@NotNull @RequestBody Registration registration, @PathVariable Long id) throws URISyntaxException {
        if (registration.getCoworker() == null || registration.getArriveTime() == null || registration.getLeaveTime() == null || registration.getTimeCategory() == null || registrationService.getById(id) == null) {
            log.error("PUT /Registration/{}. CoWorker, arriveTime, leaveTime, timeCategory or id not found!", id, this);
            return ResponseEntity.badRequest().build();
        }
        if (registration.getId() == null) {
            registration.setId(id);
        }

        Resource<Registration> resource = registrationResourceAssembler.toResource(registrationService.save(registration));
        log.info("PUT /Registration/{} REPLACED", id, this);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping(value = "/Registration/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteRegistrationById(@PathVariable Long id) {
        registrationService.delete(id);
        log.info("DELETE /Registration/{} DELETED", id, this);
        return ResponseEntity.noContent().build();
    }
}

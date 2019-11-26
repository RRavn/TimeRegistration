package dk.rr.services.timeregistrationservice.controllers;

import dk.rr.services.timeregistrationservice.models.CoWorker;
import dk.rr.services.timeregistrationservice.models.CoWorkerProperties;
import dk.rr.services.timeregistrationservice.models.Department;
import dk.rr.services.timeregistrationservice.models.Registration;
import dk.rr.services.timeregistrationservice.models.TimeCategory;
import dk.rr.services.timeregistrationservice.services.CoWorkerPropertiesService;
import dk.rr.services.timeregistrationservice.services.CoWorkerService;
import dk.rr.services.timeregistrationservice.services.DepartmentService;
import dk.rr.services.timeregistrationservice.services.RegistrationService;
import dk.rr.services.timeregistrationservice.services.TimeCategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Api(tags = "timeregistration")
public class TimeRegistrationController {
    private DepartmentService departmentService;
    private TimeCategoryService timeCategoryService;
    private CoWorkerPropertiesService coWorkerPropertiesService;
    private CoWorkerService coWorkerService;
    private RegistrationService registrationService;

    public TimeRegistrationController(DepartmentService departmentService, TimeCategoryService timeCategoryService, CoWorkerPropertiesService coWorkerPropertiesService, CoWorkerService coWorkerService, RegistrationService registrationService) {
        this.departmentService = departmentService;
        this.timeCategoryService = timeCategoryService;
        this.coWorkerPropertiesService = coWorkerPropertiesService;
        this.coWorkerService = coWorkerService;
        this.registrationService = registrationService;
    }

    @GetMapping(value = "/getDepartments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.ok(departmentService.getDepartments());
    }

    @PostMapping(value = "/createDepartment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(departmentService.saveDepartment(department));
    }

    @GetMapping(value = "/getDepartmentById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> getDepartmentById(@RequestParam(value="id", required = true) long id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        if (!department.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(department.get());
    }

    @PutMapping(value = "/updateDepartmentById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> updateDepartmentById(@RequestBody Department department) {
        if (department == null || !departmentService.getDepartmentById(department.getId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(departmentService.saveDepartment(department));
    }

    @DeleteMapping(value = "/deleteDepartmentById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteDepartmentById(@RequestParam(value="id", required = true) long id) {
        if (!departmentService.getDepartmentById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getTimeCategorys", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TimeCategory>> getTimeCategorys() {
        return ResponseEntity.ok(timeCategoryService.getTimeCategorys());
    }

    @PostMapping(value = "/createTimeCategory", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeCategory> createTimeCategory(@RequestBody TimeCategory timeCategory) {
        return ResponseEntity.ok(timeCategoryService.saveTimeCategory(timeCategory));
    }

    @GetMapping(value = "/getTimeCategoryById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeCategory> getTimeCategoryById(@RequestParam(value="id", required = true) long id) {
        Optional<TimeCategory> timeCategory = timeCategoryService.getTimeCategoryById(id);
        if (!timeCategory.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(timeCategory.get());
    }

    @PutMapping(value = "/updateTimeCategoryById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeCategory> updateTimeCategoryById(@RequestBody TimeCategory timeCategory) {
        if (timeCategory == null || !timeCategoryService.getTimeCategoryById(timeCategory.getId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(timeCategoryService.saveTimeCategory(timeCategory));
    }

    @DeleteMapping(value = "/deleteTimeCategoryById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteTimeCategoryById(@RequestParam(value="id", required = true) long id) {
        if (!timeCategoryService.getTimeCategoryById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        timeCategoryService.deleteTimeCategoryById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getCoWorkers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CoWorker>> getCoWorkers() {
        return ResponseEntity.ok(coWorkerService.getCoWorkers());
    }

    @PostMapping(value = "/createCoWorker", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoWorker> createCoWorker(@RequestBody CoWorker coWorker) {
        return ResponseEntity.ok(coWorkerService.saveCoWorker(coWorker));
    }

    @GetMapping(value = "/getCoWorkerById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoWorker> getCoWorkerById(@RequestParam(value="id", required = true) long id) {
        Optional<CoWorker> coWorker = coWorkerService.getCoWorkerById(id);
        if (!coWorker.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(coWorker.get());
    }

    @GetMapping(value = "/getCoWorkerByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoWorker> getCoWorkerByName(@RequestParam(value="name", required = true) String name) {
        Optional<CoWorker> coWorker = coWorkerService.getCoWorkerByName(name);
        if (!coWorker.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(coWorker.get());
    }

    @PutMapping(value = "/updateCoWorkerById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoWorker> updateCoWorkerById(@RequestParam(value="id", required = true) long id, @RequestParam(value="coworker", required = true) CoWorker coWorker) {
        if (!coWorkerService.getCoWorkerById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(coWorkerService.saveCoWorker(coWorker));
    }

    @DeleteMapping(value = "/deleteCoWorkerById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteCoWorkerById(@RequestParam(value="id", required = true) long id) {
        if (!coWorkerService.getCoWorkerById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        coWorkerService.deleteCoWorkerById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/createCoWorkerProperties", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoWorkerProperties> createCoWorkerProperties(@RequestBody CoWorkerProperties coWorkerProperties) {
        return ResponseEntity.ok(coWorkerPropertiesService.saveCoWorkerProperties(coWorkerProperties));
    }

    @GetMapping(value = "/getCoWorkerPropertiesByCoWorkerId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoWorkerProperties> getCoWorkerByCoWorkerId(@RequestParam(value="id", required = true) long id) {
        Optional<CoWorkerProperties> coWorkerProperties = coWorkerPropertiesService.getCoWorkerPropertiesByCoWorkerId(id);
        if (!coWorkerProperties.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(coWorkerProperties.get());
    }

    @PutMapping(value = "/updateCoWorkerPropertiesByCoWorkerId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoWorkerProperties> updateCoWorkerPropertiesByCoWorkerId(@RequestBody CoWorkerProperties coWorkerProperties) {
        if (coWorkerProperties == null || !coWorkerPropertiesService.getCoWorkerPropertiesByCoWorkerId(coWorkerProperties.getId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(coWorkerPropertiesService.saveCoWorkerProperties(coWorkerProperties));
    }

    @DeleteMapping(value = "/deleteCoWorkerPropertiesByCoWorkerId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteCoWorkerPropertiesByCoWorkerId(@RequestParam(value="id", required = true) long id) {
        if (!coWorkerPropertiesService.getCoWorkerPropertiesByCoWorkerId(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        coWorkerPropertiesService.deleteCoWorkerPropertiesByCoWorkerId(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getRegistrations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Registration>> getRegistration() {
        return ResponseEntity.ok(registrationService.getRegistrations());
    }

    @PostMapping(value = "/createRegistration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Registration> createRegistration(@RequestBody Registration registration) {
        return ResponseEntity.ok(registrationService.saveRegistration(registration));
    }

    @GetMapping(value = "/getRegistrationById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Registration> getRegistrationById(@RequestParam(value="id", required = true) long id) {
        Optional<Registration> registration = registrationService.getRegistrationById(id);
        if (!registration.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(registration.get());
    }

    @PutMapping(value = "/updateRegistrationById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Registration> updateRegistrationById(@RequestBody Registration registration) {
        if (registration == null || !registrationService.getRegistrationById(registration.getId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(registrationService.saveRegistration(registration));
    }

    @DeleteMapping(value = "/deleteRegistrationById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteRegistrationById(@RequestParam(value="id", required = true) long id) {
        if (!registrationService.getRegistrationById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        registrationService.deleteRegistrationById(id);
        return ResponseEntity.ok().build();
    }
}

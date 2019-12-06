package dk.rr.services.timeregistrationservice.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
      super("Could not find element with id " + id);
    }

    public NotFoundException(String name) {
      super("Could not find element with name " + name);
    }
  }
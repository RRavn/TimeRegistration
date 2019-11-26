package dk.rr.services.timeregistrationservice.swagger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class SwaggerController {
    @GetMapping(value = "/")
    public RedirectView swagger() {
        return new RedirectView("swagger-ui.html");
    }

}

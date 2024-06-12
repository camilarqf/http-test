package http.response;


import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prometheus")
public class MetricsController {

    private final PrometheusMeterRegistry prometheusMeterRegistry;

    @Autowired
    public MetricsController(PrometheusMeterRegistry prometheusMeterRegistry) {
        this.prometheusMeterRegistry = prometheusMeterRegistry;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String scrape() {
        return prometheusMeterRegistry.scrape();
    }
}

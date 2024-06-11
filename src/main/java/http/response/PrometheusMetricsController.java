/*
package http.response;

import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class PrometheusMetricsController {

    @PostConstruct
    public void init() {
        DefaultExports.initialize();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/prometheus")
    public void scrape(HttpServletResponse response) throws IOException {
        response.setContentType(TextFormat.CONTENT_TYPE_004);
        TextFormat.write004(response.getWriter(), io.prometheus.client.CollectorRegistry.defaultRegistry.metricFamilySamples());
    }
}
*/

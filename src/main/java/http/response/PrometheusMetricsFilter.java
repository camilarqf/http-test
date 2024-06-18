package http.response;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PrometheusMetricsFilter implements Filter {

    private static final Counter httpRequestsTotal = Counter.build()
            .name("http_server_requests_total")
            .help("Total HTTP requests.")
            .labelNames("method", "uri", "status")
            .register();

    private static final Histogram requestLatency = Histogram.build()
            .name("http_server_requests_duration_seconds")
            .help("HTTP request latency in seconds.")
            .labelNames("method", "uri", "status")
            .register();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        Histogram.Timer requestTimer = requestLatency.labels(method, uri, "").startTimer();
        try {
            chain.doFilter(request, response);
            int status = ((HttpServletResponse) response).getStatus();
            httpRequestsTotal.labels(method, uri, Integer.toString(status)).inc();
            requestTimer.observeDuration();
        } catch (Exception e) {
            httpRequestsTotal.labels(method, uri, "500").inc();
            requestTimer.observeDuration();
            throw e;
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}

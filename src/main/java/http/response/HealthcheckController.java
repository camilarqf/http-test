package http.response;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;
@Slf4j
@RestController
@RequestMapping("/healthcheck")
public class HealthcheckController {
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> health(){
        return  new ResponseEntity<>("Healthy", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "404")
    public ResponseEntity<String> notfound(){
        log.warn("teste");
        return  new ResponseEntity<>("Unhealthy", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "502")
    public ResponseEntity<String> notfound2(){
        log.error("teste");
        return  new ResponseEntity<>("Unhealthy", HttpStatus.BAD_GATEWAY);
    }
}

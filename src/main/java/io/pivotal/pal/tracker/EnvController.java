package io.pivotal.pal.tracker;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvController {

    private String port;
    private String memoryLimit;
    private String cfInstanceIndex;
    private String cfInstanceAddress;

    public EnvController(@Value("${port:${server.port:8080}}")String port, @Value("${memory.limit:1000000}") String memoryLimit, @Value("${cf.instance.index:NOT SET}") String cfInstanceIndex, @Value("${cf.instance.addr:NOT SET}") String cfInstanceAddress) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceIndex;
        this.cfInstanceAddress = cfInstanceAddress;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String, String> result = new HashMap<>();
        result.put("PORT", this.port);
        result.put("MEMORY_LIMIT", this.memoryLimit);
        result.put("CF_INSTANCE_INDEX", this.cfInstanceIndex);
        result.put("CF_INSTANCE_ADDR", this.cfInstanceAddress);
        return result;
    }
}

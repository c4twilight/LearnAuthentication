package com.example.LearnAuthentication.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "springVersion")
public class SpringVersionActuator {

    @ReadOperation
    public Map<String, String> springVersion() {
        Map<String, String> response = new HashMap<>();
        response.put("version", SpringVersion.getVersion());
        return response;
    }
}

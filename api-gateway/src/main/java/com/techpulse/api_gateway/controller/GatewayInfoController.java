package com.techpulse.api_gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gateway")
@RequiredArgsConstructor
public class GatewayInfoController {

    private final DiscoveryClient discoveryClient;
    private final RouteDefinitionLocator routeDefinitionLocator;

    @GetMapping("/info")
    public ResponseEntity<?> getGatewayInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("gateway", "TechPulse API Gateway");
        info.put("version", "1.0.0");
        info.put("status", "UP");
        return ResponseEntity.ok(info);
    }

    @GetMapping("/services")
    public ResponseEntity<?> getRegisteredServices() {
        List<String> services = discoveryClient.getServices();
        Map<String, List<ServiceInstance>> serviceDetails = new HashMap<>();

        for (String service : services) {
            serviceDetails.put(service, discoveryClient.getInstances(service));
        }

        return ResponseEntity.ok(Map.of(
                "totalServices", services.size(),
                "services", serviceDetails
        ));
    }

    @GetMapping("/routes")
    public ResponseEntity<?> getRoutes() {
        List<Map<String, String>> routes = routeDefinitionLocator.getRouteDefinitions()
                .map(this::mapRoute)
                .collectList()
                .block();

        return ResponseEntity.ok(Map.of(
                "totalRoutes", routes.size(),
                "routes", routes
        ));
    }

    private Map<String, String> mapRoute(RouteDefinition route) {
        Map<String, String> routeInfo = new HashMap<>();
        routeInfo.put("id", route.getId());
        routeInfo.put("uri", route.getUri().toString());
        routeInfo.put("predicates", route.getPredicates().toString());
        return routeInfo;
    }
}
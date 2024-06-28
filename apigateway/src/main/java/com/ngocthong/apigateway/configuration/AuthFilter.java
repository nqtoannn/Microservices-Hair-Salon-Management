package com.ngocthong.apigateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.common.net.HttpHeaders;
import java.util.List;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private JWTService jwtService;

    public static class Config {
        // empty class as I don't need any particular configuration
    }

    private AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            List<String> requiredRoles = getRequiredRoles(path);

            // Nếu không có quyền yêu cầu cho đường dẫn này, tiếp tục chuỗi bộ lọc
            if (requiredRoles.isEmpty()) {
                return chain.filter(exchange);
            }

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing authorization information");
            }
            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new RuntimeException("Incorrect authorization structure");
            }

            // Initialize JWT verifier
            try {
                String token = parts[1];
                String username = jwtService.extractUsername(token);
                String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));

                if (username == null || username.isEmpty()) {
                    throw new RuntimeException("Authorization error");
                }

                if (!hasRequiredRoles(role, requiredRoles)) {
                    throw new RuntimeException("Unauthorized access");
                }

                ServerHttpRequest request = exchange.getRequest().mutate()
                        .header("X-auth-username", username)
                        .build();

                return chain.filter(exchange.mutate().request(request).build());
            } catch (Exception exception) {
                throw new RuntimeException("Invalid token", exception);
            }
        };
    }

    private List<String> getRequiredRoles(String path) {
        if (path.startsWith("/api/v1/management") || path.startsWith("/api/v1/employee")) {
            return List.of("ADMIN", "EMPLOYEE");
        } else {
            return List.of(); // Trả về danh sách trống nếu không yêu cầu xác thực
        }
    }

    private boolean hasRequiredRoles(String role, List<String> requiredRoles) {
        return requiredRoles.contains(role);
    }
}

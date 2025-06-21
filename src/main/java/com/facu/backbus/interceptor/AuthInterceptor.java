package com.facu.backbus.interceptor;

import com.facu.backbus.model.User;
import com.facu.backbus.model.enums.UserType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor para verificar se o usuário está autenticado e tem permissões adequadas.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String[] PUBLIC_PATHS = {
        "/api/auth/login", 
        "/swagger-ui/", 
        "/v3/api-docs/"
    };

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // Verificar se o caminho é público
        String requestPath = request.getRequestURI();
        for (String publicPath : PUBLIC_PATHS) {
            if (requestPath.startsWith(publicPath)) {
                return true; // Permite acesso a caminhos públicos
            }
        }

        // Verificar se o usuário está autenticado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Usuário não autenticado");
            return false;
        }

        // Verificar permissões para rotas restritas
        User user = (User) session.getAttribute("currentUser");
        
        // Endpoints restritos ao GERENTE
        if ((requestPath.startsWith("/events") && 
             (request.getMethod().equals("PUT") || request.getMethod().equals("DELETE"))) &&
             !user.getUserType().equals(UserType.GERENTE)) {
            
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Permissão negada");
            return false;
        }
        
        return true;
    }
}

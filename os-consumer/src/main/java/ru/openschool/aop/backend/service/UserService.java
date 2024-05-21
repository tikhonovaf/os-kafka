package ru.openschool.aop.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.openschool.aop.backend.model.MigrUser;
//import ru.openschool.aspect.backend.model.Org;
import ru.openschool.aop.backend.repository.MigrUserRepository;
import ru.openschool.aop.backend.springconfig.Sha512PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MigrUserRepository migrUserRepository;

    private Sha512PasswordEncoder sha512PasswordEncoder = new Sha512PasswordEncoder();

    /**
     * Определение текущего пользователя
     * @return текущий пользователь
     */
    public MigrUser getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        SecurityContextImpl sci = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        MigrUser user;
        if (sci != null && sci.getAuthentication() != null) {
            Optional<MigrUser> usrOptional = migrUserRepository.findByLogin(sci.getAuthentication().getName());
            if (usrOptional.isPresent()) {
                user = usrOptional.get();
                return user;
            }
        }
        return null;
    }
}
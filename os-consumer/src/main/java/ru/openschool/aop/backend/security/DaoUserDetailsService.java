package ru.openschool.aop.backend.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.openschool.aop.backend.model.MigrUser;
import ru.openschool.aop.backend.repository.MigrUserRepository;

import javax.inject.Inject;

@Component
public class DaoUserDetailsService implements UserDetailsService {
    @Inject
    private MigrUserRepository migrUserRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        MigrUser user = migrUserRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + login + " not found!"));

        return User.withUsername(login)
                .password(user.getPassword())
                .roles(user.getRole() == null ? null : user.getRole().getName())
                .build();
    }
}

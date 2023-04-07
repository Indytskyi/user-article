package com.indytskyi.userservice.annotation;

import static com.indytskyi.userservice.models.enums.Role.ADMIN;

import com.indytskyi.userservice.exception.LimitedPermissionException;
import com.indytskyi.userservice.models.enums.Role;
import com.indytskyi.userservice.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AdminRoleValidator {

    private final AuthenticationService authenticationService;

    @Around("@annotation(com.indytskyi.userservice.annotation.IsAdmin)")
    @Transactional
    public Object validateAddOrDeleteItem(ProceedingJoinPoint joinPoint) throws Throwable {
        var bearerToken = (String) joinPoint.getArgs()[joinPoint.getArgs().length - 1];
        var user = authenticationService.validateToken(bearerToken);
        if (ADMIN != user.getRole()) {
            throw new LimitedPermissionException("User don`t have access to this request");
        }
        return joinPoint.proceed();
    }
}

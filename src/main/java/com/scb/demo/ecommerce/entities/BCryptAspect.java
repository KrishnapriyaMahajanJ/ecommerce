package com.scb.demo.ecommerce.entities;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
@RequiredArgsConstructor
public class BCryptAspect {


    final private BCryptPasswordEncoder passwordEncoder;


    @Around("execution(* com.scb.demo.ecommerce.repositories.*.save(..))")
    public Object encodeBCryptFields(ProceedingJoinPoint joinPoint) throws Throwable {
        Object entity = joinPoint.getArgs()[0]; // Get the entity being saved
        encodeFields(entity);
        return joinPoint.proceed(); // Proceed with the original save method
    }

    private void encodeFields(Object entity) throws IllegalAccessException {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(BCrypt.class)) {
                field.setAccessible(true);
                String rawValue = (String) field.get(entity);
                if (rawValue != null) {
                    String encodedValue = passwordEncoder.encode(rawValue);
                    field.set(entity, encodedValue);
                }
            }
        }
    }
}
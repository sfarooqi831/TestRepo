package com.alivia.bussiness.service;


import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditAwareImpl implements AuditorAware<String> {
  public Optional<String> getCurrentAuditor() {
    return Optional.of("Laiba");
  }
}
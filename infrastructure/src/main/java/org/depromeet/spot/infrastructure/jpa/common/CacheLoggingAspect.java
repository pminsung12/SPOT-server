// package org.depromeet.spot.infrastructure.jpa.common;
//
// import lombok.extern.slf4j.Slf4j;
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.springframework.cache.annotation.Cacheable;
// import org.springframework.stereotype.Component;
//
// @Aspect
// @Component
// @Slf4j
// public class CacheLoggingAspect {
//    @Around("@annotation(cacheable)")
//    public Object logCacheStats(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws
// Throwable {
//        String cacheName = cacheable.value()[0]; // blockReviews, topKeywords 등
//        String methodKey = joinPoint.getSignature().toShortString();
//
//        long start = System.nanoTime();
//        Object result = joinPoint.proceed();
//        long end = System.nanoTime();
//
//        log.info("[CACHE ACCESS] name={}, method={}, took={}μs", cacheName, methodKey, (end -
// start) / 1000);
//
//        // Prometheus 연결 시 메트릭 전송 로직
//        return result;
//    }
// }

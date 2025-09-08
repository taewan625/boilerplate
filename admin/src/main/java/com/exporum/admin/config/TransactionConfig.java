package com.exporum.admin.config;


import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * TransactionConfig.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @modifier
 * @modified
 * @since 2025. 9. 8. 최초 작성
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Bean
    public TransactionInterceptor txAdvice(PlatformTransactionManager txManager) {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        // 기본 트랜잭션 (create, update, delete)
        RuleBasedTransactionAttribute txAttr = new RuleBasedTransactionAttribute();
        txAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        source.addTransactionalMethod("create*", txAttr);
        source.addTransactionalMethod("update*", txAttr);
        source.addTransactionalMethod("delete*", txAttr);

        // select 메서드 전용 트랜잭션 (읽기 전용 + 타임아웃 10초)
        RuleBasedTransactionAttribute selectAttr = new RuleBasedTransactionAttribute();
        selectAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        selectAttr.setReadOnly(true);
        selectAttr.setTimeout(10); // 초 단위
        source.addTransactionalMethod("select*", selectAttr);

        return new TransactionInterceptor(txManager, source);
    }

    @Bean
    public Advisor txAdvisor(TransactionInterceptor txAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.exporum.admin.domain..service..*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }
}
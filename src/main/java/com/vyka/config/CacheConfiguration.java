package com.vyka.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.vyka.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.vyka.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Profile.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Profile.class.getName() + ".profileSubjects", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Profile.class.getName() + ".educations", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Profile.class.getName() + ".experiences", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Profile.class.getName() + ".awards", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Profile.class.getName() + ".availabilities", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Profile.class.getName() + ".languages", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Subject.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.ProfileSubject.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.ProfileSubject.class.getName() + ".reviews", jcacheConfiguration);
            cm.createCache(com.vyka.domain.ProfileSubject.class.getName() + ".levels", jcacheConfiguration);
            cm.createCache(com.vyka.domain.ProfileSubject.class.getName() + ".rates", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Level.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Level.class.getName() + ".chapters", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Chapters.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Rate.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.ClassLength.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Education.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Experience.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Review.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Award.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Location.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Availability.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Language.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Language.class.getName() + ".profiles", jcacheConfiguration);
            cm.createCache(com.vyka.domain.PackageOrder.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.PackageOrder.class.getName() + ".packageOrders", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Schedule.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Schedule.class.getName() + ".schedules", jcacheConfiguration);
            cm.createCache(com.vyka.domain.OrderActivity.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.OrderActivity.class.getName() + ".orderActivities", jcacheConfiguration);
            cm.createCache(com.vyka.domain.ScheduleActivity.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Payment.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.Payment.class.getName() + ".payments", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Settlement.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.CreditCardPayment.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.PaypalPayment.class.getName(), jcacheConfiguration);
            cm.createCache(com.vyka.domain.PackageOrder.class.getName() + ".schedules", jcacheConfiguration);
            cm.createCache(com.vyka.domain.PackageOrder.class.getName() + ".orderActivities", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Schedule.class.getName() + ".scheduleActivities", jcacheConfiguration);
            cm.createCache(com.vyka.domain.OrderActivity.class.getName() + ".scheduleActivities", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Payment.class.getName() + ".settlements", jcacheConfiguration);
            cm.createCache(com.vyka.domain.Subject.class.getName() + ".subjects", jcacheConfiguration);
            cm.createCache(com.vyka.domain.SubjectLevel.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}

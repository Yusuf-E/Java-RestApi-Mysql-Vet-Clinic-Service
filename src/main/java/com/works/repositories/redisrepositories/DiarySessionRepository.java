package com.works.repositories.redisrepositories;

import com.works.model.DiarySession;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface DiarySessionRepository extends CrudRepository<DiarySession,String> {
}

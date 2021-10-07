package com.works.model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Session")
public class DiarySession extends BaseDiary{
}

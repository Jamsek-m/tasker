package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.entities.persistence.admin.ConfigEntryEntity;
import com.mjamsek.tasker.lib.v1.ConfigEntry;

public class ConfigEntryMapper {
    
    public static ConfigEntryEntity toEntity(ConfigEntry entry) {
        ConfigEntryEntity entity = new ConfigEntryEntity();
        entity.setKey(entry.getKey());
        entity.setValue(entry.getValue());
        return entity;
    }
    
    public static ConfigEntry fromEntity(ConfigEntryEntity entity) {
        ConfigEntry entry = new ConfigEntry();
        entry.setId(entity.getId());
        entry.setKey(entity.getKey());
        entry.setValue(entity.getValue());
        return entry;
    }
}

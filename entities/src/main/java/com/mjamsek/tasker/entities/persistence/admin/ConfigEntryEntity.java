package com.mjamsek.tasker.entities.persistence.admin;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "configuration", indexes = @Index(name = "KEY_UNIQUE_INDEX", columnList = "config_key"))
@NamedQueries({
    @NamedQuery(name = ConfigEntryEntity.FIND_BY_KEY, query = "SELECT c FROM ConfigEntryEntity c WHERE c.key = :key")
})
public class ConfigEntryEntity {
    
    public static final String FIND_BY_KEY = "ConfigEntryEntity.findByKey";
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Column(name = "config_key", unique = true, nullable = false)
    private String key;
    
    @Column(name = "config_value")
    private String value;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}

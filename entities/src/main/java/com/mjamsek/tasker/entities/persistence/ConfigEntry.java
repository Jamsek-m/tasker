package com.mjamsek.tasker.entities.persistence;

import javax.persistence.*;

@Entity
@Table(name = "configuration", indexes = @Index(name = "KEY_UNIQUE_INDEX", columnList = "config_key"))
@NamedQueries({
    @NamedQuery(name = ConfigEntry.FIND_BY_KEY, query = "SELECT c FROM ConfigEntry c WHERE c.key = :key"),
    @NamedQuery(name = ConfigEntry.FIND_ALL, query = "SELECT c FROM ConfigEntry c ORDER BY c.key")
})
public class ConfigEntry {
    
    public static final String FIND_BY_KEY = "Config.findByKey";
    public static final String FIND_ALL = "Config.findAll";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "config_key", unique = true, nullable = false)
    private String key;
    
    @Column(name = "config_value")
    private String value;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
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

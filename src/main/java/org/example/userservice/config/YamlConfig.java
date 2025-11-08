package org.example.userservice.config;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YamlConfig {
    private final Map<String, Object> config;

    public YamlConfig(String fileName){
        Yaml yaml = new Yaml();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (in == null) {
                throw new FileNotFoundException("File not found: " + fileName);
            }
            config = yaml.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML file: " + fileName, e);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getHibernateConfig() {
        return (Map<String, Object>) config.get("hibernate");
    }
}

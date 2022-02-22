package com.planner.utility;

import org.yaml.snakeyaml.Yaml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.planner.utility.Config.Keys.*;

public enum Config {
    INSTANCE;

    private final Yaml yaml = new Yaml();
    LinkedHashMap<String, String> properties;

    public void loadSettings() {
        properties = new LinkedHashMap<>(getProperties());
        Color.setDefaultColor(properties.getOrDefault(COLOR.yamlName, null));
        DateManager.setFormat(properties.getOrDefault(FORMAT.yamlName, null));
    }

    public Map<String, String> getProperties() {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("config.yaml");
        return new LinkedHashMap<>(yaml.load(inputStream));
    }

    public void saveSettings() throws IOException {
        yaml.dump(properties, new BufferedWriter(new FileWriter("src/main/resources/config.yaml")));
    }

    public void overrideSettings(Map<String, ?> settings) throws IOException {
        yaml.dump(settings, new BufferedWriter(new FileWriter("src/main/resources/config.yaml")));
    }

    public void setProperty(String name, String value) throws IOException {
        properties.put(name, value);
        saveSettings();
    }

    public enum Keys {
        COLOR("color"), FORMAT("format"), X_SIZE("xSize"), Y_SIZE("ySize");

        private final String yamlName;

        Keys(String yamlName) {
            this.yamlName = yamlName;
        }

        public String yamlName() {
            return yamlName;
        }
    }
}

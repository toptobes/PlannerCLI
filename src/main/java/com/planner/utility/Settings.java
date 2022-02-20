package com.planner.utility;

import org.yaml.snakeyaml.Yaml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.planner.utility.Settings.Properties.COLOR;

public class Settings {
    private final Yaml yaml = new Yaml();

    public void loadSettings() {
        LinkedHashMap<String, String> settings = new LinkedHashMap<>(getSettings());
        Color.setDefaultColor(settings.getOrDefault(COLOR.yamlName, null));
    }

    public Map<String, String> getSettings() {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("config.yaml");
        return new LinkedHashMap<>(yaml.load(inputStream));
    }

    public void saveSettings(Map<String, ?> settings) throws IOException {
        yaml.dump(settings, new BufferedWriter(new FileWriter("src/main/resources/config.yaml")));
    }

    public void setProperty(String name, String value) throws IOException {
        LinkedHashMap<String, String> settings = new LinkedHashMap<>(getSettings());
        settings.put(name, value);
        saveSettings(settings);
    }

    public enum Properties {
        COLOR("color"), FORMAT("format"), X_SIZE("xSize"), Y_SIZE("ySize");

        private final String yamlName;

        Properties(String yamlName) {
            this.yamlName = yamlName;
        }

        public String yamlName() {
            return yamlName;
        }
    }
}

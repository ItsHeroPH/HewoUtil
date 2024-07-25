package com.github.itsheroph.hewoutil.configuration.yaml;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HewoYamlReader {

    private final Map<String, Object> contents = new HashMap<>();

    public HewoYamlReader(File file) throws IOException {

        this(new FileInputStream(file));

    }

    public HewoYamlReader(InputStream input) throws IOException {

        LoaderOptions options = new LoaderOptions();
        options.setAllowRecursiveKeys(true);
        Yaml yaml = new Yaml(options);
        Map<String, Object> content = yaml.load(input);

        if(content != null) {

            for(Entry<String, Object> entry : content.entrySet()) {

                this.addContent(entry, "");

            }

        }

        input.close();

    }

    public Map<String, Object> getContents() {

        return this.contents;

    }

    public void addContent(Entry<String, Object> entry, String path) {

        String key = entry.getKey();
        Object value = entry.getValue();

        String newPath = path.isEmpty() ? key : path + "." + key;
        if (value instanceof Map) {

            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) value;

            for (Map.Entry<String, Object> subEntry : map.entrySet()) {

                addContent(subEntry, newPath);

            }

        } else {

            this.contents.put(newPath, value);

        }

    }
}

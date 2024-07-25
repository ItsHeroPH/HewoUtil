package com.github.itsheroph.hewoutil.configuration.yaml;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HewoYamlMerger {

    private final Map<String, Object> defaultOptions;

    public HewoYamlMerger(Map<String, Object> defaultOptions) {

        this.defaultOptions = defaultOptions;

    }

    public Map<String, Object> merge(Map<String, Object> option) {

        Map<String, Object> mergeMap = new HashMap<>();

        for(Entry<String, Object> entry : this.defaultOptions.entrySet()) {

            String key = entry.getKey();
            Object value = option.containsKey(key) ? option.get(key) : entry.getValue();
            mergeMap.put(key, value);

        }

        return mergeMap;
    }
}

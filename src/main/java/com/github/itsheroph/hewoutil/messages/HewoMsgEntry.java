package com.github.itsheroph.hewoutil.messages;

public class HewoMsgEntry {

    private final String key;
    private final String replacement;

    public HewoMsgEntry(String key, String replacement) {

        this.key = key;
        this.replacement = replacement;

    }

    public HewoMsgEntry(String key, int replacement) {

        this(key, "" + replacement);

    }

    public HewoMsgEntry(String key, double replacement) {

        this(key, "" + replacement);

    }

    public HewoMsgEntry(String key, boolean replacement) {

        this(key, replacement ? "true" : "false");

    }

    public String getKey() {

        return this.key;

    }

    public String getReplacement() {

        return this.replacement;

    }
}

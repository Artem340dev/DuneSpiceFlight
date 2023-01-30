package org.millida.dunespiceflight.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class MessageBuilder {
    private ComponentBuilder builder;

    public MessageBuilder() {
        this(new String());
    }
    public MessageBuilder(String text) {
        this.builder = new ComponentBuilder(ChatUtil.parseColor(text));
    }

    public MessageBuilder add(String text) {
        builder.append(ChatUtil.parseColor(text));
        return this;
    }

    public MessageBuilder add(HoverEvent event) {
        builder.event(event);
        return this;
    }

    public MessageBuilder add(ClickEvent event) {
        builder.event(event);
        return this;
    }

    public BaseComponent[] build() {
        return builder.create();
    }
}
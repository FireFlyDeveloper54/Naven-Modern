package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.Naven;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.api.types.EventType;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventClientChat;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventMotion;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRespawn;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.Minecraft;

public class EventWrapper {

    public EventWrapper() {
        registerEvents();
    }

    private void registerEvents() {
        ClientSendMessageEvents.ALLOW_CHAT.register(this::onClientChat);
        ClientSendMessageEvents.MODIFY_CHAT.register(this::modifyClientChat);
    }

    private boolean onClientChat(String message) {
        EventClientChat event = new EventClientChat(message);
        Naven.getInstance().getEventManager().call(event);
        return !event.isCancelled();
    }

    private String modifyClientChat(String message) {
        EventClientChat event = new EventClientChat(message);
        Naven.getInstance().getEventManager().call(event);
        return event.getMessage();
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        if (e.getType() == EventType.PRE && Minecraft.getInstance().player != null && Minecraft.getInstance().player.tickCount <= 1) {
            Naven.getInstance().getEventManager().call(new EventRespawn());
        }
    }
}
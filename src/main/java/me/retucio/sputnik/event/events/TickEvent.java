package me.retucio.sputnik.event.events;

import me.retucio.sputnik.event.Event;


/**
 * @see me.retucio.sputnik.mixin.MinecraftClientMixin#onTickPre
 * @see me.retucio.sputnik.mixin.MinecraftClientMixin#onTickPost
 */
public class TickEvent {

    public static class Pre extends Event {}
    public static class Post extends Event {}
}

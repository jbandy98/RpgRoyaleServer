package com.rpgroyale.worldservice.entity;

    // events are objects scattered in the game world that give the player things to do
    // initially, events are part of the random seed generator to create the world, but
    // after the world is live, these elements are stored individually in the database, since they can be
    // changed by players (looted chests, killed enemies, etc.)

public enum EventType {
    NONE,
    INN,                // allows party to heal quickly for money
    SHOP,               // allows party to buy and sell items
    TAVERN,             // allows a party to obtain and complete quests
    CHURCH,             // allows a player to remove debuffs, revive (at least one member needs to be alive to get here), and get beneficial buffs for money
    SMITH,              // allows a player to perform weapon and armor crafting
    ALCHEMIST,          // allows a player to perform potion, poison, and trap making
    ENCHANTER,          // players can enchant items, perform enchanting skill
    CAMP,               // player generated structures that assist in regeneration
    DUNGEON,            // allows a party to enter caves, dungeons, castles, etc.
    ENEMY,              // a visible enemy to battle (boss, etc.)
    RESOURCE,           // a resource node to gather - lumber, ore, herbs, etc.
    TRAP,               // a trap item that can trigger and debuff / harm the player
    LOOT,               // items available for pickup, chests, etc.
    GATE,               // a door, gate, etc. that prevents movement, possibly needed key (key can be physical or quest)
    TELEPORT,           // a way to travel to a different location over a set amount of time (portal, stagecoach, boat)
    INFO                // a sign, npc with hints, etc.
}

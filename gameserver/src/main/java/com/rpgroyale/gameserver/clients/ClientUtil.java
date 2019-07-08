package com.rpgroyale.gameserver.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientUtil {

    public static EncounterClient encounterClient;
    public static GameDataClient gameDataClient;

    @Autowired
    public void setEncounterClient(EncounterClient client) {
        ClientUtil.encounterClient = client;
    }

    @Autowired
    public void setGameDataClient(GameDataClient client) { ClientUtil.gameDataClient = client; }
}

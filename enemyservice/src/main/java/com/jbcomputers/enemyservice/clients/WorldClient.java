package com.jbcomputers.enemyservice.clients;

import com.jbcomputers.enemyservice.entity.WorldInfo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="world", path="/world")
public interface WorldClient {

    @RequestMapping(method=RequestMethod.GET, value="/{gameId}")
    WorldInfo getWorldInfoForGame(@PathVariable("gameId") int gameId);
}

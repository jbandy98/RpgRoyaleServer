package com.rpgroyale.combatserver.utils;

public class PathFindingUtil {

    public static float getDistance(int startX, int startY, int endX, int endY)
    {
        float distX = endX - startX;
        float distY = endY - startY;

        float result = (float)(Math.sqrt((distX * distX) + (distY * distY)));

        return result;
    }
}

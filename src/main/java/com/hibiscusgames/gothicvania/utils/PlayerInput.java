package com.hibiscusgames.gothicvania.utils;

import com.hibiscusgames.gothicvania.entities.Player;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;

public class PlayerInput {
    private PlayerInput(){

    }

    public static void init(){
        // Quitting the Game Handling
        Input.keyboard().onKeyPressed(KeyEvent.VK_ESCAPE, e -> System.exit(0));

        // isMoving Handling
        Input.keyboard().onKeyPressed(KeyEvent.VK_A, e -> Player.instance().setIsMoving(true));
        Input.keyboard().onKeyPressed(KeyEvent.VK_D, e -> Player.instance().setIsMoving(true));
        Input.keyboard().onKeyReleased(KeyEvent.VK_A, e -> Player.instance().setIsMoving(false));
        Input.keyboard().onKeyReleased(KeyEvent.VK_D, e -> Player.instance().setIsMoving(false));

        // isCrouching Handling
        Input.keyboard().onKeyPressed(KeyEvent.VK_SHIFT, e -> Player.instance().setIsCrouching(true));
        Input.keyboard().onKeyReleased(KeyEvent.VK_SHIFT, e -> Player.instance().setIsCrouching(false));
    }
}

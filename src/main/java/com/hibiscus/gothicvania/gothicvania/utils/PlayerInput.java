package com.hibiscus.gothicvania.gothicvania.utils;

import com.hibiscus.gothicvania.gothicvania.entities.Player;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;

public class PlayerInput {
    private PlayerInput(){

    }

    public static void init(){
        Input.keyboard().onKeyPressed(KeyEvent.VK_ESCAPE, e -> System.exit(0));
        Input.keyboard().onKeyPressed(KeyEvent.VK_A, e -> Player.instance().setIsMoving(true));
        Input.keyboard().onKeyPressed(KeyEvent.VK_D, e -> Player.instance().setIsMoving(true));
        Input.keyboard().onKeyReleased(KeyEvent.VK_A, e -> Player.instance().setIsMoving(false));
        Input.keyboard().onKeyReleased(KeyEvent.VK_D, e -> Player.instance().setIsMoving(false));
    }
}

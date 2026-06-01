package com.hibiscus.gothicvania.gothicvania.utils;

import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;

public class PlayerInput {
    private PlayerInput(){

    }

    public static void init(){
        Input.keyboard().onKeyPressed(KeyEvent.VK_ESCAPE, e -> System.exit(0));
    }
}

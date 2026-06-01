package com.hibiscus.gothicvania.gothicvania.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.IMovementController;

import java.awt.event.KeyEvent;

@EntityInfo(width = 82, height = 60)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 8, collisionBoxHeight = 44, collision = true)
public class Player extends Creature implements IUpdateable {
    private static Player instance;

    private boolean moving;

    public static Player instance(){
        if(instance == null){
            instance = new Player();
        }

        return instance;
    }

    private Player(){
        super("player");

        Input.keyboard().onKeyPressed(KeyEvent.VK_A, e -> moving = true);
        Input.keyboard().onKeyPressed(KeyEvent.VK_D, e -> moving = true);
        Input.keyboard().onKeyReleased(KeyEvent.VK_A, e -> moving = false);
        Input.keyboard().onKeyReleased(KeyEvent.VK_D, e -> moving = false);
    }


    @Override
    public void update() {
        System.out.println("isIdle: " + this.isIdle());
    }

    @Override
    public boolean isIdle() {
        return !moving;
    }

    @Override
    protected IMovementController createMovementController(){
        return new PlatformingMovementController<>(this);
    }
}

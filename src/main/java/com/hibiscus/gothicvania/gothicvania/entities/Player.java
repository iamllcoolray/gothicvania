package com.hibiscus.gothicvania.gothicvania.entities;

import com.hibiscus.gothicvania.gothicvania.abilities.Jump;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.IMovementController;

import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

@EntityInfo(width = 82, height = 60)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 8, collisionBoxHeight = 44, collision = true)
public class Player extends Creature implements IUpdateable {
    private static Player instance;

    private final Jump jump;

    public static final int MAX_ADDITIONAL_JUMPS = 1;
    private boolean isMoving;
    private int consecutiveJumps;

    private Player(){
        super("player");

        this.jump = new Jump(this);

        Input.keyboard().onKeyPressed(KeyEvent.VK_A, e -> isMoving = true);
        Input.keyboard().onKeyPressed(KeyEvent.VK_D, e -> isMoving = true);
        Input.keyboard().onKeyReleased(KeyEvent.VK_A, e -> isMoving = false);
        Input.keyboard().onKeyReleased(KeyEvent.VK_D, e -> isMoving = false);
    }

    public static Player instance(){
        if(instance == null){
            instance = new Player();
        }

        return instance;
    }

    @Override
    public void update() {
        if (this.isTouchingGround()) {
            this.consecutiveJumps = 0;
        }
    }

    @Override
    public boolean isIdle() {
        return !isMoving;
    }

    @Override
    protected IMovementController createMovementController(){
        return new PlatformingMovementController<>(this);
    }

    @Action(description = "This performs the jump ability for the player's entity.")
    public void jump() {
        if (this.consecutiveJumps >= MAX_ADDITIONAL_JUMPS || !this.jump.canCast()) {
            return;
        }

        this.jump.cast();
        this.consecutiveJumps++;
    }

    private boolean isTouchingGround() {
        // the idea of this ground check is to extend the current collision box by
        // one pixel and see if
        // a) it collides with any static collision box
        Rectangle2D groundCheck = new Rectangle2D.Double(getCollisionBox().getX(), getCollisionBox().getY(), getCollisionBoxWidth(), getCollisionBoxHeight() + 1);

        // b) it collides with the map's boundaries
        if (groundCheck.getMaxY() > Game.physics().getBounds().getMaxY()) {
            return true;
        }

        return Game.physics().collides(groundCheck, Collision.STATIC);
    }
}

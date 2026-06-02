package com.hibiscus.gothicvania.gothicvania.entities;

import com.hibiscus.gothicvania.gothicvania.abilities.Jump;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.CreatureAnimationController;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.IMovementController;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import static de.gurkenlabs.litiengine.graphics.animation.AnimationController.flippedAnimation;

@EntityInfo(width = 82, height = 60)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 8, collisionBoxHeight = 44, collision = true)
public class Player extends Creature implements IUpdateable {
    private static Player instance;

    private Animation jumpAnimation;

    private final Jump jump;

    private boolean isMoving;

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

    public void setIsMoving(boolean isMoving){
        this.isMoving = isMoving;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isIdle() {
        return !isMoving;
    }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        CreatureAnimationController<Player> controller = new CreatureAnimationController<>(this, true);
        jumpAnimation = new Animation(Resources.spritesheets().get("player-jump-right"), false);
        controller.add(jumpAnimation);
        controller.add(flippedAnimation(jumpAnimation, "player-jump-left", false));
        controller.addRule(x -> x.isTouchingGround(), x -> "player-jump-" + x.getFacingDirection().name().toLowerCase(), 100);
        return controller;
    }

    @Override
    protected IMovementController createMovementController(){
        return new PlatformingMovementController<>(this);
    }

    @Action(description = "This performs the jump ability for the player's entity.")
    public void jump() {
        if (this.isTouchingGround() || !this.jump.canCast()) {
            return;
        }

        this.jump.cast();
    }

    public boolean isTouchingGround() {
        Rectangle2D groundCheck = new Rectangle2D.Double(getCollisionBox().getX(), getCollisionBox().getY(), getCollisionBoxWidth(), getCollisionBoxHeight() + 1);

        if (groundCheck.getMaxY() > Game.physics().getBounds().getMaxY()) {
            return false;
        }

        return !Game.physics().collides(groundCheck, Collision.STATIC);
    }
}

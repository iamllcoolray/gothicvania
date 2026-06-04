package com.hibiscusgames.gothicvania.entities;

import com.hibiscusgames.gothicvania.abilities.Jump;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.CreatureAnimationController;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.IMovementController;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.geom.Rectangle2D;

import static de.gurkenlabs.litiengine.graphics.animation.AnimationController.flippedAnimation;

@EntityInfo(width = 82, height = 60)
@MovementInfo(velocity = 120)
@CollisionInfo(collisionBoxWidth = 8, collisionBoxHeight = 44, collision = true)
public class Player extends Creature implements IUpdateable {
    private static Player instance;

    private final Jump jump;

    private boolean isMoving, isCrouching, isPunching, isKicking, isCrouchKicking, isFlyKicking = false;
    private double previousPositionY, currentPositionY;

    private Player(){
        super("player");

        this.jump = new Jump(this);
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

    public void setIsCrouching(boolean isCrouching){
        this.isCrouching = isCrouching;
        if (isCrouching) {
            this.setCollisionBoxWidth(40);
            this.setCollisionBoxHeight(30);
        } else {
            this.setCollisionBoxWidth(8);
            this.setCollisionBoxHeight(44);
        }
    }

    public void setIsCrouchKicking(boolean isCrouchKicking){
        this.isCrouchKicking = isCrouchKicking;
    }

    public void setIsPunching(boolean isPunching){
        this.isPunching = isPunching;
    }

    public void setIsKicking(boolean isKicking){
        this.isKicking = isKicking;
    }

    public void setIsFlyKicking(boolean isFlyKicking){
        this.isFlyKicking = isFlyKicking;
    }

    public boolean canCrouch() {
        return isCrouching && isTouchingGround();
    }

    public boolean canPunch() {
        return isPunching && isTouchingGround();
    }

    public boolean canKick() {
        return isKicking && isTouchingGround();
    }

    public boolean canFlyingKick() {
        return !isTouchingGround() && isFlyKicking;
    }

    public boolean canCrouchKick() {
        return canCrouch() && isCrouchKicking;
    }

    public boolean isJumpingUp(){
        return previousPositionY > currentPositionY && !isTouchingGround();
    }

    public boolean isFallingDown(){
        return previousPositionY < currentPositionY && !isTouchingGround();
    }

    @Override
    public void update() {
        previousPositionY = currentPositionY;
        currentPositionY = this.getY();
    }

    @Override
    public boolean isIdle() {
        return !isMoving;
    }

    @Override
    protected IEntityAnimationController<? extends Creature> createAnimationController() {
        CreatureAnimationController<Player> controller = new CreatureAnimationController<>(this, true);

//        Animation jumpAnimation = new Animation(Resources.spritesheets().get("player-jump-right"), false);
//        controller.add(jumpAnimation);
//        controller.add(flippedAnimation(jumpAnimation, "player-jump-left", false));
//        controller.addRule(Player::isJumpingUp, x -> "player-jump-" + x.getFacingDirection().name().toLowerCase(), 0);

        Animation jumpAnimation = new Animation(Resources.spritesheets().get("player-jump-right"), false);
        controller.add(jumpAnimation);
        controller.add(flippedAnimation(jumpAnimation, "player-jump-left", false));
        controller.addRule(x -> !x.isTouchingGround(), x -> "player-jump-" + x.getFacingDirection().name().toLowerCase(), 0);

        Animation crouchAnimation = new Animation(Resources.spritesheets().get("player-crouch-right"), false);
        controller.add(crouchAnimation);
        controller.add(flippedAnimation(crouchAnimation, "player-crouch-left", false));
        controller.addRule(Player::canCrouch, x -> "player-crouch-" + x.getFacingDirection().name().toLowerCase(), 0);

        Animation punchAnimation = new Animation(Resources.spritesheets().get("player-punch-right"), false);
        controller.add(punchAnimation);
        controller.add(flippedAnimation(punchAnimation, "player-punch-left", false));
        controller.addRule(Player::canPunch, x -> "player-punch-" + x.getFacingDirection().name().toLowerCase(), 0);

        Animation kickAnimation = new Animation(Resources.spritesheets().get("player-kick-right"), false);
        controller.add(kickAnimation);
        controller.add(flippedAnimation(kickAnimation, "player-kick-left", false));
        controller.addRule(Player::canKick, x -> "player-kick-" + x.getFacingDirection().name().toLowerCase(), 0);

        Animation crouchKickAnimation = new Animation(Resources.spritesheets().get("player-crouch_kick-right"), false);
        controller.add(crouchKickAnimation);
        controller.add(flippedAnimation(crouchKickAnimation, "player-crouch_kick-left", false));
        controller.addRule(Player::canCrouchKick, x -> "player-crouch_kick-" + x.getFacingDirection().name().toLowerCase(), 0);

//        Animation fallAnimation = new Animation(Resources.spritesheets().get("player-fall-right"), false);
//        controller.add(fallAnimation);
//        controller.add(flippedAnimation(fallAnimation, "player-fall-left", false));
//        controller.addRule(Player::isFallingDown, x -> "player-fall-" + x.getFacingDirection().name().toLowerCase(), 0);

        Animation flyingKickAnimation = new Animation(Resources.spritesheets().get("player-flying_kick-right"), false);
        controller.add(flyingKickAnimation);
        controller.add(flippedAnimation(flyingKickAnimation, "player-flying_kick-left", false));
        controller.addRule(Player::canFlyingKick, x -> "player-flying_kick-" + x.getFacingDirection().name().toLowerCase(), 0);

//        Animation hurtAnimation = new Animation(Resources.spritesheets().get("player-hurt-right"), false);
//        controller.add(hurtAnimation);
//        controller.add(flippedAnimation(hurtAnimation, "player-hurt-left", false));
//        controller.addRule(x -> x.isFallingDown(), x -> "player-hurt-" + x.getFacingDirection().name().toLowerCase(), 0);

        return controller;
    }

    @Override
    protected IMovementController createMovementController(){
        return new PlatformingMovementController<>(this);
    }

    @Action(description = "This performs the jump ability for the player's entity.")
    public void jump() {
        if (!this.isTouchingGround() || !this.jump.canCast()) {
            return;
        }

        this.jump.cast();
    }

    public boolean isTouchingGround() {
        Rectangle2D groundCheck = new Rectangle2D.Double(getCollisionBox().getX(), getCollisionBox().getY(), getCollisionBoxWidth(), getCollisionBoxHeight() + 1);

        if (groundCheck.getMaxY() > Game.physics().getBounds().getMaxY()) {
            return true;
        }

        return Game.physics().collides(groundCheck, Collision.STATIC);
    }
}

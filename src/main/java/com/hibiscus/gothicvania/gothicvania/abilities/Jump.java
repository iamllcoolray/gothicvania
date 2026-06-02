package com.hibiscus.gothicvania.gothicvania.abilities;

import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityPivotType;

@AbilityInfo(cooldown = 500, origin = EntityPivotType.COLLISIONBOX_CENTER, duration = 300, value = 350)
public class Jump extends Ability {
    public Jump(Creature executor) {
        super(executor);

        addEffect(new JumpEffect(getExecutor(), getAttributes().value().get(), getAttributes().duration().get()));
    }
}

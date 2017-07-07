package com.lukelavin.orbit.collision;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lukelavin.orbit.component.ShieldingComponent;
import com.lukelavin.orbit.type.EntityType;

public class OrbitalProjectileHandler extends CollisionHandler
{
    public OrbitalProjectileHandler(EntityType orbital, EntityType projectile)
    {
        super(orbital, projectile);
    }

    @Override
    protected void onCollisionBegin(Entity orbital, Entity projectile)
    {
        Entity player = FXGL.getApp().getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0);
        if(player.getComponentUnsafe(ShieldingComponent.class) != null)
            projectile.removeFromWorld();
    }
}

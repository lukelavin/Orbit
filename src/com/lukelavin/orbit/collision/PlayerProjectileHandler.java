package com.lukelavin.orbit.collision;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lukelavin.orbit.OrbitApp;
import com.lukelavin.orbit.component.ConfuseComponent;
import com.lukelavin.orbit.component.SubTypeComponent;
import com.lukelavin.orbit.type.EntityType;
import com.lukelavin.orbit.type.ProjectileType;

public class PlayerProjectileHandler extends CollisionHandler
{
    public PlayerProjectileHandler(EntityType player, EntityType projectile)
    {
        super(player, projectile);
    }

    OrbitApp app = (OrbitApp) FXGL.getApp();

    @Override
    protected void onCollisionBegin(Entity player, Entity projectile)
    {
        if(projectile.getComponentUnsafe(SubTypeComponent.class).getValue() == ProjectileType.CONFUSE)
        {
            player.addComponent(new ConfuseComponent());

            //sound from Tim Gormly on freesound.org under creative commons
            app.getAudioPlayer().playSound("confuse_sound.aiff");

            projectile.removeFromWorld();
        }
        else
        {
            app.loseHP();

            //sound from Tim Gormly on freesound.org under creative commons
            app.getAudioPlayer().playSound("hit_sound.aiff");
            projectile.removeFromWorld();
        }
    }
}

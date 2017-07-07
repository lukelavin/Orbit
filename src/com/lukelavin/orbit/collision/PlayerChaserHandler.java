package com.lukelavin.orbit.collision;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lukelavin.orbit.OrbitApp;

/**
 * Created by lukel on 4/25/2017.
 */
public class PlayerChaserHandler extends CollisionHandler
{
    OrbitApp app = (OrbitApp) FXGL.getApp();

    public PlayerChaserHandler(Object a, Object b)
    {
        super(a, b);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b)
    {
        app.loseHP();
        //sound from Tim Gormly on freesound.org under creative commons
        app.getAudioPlayer().playSound("hit_sound.aiff");
    }
}

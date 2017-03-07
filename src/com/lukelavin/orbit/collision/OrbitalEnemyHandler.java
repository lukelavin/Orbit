package com.lukelavin.orbit.collision;

import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lukelavin.orbit.control.EnemyControl;
import com.lukelavin.orbit.type.EntityType;

/**
 * Created by lukel on 3/6/2017.
 */
public class OrbitalEnemyHandler extends CollisionHandler
{
    public OrbitalEnemyHandler(EntityType orbital, EntityType enemy)
    {
        super(orbital, enemy);
    }

    @Override
    protected void onCollisionBegin(Entity o, Entity e)
    {
        GameEntity orbital = (GameEntity) o;
        GameEntity enemy = (GameEntity) e;
        EnemyControl enemyControl = enemy.getControlUnsafe(EnemyControl.class);

        enemyControl.takeDamage(orbital.getWidth());
    }

    @Override
    protected void onCollisionEnd(Entity o, Entity e)
    {
        GameEntity orbital = (GameEntity) o;
        GameEntity enemy = (GameEntity) e;
        EnemyControl enemyControl = enemy.getControlUnsafe(EnemyControl.class);

        enemyControl.resetTexture();
    }
}

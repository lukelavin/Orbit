package com.lukelavin.orbit.collision;

import com.almasb.ents.Control;
import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lukelavin.orbit.control.enemy.AbstractEnemyControl;
import com.lukelavin.orbit.control.enemy.Enemy1Control;
import com.lukelavin.orbit.type.EntityType;

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

        AbstractEnemyControl enemyControl = new Enemy1Control();
        for(Control control : enemy.getControls())
            if(control instanceof AbstractEnemyControl)
                enemyControl = (AbstractEnemyControl) control;
        //Enemy1Control enemyControl = enemy.getControlUnsafe(Enemy1Control.class);

        enemyControl.takeDamage(orbital.getWidth());
    }

    @Override
    protected void onCollisionEnd(Entity o, Entity e)
    {
        GameEntity orbital = (GameEntity) o;
        GameEntity enemy = (GameEntity) e;

        AbstractEnemyControl enemyControl = new Enemy1Control();
        for(Control control : enemy.getControls())
            if(control instanceof AbstractEnemyControl)
                enemyControl = (AbstractEnemyControl) control;

        enemyControl.resetTexture();
    }
}

package com.lukelavin.orbit.control.enemy;

import com.almasb.ents.Entity;
public class Enemy4BranchControl extends AbstractEnemyControl
{
    private Enemy4Control base;

    public Enemy4BranchControl(Enemy4Control base)
    {
        this.base = base;
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {

    }

    @Override
    protected void checkHP()
    {
        if(hp() <= 0)
        {
            gameEntity.removeFromWorld();
            base.branchDeath(gameEntity);
        }
        base.checkHP();
    }
}

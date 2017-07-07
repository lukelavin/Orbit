package com.lukelavin.orbit.control.enemy;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.EntityView;
import com.lukelavin.orbit.EntityFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Enemy5BranchControl extends AbstractEnemyControl
{
    private Enemy5Control base;
    private String mode;
    private int hitCount;

    public Enemy5BranchControl(Enemy5Control base)
    {
        this.base = base;
    }

    @Override
    public void onAdded(Entity entity)
    {
        super.onAdded(entity);
        mode = "VULNERABLE";
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        updateTexture();

        if(mode.equals("INVINCIBLE"))
        {
            if(projectileTimer.elapsed(Duration.millis(2000 - 500 * (4 - base.getOthers().size()))))
            {
                app.getGameWorld().addEntity(EntityFactory.newBasicProjectile(gameEntity.getCenter(), findDestinationEdge(gameEntity.getCenter(), player().getPosition())));
                projectileTimer.capture();
            }
        }
        else
        {
            projectileTimer.capture();
        }
    }

    private void updateTexture()
    {
        if(mode.equals("INVINCIBLE"))
        {
            gameEntity.setView(new EntityView(new Rectangle(50, 50, Color.GRAY)));
        }
    }

    @Override
    public void takeDamage(double damage)
    {
        if(!mode.equals("INVINCIBLE"))
        {
            super.takeDamage(damage);
            hitCount++;
            if (hitCount == 3)
            {
                base.nextPhase(gameEntity);
                hitCount = 0;
            }

            //sound from Tim Gormly on freesound.org under creative commons
            FXGL.getApp().getAudioPlayer().playSound("boss_hit_sound.aiff");
        }
    }

    @Override
    public void resetTexture()
    {
        if(!mode.equals("INVINCIBLE"))
            super.resetTexture();
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

    public String getMode()
    {
        return mode;
    }

    public void setMode(String mode)
    {
        this.mode = mode;

        resetTexture();
    }
}

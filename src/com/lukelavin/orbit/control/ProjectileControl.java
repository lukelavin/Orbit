package com.lukelavin.orbit.control;

import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.GameEntity;
import com.lukelavin.orbit.component.DamageComponent;
import javafx.geometry.Point2D;

public class ProjectileControl extends AbstractControl
{
    GameEntity projectile;
    double projectile_speed = 10;

    Point2D target;
    Point2D currentDestination;
    Point2D[] checkpoints;
    int checkpointIndex;
    long startTime;

    public double damage(){
        return projectile.getComponentUnsafe(DamageComponent.class).getValue();
    }

    @Override
    public void onAdded(Entity entity) {
        projectile = (GameEntity) entity;
        projectile_speed = 10;

        currentDestination = target;

        checkpointIndex = 0;
        startTime = System.nanoTime();
    }

    @Override
    public void onUpdate(Entity entity, double v) {

        double dx = currentDestination.getX() - projectile.getPosition().getX();
        double dy = currentDestination.getY() - projectile.getPosition().getY();

        double dv = Math.sqrt(dx * dx + dy * dy);
        double vRatio = dv / projectile_speed;

        projectile.getPositionComponent().translate(dx / vRatio, dy / vRatio);

        dx = currentDestination.getX() - projectile.getPosition().getX();
        dy = currentDestination.getY() - projectile.getPosition().getY();
        if(Math.abs(dx) <= projectile_speed && Math.abs(dy) <= projectile_speed)
        {
            if(currentDestination.equals(target)){
                projectile.removeFromWorld();
            }
            else if(currentDestination.equals(checkpoints[checkpointIndex])){
                checkpointIndex++;
                currentDestination = checkpoints[checkpointIndex];
            }
        }
    }

    public void setTarget(Point2D newTarget){
        target = newTarget;
    }

    public void setChekpoints(Point2D... newCheckpoints)
    {
        checkpoints = newCheckpoints;
    }
}

package com.lukelavin.orbit.control.enemy;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.EntityView;
import com.almasb.fxgl.time.LocalTimer;
import com.lukelavin.orbit.EntityFactory;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class Enemy2Control extends AbstractEnemyControl
{
    private EntityView initial;
    private LocalTimer projectile2Timer;

    @Override
    public void onAdded(Entity entity)
    {
        super.onAdded(entity);

        initial = gameEntity.getView();
        projectile2Timer = FXGL.newLocalTimer();
        projectile2Timer.capture();

        app.initBossHealth(500);
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        double x = player().getPosition().getX();
        double y = player().getPosition().getY();

        double gameHeight = FXGL.getApp().getHeight();
        double gameWidth = FXGL.getApp().getWidth();

        if(projectileTimer.elapsed(Duration.millis(750))){
            Point2D origin = randomOrigin(new Point2D(0,0), new Point2D(0, gameHeight), new Point2D(gameWidth, 0), new Point2D(gameWidth, gameHeight));
            Point2D target = new Point2D(player().getPosition().getX(), player().getPosition().getY());

            FXGL.getApp().getGameWorld().addEntity(EntityFactory.newBasicProjectile(origin, findDestinationEdge(origin, target)));
            projectileTimer.capture();
        }

        if(projectile2Timer.elapsed(Duration.millis(3000))){
            Point2D origin = gameEntity.getCenter();
            Point2D target = new Point2D(player().getPosition().getX(), player().getPosition().getY());

            FXGL.getApp().getGameWorld().addEntity(EntityFactory.newConfuseProjectile(origin, findDestinationEdge(origin, target)));
            projectile2Timer.capture();
        }
    }

    private Point2D randomOrigin(Point2D... randoms)
    {
        int random = (int) (Math.random() * randoms.length);

        return randoms[random];
    }
}

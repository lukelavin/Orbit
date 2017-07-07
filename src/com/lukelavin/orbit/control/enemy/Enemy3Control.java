package com.lukelavin.orbit.control.enemy;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.lukelavin.orbit.EntityFactory;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.lukelavin.orbit.Config.*;
/**
 * Created by 1072524 on 4/24/2017.
 */
public class Enemy3Control extends AbstractEnemyControl
{
    @Override
    public void onAdded(Entity entity)
    {
        super.onAdded(entity);

        app.initBossHealth(450);
    }

    @Override
    public void onUpdate(Entity entity, double v) {
        double x = player().getPosition().getX();
        double y = player().getPosition().getY();

        double gameHeight = FXGL.getApp().getHeight();
        double gameWidth = FXGL.getApp().getWidth();

        if(projectileTimer.elapsed(Duration.millis(500))){
            Point2D origin;
            Point2D target = player().getPosition();

            int random = (int) (Math.random() * 4);

            //start the projectile at a random place on an edge, and move directly to the opposite edge
            if(random == 0)
            {
                int randx = (int) (Math.random() * gameWidth);
                origin = new Point2D(randx, 0);
            }
            else if(random == 1)
            {
                int randx = (int) (Math.random() * gameWidth);
                origin = new Point2D(randx, gameHeight);
            }
            else if(random == 2)
            {
                int randy = (int) (Math.random() * gameHeight);
                origin = new Point2D(0, randy);
            }
            else
            {
                int randy = (int) (Math.random() * gameHeight);
                origin = new Point2D(gameWidth, randy);
            }

            int rand = (int) (Math.random() * 10);
            if(rand == 0)
                FXGL.getApp().getGameWorld().addEntity(EntityFactory.newConfuseProjectile(origin, findDestinationEdge(origin, target)));
            else
                FXGL.getApp().getGameWorld().addEntity(EntityFactory.newBasicProjectile(origin, findDestinationEdge(origin, target)));
            projectileTimer.capture();
        }

        if(Math.abs(x - gameEntity.getX()) > Math.abs(y - gameEntity.getY()))
        {
            int direction = (int) Math.signum(x - gameEntity.getX());
            gameEntity.getPositionComponent().translate(direction * DEFAULT_SPEED / 2, 0);
        }
        else
        {
            int direction = (int) Math.signum(y - gameEntity.getY());
            gameEntity.getPositionComponent().translate(0, direction * DEFAULT_SPEED / 2);
        }
    }

}

package com.lukelavin.orbit.control.enemy;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.EntityView;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.time.LocalTimer;
import com.lukelavin.orbit.EntityFactory;
import com.lukelavin.orbit.component.HPComponent;
import com.lukelavin.orbit.type.EntityType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.lukelavin.orbit.Config.DEFAULT_SPEED;
import static com.lukelavin.orbit.RenderLayers.bottom;

public class Enemy4Control extends AbstractEnemyControl
{
    private List<GameEntity> others;
    private GameEntity chaser;

    private LocalTimer projectileTimer2;

    @Override
    public void onAdded(Entity entity)
    {
        super.onAdded(entity);
        projectileTimer2 = FXGL.newLocalTimer();
        projectileTimer2.capture();

        others = new ArrayList<GameEntity>();
        others.add(newOther(50, 50));
        others.add(newOther(50, app.getHeight() - 200));
        others.add(newOther(app.getWidth() - 150, 50));
        others.add(newOther(app.getWidth() - 150, app.getHeight() - 200));
        for(GameEntity other : others)
        {
            app.getGameWorld().addEntity(other);
        }

        chaser = newChaser(app.getWidth() / 2 - 25, app.getHeight() / 2 - 25);
        app.getGameWorld().addEntity(chaser);

        app.initBossHealth(600);
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        double x = player().getPosition().getX();
        double y = player().getPosition().getY();

        if(Math.abs(x - chaser.getX()) > Math.abs(y - chaser.getY()))
        {
            int direction = (int) Math.signum(x - chaser.getX());
            chaser.getPositionComponent().translate(direction * DEFAULT_SPEED / 2, 0);
        }
        else
        {
            int direction = (int) Math.signum(y - chaser.getY());
            chaser.getPositionComponent().translate(0, direction * DEFAULT_SPEED / 2);
        }

        if(projectileTimer.elapsed(Duration.millis(others.size() * 500 + 250)))
        {
            app.getGameWorld().addEntity(EntityFactory.newBasicProjectile(chaser.getCenter(), findDestinationEdge(chaser.getCenter(), player().getCenter())));
            projectileTimer.capture();
        }
        if(projectileTimer2.elapsed(Duration.millis(1500)))
        {
            Point2D origin;
            Point2D target;

            int random = (int) (Math.random() * 4);

            //start the projectile at a random place on an edge, and move directly to the opposite edge
            if(random == 0)
            {
                int randx = (int) (Math.random() * app.getWidth());
                origin = new Point2D(randx, 0);
                target = new Point2D(randx, app.getHeight());
            }
            else if(random == 1)
            {
                int randx = (int) (Math.random() * app.getWidth());
                origin = new Point2D(randx, app.getHeight());
                target = new Point2D(randx, 0);
            }
            else if(random == 2)
            {
                int randy = (int) (Math.random() * app.getHeight());
                origin = new Point2D(0, randy);
                target = new Point2D(app.getWidth(), randy);
            }
            else
            {
                int randy = (int) (Math.random() * app.getHeight());
                origin = new Point2D(app.getWidth(), randy);
                target = new Point2D(0, randy);
            }

            FXGL.getApp().getGameWorld().addEntity(EntityFactory.newBasicProjectile(origin, target));
            projectileTimer2.capture();
        }
    }

    @Override
    protected void checkHP()
    {
        double sum = 0;
        for(GameEntity other : others)
            sum += other.getComponentUnsafe(HPComponent.class).getValue();

        System.out.println(sum);

        if(sum <= 1)
        {
            chaser.removeFromWorld();
            gameEntity.removeFromWorld();
            app.bossDeath();
        }
    }

    private GameEntity newOther(double x, double y)
    {
        EntityView entityView = new EntityView(new Rectangle(100, 100, Color.FIREBRICK));
        entityView.setRenderLayer(bottom);

        Enemy4BranchControl control = new Enemy4BranchControl(this);

        GameEntity other =  Entities.builder()
                .at(x, y)
                .type(EntityType.ENEMY)
                .viewFromNodeWithBBox(entityView)
                .with(new HPComponent(150))
                .with(new CollidableComponent(true))
                .build();
        other.addControl(control);

        return other;
    }
    
    private GameEntity newChaser(double x, double y)
    {
        EntityView entityView = new EntityView(new Rectangle(50, 50, Color.GRAY));
        entityView.setRenderLayer(bottom);

        GameEntity other =  Entities.builder()
                .at(x, y)
                .type(EntityType.CHASER)
                .viewFromNodeWithBBox(entityView)
                .with(new CollidableComponent(true))
                .build();

        return other;
    }

    public void branchDeath(GameEntity other)
    {
        others.remove(other);
        updateTexture();
    }

    private void updateTexture()
    {
        Color gray = Color.GRAY;
        EntityView entityView = chaser.getMainViewComponent().getView();
        System.out.println("RED: " + gray.getRed() + " GREEN: " + gray.getGreen() + " BLUE: " + gray.getBlue());

        if (others.size() == 3)
        {
            entityView = new EntityView(new Rectangle(50, 50, gray.darker()));
        }
        else if(others.size() == 2)
        {
            entityView = new EntityView(new Rectangle(50, 50, gray.darker().darker()));
        }
        else if(others.size() == 1)
        {
            entityView = new EntityView(new Rectangle(50, 50, gray.darker().darker().darker()));
        }
        else
        {
            entityView = new EntityView();
        }
        chaser.setView(entityView);

        //sound from Tim Gormly on freesound.org under creative commons
        app.getAudioPlayer().playSound("boss_signal.aiff");
    }
}

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.lukelavin.orbit.RenderLayers.bottom;

/**
 * Created by lukel on 4/26/2017.
 */
public class Enemy5Control extends AbstractEnemyControl
{
    private List<GameEntity> others;
    private GameEntity chaser;

    private LocalTimer modeTimer;
    private String fireMode;
    private final String[] fireModes = {"BASIC", "CONFUSE", "RAPID"};

    private LocalTimer rapidTimer;

    @Override
    public void onAdded(Entity entity)
    {
        super.onAdded(entity);

        others = new ArrayList<GameEntity>();
        others.add(newOther(50, 50));
        others.add(newOther(50, app.getHeight() - 200));
        others.add(newOther(app.getWidth() - 150, 50));
        others.add(newOther(app.getWidth() - 150, app.getHeight() - 200));
        for(GameEntity other : others)
        {
            app.getGameWorld().addEntity(other);
            other.getControlUnsafe(Enemy5BranchControl.class).setMode("INVINCIBLE");
        }

        int random = (int) (Math.random() * others.size());
        others.get(random).getControlUnsafe(Enemy5BranchControl.class).setMode("VULNERABLE");

        chaser = newChaser(app.getWidth() / 2 - 25, app.getHeight() / 2 - 25);
        app.getGameWorld().addEntity(chaser);

        app.initBossHealth(1000);

        fireMode = fireModes[0];
        modeTimer = FXGL.newLocalTimer();
        modeTimer.capture();

        rapidTimer = FXGL.newLocalTimer();
        rapidTimer.capture();
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        if(modeTimer.elapsed(Duration.seconds(5)))
        {
            int randomIndex = (int) (Math.random() * fireModes.length);
            fireMode = fireModes[randomIndex];
            modeTimer.capture();
        }

        if(others.size() == 1)
            fireMode = "RAPID";

        if(fireMode.equals("BASIC"))
        {
            if(projectileTimer.elapsed(Duration.seconds(1)))
            {
                app.getGameWorld().addEntity(EntityFactory.newBasicProjectile(chaser.getCenter(), findDestinationEdge(chaser.getCenter(), player().getCenter())));
                projectileTimer.capture();
            }
        }
        else if(fireMode.equals("CONFUSE"))
        {
            if(projectileTimer.elapsed(Duration.seconds(2)))
            {
                app.getGameWorld().addEntity(EntityFactory.newConfuseProjectile(chaser.getCenter(), findDestinationEdge(chaser.getCenter(), player().getCenter())));
                projectileTimer.capture();
            }
        }
        else if(fireMode.equals("RAPID")){
            if(projectileTimer.elapsed(Duration.seconds(1))) {
                if (rapidTimer.elapsed(Duration.millis(150)))
                {
                    app.getGameWorld().addEntity(EntityFactory.newBasicProjectile(chaser.getCenter(), findDestinationEdge(chaser.getCenter(), player().getCenter())));
                    rapidTimer.capture();
                }
            }
            if(projectileTimer.elapsed(Duration.seconds(2))){
                projectileTimer.capture();
            }
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
        EntityView entityView = new EntityView(new Rectangle(50, 50, Color.FIREBRICK));
        entityView.setRenderLayer(bottom);

        Enemy5BranchControl control = new Enemy5BranchControl(this);

        GameEntity other =  Entities.builder()
                .at(x, y)
                .type(EntityType.ENEMY)
                .viewFromNodeWithBBox(entityView)
                .with(new HPComponent(250))
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

    public void nextPhase(GameEntity trigger)
    {
        if(others.size() > 1)
        {
            //select a random branch to be vulnerable (as long as it wasn't the previously vulnerable branch
            int random = (int) (Math.random() * others.size());
            while (others.get(random) == trigger)
            {
                random = (int) (Math.random() * others.size());
            }
            others.get(random).getControlUnsafe(Enemy5BranchControl.class).setMode("VULNERABLE");

            //set all other branches to be invincible
            for (int i = 0; i < others.size(); i++)
            {
                if (i != random)
                    others.get(i).getControlUnsafe(Enemy5BranchControl.class).setMode("INVINCIBLE");
            }

            app.getAudioPlayer().playSound("boss_signal.aiff");
        }
    }

    public void branchDeath(GameEntity other)
    {
        nextPhase(other);
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

    public List<GameEntity> getOthers()
    {
        return others;
    }
}

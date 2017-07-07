package com.lukelavin.orbit.control.enemy;

import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.EntityView;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.time.LocalTimer;
import com.lukelavin.orbit.OrbitApp;
import com.lukelavin.orbit.component.HPComponent;
import com.lukelavin.orbit.type.EntityType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.lukelavin.orbit.RenderLayers.bottom;

public abstract class AbstractEnemyControl extends AbstractControl
{
    protected GameEntity gameEntity;
    protected OrbitApp app = (OrbitApp) FXGL.getApp();

    protected EntityView initial;

    protected LocalTimer projectileTimer;

    protected GameEntity player()
    {
        return (GameEntity) FXGL.getApp().getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0);
    }

    protected double hp()
    {
        return gameEntity.getComponentUnsafe(HPComponent.class).getValue();
    }

    @Override
    public void onAdded(Entity entity)
    {
        gameEntity = (GameEntity) entity;
        projectileTimer = FXGL.newLocalTimer();
        projectileTimer.capture();

        initial = gameEntity.getView();
        Rectangle initialRectangle = (Rectangle) initial.getNodes().get(0);
        Color initialColor = (Color) initialRectangle.getFill();
        EntityView newView = new EntityView(new Rectangle(initialRectangle.getWidth(), initialRectangle.getHeight(), initialColor));
        newView.setRenderLayer(bottom);
        initial = newView;
    }

    public abstract void onUpdate(Entity entity, double v);

    public void takeDamage(double damage)
    {
        setHP(hp() - damage);
        checkHP();

        Rectangle initialRectangle = (Rectangle) initial.getNodes().get(0);
        Color initialColor = (Color) initialRectangle.getFill();
        EntityView newView = new EntityView(new Rectangle(initialRectangle.getWidth(), initialRectangle.getHeight(), initialColor.darker()));
        newView.setRenderLayer(bottom);

        if (!newView.equals(gameEntity.getView()))
            gameEntity.getMainViewComponent().setView(newView);

        //sound from Tim Gormly on freesound.org under creative commons
        FXGL.getApp().getAudioPlayer().playSound("boss_hit_sound.aiff");
    }

    public void resetTexture()
    {
        if(hp() > 0)
        {

            Rectangle initialRectangle = (Rectangle) initial.getNodes().get(0);
            Color initialColor = (Color) initialRectangle.getFill();
            EntityView newView = new EntityView(new Rectangle(initialRectangle.getWidth(), initialRectangle.getHeight(), initialColor));
            newView.setRenderLayer(bottom);
            if (!newView.equals(gameEntity.getView()))
                gameEntity.getMainViewComponent().setView(newView);
        }
    }

    public void setHP(double hp)
    {
        gameEntity.getComponentUnsafe(HPComponent.class).setValue(hp);
    }

    protected void checkHP(){
        if(hp() <= 0)
        {
            gameEntity.removeFromWorld();
            app.bossDeath();
        }
    }

    protected Point2D findDestinationEdge(Point2D origin, Point2D target)
    {
        double slope = (target.getY() - origin.getY()) / (target.getX() - origin.getX());

        //store the dimensions of the screen for convenience
        double maxX = app.getWidth();
        double maxY = app.getHeight();

        //if the slope is less than the diagonal slope across the screen
        if(slope < maxY / maxX) //then the target will be right or left edges
        {
            //if the target is farther right than the origin
            if(target.getX() > origin.getX()) //then the end target is on the right edge
            {
                /*
                find where the target curve intersects with the right wall
                using the slope, points, and a bit of algebra

                y - originY = slope(edgeX - origin x)
                solve for y, you know the x
                */
                double y = slope * (maxX - origin.getX()) + origin.getY();
                return new Point2D(maxX, y);
            }
            //if the target is farther left than the origin
            else // //then the end target is on the left edge
            {
                double y = slope * (0 - origin.getX()) + origin.getY();
                return new Point2D(0, y);
            }
        }
        else //target will be top or bottom edges if slope is less than the diagonal slope
        {
            if(target.getY() > origin.getY()) //target on bottom edge
            {
                /*
                find where the target curve intersects with the right wall
                using the slope, points, and a bit of algebra
                */
                double x = (maxY - origin.getY()) / slope + origin.getX();
                return new Point2D(x, maxY);
            }
            else //target on top edge
            {
                double x = (0 - origin.getY()) / slope + origin.getX();
                return new Point2D(x, 0);
            }
        }
    }
}

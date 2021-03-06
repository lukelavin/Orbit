package com.lukelavin.orbit.control;

import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.GameEntity;
import com.lukelavin.orbit.EntityFactory;
import com.lukelavin.orbit.component.ConfuseComponent;
import com.lukelavin.orbit.component.DamageComponent;
import com.lukelavin.orbit.component.HPComponent;
import com.lukelavin.orbit.component.RangeComponent;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.lukelavin.orbit.Config.*;

public class PlayerControl extends AbstractControl
{
    private GameEntity gameEntity;

    private double speed;
    private Point2D direction;
    private double orbitalSpeed;

    private double angle;
    private List<GameEntity> orbitals;

    private double range(){
        return gameEntity.getComponentUnsafe(RangeComponent.class).getValue();
    }
    private double damage(){
        return gameEntity.getComponentUnsafe(DamageComponent.class).getValue();
    }
    private HPComponent HP(){ return gameEntity.getComponentUnsafe(HPComponent.class);}

    @Override
    public void onAdded(Entity entity)
    {
        gameEntity = (GameEntity) entity;
        direction = new Point2D(0, 0);
        speed = DEFAULT_SPEED;
        orbitalSpeed = DEFAULT_SPEED;

        angle = 0;
        orbitals = new ArrayList<GameEntity>();
        addOrbital();
        addOrbital();
        addOrbital();
    }

    Color[] orbitalColors = {Color.ORANGE, Color.LIMEGREEN, Color.LIGHTSEAGREEN, Color.YELLOW, Color.PURPLE, Color.INDIANRED, Color.FORESTGREEN, Color.DARKKHAKI, Color.VIOLET, Color.SKYBLUE};
    public void addOrbital(){
        GameEntity orbital = EntityFactory.newOrbital(gameEntity.getX() + 10 + TILE_SIZE * Math.cos(0), gameEntity.getY() + 10 + TILE_SIZE * Math.sin(0), //x and y
                                                        orbitalColors[orbitals.size() % orbitalColors.length], damage()); //color and size
        orbitals.add(orbital);
        FXGL.getApp().getGameWorld().addEntity(orbital);
    }

    public void removeOrbital(){
        GameEntity orbital = orbitals.get(orbitals.size() - 1);
        orbital.removeFromWorld();
        orbitals.remove(orbital);
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        angle += 1.5 + (orbitalSpeed - Math.signum(orbitalSpeed) *5) / 3;
        updateOrbitals();
        move();

        ConfuseComponent confuseComponent = gameEntity.getComponentUnsafe(ConfuseComponent.class);
        if(confuseComponent != null)
        {
            if(confuseComponent.getConfuseTimer().elapsed(Duration.millis(1000)))
                gameEntity.removeComponent(ConfuseComponent.class);

            Label notifier = confuseComponent.getNotifier();
            notifier.setTranslateX(gameEntity.getX() - 10);
            notifier.setTranslateY(gameEntity.getY() - 20);
        }
    }

    public void updateOrbitals()
    {
        Point2D center = new Point2D(gameEntity.getX() + gameEntity.getWidth() / 2, gameEntity.getY() + gameEntity.getHeight() / 2);
        for(int i = 1; i <= orbitals.size(); i++)
        {
            GameEntity current = orbitals.get(i - 1);
            current.getPositionComponent().setX(center.getX() + range() * Math.cos(((360 / (orbitals.size() * 1.0 / i)) + angle) * (Math.PI / 180)) - current.getWidth() / 2);
            current.getPositionComponent().setY(center.getY() + range() * Math.sin(((360 / (orbitals.size() * 1.0 / i)) + angle) * (Math.PI / 180)) - current.getHeight() / 2);

            //rotate the orbital itself 3 times as fast as they orbit
            current.setRotation(angle * -3);
        }
    }

    public void up()
    {
        if(gameEntity.getY() > speed)
            direction = new Point2D(direction.getX(), -speed);
    }
    public void down()
    {
        if(gameEntity.getY() < FXGL.getApp().getHeight() - speed - TILE_SIZE * 2 - 50)
            direction = new Point2D(direction.getX(), speed);
    }
    public void left()
    {
        if(gameEntity.getX() > speed)
            direction = new Point2D(-speed, direction.getY());
    }
    public void right()
    {
        if(gameEntity.getX() < FXGL.getApp().getWidth() - speed - TILE_SIZE * 2)
            direction = new Point2D(speed, direction.getY());
    }

    private void move(){
        double absX = Math.abs(direction.getX());
        double absY = Math.abs(direction.getY());

        double maxLength = Math.max(absX, absY);
        if(maxLength != 0 && absX == maxLength && absY == maxLength)
            direction = direction.multiply(1 / Math.sqrt(2));

        gameEntity.getPositionComponent().translate(direction);

        direction = new Point2D(0,0);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setRange(double range)
    {
        gameEntity.getComponentUnsafe(RangeComponent.class).setValue(range);
    }

    public double getRange()
    {
        return gameEntity.getComponentUnsafe(RangeComponent.class).getValue();
    }

    public void setDamage(double damage)
    {
        gameEntity.getComponentUnsafe(DamageComponent.class).setValue(damage);
        growOrbitals();
    }

    public double getDamage()
    {
        return gameEntity.getComponentUnsafe(DamageComponent.class).getValue();
    }

    public void growOrbitals()
    {
        int count = orbitals.size();
        for(int i = orbitals.size() - 1; i >= 0; i--)
        {
            GameEntity current = orbitals.get(i);
            orbitals.remove(current);
            current.removeFromWorld();
        }
        for(int i = 0; i < count; i++)
            addOrbital();
    }

    public double getHP(){
        return HP().getValue();
    }
    public void setHP(double newHP){
        HP().setValue(newHP);
    }

    public double getOrbitalSpeed()
    {
        return orbitalSpeed;
    }

    public void setOrbitalSpeed(double orbitalSpeed)
    {
        this.orbitalSpeed = orbitalSpeed;
    }
}

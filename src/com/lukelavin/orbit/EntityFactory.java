package com.lukelavin.orbit;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.EntityView;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.lukelavin.orbit.component.DamageComponent;
import com.lukelavin.orbit.component.HPComponent;
import com.lukelavin.orbit.component.RangeComponent;
import com.lukelavin.orbit.component.SubTypeComponent;
import com.lukelavin.orbit.control.PopUpControl;
import com.lukelavin.orbit.control.enemy.*;
import com.lukelavin.orbit.control.PlayerControl;
import com.lukelavin.orbit.control.ProjectileControl;
import com.lukelavin.orbit.type.EntityType;
import com.lukelavin.orbit.type.ProjectileType;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.lukelavin.orbit.RenderLayers.*;
import static com.lukelavin.orbit.Config.*;

/**
 * Created by lukel on 3/4/2017.
 */
public class EntityFactory
{
    private static double getWidth(){ return FXGL.getApp().getWidth(); }
    private static double getHeight(){ return FXGL.getApp().getHeight(); }

    public static GameEntity newPlayer(double x, double y)
    {
        EntityView entityView = new EntityView(new Circle(TILE_SIZE, Color.WHITE));
        entityView.setRenderLayer(top);

        return Entities.builder()
                .at(x, y)
                .type(EntityType.PLAYER)
                .with(new RangeComponent(100))
                .with(new DamageComponent(15))
                .with(new HPComponent(5))
                .with(new PlayerControl())
                .with(new CollidableComponent(true))
                .viewFromNodeWithBBox(entityView)
                .build();
    }

    public static GameEntity newOrbital(double x, double y, Color color, double size)
    {
        EntityView entityView = new EntityView(new Rectangle(size, size,  color));
        entityView.setRenderLayer(middle);

        return Entities.builder()
                .at(x, y)
                .type(EntityType.ORBITAL)
                .viewFromNodeWithBBox(entityView)
                .with(new CollidableComponent(true))
                .build();
    }

    public static GameEntity newBasicProjectile(Point2D origin, Point2D target)
    {
        EntityView entityView = new EntityView(new Rectangle(15, 15, Color.CRIMSON));
        entityView.setRenderLayer(bottom);

        ProjectileControl projectileControl = new ProjectileControl();
        projectileControl.setTarget(target);

        GameEntity projectile = Entities.builder()
                                    .at(origin.getX(), origin.getY())
                                    .type(EntityType.PROJECTILE)
                                    .with(new SubTypeComponent(ProjectileType.BASIC))
                                    .viewFromNodeWithBBox(entityView)
                                    .with(new DamageComponent(1))
                                    .with(projectileControl)
                                    .with(new CollidableComponent(true))
                                    .build();

        return projectile;
    }

    public static GameEntity newConfuseProjectile(Point2D origin, Point2D target)
    {
        EntityView entityView = new EntityView(new Rectangle(15, 15, Color.MEDIUMSLATEBLUE));
        entityView.setRenderLayer(bottom);

        ProjectileControl projectileControl = new ProjectileControl();
        projectileControl.setTarget(target);

        GameEntity projectile = Entities.builder()
                .at(origin.getX(), origin.getY())
                .type(EntityType.PROJECTILE)
                .with(new SubTypeComponent(ProjectileType.CONFUSE))
                .viewFromNodeWithBBox(entityView)
                .with(new DamageComponent(1))
                .with(projectileControl)
                .with(new CollidableComponent(true))
                .build();

        return projectile;
    }

//    public static GameEntity newProjectile(Point2D origin, Point2D target, Point2D... checkpoints)
//    {
//        EntityView entityView = new EntityView(new Rectangle(3, 3, Color.CRIMSON));
//        entityView.setRenderLayer(bottom);
//
//        ProjectileControl projectileControl = new ProjectileControl();
//        projectileControl.setTarget(target);
//        projectileControl.setChekpoints(checkpoints);
//
//        GameEntity projectile = Entities.builder()
//                .at(origin.getX(), origin.getY())
//                .type(EntityType.PROJECTILE)
//                .viewFromNode(entityView)
//                .with(new DamageComponent(1))
//                .with(projectileControl)
//                .with(new CollidableComponent(true))
//                .build();
//
//        return projectile;
//    }

    public static GameEntity newTutorialBoss()
    {
        EntityView entityView = new EntityView(new Rectangle(200, 200, Color.FIREBRICK));
        entityView.setRenderLayer(bottom);

        System.out.println(entityView.getNodes().get(0));

        return Entities.builder()
                .at(getWidth() * 3 / 4 - 100, getHeight() / 2 - 100)
                .type(EntityType.ENEMY)
                .viewFromNodeWithBBox(entityView)
                .with(new HPComponent(150))
                .with(new TutorialEnemyControl())
                .with(new CollidableComponent(true))
                .build();
    }

    public static GameEntity newBoss1()
    {
        EntityView entityView = new EntityView(new Rectangle(100, 100, Color.FIREBRICK));
        entityView.setRenderLayer(bottom);

        return Entities.builder()
                .at(getWidth() / 2 - 50, getHeight() / 2 - 50)
                .type(EntityType.ENEMY)
                .viewFromNodeWithBBox(entityView)
                .with(new HPComponent(300))
                .with(new Enemy1Control())
                .with(new CollidableComponent(true))
                .build();
    }

    public static GameEntity newBoss2()
    {
        EntityView entityView = new EntityView(new Rectangle(100, 100, Color.FIREBRICK));
        entityView.setRenderLayer(bottom);

        return Entities.builder()
                .at(getWidth() / 2 - 50, getHeight() / 2 - 50)
                .type(EntityType.ENEMY)
                .viewFromNodeWithBBox(entityView)
                .with(new HPComponent(500))
                .with(new Enemy2Control())
                .with(new CollidableComponent(true))
                .build();
    }

    public static GameEntity newBoss3()
    {
        EntityView entityView = new EntityView(new Rectangle(100, 100, Color.FIREBRICK));
        entityView.setRenderLayer(bottom);

        return Entities.builder()
                .at(getWidth() / 2 - 50, getHeight() / 2 - 50)
                .type(EntityType.ENEMY)
                .viewFromNodeWithBBox(entityView)
                .with(new HPComponent(450))
                .with(new Enemy3Control())
                .with(new CollidableComponent(true))
                .build();
    }

    public static GameEntity newBoss4()
    {
        EntityView entityView = new EntityView(new Rectangle(50, 50, Color.FIREBRICK));
        entityView.setRenderLayer(bottom);

        return Entities.builder()
                .at(-1000, -1000)
                .type(EntityType.ENEMY)
                .viewFromNodeWithBBox(entityView)
                .with(new HPComponent(1))
                .with(new Enemy4Control())
                .with(new CollidableComponent(true))
                .build();
    }

    public static GameEntity newBoss5()
    {
        EntityView entityView = new EntityView(new Rectangle(50, 50, Color.FIREBRICK));
        entityView.setRenderLayer(bottom);

        return Entities.builder()
                .at(-1000, -1000)
                .type(EntityType.ENEMY)
                .viewFromNodeWithBBox(entityView)
                .with(new HPComponent(1))
                .with(new Enemy5Control())
                .with(new CollidableComponent(true))
                .build();
    }


    public static GameEntity newPopUp(String text, Duration duration)
    {
        PopUpControl popUpControl = new PopUpControl(text, duration);

        return Entities.builder()
                .at(0, 0)
                .type(EntityType.UTIL)
                .with(popUpControl)
                .build();
    }

    public static GameEntity newBackground()
    {
        EntityView entityView = new EntityView(new Rectangle(2000, 2000));
        entityView.setRenderLayer(background);
        return  Entities.builder()
                .at(0, 0)
                .viewFromNode(entityView)
                .build();
    }

    // I used this method to mark the path of my orbitals when testing.
//    public static GameEntity newPixel(double x, double y){
//        return Entities.builder()
//                .at(x, y)
//                .viewFromNode(new Rectangle(1, 1, Color.BLACK))
//                .build();
//    }
}

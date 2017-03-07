package com.lukelavin.orbit;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.EntityView;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.lukelavin.orbit.component.DamageComponent;
import com.lukelavin.orbit.component.HPComponent;
import com.lukelavin.orbit.component.RangeComponent;
import com.lukelavin.orbit.control.EnemyControl;
import com.lukelavin.orbit.control.PlayerControl;
import com.lukelavin.orbit.type.EntityType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.lukelavin.orbit.RenderLayers.*;
import static com.lukelavin.orbit.Config.*;

/**
 * Created by lukel on 3/4/2017.
 */
public class EntityFactory
{
    public static GameEntity newPlayer(double x, double y)
    {
        EntityView entityView = new EntityView(new Circle(TILE_SIZE, Color.WHITE));
        entityView.setRenderLayer(top);

        return Entities.builder()
                .at(x, y)
                .type(EntityType.PLAYER)
                .with(new RangeComponent(80))
                .with(new DamageComponent(15))
                .with(new HPComponent(6))
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

    public static GameEntity newTutorialBoss(double x, double y)
    {
        EntityView entityView = new EntityView(new Rectangle(100, 100, Color.FIREBRICK));
        entityView.setRenderLayer(bottom);

        return Entities.builder()
                .at(x, y)
                .type(EntityType.ENEMY)
                .viewFromNodeWithBBox(entityView)
                .with(new HPComponent(10000))
                .with(new EnemyControl())
                .with(new CollidableComponent(true))
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

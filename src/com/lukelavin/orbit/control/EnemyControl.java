package com.lukelavin.orbit.control;

import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.EntityView;
import com.almasb.fxgl.entity.GameEntity;
import com.lukelavin.orbit.component.HPComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.lukelavin.orbit.RenderLayers.bottom;

/**
 * Created by lukel on 3/6/2017.
 */
public class EnemyControl extends AbstractControl
{
    GameEntity gameEntity;
    EntityView initial;

    private double hp()
    {
        return gameEntity.getComponentUnsafe(HPComponent.class).getValue();
    }

    @Override
    public void onAdded(Entity entity)
    {
        gameEntity = (GameEntity) entity;
        initial = gameEntity.getMainViewComponent().getView();
        System.out.println(initial.getNodes().get(0));
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        System.out.println(hp());
    }

    private void checkHP(){
        if(hp() <= 0)
        {
            System.out.println(hp() + "true");
            gameEntity.removeFromWorld();
        }
    }

    public void takeDamage(double damage)
    {
        setHP(hp() - damage);

        EntityView newView = new EntityView();

        if(!initial.equals(gameEntity.getView()))
        {
            Rectangle initialRectangle = (Rectangle) initial.getNodes().get(0);
            Color initialColor = (Color) initialRectangle.getFill();
            newView = new EntityView(new Rectangle(initialRectangle.getWidth(), initialRectangle.getHeight(), initialColor));
            newView.setRenderLayer(bottom);
        }

        gameEntity.getMainViewComponent().setView(newView, true);

        checkHP();
    }

    public void setHP(double hp)
    {
        gameEntity.getComponentUnsafe(HPComponent.class).setValue(hp);
    }

    public void resetTexture()
    {
        System.out.println("!!!!!!!");
        System.out.println((Rectangle) initial.getNodes().get(0));
        gameEntity.getMainViewComponent().setView(initial, true);
    }
}

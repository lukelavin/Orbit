package com.lukelavin.orbit.control.enemy;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.EntityView;
import com.lukelavin.orbit.EntityFactory;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Created by lukel on 3/6/2017.
 */
public class Enemy1Control extends AbstractEnemyControl
{
    private Label tip;

    @Override
    public void onAdded(Entity entity)
    {
        super.onAdded(entity);

        tip = new Label("This one's a bit harder.");
        tip.setTextFill(Color.WHITE);
        tip.setTranslateX(200);
        tip.setTranslateY(200);
        app.getGameScene().addUINode(tip);

        app.initBossHealth(300);
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        if(projectileTimer.elapsed(Duration.millis(400))){
            double gameHeight = FXGL.getApp().getHeight();
            double gameWidth = FXGL.getApp().getWidth();

            Point2D origin;
            Point2D target;

            int random = (int) (Math.random() * 4);

            //start the projectile at a random place on an edge, and move directly to the opposite edge
            if(random == 0)
            {
                int x = (int) (Math.random() * gameWidth);
                origin = new Point2D(x, 0);
                target = new Point2D(x, gameHeight);
            }
            else if(random == 1)
            {
                int x = (int) (Math.random() * gameWidth);
                origin = new Point2D(x, gameHeight);
                target = new Point2D(x, 0);
            }
            else if(random == 2)
            {
                int y = (int) (Math.random() * gameHeight);
                origin = new Point2D(0, y);
                target = new Point2D(gameWidth, y);
            }
            else
            {
                int y = (int) (Math.random() * gameHeight);
                origin = new Point2D(gameWidth, y);
                target = new Point2D(0, y);
            }

            FXGL.getApp().getGameWorld().addEntity(EntityFactory.newBasicProjectile(origin, target));
            projectileTimer.capture();
        }
    }

//    @Override
//    public void takeDamage(double damage)
//    {
//        super.takeDamage(damage);
//
//        Rectangle initialRectangle = (Rectangle) initial.getNodes().get(0);
//        Color initialColor = (Color) initialRectangle.getFill();
//        EntityView newView = new EntityView(new Rectangle(initialRectangle.getWidth(), initialRectangle.getHeight(), initialColor.darker()));
//        newView.setRenderLayer(bottom);
//
//        if(!newView.equals(gameEntity.getView()))
//            gameEntity.getMainViewComponent().setView(newView);
//    }
//
//    public void resetTexture()
//    {
//        if(hp() > 0)
//        {
//            System.out.println("resetTexture");
//            System.out.println(initial);
//            System.out.println(initial.getNodes().get(0));
//
//            Rectangle initialRectangle = (Rectangle) initial.getNodes().get(0);
//            Color initialColor = (Color) initialRectangle.getFill();
//            EntityView newView = new EntityView(new Rectangle(initialRectangle.getWidth(), initialRectangle.getHeight(), initialColor));
//            newView.setRenderLayer(bottom);
//            if (!newView.equals(gameEntity.getView()))
//                gameEntity.getMainViewComponent().setView(newView);
//        }
//    }

    @Override
    protected void checkHP(){
        if(hp() <= 0)
        {
            gameEntity.removeFromWorld();
            app.bossDeath();

            System.out.println(app.getGameScene().removeUINode(tip));
            app.getGameScene().removeUINode(tip);
        }
    }
}

package com.lukelavin.orbit.control.enemy;

import com.almasb.ents.Entity;
import com.lukelavin.orbit.EntityFactory;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Created by 1072524 on 4/25/2017.
 */
public class TutorialEnemyControl extends AbstractEnemyControl
{
    private Label tip;
    private boolean tipChanged;

    @Override
    public void onAdded(Entity entity) {
        super.onAdded(entity);
        tip = new Label("Use your orbitals to hit the enemy!" + "\n" +
                        "The key is to get close enough to hit the enemy" + "\n" +
                        "while still staying at a safe distance.");

        tip.setTextFill(Color.WHITE);
        tip.setTranslateX(200);
        tip.setTranslateY(200);
        app.getGameScene().addUINode(tip);

        app.initBossHealth(hp());
    }

    @Override
    public void onUpdate(Entity entity, double v) {
        if(hp() < 100)
        {

            if(projectileTimer.elapsed(Duration.seconds(1)))
            {
                Point2D origin = new Point2D(app.getWidth() / 2, 0);
                app.getGameWorld().addEntity(EntityFactory.newBasicProjectile(origin, findDestinationEdge(origin, player().getPosition())));
                projectileTimer.capture();
            }
        }
    }

    @Override
    protected void checkHP(){
        super.checkHP();
        if(hp() < 100 && !tipChanged)
        {
            app.getGameScene().removeUINode(tip);
            tip = new Label("Good work! Now dodge these. They do damage." + "\n" +
                            "Generally any bad thing is red, although there are some exceptions" + "\n" +
                            "that you'll find for yourself!");

            tip.setTextFill(Color.WHITE);
            tip.setTranslateX(200);
            tip.setTranslateY(200);
            app.getGameScene().addUINode(tip);
            tipChanged = true;
        }
    }
}

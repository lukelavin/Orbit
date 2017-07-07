package com.lukelavin.orbit.component;

import com.almasb.ents.Component;
import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.time.LocalTimer;
import com.lukelavin.orbit.control.PlayerControl;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ConfuseComponent implements Component
{
    private GameEntity player;
    private PlayerControl playerControl;
    private LocalTimer confuseTimer;

    private Label notifier;

    @Override
    public void onAdded(Entity entity)
    {
        player = (GameEntity) entity;
        playerControl = player.getControlUnsafe(PlayerControl.class);

        playerControl.setSpeed(-playerControl.getSpeed());

        confuseTimer = FXGL.newLocalTimer();
        confuseTimer.capture();

        notifier = new Label("CONFUSED");
        notifier.setTextFill(Color.WHITE);
        notifier.setTranslateX(player.getX() - 20);
        notifier.setTranslateY(player.getY() - 20);
        FXGL.getApp().getGameScene().addUINode(notifier);
    }

    @Override
    public void onRemoved(Entity entity)
    {
        playerControl.setSpeed(-playerControl.getSpeed());
        FXGL.getApp().getGameScene().removeUINode(notifier);
    }

    public LocalTimer getConfuseTimer()
    {
        return confuseTimer;
    }

    public Label getNotifier()
    {
        return notifier;
    }
}

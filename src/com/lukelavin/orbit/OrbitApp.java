package com.lukelavin.orbit;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import com.lukelavin.orbit.collision.OrbitalEnemyHandler;
import com.lukelavin.orbit.control.PlayerControl;
import com.lukelavin.orbit.type.EntityType;
import javafx.scene.input.KeyCode;

import static com.lukelavin.orbit.Config.*;

/**
 * Created by lukel on 3/4/2017.
 */
public class OrbitApp extends GameApplication
{
    @Override
    protected void initSettings(GameSettings gameSettings)
    {
        gameSettings.setTitle("orbit");
        gameSettings.setVersion("0.1");

        gameSettings.setWidth(1440);
        gameSettings.setHeight(900);

        gameSettings.setIntroEnabled(false);
        gameSettings.setCloseConfirmation(false);
        gameSettings.setMenuEnabled(false);
        gameSettings.setProfilingEnabled(false);

        gameSettings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    public GameEntity player()
    {
        return (GameEntity) getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0);
    }
    public PlayerControl playerControl()
    {
        return player().getControlUnsafe(PlayerControl.class);
    }

    @Override
    protected void initInput()
    {
        getInput().addAction(new UserAction("UP")
        {
            @Override
            protected void onAction()
            {
                playerControl().up();
            }
        }, UP_KEY);

        getInput().addAction(new UserAction("DOWN")
        {
            @Override
            protected void onAction()
            {
                playerControl().down();
            }
        }, DOWN_KEY);

        getInput().addAction(new UserAction("LEFT")
        {
            @Override
            protected void onAction()
            {
                playerControl().left();
            }
        }, LEFT_KEY);

        getInput().addAction(new UserAction("RIGHT")
        {
            @Override
            protected void onAction()
            {
                playerControl().right();
            }
        }, RIGHT_KEY);


        getInput().addAction(new UserAction("ADDORBITAL")
        {
            @Override
            protected void onActionBegin()
            {
                playerControl().addOrbital();
            }
        }, KeyCode.NUMPAD1);

        getInput().addAction(new UserAction("REMOVEORBITAL")
        {
            @Override
            protected void onActionBegin()
            {
                playerControl().removeOrbital();
            }
        }, KeyCode.NUMPAD0);

        getInput().addAction(new UserAction("ADDRANGE")
        {
            @Override
            protected void onAction()
            {
                playerControl().setRange(playerControl().getRange() + 1);
            }
        }, KeyCode.NUMPAD3);
        getInput().addAction(new UserAction("REMOVERANGE")
        {
            @Override
            protected void onAction()
            {
                playerControl().setRange(playerControl().getRange() - 1);
            }
        }, KeyCode.NUMPAD2);
        getInput().addAction(new UserAction("ADDDAMAGE")
        {
            @Override
            protected void onActionBegin()
            {
                playerControl().setDamage(playerControl().getDamage() + 1);
            }
        }, KeyCode.NUMPAD5);
        getInput().addAction(new UserAction("REMOVEDAMAGE")
        {
            @Override
            protected void onAction()
            {
                playerControl().setDamage(playerControl().getDamage() - 1);
            }
        }, KeyCode.NUMPAD4);
    }

    @Override
    protected void initAssets()
    {

    }

    @Override
    protected void initGame()
    {
        getGameWorld().addEntity(EntityFactory.newBackground());
        getGameWorld().addEntity(EntityFactory.newPlayer(getWidth() / 2, getHeight() / 2));
        getGameWorld().addEntity(EntityFactory.newTutorialBoss(getWidth() / 2 - 50, 0));
    }

    @Override
    protected void initPhysics()
    {
        getPhysicsWorld().addCollisionHandler(new OrbitalEnemyHandler(EntityType.ORBITAL, EntityType.ENEMY));
    }

    @Override
    protected void initUI()
    {

    }

    @Override
    protected void onUpdate(double v)
    {

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

package com.lukelavin.orbit.collision;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.gameplay.GameWorld;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lukelavin.orbit.EntityFactory;
import com.lukelavin.orbit.component.ShieldingComponent;
import com.lukelavin.orbit.component.SubTypeComponent;
import com.lukelavin.orbit.control.PlayerControl;
import com.lukelavin.orbit.type.EntityType;
import com.lukelavin.orbit.type.PickupType;
import javafx.util.Duration;

public class PlayerPickupHandler extends CollisionHandler
{
    public PlayerPickupHandler(EntityType player, EntityType pickup)
    {
        super(player, pickup);
    }
    
    @Override
    protected void onCollisionBegin(Entity player, Entity pickup)
    {
        GameWorld gameWorld = FXGL.getApp().getGameWorld();

        PlayerControl playerControl = player.getControlUnsafe(PlayerControl.class);

        double range = playerControl.getRange();
        double damage = playerControl.getDamage();
        double speed = playerControl.getSpeed();
        double orbitalSpeed = playerControl.getOrbitalSpeed();
        
        PickupType pickupType = (PickupType) pickup.getComponentUnsafe(SubTypeComponent.class).getValue();
        
        if(pickupType == PickupType.RANGE)
        {
            playerControl.setRange(range + 30);
            pickup.removeFromWorld();
            gameWorld.addEntity(EntityFactory.newPopUp("Range Up", Duration.seconds(2)));
        }
        else if(pickupType == PickupType.DAMAGE)
        {
            playerControl.setDamage(damage + 8);
            pickup.removeFromWorld();
            gameWorld.addEntity(EntityFactory.newPopUp("Damage Up", Duration.seconds(2)));
        }
        else if(pickupType == PickupType.SPEED)
        {
            playerControl.setSpeed(speed + 1);
            pickup.removeFromWorld();
            gameWorld.addEntity(EntityFactory.newPopUp("Speed Up", Duration.seconds(2)));
        }
        else if(pickupType == PickupType.ORBITAL_SPEED)
        {
            playerControl.setOrbitalSpeed(orbitalSpeed + 1.5);
            pickup.removeFromWorld();
            gameWorld.addEntity(EntityFactory.newPopUp("Orbital Speed Up", Duration.seconds(2)));
        }
        else if(pickupType == PickupType.SHIELDING)
        {
            player.addComponent(new ShieldingComponent(true));
            pickup.removeFromWorld();
            gameWorld.addEntity(EntityFactory.newPopUp("Shielding Orbitals!", Duration.seconds(3)));
        }
        else if(pickupType == PickupType.ORBITAL)
        {
            playerControl.addOrbital();
            pickup.removeFromWorld();
            gameWorld.addEntity(EntityFactory.newPopUp("Extra Orbital!", Duration.seconds(3)));
        }
        else if(pickupType == PickupType.DOUBLE_ORBITAL)
        {
            playerControl.addOrbital();
            playerControl.addOrbital();
            pickup.removeFromWorld();
            gameWorld.addEntity(EntityFactory.newPopUp("Two Extra Orbitals!", Duration.seconds(3)));
        }

        //sound from Tim Gormly on freesound.org under creative commons
        FXGL.getApp().getAudioPlayer().playSound("pickup_sound.aiff");
    }
}

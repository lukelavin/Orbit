package com.lukelavin.orbit;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.lukelavin.orbit.component.SubTypeComponent;
import com.lukelavin.orbit.type.EntityType;
import com.lukelavin.orbit.type.PickupType;

public class PickupFactory
{
    public static GameEntity newRangePickup(double x, double y)
    {
        return Entities.builder()
                .at(x, y)
                .type(EntityType.PICKUP)
                .with(new SubTypeComponent(PickupType.RANGE))
                .with(new CollidableComponent(true))
                .viewFromTextureWithBBox("range_upgrade.png")
                .build();
    }
    public static GameEntity newDamagePickup(double x, double y)
    {
        return Entities.builder()
                .at(x, y)
                .type(EntityType.PICKUP)
                .with(new SubTypeComponent(PickupType.DAMAGE))
                .with(new CollidableComponent(true))
                .viewFromTextureWithBBox("damage_upgrade.png")
                .build();
    }
    public static GameEntity newSpeedPickup(double x, double y)
    {
        return Entities.builder()
                .at(x, y)
                .type(EntityType.PICKUP)
                .with(new SubTypeComponent(PickupType.SPEED))
                .with(new CollidableComponent(true))
                .viewFromTextureWithBBox("speed_upgrade.png")
                .build();
    }
    public static GameEntity newOrbitalSpeedPickup(double x, double y)
    {
        return Entities.builder()
                .at(x, y)
                .type(EntityType.PICKUP)
                .with(new SubTypeComponent(PickupType.ORBITAL_SPEED))
                .with(new CollidableComponent(true))
                .viewFromTextureWithBBox("orbital_speed_upgrade.png")
                .build();
    }
    public static GameEntity newShieldingPickup(double x, double y)
    {
        return Entities.builder()
                .at(x, y)
                .type(EntityType.PICKUP)
                .with(new SubTypeComponent(PickupType.SHIELDING))
                .with(new CollidableComponent(true))
                .viewFromTextureWithBBox("shield_upgrade.png")
                .build();
    }
    public static GameEntity newExtraOrbitalPickup(double x, double y)
    {
        return Entities.builder()
                .at(x, y)
                .type(EntityType.PICKUP)
                .with(new SubTypeComponent(PickupType.ORBITAL))
                .with(new CollidableComponent(true))
                .viewFromTextureWithBBox("extra_orbital_upgrade.png")
                .build();
    }
    public static GameEntity newTwoExtraOrbitalPickup(double x, double y)
    {
        return Entities.builder()
                .at(x, y)
                .type(EntityType.PICKUP)
                .with(new SubTypeComponent(PickupType.DOUBLE_ORBITAL))
                .with(new CollidableComponent(true))
                .viewFromTextureWithBBox("two_extra_orbital_upgrade.png")
                .build();
    }

}

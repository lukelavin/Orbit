package com.lukelavin.orbit.component;

import com.almasb.ents.component.DoubleComponent;

/**
 * Created by lukel on 3/5/2017.
 */
public class DamageComponent extends DoubleComponent
{
    public DamageComponent(double initialValue)
    {
        super(initialValue);
    }

    public DamageComponent()
    {
        setValue(10);
    }
}

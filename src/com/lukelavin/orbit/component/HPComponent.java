package com.lukelavin.orbit.component;

import com.almasb.ents.component.DoubleComponent;

import static com.lukelavin.orbit.Config.TILE_SIZE;

/**
 * Created by lukel on 3/5/2017.
 */
public class HPComponent extends DoubleComponent
{
    public HPComponent(double initialValue)
    {
        super(initialValue);
    }

    public HPComponent()
    {
        setValue(6);
    }
}

package com.lukelavin.orbit.component;

import com.almasb.ents.component.DoubleComponent;

import static com.lukelavin.orbit.Config.TILE_SIZE;

/**
 * Created by lukel on 3/5/2017.
 */
public class RangeComponent extends DoubleComponent
{
    public RangeComponent(double initialValue)
    {
        super(initialValue);
    }

    public RangeComponent()
    {
        setValue(TILE_SIZE * 4);
    }
}

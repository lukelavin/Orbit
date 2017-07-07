package com.lukelavin.orbit.component;

import com.almasb.ents.component.DoubleComponent;

public class HPComponent extends DoubleComponent
{
    private double initialHP;

    public HPComponent(double initialValue)
    {
        super(initialValue);
        initialHP = initialValue;
    }

    public HPComponent()
    {
        setValue(6);
    }

    public double getInitialHP()
    {
        return initialHP;
    }
}

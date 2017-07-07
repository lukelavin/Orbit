package com.lukelavin.orbit.component;
import com.almasb.ents.component.DoubleComponent;
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

package com.lukelavin.orbit;

import com.almasb.fxgl.entity.RenderLayer;

public class RenderLayers
{
    public static RenderLayer background = new RenderLayer()
    {
        @Override
        public String name()
        {
            return "background";
        }

        @Override
        public int index()
        {
            return 0;
        }
    };
    public static RenderLayer bottom = new RenderLayer()
    {
        @Override
        public String name()
        {
            return "bottom";
        }

        @Override
        public int index()
        {
            return 1;
        }
    };
    public static RenderLayer middle = new RenderLayer()
    {
        @Override
        public String name()
        {
            return "middle";
        }

        @Override
        public int index()
        {
            return 50;
        }
    };
    public static RenderLayer top = new RenderLayer()
    {
        @Override
        public String name()
        {
            return "top";
        }

        @Override
        public int index()
        {
            return 100;
        }
    };
}

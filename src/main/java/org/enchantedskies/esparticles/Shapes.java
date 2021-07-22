package org.enchantedskies.esparticles;

import org.enchantedskies.esparticles.shapesdata.*;

public enum Shapes {
    HELIX(Helix.class), WINGS(Wings.class), POINT(Point.class), HALO(Halo.class), WISP(Wisp.class), HEART(Heart.class), FAIRY_WINGS(FairyWings.class), EARS(Ears.class);

    private Class<? extends Shape> shapeClass;

    Shapes(Class<? extends Shape> shapeClass) {
        this.shapeClass = shapeClass;
    }

    public Class<? extends Shape> getShapeClass() {
        return shapeClass;
    }
}

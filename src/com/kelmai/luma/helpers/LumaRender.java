package com.kelmai.luma.helpers;

import net.minecraft.client.renderer.Tessellator;

/**
 * Created with IntelliJ IDEA.
 * User: KelMai
 * Date: 30.08.13
 * Time: 20:23
 */
public class LumaRender {
    public static Tessellator t = Tessellator.instance;

    public void drawSide(int side, double posX, double posY, double posZ, double width, double height, double uvStartX, double uvStartY, double uvEndX, double uvEndY) {
        switch (side) {
            case 0: // Bottom
//                t.addVertexWithUV(0, 0, 0, u, v); //tl
//                t.addVertexWithUV(0, 0, 0, u, V); //bl
//                t.addVertexWithUV(1, 0, 0, U, V); //br
//                t.addVertexWithUV(1, 0, 1, U, v); //
                break;
            case 1: // Top

                break;
            case 2: // North

                break;
            case 3: // South

                break;
            case 4: // West

                break;
            case 5: // East

                break;
        }

    }
}

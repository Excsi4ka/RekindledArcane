package excsi.rekindledarcane.client.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

//todo get rid of global mutable states and make it a basic wrapper
public class RenderHelperWrapper {

    public static boolean blendEnabled;

    public static boolean textureEnabled;

    public static void enableBlend() {
        blendEnabled = GL11.glIsEnabled(GL11.GL_BLEND);
        if(!blendEnabled) {
            GL11.glEnable(GL11.GL_BLEND);
        }
    }

    public static void disableTexture2D() {
        textureEnabled = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
        if(textureEnabled) {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }
    }

    public static void restoreStates() {
        if (!blendEnabled) {
            GL11.glDisable(GL11.GL_BLEND);
        }
        if(textureEnabled) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
    }

    public static void blendMode(BlendMode mode) {
        GL11.glBlendFunc(mode.sFactor, mode.dFactor);
    }

    public static void scissorStart(int x, int y, int width, int height, int scaleFactor) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x * scaleFactor, y * scaleFactor, width * scaleFactor, height * scaleFactor); //up<->down
    }

    public static void scissorEnd() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static void drawFullSizeTexturedRectangle(int x, int y, int width, int height, float zOffset) {
        Tessellator tes = Tessellator.instance;
        tes.startDrawingQuads();
        tes.addVertexWithUV(x + width,y + height,zOffset,1,1);
        tes.addVertexWithUV(x + width,y,zOffset,1,0);
        tes.addVertexWithUV(x,y,zOffset,0,0);
        tes.addVertexWithUV(x,y + height,zOffset,0,1);
        tes.draw();
    }

    public static void drawTexturedRectangle(int x, int y, int width, int height, float zOffset) {
        Tessellator tes = Tessellator.instance;
        tes.startDrawingQuads();
        tes.addVertexWithUV(x + width,y + height,zOffset,1,1);
        tes.addVertexWithUV(x + width,y,zOffset,1,0);
        tes.addVertexWithUV(x,y,zOffset,0,0);
        tes.addVertexWithUV(x,y + height,zOffset,0,1);
        tes.draw();
    }

    public static void drawIcon(int x, int y, int width, int height, float zOffset, IIcon icon) {
        Tessellator tes = Tessellator.instance;
        tes.startDrawingQuads();
        tes.addVertexWithUV(x + width,y + height,zOffset,icon.getMaxU(),icon.getMaxV());
        tes.addVertexWithUV(x + width,y,zOffset,icon.getMaxU(),icon.getMinV());
        tes.addVertexWithUV(x,y,zOffset,icon.getMinU(),icon.getMinV());
        tes.addVertexWithUV(x,y + height,zOffset,icon.getMinU(),icon.getMaxV());
        tes.draw();
    }

    public static void batchDrawIcon(Tessellator tes, int x, int y, int width, int height, float zOffset, IIcon icon) {
        tes.addVertexWithUV(x + width,y + height,zOffset,icon.getMaxU(),icon.getMaxV());
        tes.addVertexWithUV(x + width,y,zOffset,icon.getMaxU(),icon.getMinV());
        tes.addVertexWithUV(x,y,zOffset,icon.getMinU(),icon.getMinV());
        tes.addVertexWithUV(x,y + height,zOffset,icon.getMinU(),icon.getMaxV());
    }

    public static void drawCooldownSquare(int middleX, int middleY, int squareRadius, float progress) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator tes = Tessellator.instance;
        tes.startDrawing(GL11.GL_TRIANGLE_FAN);
        tes.addVertex(middleX, middleY, 0);


        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void drawTestGradientSquare(int x, int y, int width, int height, int z) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tes = Tessellator.instance;
        tes.startDrawingQuads();
        tes.setColorOpaque(0,255,0);
        tes.addVertex(x + width,y + height,z);
        tes.setColorOpaque(0,0,255);
        tes.addVertex(x + width,y,z);


        tes.setColorOpaque(255,255,0);
        tes.addVertex(x,y,z);

        tes.setColorOpaque(255,255,0);
        tes.addVertex(x,y + height,z);
        tes.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

}

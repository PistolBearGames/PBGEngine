/** 
 * GlowAnimation Class
 * @author PistolBear
 * 
 * Makes sprites appear to have a glowing effect behind them.  If Start/End scales are the same,
 * has a constant glow around the sprite.
 * 
 * Version 0.0 - Psuedocode only.  Vector below is a placeholder for the path object that will follow the outside of the texture.
 */
package pbgames.pbgengine;

import java.util.Vector;
import android.graphics.ColorFilter;


public class GlowAnimation extends Animation {
    private ColorFilter p_glowColor;
    private Vector p_glowVector;
    
    /**
     * Glow Animation
     * @param startScale
     * @param endScale
     * @param speed
     */
    public GlowAnimation(float startScale, float endScale, float speed) {
        this(startScale, endScale, speed, false);
        animationName = "GlowAnimation";
    }

    /**
     * Glow Animation
     * @param startScale
     * @param endScale
     * @param speed
     * @param repeat
     */
    public GlowAnimation(float startScale, float endScale, float speed, 
            boolean repeat) {
    	// Currently does nothing.
    }
    
    @Override
    public Texture adjustGlow(Texture original) {
    	return original;
    }

}

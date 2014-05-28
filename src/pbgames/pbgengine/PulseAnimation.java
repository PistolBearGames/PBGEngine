/** 
 * PulseAnimation Class
 * @author PistolBear
 * 
 * Useful for making things get momentarily bigger, either to show interaction or as part of 
 * background animation (pulsing hearts, etc.)
 */
package pbgames.pbgengine;
import android.renderscript.Float2;

public class PulseAnimation extends Animation {
    private float p_startScale, p_endScale, p_speed;
    private boolean p_started, p_repeat;
    
    /**
     * Pulse Animation
     * @param startScale
     * @param endScale
     * @param speed
     */
    public PulseAnimation(float startScale, float endScale, float speed) {
        this(startScale, endScale, speed, false);
    }

    /**
     * Pulse Animation
     * @param startScale
     * @param endScale
     * @param speed
     * @param repeat
     */
    public PulseAnimation(float startScale, float endScale, float speed, 
            boolean repeat) { 
    	animationName = "PulseAnimation";
        p_started = false;
        animating = true;
        this.p_startScale = startScale;
        this.p_endScale = endScale;
        this.p_speed = speed;
        this.p_repeat = repeat;
    }
    
    @Override
    public Float2 adjustScale(Float2 original) {
        Float2 modified = original;
        if (!p_started) {
            modified.x = p_startScale;
            modified.y = p_startScale;
            p_started = true;
        }
        modified.x += p_speed;
        modified.y += p_speed;
        if (modified.x >= p_endScale)
            p_speed *= -1;
        else if (modified.x <= p_startScale) {
            if (!p_repeat)
                animating = false;
            else
                p_speed *= -1;
        }
        
        super.adjustPosition(new Float2(-original.x, -original.y / 2));

        return modified;
    }

}

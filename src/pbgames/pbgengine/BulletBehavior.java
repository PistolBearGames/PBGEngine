/** 
 * BulletBehavior class - derived from Animation 
 * Requires game.engine.Engine to build.
 */

package pbgames.pbgengine;
import android.renderscript.Float2;

public class BulletBehavior extends Animation {
    private double p_angle;
    private double p_multiplier;
    private double p_velX, p_velY;
    private int p_lifetime;
    private Timer p_timer;
    
    
    public BulletBehavior(double angleDegrees, float speedMultiplier, 
            int lifetime) {
    	animationName = "BulletBehavior";
        animating = true;
        p_lifetime = lifetime;
        p_timer = new Timer();
        p_angle = angleDegrees;
        p_multiplier = speedMultiplier;
        double angleRadians = Math.toRadians(p_angle);
        //calculate X velocity
        p_velX = Math.cos(angleRadians) * p_multiplier;
        //calculate Y velocity
        p_velY = Math.sin(angleRadians) * p_multiplier;
    }
    
    @Override
    public Float2 adjustPosition(Float2 original) {
        Float2 modified = new Float2(original.x,original.y);
        modified.x += p_velX;
        modified.y += p_velY;
        return modified;
    }

    @Override
    public boolean adjustActive(boolean original) {
        boolean modified = original;
        // If passing 0 for lifetime in original CTOR, sprite 
        // does not adjustActive(false), but leaves it as it was.
        if (p_lifetime > 0) {
            if (p_timer.stopwatch(p_lifetime)) {
                modified = false;
            }
        }
        return modified;
    }
}


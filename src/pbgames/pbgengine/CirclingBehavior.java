/** 
 * CirclingBehavior Class
 */
package pbgames.pbgengine;
import android.renderscript.Float2;

public class CirclingBehavior extends Animation {
    private int p_radius;
    private Float2 p_center;
    private double p_angle;
    private float p_velocity;
    
    public CirclingBehavior(int centerx, int centery, int radius, 
            double angle, float velocity) {
    	animationName = "CirclingBehavior";
        animating = true;
        this.p_center = new Float2(centerx,centery);
        this.p_radius = radius;
        this.p_angle = angle;
        this.p_velocity = velocity;
    }
    
    @Override
    public Float2 adjustPosition(Float2 original) {
        Float2 modified = original;
        p_angle += p_velocity;
        modified.x = (int)(p_center.x + (float)(Math.cos(p_angle) * 
                p_radius));
        modified.y = (int)(p_center.y + (float)(Math.sin(p_angle) * 
                p_radius));
        return modified;
    }

}

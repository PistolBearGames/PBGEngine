/** 
 * CirclingBehavior Class
 * @author PistolBear
 */
package pbgames.pbgengine;
import android.renderscript.Float2;

public class OrbitBehavior extends Animation {
    private int p_radius;
    private Float2 p_center;
    private double p_angle;
    private float p_velocity;
    Sprite p_anchor;
    
    public OrbitBehavior(Float2 center, int radius, 
            double angle, float velocity, Sprite anchor) {
        this((int)center.x, (int)center.y, (int)radius, angle, velocity, anchor);
    }
    
    public OrbitBehavior(int centerx, int centery, int radius, 
            double angle, float velocity, Sprite anchor) {
    	animationName = "OrbitBehavior";
        animating = true;
        p_center = new Float2(centerx,centery);
        p_radius = radius;
        p_angle = angle;
        p_velocity = velocity;
        p_anchor = anchor;
    }
    
    public OrbitBehavior(Sprite anchor, double angle, float velocity) {
        animating = true;
        p_center = anchor.getGlobalCenter();
        p_radius = (int)anchor.getRadius()+30;
        p_angle = angle;
        p_velocity = velocity;
        p_anchor = anchor;
    }
    
    @Override
    public Float2 adjustAnchor(Sprite sprite) {
    	Float2 modified = sprite.getGlobalCenter();
    	return modified;
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

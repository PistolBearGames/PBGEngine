package pbgames.pbgengine;

import android.graphics.Point;
import android.graphics.RectF;
import android.renderscript.Float2;

public class RemovalBehavior extends Animation {
    private RectF p_bounds;
    private Float2 p_velocity;
    private Point p_size;
    private boolean p_active;
    
    public RemovalBehavior(RectF outOfBounds, int w, int h, Float2 velocity) {
        this(outOfBounds, new Point(w,h), velocity);
    }
    
    public RemovalBehavior(RectF outOfBounds, Point size, Float2 velocity) {
    	animationName = "RemovalBehavior";
        animating = true;
        p_bounds = outOfBounds;
        p_velocity = velocity;
        p_size = size;
        p_active = true;
    }
    
    @Override
    public Float2 adjustPosition(Float2 original) {
        Float2 modified = original;
        modified.x += p_velocity.x;
        modified.y += p_velocity.y;
        
        if (modified.x + p_size.x < p_bounds.left || modified.x  - p_size.x> p_bounds.right-p_size.x || 
        		modified.y + p_size.y < p_bounds.top || modified.y - p_size.y > p_bounds.bottom-p_size.y){
            animating = false;
            p_active = false;
        }
        
        return modified;
    }
    
    @Override
    public boolean adjustActive(boolean notUsed) {
    	// p_active is adjusted in adjustPosition with this class.  If sprite leaves the bounds,
    	// it is marked inactive, and therefore removed.
    	return p_active;
    }
}

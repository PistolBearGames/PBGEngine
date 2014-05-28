/** 
 * FenceBehavior Class
 */
package pbgames.pbgengine;
import android.graphics.RectF;
import android.renderscript.Float2;

public class FenceBehavior extends Animation {
    private RectF p_fence;
    
    public FenceBehavior(RectF fence) {
        p_fence = fence;
        animating = true; 
        animationName = "FenceBehavior";
    }
    
    @Override
    public Float2 adjustPosition(Float2 original) {
        Float2 modified = original;
        
        if (modified.x < p_fence.left)
            modified.x = p_fence.left+1;
        else if (modified.x > p_fence.right)
            modified.x = p_fence.right-1;
        if (modified.y < p_fence.top)
            modified.y = p_fence.top+1;
        else if (modified.y > p_fence.bottom)
            modified.y = p_fence.bottom-1;

        return modified;
    }
    
}

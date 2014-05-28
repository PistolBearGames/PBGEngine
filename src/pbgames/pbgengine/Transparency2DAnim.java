/** 
 * Transparency2DAnim Class 
 * @author PistolBear
 */

package pbgames.pbgengine;

import pbgames.pbgengine.Animation;

public class Transparency2DAnim extends Animation {
    private int p_minAlpha;
    private int p_maxAlpha;
    private int p_change;
    
    /**
     * Transparency2DAnim (Alpha settings)
     * @param minAlpha
     * @param maxAlpha
     * @param change
     */
    public Transparency2DAnim(int minAlpha, int maxAlpha, int change) {
    	animationName = "Transparency2DAnim";
        this.p_minAlpha = minAlpha;
        this.p_maxAlpha = maxAlpha;
        this.p_change = change;
        animating = true;
    }
    
    @Override
    public int adjustTransparency(int original) {
        int modified = original;
        modified += p_change;
        if (modified < p_minAlpha) {
            modified = p_minAlpha;
            animating = false;
        }
        if (modified > p_maxAlpha) {
            modified = p_maxAlpha;
            animating = false;
        }
        return modified;
    }

}

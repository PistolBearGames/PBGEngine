/** 
 * PBGEngine SpinAnimation Class
 * 
 * Spins things around their own origin indefinitely.
 */
package pbgames.pbgengine;

public class SpinAnimation extends Animation {
    private float p_angleDist, p_velocity;
    
    public SpinAnimation(float velocity) {
    	animationName = "SpinAnimation";
        animating = true;
        this.p_velocity = velocity;
        this.p_angleDist = 0.0f;
    }
    
    @Override
    public float adjustRotation(float original) {
        float modified = original;
        float fullCircle = (float)(2.0 * Math.PI);
        p_angleDist += p_velocity;
        // Uncomment the next lines if you want it to spin just once.
//        if (p_angleDist > fullCircle)
//            animating = false;
        modified += p_velocity;
        return modified;
    }

}

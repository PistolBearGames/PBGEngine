/** 
 * Sprite Animation Class 
 * 
 * Uses the pbgames.pbgengine package.
 * Creates dummy functions to be overridden in respective Animation or Behavior classes.
 * See the Sprite.java file and the following list of special Animations and Behaviors:
 * TODO: Update this list as necessary.
 * 
 * BoundsBehavior.java
 * FrameAnimation.java
 * GlowAnimation.java
 * ImageColorManager.java
 * OrbitBehavior.java
 * PulseAnimation.java
 * SpinAnimation.java
 * Transparency2DAnim.java
 * BulletBehavior.java
 * 
 * @author PistolBear
 */
package pbgames.pbgengine;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.renderscript.Float2;

public class Animation {
    public boolean animating;
    public String animationName;
    
    public Animation() {
        animating = false;
        animationName = "UninitializedAnimation";
    }
    
    // The following methods do nothing but return the original unless overridden.

    public int adjustFrame(int original) {
        return original;
    }
    
    public int adjustTransparency(int original) {
        return original;
    }
    
    public Float2 adjustScale(Float2 original) {
        return original;
    }
    
    public float adjustRotation(float original) {
        return original;        
    }

    public Float2 adjustPosition(Float2 original) {
        return original;
    }
    
    public Float2 adjustVelocity(Float2 original) {
    	return original;
    }
    
    public boolean adjustActive(boolean original) {
        return original;
    }
    
    public ColorFilter adjustColor(ColorFilter original) {
    	return original;
    }

	public Paint adjustColor(Paint original) {
		return original;
	}
	
	public Texture adjustGlow(Texture original) {
		return original;
	}

    public Float2 adjustAnchor(Sprite original) {
		return original.getGlobalCenter();
    }
}



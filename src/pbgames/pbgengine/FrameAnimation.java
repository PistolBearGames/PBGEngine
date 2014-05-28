/** 
 * FrameAnimation Class 
 */
package pbgames.pbgengine;

public class FrameAnimation extends Animation {
    private int p_firstFrame;
    private int p_lastFrame;
    private int p_direction;
    
    /**
     * FrameAnimation
     * @param firstFrame
     * @param lastFrame
     * @param direction
     */
    public FrameAnimation(int firstFrame, int lastFrame, int direction) {
    	animationName = "FrameAnimation";
        animating = true;
        p_firstFrame = firstFrame;
        p_lastFrame = lastFrame;
        p_direction = direction;
    }

    @Override
    public int adjustFrame(int original) {
        int modified = original + p_direction;
        if (modified < p_firstFrame)
            modified = p_lastFrame;
        else if (modified > p_lastFrame)
            modified = p_firstFrame;
        return modified;
    }

}

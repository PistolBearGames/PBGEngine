/** 
 * Timer Class for Android Game Engine
 * Teach Yourself Android 4.0 Game Programming in 24 Hours
 * Copyright (c)2012 by Jonathan S. Harbour
 * 
 * This class helps with printing lines of text with auto line
 * increment and reusable properties.
 */

package pbgames.pbgengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class TextPrinter {
    private Canvas p_canvas;
    private Paint p_paint;
    private float p_x, p_y;
    private float p_spacing;
    
    public TextPrinter() {
        this(null);
    }
    
    public TextPrinter(Canvas canvas) {
        p_canvas = canvas;
        p_paint = new Paint();
        p_x = p_y = 0;
        p_spacing = 22;
        setTextSize(18);
        setColor(Color.WHITE);
    }
    
    public void setCanvas(Canvas canvas) {
        p_canvas = canvas;
    }
    
    public void setLineSpacing(float spacing) {
        p_spacing = spacing;
    }
    
    public void setTextSize(float size) {
        p_paint.setTextSize(size);
    }
    
    public void setColor(int color) {
        p_paint.setColor(color);
    }
    
    public void draw(String text, float x, float y) {
        p_x = x;
        p_y = y;
        draw(text);
    }
    
    public void draw(String text) {
        p_canvas.drawText(text, p_x, p_y, p_paint);
        p_y += p_spacing;
    }
}


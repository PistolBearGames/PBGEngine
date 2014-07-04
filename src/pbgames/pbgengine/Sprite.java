package pbgames.pbgengine;

/**
 * Sprite class for the PBGEngine
 * @author PistolBear
 * @email pistolbear@gmail.com
 * 
 * The meat and also the potatoes of the 2D side of PBGEngine
 * Handles loaded images as 2D actors and props, including
 * collision, transforms, effects, etc.
 */

import java.util.ListIterator;

import pbgames.pbgengine.Texture;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.renderscript.Float2;
import android.util.Log;
import pbgames.pbgengine.Animation;

import java.util.LinkedList;

/**
 * Sprite class
 * 
 * @author PistolBear
 * @param engine
 * 
 *            Declare new CTORs with just engine or with the below as well
 * @param int width
 * @param int height
 * @param int columns
 * 
 */
public class Sprite {
	// declare identifying private variables
	private PBGEngine p_engine;
	private Canvas p_canvas, p_backCanvas;
	private Texture p_texture;
	private Paint p_paint;
	private String p_name;
	private Texture p_glow;
	// TODO: implement below color management items if needed as well.
	// private ColorFilter p_colorFilter;
	// private ImageColorManager p_icm;

	// integers for use with pixel maths, frame position, alpha, and identifiers
	private int p_width, p_height, p_columns, p_alpha, p_frame, p_identifier;

	// LinkedList tracks number and order of animations applied to sprites
	private LinkedList<Animation> p_animations;

	// scale and velocity are self explanatory; position is actual (pixel
	// position is rounded). Use only for tile-based calculations.
	// Anything requiring more precision should use the matrix variables
	private Float2 p_scale, p_velocity, p_center, p_anchorCenter;
	public Float2 position;

	// rotation, scaling, and translation here
	private float p_rotation, p_radius;
	private Matrix p_mat_translate, p_mat_scale, p_mat_rotate, p_matrix;

	// for applying various effects, transforms, and animations before rendering
	// to the screen
	private Bitmap p_backBitmap;

	// collision is here too
	private boolean p_collidable, p_collided, p_active,
			p_collisionTypeCircular, p_debugMode;
	private Sprite p_colliderSprite, p_anchorSprite;

	/**
	 * Sprite class primary constructor
	 * 
	 * Full Constructor requires int width, int height, int columns
	 * 
	 * @param engine
	 *            (usually this)
	 * 
	 */
	public Sprite(PBGEngine engine) {
		this(engine, 0, 0, 1);
	}

	/**
	 * Sprite class full constructor
	 * 
	 * @param engine
	 *            PBGEngine
	 * @param int width
	 * @param int height
	 * @param int columns
	 */
	public Sprite(PBGEngine engine, int width, int height, int columns) {
		p_engine = engine;
		p_width = width;
		p_height = height;
		p_canvas = null;
		p_columns = columns;
		p_texture = new Texture(engine);
		p_alpha = 255;
		p_paint = new Paint();
		p_animations = new LinkedList<Animation>();
		position = new Float2(0, 0);
		p_frame = 0;
		p_scale = new Float2(1.0f, 1.0f);
		p_center = new Float2(1.0f, 1.0f);
		p_anchorCenter = new Float2(0.0f, 0.0f);
		p_rotation = 0.0f;
		p_mat_translate = new Matrix();
		p_mat_scale = new Matrix();
		p_mat_rotate = new Matrix();
		p_matrix = new Matrix();
		p_backBitmap = null;
		p_backCanvas = null;
		p_collidable = p_collided = false;
		p_colliderSprite = p_anchorSprite = null;
		p_name = "";
		p_identifier = 0;
		p_velocity = new Float2(0, 0);
		p_active = true;
		p_collisionTypeCircular = false; // Default sprites to NOT being
											// Circular
		p_debugMode = engine.getDebugMode(); // Have needed to debug Sprite
	}

	/**
	 * draw() function
	 * 
	 */
	public void draw() {
		p_canvas = p_engine.getCanvas();

		// fill in size if this sprite is not animated
		if (p_width == 0 || p_height == 0) {
			p_width = p_texture.getBitmap().getWidth();
			p_height = p_texture.getBitmap().getHeight();
		}

		// create backBitmap for drawing all effects to
		if (p_backBitmap == null) {
			p_backBitmap = Bitmap.createBitmap(p_width, p_height,
					Config.ARGB_8888);
			p_backCanvas = new Canvas(p_backBitmap);
		}

		// First, copy the animation frame onto a scratch bitmap.
		// +define the source rect representing one frame
		int u = (p_frame % p_columns) * p_width;
		int v = (p_frame / p_columns) * p_height;
		Rect src = new Rect(u, v, u + p_width, v + p_height);

		// +define the destination location
		int x = 0;
		int y = 0;
		int w = p_width;
		int h = p_height;
		Rect dst = new Rect(x, y, x + w, y + h);

		// wipe temp bitmap
		p_backBitmap.eraseColor(Color.TRANSPARENT);

		// copy backBitmap onto backCanvas
		p_paint.setAlpha(p_alpha);
		p_backCanvas.drawBitmap(p_texture.getBitmap(), src, dst, p_paint);
		if (p_debugMode)
			Log.d("Sprite class draw()", "p_backBitmap IS UPDATED with "
					+ p_name);

		/**
		 * Second, draw the scratch bitmap using matrix transforms.
		 */

		// update transform matrices
		p_mat_scale = new Matrix();
		p_mat_scale.setScale(p_scale.x, p_scale.y);

		p_mat_rotate = new Matrix();
		p_mat_rotate.setRotate((float) Math.toDegrees(p_rotation));

		p_mat_translate = new Matrix();
		p_mat_translate.setTranslate(position.x, position.y);

		p_matrix = new Matrix(); // set to identity
		p_matrix.postConcat(p_mat_scale);
		p_matrix.postConcat(p_mat_rotate);
		p_matrix.postConcat(p_mat_translate);

		// draw frame bitmap onto screen
		p_canvas.drawBitmap(p_backBitmap, p_matrix, p_paint);
	}

	// add an animation technique to this sprite
	public void addAnimation(Animation anim) {
		p_animations.add(anim);
	}

	// remove all animations
	public void removeAnimations() {
		if (p_animations.isEmpty())
			return;
		while (!p_animations.isEmpty()) {
			p_animations.removeFirst();
		}
	}

	// turn off specific animations
	public void stopAnimation(String animName) {
		if (p_animations.isEmpty())
			return;
		else {
			Animation a;
			ListIterator<Animation> iterator = p_animations.listIterator();
			while (iterator.hasNext()) {
				a = iterator.next();
				if (a.animationName == animName && a.animating)
					a.animating = false;
			}
		}
	}
	
	// turn on specific animations
	public void startAnimation(String animName) {
		if (p_animations.isEmpty())
			return;
		else {
			Animation a;
			ListIterator<Animation> iterator = p_animations.listIterator();
			while (iterator.hasNext()) {
				a = iterator.next();
				if (a.animationName == animName && a.animating)
					a.animating = true;
			}
		}
	}

	// run through all of the animations
	public void animate() {
		if (p_animations.size() == 0)
			return;

		ListIterator<Animation> iterator = p_animations.listIterator();
		while (iterator.hasNext()) {
			Animation anim = iterator.next();
			if (anim.animating) {
				p_glow = anim.adjustGlow(p_glow);
				p_paint = anim.adjustColor(p_paint);
				p_frame = anim.adjustFrame(p_frame);
				p_alpha = anim.adjustTransparency(p_alpha);
				p_rotation = anim.adjustRotation(p_rotation);
				p_scale = anim.adjustScale(p_scale);
				p_velocity = anim.adjustVelocity(p_velocity);
				position = anim.adjustPosition(position);
				p_active = anim.adjustActive(p_active);
				if (p_anchorSprite != null)
					p_anchorCenter = anim.adjustAnchor(p_anchorSprite);
			} else {
				p_animations.remove(anim);
				return;
			}
		}
	}

	/**
	 * common get/set and support methods
	 */

	public void setAlpha(int alpha) {
		p_alpha = alpha;
	}

	public int getAlpha() {
		return p_alpha;
	}

	public void setPaint(Paint paint) {
		p_paint = paint;
	}

	public void setPaintWithColorFilter(Paint paint, ColorFilter cf) {
		paint.setColorFilter(cf);
		p_paint = paint;
	}

	public void setTexture(Texture texture) {
		p_texture = texture;
		setCenterPerTexture(texture);
		if (p_collisionTypeCircular) // Might as well generate a radius as soon
										// as we can.
			generateRadius();
	}

	public void setCenterPerTexture(Texture texture) {
		// This needs to happen before drawing for purposes of placing the
		// sprite correctly when using setPositionCenteredOn(Float2)
		if (p_width == 0 || p_height == 0) {
			p_center.x = (texture.getBitmap().getWidth() * p_scale.x) / 2;
			p_center.y = (texture.getBitmap().getHeight() * p_scale.y) / 2;
		}

	}

	public Float2 getGlobalCenter() {
		return new Float2(getPosition().x + getCenterOfSprite().x,
				getPosition().y + getCenterOfSprite().y);
	}

	public Float2 getCenterOfSprite() {
		if (p_center.x == 1.0f && p_center.y == 1.0f) {
			if (p_texture != null) {
				p_center.x = (p_width * p_scale.x) / 2;
				p_center.y = (p_height * p_scale.y) / 2;
			}
		}

		// Always just return p_center. Above is in case setCenter has not been
		// called; there
		// may be cases it needs to be at the center of a different texture or
		// simply at the
		// defaulted (1,1).
		return p_center; // Note: p_center are LOCAL coordinates, not global.
	}

	public Texture getTexture() {
		return p_texture;
	}

	public void setPosition(Float2 p) {
		this.position = p;
	}

	public void setPositionCenteredOn(Float2 p) {
		// Using p_center here to center in case Sprite is non-circular
		position.x = p.x - p_center.x;
		position.y = p.y - p_center.y;

	}

	public Float2 getPosition() {
		return position;
	}

	public int getWidth() {
		return p_width;
	}

	public void setWidth(int width) {
		p_width = width;
	}

	public int getHeight() {
		return p_height;
	}

	public void setHeight(int height) {
		p_height = height;
	}

	public Float2 getSize() {
		return new Float2(p_width, p_height);
	}

	public int getFrame() {
		return p_frame;
	}

	public void setFrame(int frame) {
		p_frame = frame;
	}

	public Float2 getScale() {
		return p_scale;
	}

	public void setScale(Float2 scale) {
		p_scale = scale;
		if (getCollisionTypeCircular())
			generateRadius(); // Any time scale is changed, should probably
								// affect Radius, if it applies.
	}

	public void setScale(float scale) {
		p_scale = new Float2(scale, scale);
	}

	public float getRotation() {
		return p_rotation;
	}

	public void setRotation(float radians) {
		p_rotation = radians;
	}

	public boolean getCollidable() {
		return p_collidable;
	}

	public void setCollidable(boolean value) {
		p_collidable = value;
	}

	public boolean getCollided() {
		return p_collided;
	}

	public void setCollided(boolean value) {
		p_collided = value;
	}

	public Sprite getColliderSprite() {
		return p_colliderSprite;
	}

	public void setColliderSprite(Sprite value) {
		p_colliderSprite = value;
	}

	public String getName() {
		return p_name;
	}

	public void setName(String value) {
		p_name = value;
	}

	/**
	 * getIdentifier() Sprite class method returning the identifier has flag
	 * checking for debug, to test if this was used in collision, etc.
	 * 
	 * @return
	 */
	public int getIdentifier() {
		return p_identifier;
	}

	public void setIdentifier(int value) {
		p_identifier = value;
	}

	public RectF getBoundsF() {
		RectF r = new RectF(position.x, position.y, position.x + p_width,
				position.y + p_height);
		return r;
	}

	public RectF getBoundsScaledF() {
		RectF r = getBoundsF();
		r.right = (int) (r.left + r.width() * p_scale.x);
		r.bottom = (int) (r.top + r.height() * p_scale.y);
		return r;
	}

	public Rect getBounds() {
		Rect r = new Rect((int) position.x, (int) position.y, (int) position.x
				+ (int) p_width, (int) position.y + (int) p_height);
		return r;
	}

	public Rect getBoundsScaled() {
		Rect r = getBounds();
		r.right = (int) (r.left + r.width() * p_scale.x);
		r.bottom = (int) (r.top + r.height() * p_scale.y);
		return r;
	}

	public Float2 getVelocity() {
		return p_velocity;
	}

	public void setVelocity(Float2 value) {
		p_velocity = value;
	}

	public void setVelocity(double x, double y) {
		p_velocity.x = (float) x;
		p_velocity.y = (float) y;
	}

	public void setVelocity(float x, float y) {
		p_velocity.x = x;
		p_velocity.y = y;
	}

	public boolean getActive() {
		return p_active;
	}

	public void setActive(boolean value) {
		p_active = value;
	}

	public void setAnchorSprite(Sprite original) {
		p_anchorSprite = original;
	}

	public Sprite getAnchorSprite() {
		return p_anchorSprite;
	}

	public boolean getCollisionTypeCircular() {
		return p_collisionTypeCircular;
	}

	public void setCollisionTypeCircular(boolean value) {
		p_collisionTypeCircular = value;
	}

	public float getRadius() {

		return p_radius;
	}

	/**
	 * Only inteded to be used with Circular Sprites
	 * 
	 * @param r
	 */
	public void setRadius(float r) {
		p_radius = r;
	}

	/**
	 * generateRadius()
	 * 
	 * Calculates point from local (0,0) to local (1,1) on a unit plane, then
	 * divides by 2. Will extend a little beyond most compact Sprites.
	 */
	public void generateRadius() {
		p_radius = (float) ((Math.sqrt(Math.pow(p_width * p_scale.x, 2)
				+ Math.pow(p_height * p_scale.y, 2))) / 2);
	}

	// public ColorFilter getColorFilter() {
	// return p_colorFilter;
	// }
	//
	// public void setColorFilter(ColorFilter value) {
	// p_colorFilter = value;
	// }

}

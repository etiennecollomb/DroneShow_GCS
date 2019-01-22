package com.drone.show.hud.views;

import com.drone.show.GlobalManager;
import com.drone.show.hud.presenters.ButtonPresenter;
import com.drone.show.hud.tween.ActorTween;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;


public class ButtonView extends Actor {

	private ButtonPresenter buttonPresenter;

	private Sprite backgroundSprite;
	private String text;

	private Object value; //value sent to the presenter (optional)

	private BitmapFont font;
	private Color fontColor;

	private final float textButtonSizeRatio = 75f; //en %

	private Timeline tweenTimeLine;
	private boolean tweenTimeLineLaunched;
	private boolean isTweenEffect;

	
	
	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/
	
	public ButtonView(String backgroundFileName_, String text_, Color textColor_, boolean isTweenEffect, float width, float height){
		this(backgroundFileName_, text_, textColor_, isTweenEffect, "---", width, height);
	}
	
	public ButtonView(String backgroundFileName_, String text_, Color textColor_, boolean isTweenEffect_, Object value_, float width, float height){

		super();

		Texture texture = GlobalManager.assestsLoader.getAssetManager().get(backgroundFileName_);
		Sprite sprite = new Sprite(texture);

		this.setText( text_ );
		this.setValue( value_ );
		this.setBackgroundSprite( sprite );

		this.setTweenEffect( isTweenEffect_ );

		this.setTouchable(Touchable.enabled);

		this.addListener(new InputListener() {

			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(GlobalManager.ISDEBUG) System.out.println("down "+getText() );

				//GlobalManager.soundManager.playSound(SoundType.BUTTON_CLICK);

				return true;  // must return true for touchUp event to occur
			}

			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(GlobalManager.ISDEBUG) System.out.println("up "+getText() );

				//Do we have still the focus on the event (can be cancelled by parent group, see : Tools.cancelTouchFocusGroup)
				if(!event.isTouchFocusCancel()){

					/** Tween Effect */
					if(isTweenEffect()) {
						launchTweenEffect();
					}
					/** else launch action directly */
					else {
						getButtonPresenter().onClick(getValue());
					}
				}
			}
		});


		if(width == 0) {
			width = this.getBackgroundSprite().getWidth();
		}
		if(height == 0) {
			height = this.getBackgroundSprite().getHeight();
		}


		this.setSize(width, height);
		this.getBackgroundSprite().setSize(width, height);

		/** Font */
		//on prend la font qui aura la bonne taille pour la hauteur
		float textScreenSizeInPixel = ( this.getHeight() /100f * this.getTextButtonSizeRatio() );
		//this.setFont(Tools.getFontFromHeightSize("fonts/RockoFLF-Bold.ttf",textScreenSizeInPixel) );
		this.setFont( GlobalManager.fontManager.getBitmapFonts(textScreenSizeInPixel) );
		this.setFontColor( textColor_ );

	}



	/**************************************
	 * 
	 * Property Change Support/Listener
	 * 
	 **************************************/

	/**************************************
	 * 
	 * Methods
	 * 
	 **************************************/

	public void launchTweenEffect() {
		this.setTweenTimeLine( Timeline.createSequence().beginSequence()
				.push(Tween.to(this, ActorTween.SCALE, 0.05f).target(1.70f, 1.20f).ease(TweenEquations.easeInOutSine))
				.push(Tween.to(this, ActorTween.SCALE, 0.10f).target(1f, 1f).ease(TweenEquations.easeInOutSine))
				.end()
				);
		this.getTweenTimeLine().start(GlobalManager.tweenManager);
		this.setTweenTimeLineLaunched( true );
	}



	/**************************************
	 * 
	 * Override
	 * 
	 **************************************/

	@Override
	public void act(float delta) {
		super.act(delta);

		/** if tween effect */
		/** wait that tween effect finished before lauching action */
		if(this.getTweenTimeLine() != null && this.isTweenTimeLineLaunched() && this.getTweenTimeLine().isFinished()){
			this.getButtonPresenter().onClick(this.getValue());
			this.setTweenTimeLineLaunched( false );
		}
	};


	@Override
	public void draw(Batch batch, float alpha){
		batch.end();

		this.getFont().setColor(this.getFontColor());

		/** Background */
		/** For centered in the actor*/
		float offsetForCenteringBackgroundX = (getWidth()-this.getBackgroundSprite().getWidth())/2f;
		float offsetForCenteringBackgroundY = (getHeight()-this.getBackgroundSprite().getHeight())/2f;
		GlobalManager.drawSprite(batch, this.getBackgroundSprite(),
				this.getX() + offsetForCenteringBackgroundX,
				this.getY() + offsetForCenteringBackgroundY,
				this.getBackgroundSprite().getWidth(),
				this.getBackgroundSprite().getHeight(),
				this.getScaleX(),
				this.getScaleY());

		/** Text Button */
		/** For centered in the actor*/
		float offsetForCenteringFontX = getWidth()/2;
		float offsetForCenteringFontY = getHeight()/2;
		GlobalManager.drawFont(batch, this.getFont(),
				this.getX()+offsetForCenteringFontX,
				this.getY()+offsetForCenteringFontY,
				this.getText(),
				this.getScaleX(),
				this.getScaleY(),
				0, Align.center);

		batch.begin();

	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public void setButtonPresenter(ButtonPresenter buttonPresenter) {
		this.buttonPresenter = buttonPresenter;
	}

	public Sprite getBackgroundSprite() {
		return backgroundSprite;
	}

	public void setBackgroundSprite(Sprite backgroundSpriteA) {
		this.backgroundSprite = backgroundSpriteA;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public Timeline getTweenTimeLine() {
		return tweenTimeLine;
	}

	public void setTweenTimeLine(Timeline tweenTimeLine) {
		this.tweenTimeLine = tweenTimeLine;
	}

	public boolean isTweenTimeLineLaunched() {
		return tweenTimeLineLaunched;
	}

	public void setTweenTimeLineLaunched(boolean tweenTimeLineLaunched) {
		this.tweenTimeLineLaunched = tweenTimeLineLaunched;
	}

	public ButtonPresenter getButtonPresenter() {
		return buttonPresenter;
	}

	public String getText() {
		return text;
	}

	public float getTextButtonSizeRatio() {
		return textButtonSizeRatio;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isTweenEffect() {
		return isTweenEffect;
	}

	public void setTweenEffect(boolean isTweenEffect) {
		this.isTweenEffect = isTweenEffect;
	}

}
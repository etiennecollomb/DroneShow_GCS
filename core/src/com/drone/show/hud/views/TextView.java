package com.drone.show.hud.views;

import com.drone.show.generic.Tools;
import com.drone.show.hud.presenters.TextPresenter;
import com.drone.show.hud.tween.ActorTween;
import com.drone.show.GlobalManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;



public class TextView extends Actor {


	public static enum AlignText{
		ALIGN_LEFT,
		ALIGN_CENTER,
		ALIGN_RIGHT
	};

	private TextPresenter textViewPresenter;

	private AlignText alignText;

	private String text;
	private BitmapFont font;
	private Color fontColor;

	private Timeline tweenTimeline;

	private SpriteBatch fontSpriteBatch = new SpriteBatch();



	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/


	/** NO Actor Size specified: actor size is the one of the text itself */
	public TextView(String text, BitmapFont font, Color fontColor){

		this.setText(text);
		this.setFont(font);
		this.setFontColor(fontColor);
		this.setAlignText(AlignText.ALIGN_CENTER);

		if(text.equals("")) Tools.glyphLayout.setText(font, "0"); //if not text, set minimum container size....
		else Tools.glyphLayout.setText(font, text);
		setSize(Tools.glyphLayout.width, Tools.glyphLayout.height);

	}

	/** Actor Size specified: font size is the one of Actor height */
	public TextView(float width, float height, String text, Color fontColor){

		this.setText(text);
		this.setSize(width, height);
		this.setAlignText(AlignText.ALIGN_CENTER);

		/** Font */
		//on prend la font qui aura la bonne taille pour la hauteur
		float textScreenSizeInPixel = ( this.getHeight() );
		//this.setFont( GlobalManager.fontManager.getBitmapFonts(textScreenSizeInPixel) );
		//TEMP TODO: le temps de generer ou pas les bonnes fonts et de charger avec la ligne commentee ci desssu
		this.setFont( Tools.getFontFromHeightSize("fonts/Chewy.ttf", textScreenSizeInPixel ));
		this.setFontColor(fontColor);

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
		this.setTweenTimeline( Timeline.createSequence().beginSequence()
				.push(Tween.to(this, ActorTween.SCALE, 0.05f).target(1.70f, 1.20f).ease(TweenEquations.easeInOutSine))
				.push(Tween.to(this, ActorTween.SCALE, 0.5f).target(1f, 1f).ease(TweenEquations.easeOutElastic))
				.end()
				);
		this.getTweenTimeline().start(GlobalManager.tweenManager);
	}


	public void launchVictoryTweenEffect() {
		this.setTweenTimeline( Timeline.createSequence().beginSequence()
				.push(Tween.to(this, ActorTween.SCALE, 0.05f).target(1.70f, 1.20f).ease(TweenEquations.easeInOutSine))
				.push(Tween.to(this, ActorTween.SCALE, 1f).target(1f, 1f).ease(TweenEquations.easeOutElastic))
				.repeat(5, 0.5f)
				.end()
				);
		this.getTweenTimeline().start(GlobalManager.tweenManager);
	}


	public void killTweenEffect(){

		if(this.getTweenTimeline() != null){
			this.getTweenTimeline().update(999999);
		}
	}



	/**************************************
	 * 
	 * Override
	 * 
	 **************************************/

	@Override
	public void draw(Batch batch, float alpha){
		batch.end();

		this.getFont().setColor(this.getFontColor());

		/** For center font in the actor*/
		float offsetForCenteringFontX = 0;
		float offsetForCenteringFontY = getHeight()/2;

		switch(this.getAlignText()) {

		case ALIGN_LEFT:
			Tools.glyphLayout.setText(this.getFont(), this.getText());
			offsetForCenteringFontX = Tools.glyphLayout.width/2f;
			break;
		case ALIGN_CENTER:
			offsetForCenteringFontX = getWidth()/2;
			break;
		case ALIGN_RIGHT:
			offsetForCenteringFontX = getWidth()/2;
			break;

		}


		GlobalManager.drawFont(batch, this.getFont(),
				this.getX() + offsetForCenteringFontX,
				this.getY() + offsetForCenteringFontY,
				this.getText(),
				this.getScaleX(),
				this.getScaleY(),
				0, Align.center);


		batch.begin();
	}


	public void setTextViewPresenter(TextPresenter textViewPresenter) {
		this.textViewPresenter = textViewPresenter;

	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public void setFont(BitmapFont font){
		this.font = font;
	}

	public Timeline getTweenTimeline() {
		return tweenTimeline;
	}

	public void setTweenTimeline(Timeline tweenTimeline) {
		this.tweenTimeline = tweenTimeline;
	}

	public SpriteBatch getFontSpriteBatch() {
		return fontSpriteBatch;
	}

	public void setFontSpriteBatch(SpriteBatch fontSpriteBatch) {
		this.fontSpriteBatch = fontSpriteBatch;
	}

	public TextPresenter getTextViewPresenter() {
		return textViewPresenter;
	}

	public BitmapFont getFont() {
		return font;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor){
		this.fontColor = fontColor;
	}

	public String getText() {
		return text;
	}

	public void setText(String text){
		this.text = text;
	}

	public AlignText getAlignText() {
		return alignText;
	}

	public void setAlignText(AlignText alignText) {
		this.alignText = alignText;
	}



}
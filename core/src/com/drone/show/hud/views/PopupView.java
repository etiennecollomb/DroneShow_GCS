package com.drone.show.hud.views;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.drone.show.GlobalManager;
import com.drone.show.generic.Tools;
import com.drone.show.hud.presenters.ButtonPresenter;
import com.drone.show.hud.presenters.TextPresenter;
import com.drone.show.hud.tween.ActorTween;


/**
 * Notes :
 * La taille de l actor est egale a la taille de la Cell qui le contient,
 * une fois le tableau initialisé...
 * On peut connaitre cette taille dans le draw() avec (Actor)this.getWidth(), (Actor)this.getHeight(), 
 * une fois que le table est initialisé.
 */
public class PopupView extends Table {

	private Sprite backgroundSprite;

	private float scaleCoeff; //to scale the whole popup (works only at the constructor)

	/** Drag Popups Stuff */
	private boolean isDragPopup; //allow slide of Popup
	private float lastDragX, lastDragY;
	private boolean isMoveDirectionAlreadyChecked;
	private boolean isHorizontalDrag, isRightDrag;
	public final static int LEFT_SLIDE_POPUP = 0;
	public final static int RIGHT_SLIDE_POPUP = 1;

	/** Tween effect stuff */
	private float originalX, originalY;
	private Timeline tweenTimeLine;
	private boolean tweenTimeLineLaunched;
	public final static int OUT_LEFT_SCREEN_TWEEN_TYPE = 0;
	public final static int OUT_RIGHT_SCREEN_TWEEN_TYPE = 1;
	public final static int CENTERED_SCREEN_TWEEN_TYPE = 2;


	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	public PopupView(float x, float y, float width, float height, float scaleCoeff_) {
		this(x, y, width, height, null, scaleCoeff_);
	}

	/** Size is proportional from the background Texture */
	public PopupView(float x, float y, String backgroundFileName, float scaleCoeff_) {
		this(x, y, 0, 0, backgroundFileName, scaleCoeff_);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param scaleCoeff_ : taille en % pour grossir ou rapeticir de facon proportionnelle (0;0f - 1.0f)
	 * @param backgroundFileName
	 */
	private PopupView(float x, float y, float width, float height, String backgroundFileName, float scaleCoeff_) {

		this.setScaleCoeff(scaleCoeff_);

		this.setX(x);
		this.setY(y);

		this.setOriginalX( this.getX() );
		this.setOriginalY( this.getY() );

		if(backgroundFileName != null) {
			/** we set the size from the background image */
			Texture texture = GlobalManager.assestsLoader.getAssetManager().get(backgroundFileName);
			Sprite sprite = new Sprite(texture);
			sprite.setSize(sprite.getWidth()*this.getScaleCoeff(),
					sprite.getHeight()*this.getScaleCoeff());
			this.setWidth(sprite.getWidth());
			this.setHeight(sprite.getHeight());
			this.setBackgroundSprite( sprite );
		}else {
			/** we keep the given size */
			this.setWidth(width*this.getScaleCoeff());
			this.setHeight(height*this.getScaleCoeff());
			this.setBackgroundSprite( null );
		}

		this.addInputListener();

		if(GlobalManager.ISDEBUG) this.setDebug(true);
		this.setVisible(false);

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

	private void addInputListener() {

		final PopupView myself = this; //Needed for cancelTouchFocusGroup

		/** 
		 * add touch listener to the popup to drag it 
		 * */
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				if(GlobalManager.ISDEBUG) System.out.println("##### popup down");

				//Drag stuff
				if( isDragPopup() ){
					setLastDragX(x);
					setLastDragY(y);
					setMoveDirectionAlreadyChecked(false);
					setHorizontalDrag(false);
					setRightDrag(false);
				}

				return true;  // must return true for touchDragged & touchUp event to occur
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {

				if( isDragPopup() ){
					/** slide only if we slide horizontally */
					/** check first if we detect a horizontal direction */
					if(isMoveDirectionAlreadyChecked() == false) {
						float moveX = x-getLastDragX();
						float moveY = y-getLastDragY();
						if(Math.abs(moveX) > Math.abs(moveY))
							setHorizontalDrag(true);
						if(moveX > 0)
							setRightDrag(true);
						setMoveDirectionAlreadyChecked(true);
					}

					/** if horizontalDrag then move it */
					if(isHorizontalDrag()) {

						if(GlobalManager.ISDEBUG) System.out.println("##### popup horizontal dragged");

						/** Remove touch focus on buttons from table */
						Tools.cancelTouchFocusGroup(GlobalManager.HUDStage, myself, false);

						/** slide popup*/
						setPosition(getX()+ x-getLastDragX(), getY());
					}
				}

			}

			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

				if(GlobalManager.ISDEBUG) System.out.println("##### popup up");

				if( isDragPopup() ){
					if(isHorizontalDrag() && isRightDrag())
						slidePopup(PopupView.RIGHT_SLIDE_POPUP);
					else if(isHorizontalDrag() && !isRightDrag())
						slidePopup(PopupView.LEFT_SLIDE_POPUP);
				}

			}

		});

	}


	public void slidePopup(int SLIDE_TYPE) {

		//		this.launchTweenEffect(PopupView.CENTERED_SCREEN_TWEEN_TYPE); //TEST

		switch(SLIDE_TYPE){
		case PopupView.LEFT_SLIDE_POPUP:
			this.launchTweenEffect(PopupView.OUT_LEFT_SCREEN_TWEEN_TYPE);
			break;

		case PopupView.RIGHT_SLIDE_POPUP:
			this.launchTweenEffect(PopupView.OUT_RIGHT_SCREEN_TWEEN_TYPE);
			break;
		}


	}


	public void launchTweenEffect(int tweenEffectType) { //Slide out of screen, slide back center...

		switch(tweenEffectType){

		/** si on doit faire revenir la popoup au centre
		 * (dans une liste de popup par exemple , la premiere et derniere popup)
		 */
		case PopupView.CENTERED_SCREEN_TWEEN_TYPE:
			this.setTweenTimeLine( Timeline.createSequence().beginSequence()
					.push(Tween.to(this, ActorTween.POSITION, 0.5f).target(this.getOriginalX(), this.getY())
							.ease(TweenEquations.easeInOutExpo))
					.end()
					);
			break;
		case PopupView.OUT_LEFT_SCREEN_TWEEN_TYPE: 
			this.setTweenTimeLine( Timeline.createSequence().beginSequence()
					.push(Tween.to(this, ActorTween.POSITION, 0.5f).target(-this.getWidth(), this.getY())
							.ease(TweenEquations.easeInOutCubic))
					.end()
					);
			break;
		case PopupView.OUT_RIGHT_SCREEN_TWEEN_TYPE:
			this.setTweenTimeLine( Timeline.createSequence().beginSequence()
					.push(Tween.to(this, ActorTween.POSITION, 0.5f).target(GlobalManager.DEVICE_SCREEN_WIDTH, this.getY())
							.ease(TweenEquations.easeInOutCubic))
					.end()
					);
			break;

		}

		this.getTweenTimeLine().start(GlobalManager.tweenManager);
		this.setTweenTimeLineLaunched(true);
	}



	public void show() {

		this.setTouchable(Touchable.enabled);
		this.setVisible(true);
	}

	public void hide() {

		this.setTouchable(Touchable.disabled);
		this.setVisible(false);
	}

	/**
	 * 
	 * @param action_ action send back to the ApplicationModel
	 * @param label_ english label from bundle
	 * @param colorLabel_
	 * @param width
	 * @param height
	 * @return
	 */
	public Cell<ButtonView> addButton(Table table_, String event_, String label_, Color colorLabel_, String backgroundFilename, float width, float height) {

		ButtonView playButton = new ButtonView(backgroundFilename, GlobalManager.i18NBundle.get(label_),
				colorLabel_, true,
				width, height);
		ButtonPresenter playButtonPresenter = new ButtonPresenter(playButton, event_);
		return table_.add( playButton );

	}
	
	
	public Cell<TextView> addText(Table table_, String event_, String label_, Color colorLabel_, float width, float height, TextView.AlignText alignText) {

		TextView textView = new TextView(width, height,
				label_, colorLabel_
				);
		textView.setAlignText(alignText);
		TextPresenter textPresenter = new TextPresenter(textView, event_);
		return table_.add( textView );

	}
	
	
	public Cell<ScrollPane> addScrollPan(Table scrollPanTable){

		scrollPanTable.top();
		if(GlobalManager.ISDEBUG) scrollPanTable.setDebug(true);
		ScrollPane scrollPan = new ScrollPane(scrollPanTable); //Scroll for Saved Game (need to be refresh by new)
		scrollPan.setFadeScrollBars(false);
		scrollPan.setScrollingDisabled(true, false);
		
		return this.add(scrollPan);
	}


	/**************************************
	 * 
	 * Override
	 * 
	 **************************************/

	@Override
	protected void drawBackground (Batch batch, float parentAlpha, float x, float y) {
		if (this.getBackgroundSprite() == null) return;

		batch.end();

		GlobalManager.drawSprite(batch, this.getBackgroundSprite(),
				x,
				y,
				this.getWidth(),
				this.getHeight(),
				this.getScaleX(),
				this.getScaleY());

		batch.begin();
	}


	@Override
	public void act(float delta) {
		super.act(delta);

		/** if tween effect */
		/** check that tween effect is finished */
		if(this.getTweenTimeLine() != null && this.isTweenTimeLineLaunched() && this.getTweenTimeLine().isFinished()){
			this.setTweenTimeLineLaunched( false );
		}

	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/


	public Sprite getBackgroundSprite() {
		return backgroundSprite;
	}

	public float getScaleCoeff() {
		return scaleCoeff;
	}

	public void setScaleCoeff(float scaleCoeff) {
		this.scaleCoeff = scaleCoeff;
	}

	public void setBackgroundSprite(Sprite backgroundSprite) {
		this.backgroundSprite = backgroundSprite;
	}

	public boolean isDragPopup() {
		return isDragPopup;
	}

	public void setDragPopup(boolean isDragPopup) {
		this.isDragPopup = isDragPopup;
	}

	public float getLastDragX() {
		return lastDragX;
	}

	public void setLastDragX(float lastDragX) {
		this.lastDragX = lastDragX;
	}

	public float getLastDragY() {
		return lastDragY;
	}

	public void setLastDragY(float lastDragY) {
		this.lastDragY = lastDragY;
	}

	public boolean isMoveDirectionAlreadyChecked() {
		return isMoveDirectionAlreadyChecked;
	}

	public void setMoveDirectionAlreadyChecked(boolean isMoveDirectionAlreadyChecked) {
		this.isMoveDirectionAlreadyChecked = isMoveDirectionAlreadyChecked;
	}

	public boolean isHorizontalDrag() {
		return isHorizontalDrag;
	}

	public void setHorizontalDrag(boolean isHorizontalDrag) {
		this.isHorizontalDrag = isHorizontalDrag;
	}

	public boolean isRightDrag() {
		return isRightDrag;
	}

	public void setRightDrag(boolean isRightDrag) {
		this.isRightDrag = isRightDrag;
	}

	public float getOriginalX() {
		return originalX;
	}

	public void setOriginalX(float originalX) {
		this.originalX = originalX;
	}

	public float getOriginalY() {
		return originalY;
	}

	public void setOriginalY(float originalY) {
		this.originalY = originalY;
	}

	public Timeline getTweenTimeLine() {
		return tweenTimeLine;
	}

	public void setTweenTimeLine(Timeline tweenTimeLine) {
		this.tweenTimeLine = tweenTimeLine;
	}

	public void setTweenTimeLineLaunched(boolean tweenTimeLineLaunched) {
		this.tweenTimeLineLaunched = tweenTimeLineLaunched;
	};

	public boolean isTweenTimeLineLaunched() {
		return tweenTimeLineLaunched;
	}

}



package com.drone.show.hud.views;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.drone.show.GlobalManager;
import com.drone.show.hud.presenters.ButtonPresenter;
import com.drone.show.hud.presenters.CheckboxPresenter;


public class CheckboxView extends Actor {


	private CheckboxPresenter checkBoxPresenter;

	private Sprite backgroundSpriteA;
	private Sprite backgroundSpriteB;
	private Sprite currentBackGroundSprite;

	private boolean isON;



	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	/** Renderer the 1,2,....,8,9 buttons under the suduko grid */
	public CheckboxView(Sprite spriteA, Sprite spriteB){
		super();

		this.setBackgroundSpriteA( spriteA );
		this.setBackgroundSpriteB( spriteB );
		this.setCurrentBackGroundSprite( this.getBackgroundSpriteA() );

		this.setON( true );

		this.setTouchable(Touchable.enabled);

		this.addListener(new InputListener() {

			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(GlobalManager.ISDEBUG) System.out.println("checkbox down ");

				return true;  // must return true for touchUp event to occur
			}

			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(GlobalManager.ISDEBUG) System.out.println("checkbox up ");

				//Do we have still the focus on the event (can be cancelled by parent group, see : Tools.cancelTouchFocusGroup)
				if(!event.isTouchFocusCancel()){
					switchState();
					getCheckBoxPresenter().onClick(isON());
				}
			}
		});

		this.setSize(this.getCurrentBackGroundSprite().getWidth(), this.getCurrentBackGroundSprite().getHeight());


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

	private void switchState() {

		if(this.isON()){
			this.setCurrentBackGroundSprite( this.getBackgroundSpriteB() );
			this.setON( false );
		}
		else{
			this.setCurrentBackGroundSprite( this.getBackgroundSpriteA() );
			this.setON( true );
		}
	}


	public void switchState(boolean newState) {

		this.setON( newState );

		if(this.isON()){
			this.setCurrentBackGroundSprite( this.getBackgroundSpriteA() );
		}
		else{
			this.setCurrentBackGroundSprite( this.getBackgroundSpriteB() );
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

		/** Sprite */
		/** For centered in the actor*/
		float offsetForCenteringBackgroundX = 0;
		float offsetForCenteringBackgroundY = 0;
		GlobalManager.drawSprite(batch, this.getCurrentBackGroundSprite(),
				this.getX() + offsetForCenteringBackgroundX,
				this.getY() + offsetForCenteringBackgroundY,
				this.getCurrentBackGroundSprite().getWidth(),
				this.getCurrentBackGroundSprite().getHeight(),
				this.getScaleX(),
				this.getScaleY());


		batch.begin();

	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public void setButtonPresenter(CheckboxPresenter buttonPresenter) {
		this.checkBoxPresenter = buttonPresenter;
	}

	public Sprite getBackgroundSpriteA() {
		return backgroundSpriteA;
	}

	public void setBackgroundSpriteA(Sprite backgroundSpriteA) {
		this.backgroundSpriteA = backgroundSpriteA;
	}

	public Sprite getBackgroundSpriteB() {
		return backgroundSpriteB;
	}

	public void setBackgroundSpriteB(Sprite backgroundSpriteB) {
		this.backgroundSpriteB = backgroundSpriteB;
	}

	public Sprite getCurrentBackGroundSprite() {
		return currentBackGroundSprite;
	}

	public void setCurrentBackGroundSprite(Sprite currentBackGroundSprite) {
		this.currentBackGroundSprite = currentBackGroundSprite;
	}

	public boolean isON() {
		return isON;
	}

	public void setON(boolean isON) {
		this.isON = isON;
	}

	public CheckboxPresenter getCheckBoxPresenter() {
		return checkBoxPresenter;
	}


}
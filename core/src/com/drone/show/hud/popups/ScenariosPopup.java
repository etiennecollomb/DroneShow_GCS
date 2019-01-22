package com.drone.show.hud.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.drone.show.GlobalManager;
import com.drone.show.generic.ColorsCatalog;
import com.drone.show.generic.Tools;
import com.drone.show.hud.presenters.ButtonPresenter;
import com.drone.show.hud.views.ButtonView;
import com.drone.show.hud.views.PopupView;
import com.drone.show.hud.views.TextView.AlignText;

public class ScenariosPopup extends PopupView {

	
	/** ACTIONs EVENTs */
	public static final String LAUNCH_SCENARIO = "LAUNCH_SCENARIO";
		
	
	
	public ScenariosPopup(float x, float y, float proportionalSizeCoeff) {
		super(x, y, "ui/popup_bg_02.png", proportionalSizeCoeff);
		this.build();
	}
	
	
	
	private void build() {
		
		
		this.setDragPopup(false);
		this.show();
		
		this.addButton(this, LAUNCH_SCENARIO, "play", ColorsCatalog.GREY_01, "ui/button-red.png", 100, 25)
			.expandX().expandY().colspan(2);
		
		this.row();
		
		/** 
		 * Scenario Scroll 
		 * */
		Table scrollPanTable =  new Table(); //Layout for saved game in the popup
		this.addScrollPan(scrollPanTable).expandX().expandY().colspan(4);
		
		this.addText(scrollPanTable, "", "scenarios", ColorsCatalog.GREY_01, 100, 25, AlignText.ALIGN_CENTER)
		.width(this.getWidth()/1.5f).height(this.getHeight()/6f).colspan(1); //trs proportionnel si on veu garder les proportion au resize
		
		scrollPanTable.row();
		this.addButton(scrollPanTable, "", "play", ColorsCatalog.GREY_01, "ui/button-green.png", 100, 25)
		.width(this.getWidth()/1.5f).height(this.getHeight()/6f).colspan(1); //trs proportionnel si on veu garder les proportion au resize
		
		scrollPanTable.row();
		this.addButton(scrollPanTable, "", "play", ColorsCatalog.GREY_01, "ui/button-green.png", 100, 25)
		.width(this.getWidth()/1.5f).height(this.getHeight()/6f).colspan(1);
		
		scrollPanTable.row();
		this.addButton(scrollPanTable, "", "play", ColorsCatalog.GREY_01, "ui/button-green.png", 100, 25)
		.width(this.getWidth()/1.5f).height(this.getHeight()/6f).colspan(1);
		
		scrollPanTable.row();
		this.addButton(scrollPanTable, "", "play", ColorsCatalog.GREY_01, "ui/button-green.png", 100, 25)
		.width(this.getWidth()/1.5f).height(this.getHeight()/6f).colspan(1);
		
		scrollPanTable.row();
		this.addButton(scrollPanTable, "", "play", ColorsCatalog.GREY_01, "ui/button-green.png", 100, 25)
		.width(this.getWidth()/1.5f).height(this.getHeight()/6f).colspan(1);
		
		
	}
	
	
	
	
	
}

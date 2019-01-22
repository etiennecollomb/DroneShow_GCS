package com.drone.show.hud.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.drone.show.generic.ColorsCatalog;
import com.drone.show.hud.presenters.TextPresenter;
import com.drone.show.hud.presenters.ekftextpresenter.EkfFlagTextPresenter;
import com.drone.show.hud.presenters.ekftextpresenter.EkfVarianceTextPresenter;
import com.drone.show.hud.presenters.ekftextpresenter.EkfVarianceTextPresenter.VarianceType;
import com.drone.show.hud.views.PopupView;
import com.drone.show.hud.views.TextView;
import com.drone.show.hud.views.TextView.AlignText;

import io.dronefleet.mavlink.ardupilotmega.EkfStatusFlags;

public class TelemetryPopup extends PopupView {

	
	/** ACTIONs EVENTs */
	public static final String MODE_TEXT = "MODE_TEXT";
	public static final String NUMBER_OF_SATTELITES_TEXT = "NUMBER_OF_SATTELITES_TEXT";
	public static final String GPS_FIX_TYPE_TEXT = "GPS_FIX_TYPE_TEXT";
	public static final String ARM_STATUS_TEXT = "ARM_STATUS_TEXT";
	public static final String STATUS_TEXT = "STATUS_TEXT";
	public static final String EKF_STATUS_TEXT = "EKF_STATUS_TEXT";
		
	
	
	public TelemetryPopup(float x, float y, float proportionalSizeCoeff) {
		super(x, y, "ui/popup_bg_01.png", proportionalSizeCoeff);
		this.build();
	}
	
	
	
	private void build() {
		
		float cellHeight = 20;
		float textHeight = 15;
		
		this.setDragPopup(false);
		this.show();
		
		this.add().expandX().height(5).colspan(2);
		
		this.row();
		this.addText(this, "", " Drone Status", ColorsCatalog.GREY_03, 100, textHeight+5, AlignText.ALIGN_CENTER)
		.width(150).height(cellHeight).colspan(2);
		
		this.row();
		this.addText(this, "", " Mode:", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.width(150).height(cellHeight).colspan(1);
		this.addText(this, MODE_TEXT, "", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		
		this.row();
		this.addText(this, "", " Nombre de GPS:", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.width(150).height(cellHeight).colspan(1);
		this.addText(this, NUMBER_OF_SATTELITES_TEXT, "", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		
		this.row();
		this.addText(this, "", " GPS Fix Type:", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.width(150).height(cellHeight).colspan(1);
		this.addText(this, GPS_FIX_TYPE_TEXT, "", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		
		this.row();
		this.addText(this, "", " Arm:", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.width(150).height(cellHeight).colspan(1);
		this.addText(this, ARM_STATUS_TEXT, "", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		
		this.row();
		this.addText(this, "", " Status:", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.width(150).height(cellHeight).colspan(1);
		this.addText(this, STATUS_TEXT, "", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		
		//**** EKF STATUS ****//
		this.row();
		this.addText(this, "", " EKF Variance:", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.width(150).height(cellHeight).colspan(1);
		this.addText(this, "", "Velocity", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addText(this, "", "Pos Horiz", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addText(this, "", "Pos Vert", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addText(this, "", "Compass", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addText(this, "", "Terrain Alt", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addText(this, "", "Air Speed", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.row();
		this.add().width(100).height(textHeight).colspan(1);
		this.addEkfVarianceText(this, EKF_STATUS_TEXT, "", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, VarianceType.VELOCITY_VARIANCE)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfVarianceText(this, EKF_STATUS_TEXT, "", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, VarianceType.POS_HORIZ_VARIANCE)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfVarianceText(this, EKF_STATUS_TEXT, "", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, VarianceType.POS_VERT_VARIANCE)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfVarianceText(this, EKF_STATUS_TEXT, "", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, VarianceType.COMPASS_VARIANCE)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfVarianceText(this, EKF_STATUS_TEXT, "", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, VarianceType.TERRAIN_ALT_VARIANCE)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfVarianceText(this, EKF_STATUS_TEXT, "", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, VarianceType.AIR_SPEED_VARIANCE)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		
		this.row();
		
		this.row();
		this.addText(this, "", " EKF Flags:", ColorsCatalog.GREY_01, 100, textHeight, AlignText.ALIGN_LEFT)
		.width(150).height(cellHeight).colspan(1);
		this.addEkfFlagText(this, EKF_STATUS_TEXT, "Attitude", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, EkfStatusFlags.EKF_ATTITUDE)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfFlagText(this, EKF_STATUS_TEXT, "Vel Horiz", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, EkfStatusFlags.EKF_VELOCITY_HORIZ)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfFlagText(this, EKF_STATUS_TEXT, "Vel Vert", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, EkfStatusFlags.EKF_VELOCITY_VERT)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfFlagText(this, EKF_STATUS_TEXT, "Pos Horiz Rel", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, EkfStatusFlags.EKF_POS_HORIZ_REL)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfFlagText(this, EKF_STATUS_TEXT, "Pos Horiz Abs", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, EkfStatusFlags.EKF_POS_HORIZ_ABS)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.row();
		this.add().width(100).height(textHeight).colspan(1);
		this.addEkfFlagText(this, EKF_STATUS_TEXT, "Pos Vert Abs", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, EkfStatusFlags.EKF_POS_VERT_ABS)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfFlagText(this, EKF_STATUS_TEXT, "Pos Vert Agl", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, EkfStatusFlags.EKF_POS_VERT_AGL)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfFlagText(this, EKF_STATUS_TEXT, "Const Pos Mode", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, EkfStatusFlags.EKF_CONST_POS_MODE)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfFlagText(this, EKF_STATUS_TEXT, "Pred Pos Horiz Rel", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, EkfStatusFlags.EKF_PRED_POS_HORIZ_REL)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		this.addEkfFlagText(this, EKF_STATUS_TEXT, "Pred Pos Horiz Abs", ColorsCatalog.GREY_01, 150, textHeight, AlignText.ALIGN_LEFT, EkfStatusFlags.EKF_PRED_POS_HORIZ_ABS)
		.expandX().height(cellHeight).colspan(1).align(Align.left);
		//**** END EKF STATUS ****//
		
		
		
		/** pour remplir le reste de l espace */
		this.row();
		this.add().expandX().expandY().colspan(2);
		
	}
	
	

	public Cell<TextView> addEkfVarianceText(Table table_, String event_, String label_, Color colorLabel_, float width, float height, TextView.AlignText alignText, VarianceType varianceType) {

		TextView textView = new TextView(width, height,
				label_, colorLabel_
				);
		textView.setAlignText(alignText);
		EkfVarianceTextPresenter textPresenter = new EkfVarianceTextPresenter(textView, event_, varianceType);
		return table_.add( textView );

	}
	
	public Cell<TextView> addEkfFlagText(Table table_, String event_, String label_, Color colorLabel_, float width, float height, TextView.AlignText alignText, EkfStatusFlags ekfStatusFlags) {

		TextView textView = new TextView(width, height,
				label_, colorLabel_
				);
		textView.setAlignText(alignText);
		EkfFlagTextPresenter textPresenter = new EkfFlagTextPresenter(textView, event_, ekfStatusFlags);
		return table_.add( textView );

	}
	
	
	
}

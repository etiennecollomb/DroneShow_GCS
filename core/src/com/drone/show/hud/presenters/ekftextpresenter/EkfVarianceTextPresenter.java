package com.drone.show.hud.presenters.ekftextpresenter;

import java.beans.PropertyChangeEvent;

import com.badlogic.gdx.graphics.Color;
import com.drone.show.generic.Tools;
import com.drone.show.hud.presenters.TextPresenter;
import com.drone.show.hud.views.TextView;

import io.dronefleet.mavlink.ardupilotmega.EkfStatusReport;

public class EkfVarianceTextPresenter extends TextPresenter {

	//TODO : rajouter les 2 variances qui manquent (gps...) dans le code de dronefleet
	public static enum VarianceType{
		VELOCITY_VARIANCE,
		POS_HORIZ_VARIANCE,
		POS_VERT_VARIANCE,
		COMPASS_VARIANCE,
		TERRAIN_ALT_VARIANCE,
		AIR_SPEED_VARIANCE
	}

	public static float ORANGE_VALUE_LIMIT = 0.5f;
	public static float RED_VALUE_LIMIT = 0.8f;

	VarianceType varianceType;

	public EkfVarianceTextPresenter(TextView textView, String event, VarianceType varianceType) {
		super(textView, event);

		this.varianceType = varianceType;
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		String propertyName = evt.getPropertyName();

		if (propertyName.equals(this.getEvent())) {

			EkfStatusReport ekfStatusReport = (EkfStatusReport) evt.getNewValue();
			float value = 999;

			switch(this.varianceType) {
			case VELOCITY_VARIANCE:
				value = ekfStatusReport.velocityVariance();
				break;
			case POS_HORIZ_VARIANCE:
				value = ekfStatusReport.posHorizVariance();
				break;
			case POS_VERT_VARIANCE:
				value = ekfStatusReport.posVertVariance();
				break;
			case COMPASS_VARIANCE:
				value = ekfStatusReport.compassVariance();
				break;
			case TERRAIN_ALT_VARIANCE:
				value = ekfStatusReport.terrainAltVariance();
				break;
			case AIR_SPEED_VARIANCE:
				value = ekfStatusReport.airspeedVariance();
				break;
			}
			
			this.getTextView().setText(""+Tools.roundFloat(value, 6));
			if(value>=RED_VALUE_LIMIT)
				this.getTextView().setFontColor(Color.RED);
			else if(value>=ORANGE_VALUE_LIMIT)
				this.getTextView().setFontColor(Color.ORANGE);
			else
				this.getTextView().setFontColor(Color.GREEN);
			
		}
	}

}

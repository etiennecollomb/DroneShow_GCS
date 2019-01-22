package com.drone.show.hud.presenters.ekftextpresenter;

import java.beans.PropertyChangeEvent;

import com.badlogic.gdx.graphics.Color;
import com.drone.show.hud.presenters.TextPresenter;
import com.drone.show.hud.views.TextView;

import io.dronefleet.mavlink.ardupilotmega.EkfStatusFlags;
import io.dronefleet.mavlink.ardupilotmega.EkfStatusReport;

public class EkfFlagTextPresenter extends TextPresenter {

	EkfStatusFlags ekfStatusFlag;
	
	public EkfFlagTextPresenter(TextView textView, String event, EkfStatusFlags ekfStatusFlag) {
		super(textView, event);
		
		this.ekfStatusFlag = ekfStatusFlag;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		String propertyName = evt.getPropertyName();
		
		if (propertyName.equals(this.getEvent())) {
			
			EkfStatusReport ekfStatusReport = (EkfStatusReport) evt.getNewValue();
			if(ekfStatusReport.flags().flagsEnabled(this.ekfStatusFlag)) {
				if(this.ekfStatusFlag.equals(EkfStatusFlags.EKF_CONST_POS_MODE))
					this.getTextView().setFontColor(Color.RED);
				else
					this.getTextView().setFontColor(Color.GREEN);
			}else {
				if(this.ekfStatusFlag.equals(EkfStatusFlags.EKF_CONST_POS_MODE))
					this.getTextView().setFontColor(Color.GREEN);
				else
					this.getTextView().setFontColor(Color.RED);
			}
			
		}
	}

	
	
}

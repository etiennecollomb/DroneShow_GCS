package com.drone.show.gcs.DEPRECATED_oldStuff;

import com.badlogic.gdx.graphics.Color;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.DEPRECATED_oldStuff.MavlinkAction.MavlinkActionType;
import com.drone.show.gcs.MavlinkCommunicationModel.Mode;
import com.drone.show.generic.Tools;

import io.dronefleet.mavlink.MavlinkConnection;


public class RedToGreenToBlueLedColor extends MavlinkAction {


	protected static final float MAX_PWM = 2000;
	private static final float HERTZ = 10f; //frequence d'envoie de la commande

	private float counter;
	private boolean isFwd;


	public RedToGreenToBlueLedColor(MavlinkConnection connection, MavlinkCommunicationModel droneModel) {
		super(MavlinkActionType.INIT_LED, connection, droneModel);
		this.setSend_command_timer( System.currentTimeMillis() );
		this.setSend_command_interval((long)(1000.0f/HERTZ));

		this.counter = 0;
		this.isFwd = true;
	}



	@Override
	public void update(float deltaTime) {

		Color color = new Color(0f,0f,0f, 0f);
		this.counter = this.counter +20;
		float currentValue=0;

		if(isFwd) {
			if(this.counter>MAX_PWM) {
				this.isFwd=false;
				this.counter = 0;
			}
			else {
				currentValue = this.counter;
			}
		}
		else {
			if(this.counter>MAX_PWM) {
				this.isFwd=true;
				this.counter = 0;
			}
			else {
				currentValue = MAX_PWM - this.counter;
			}
		}

		Tools.setColorRedToGreenToBlue(color, 0f, MAX_PWM, currentValue);
//		MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setRGB_to_channel((int)(color.r*MAX_PWM), (int)(color.g*MAX_PWM), (int)(color.b*MAX_PWM)));

//		System.out.println(currentValue);
//		System.out.println((int)(color.r*MAX_PWM)+","+(int)(color.g*MAX_PWM)+","+(int)(color.b*MAX_PWM));
		//this.setFinished(true);
	}

	@Override
	public void onCommandAckReceived() {
		// TODO Auto-generated method stub
	}

}

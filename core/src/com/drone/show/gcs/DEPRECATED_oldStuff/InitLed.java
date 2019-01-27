package com.drone.show.gcs.DEPRECATED_oldStuff;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.RealDroneModel;

import io.dronefleet.mavlink.MavlinkConnection;


public class InitLed{

//	private static final float HERTZ = 2f; //frequence d'envoie de la commande
//	
//	private static final int SERVO_MIN = 10;
//	private static final int SERVO_MAX = 2000;
//	
//
//	public InitLed(MavlinkConnection connection, MavlinkCommunicationModel droneModel) {
//		super(MavlinkActionType.INIT_LED, connection, droneModel);
//		this.setSend_command_timer( System.currentTimeMillis() );
//		this.setSend_command_interval((long)(1000.0f/HERTZ));
//	}
//
//	@Override
//	public void update(float deltaTime) {
//
//		if(System.currentTimeMillis() - this.getSend_command_timer() > this.getSend_command_interval()) {
//			this.setSend_command_timer( System.currentTimeMillis() );
//			
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setCHx_OPT(6, MavLinkToolKit.DISABLE));
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setSERVOx_FUNCTION(6, MavLinkToolKit.RC_PASS_TRU));
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setCHx_OPT(7, MavLinkToolKit.DISABLE));
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setSERVOx_FUNCTION(7, MavLinkToolKit.RC_PASS_TRU));
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setCHx_OPT(8, MavLinkToolKit.DISABLE));
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setSERVOx_FUNCTION(8, MavLinkToolKit.RC_PASS_TRU));
//			
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setSERVOx_MIN(6, SERVO_MIN));
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setSERVOx_MAX(6, SERVO_MAX));
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setSERVOx_MIN(7, SERVO_MIN));
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setSERVOx_MAX(7, SERVO_MAX));
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setSERVOx_MIN(8, SERVO_MIN));
//			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setSERVOx_MAX(8, SERVO_MAX));
//		}
//		
//		//TODO : mettre un propoerty change listener sur un objet led, qui udate les valueures a chaque fois qu on recoit le bon param value
//		/** check that everything is set up */
//		if(this.getDroneModel().getCh6_opt() == MavLinkToolKit.DISABLE && this.getDroneModel().getServo6_function() == MavLinkToolKit.RC_PASS_TRU
//				&& this.getDroneModel().getCh7_opt() == MavLinkToolKit.DISABLE && this.getDroneModel().getServo7_function() == MavLinkToolKit.RC_PASS_TRU
//				&& this.getDroneModel().getCh8_opt() == MavLinkToolKit.DISABLE && this.getDroneModel().getServo8_function() == MavLinkToolKit.RC_PASS_TRU
//				&& this.getDroneModel().getServo6_min() == SERVO_MIN && this.getDroneModel().getServo6_max() == SERVO_MAX
//				&& this.getDroneModel().getServo7_min() == SERVO_MIN && this.getDroneModel().getServo7_max() == SERVO_MAX
//				&& this.getDroneModel().getServo8_min() == SERVO_MIN && this.getDroneModel().getServo8_max() == SERVO_MAX
//				) {
//			this.setFinished( true );
//		}
//	}
//
//	@Override
//	public void onCommandAckReceived() {
//		// TODO Auto-generated method stub
//	}

}

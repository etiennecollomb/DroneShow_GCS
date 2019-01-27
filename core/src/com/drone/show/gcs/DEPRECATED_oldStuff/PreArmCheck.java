package com.drone.show.gcs.DEPRECATED_oldStuff;

import com.badlogic.gdx.graphics.Color;
import com.drone.show.GlobalManager;
import com.drone.show.gcs.RealDroneModel;
import com.drone.show.generic.Tools;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.common.GpsFixType;



public class PreArmCheck {

//	private static final float HERTZ = 0.5f;
//
//	private static final int GPS_FIX_TYPE_NO_GPS = 0;
//	private static final int GPS_FIX_TYPE_NO_FIX = 1;
//	private static final int GPS_FIX_TYPE_2D_FIX = 2;
//	private static final int GPS_FIX_TYPE_3D_FIX = 3;
//	private static final int GPS_FIX_TYPE_DGPS = 4;
//	private static final int GPS_FIX_TYPE_RTK_FLOAT = 5;
//	private static final int GPS_FIX_TYPE_RTK_FIXED = 6;
//	private static final int GPS_FIX_TYPE_STATIC = 7;
//	private static final int GPS_FIX_TYPE_PPP = 8;
//
//
//	/** GPS stuff
//	 * Par ordre croissant de precision pour Kalhman
//	 */
//	//No RTK
//	private boolean is3DFixNeeded = true;
//	private boolean isDGPSFixNeeded = false;
//	//with RTK
//	private boolean isRTKFloatNeeded = false;
//	private boolean isRTKFixNeeded = false;
//
//	
//	public PreArmCheck(MavlinkConnection connection, MavlinkCommunicationModel droneModel) {
//		super(MavlinkActionType.PRE_ARM_CHECK, connection, droneModel);
//		this.setSend_command_interval((long)(1000.0f/HERTZ));
//	}
//
//
//	@Override
//	public void update(float deltaTime) {
//
//		if(System.currentTimeMillis() - this.getSend_command_timer() > this.getSend_command_interval()) {	
//			//if(GlobalManager.ISDEBUG) Tools.writeLogs("Waiting for GPS... (" + this.getDroneModel().getNumberOfSatellite() + ")");
//		}
//
//		if(this.getMavComModel().getGpsFixType() != null) {
//
//			boolean isAllOK = true;
//
//			int currentGPSFixType = -1;
//			if(this.getMavComModel().getGpsFixType() == GpsFixType.GPS_FIX_TYPE_NO_GPS) currentGPSFixType = GPS_FIX_TYPE_NO_GPS;
//			if(this.getMavComModel().getGpsFixType() == GpsFixType.GPS_FIX_TYPE_NO_FIX) currentGPSFixType = GPS_FIX_TYPE_NO_FIX;
//			if(this.getMavComModel().getGpsFixType() == GpsFixType.GPS_FIX_TYPE_2D_FIX) currentGPSFixType = GPS_FIX_TYPE_2D_FIX;
//			if(this.getMavComModel().getGpsFixType() == GpsFixType.GPS_FIX_TYPE_3D_FIX) currentGPSFixType = GPS_FIX_TYPE_3D_FIX;
//			if(this.getMavComModel().getGpsFixType() == GpsFixType.GPS_FIX_TYPE_DGPS) currentGPSFixType = GPS_FIX_TYPE_DGPS;
//			if(this.getMavComModel().getGpsFixType() == GpsFixType.GPS_FIX_TYPE_RTK_FLOAT) currentGPSFixType = GPS_FIX_TYPE_RTK_FLOAT;
//			if(this.getMavComModel().getGpsFixType() == GpsFixType.GPS_FIX_TYPE_RTK_FIXED) currentGPSFixType = GPS_FIX_TYPE_RTK_FIXED;
//			if(this.getMavComModel().getGpsFixType() == GpsFixType.GPS_FIX_TYPE_STATIC) currentGPSFixType = GPS_FIX_TYPE_STATIC;
//			if(this.getMavComModel().getGpsFixType() == GpsFixType.GPS_FIX_TYPE_PPP) currentGPSFixType = GPS_FIX_TYPE_PPP;
//
//
//			if(this.is3DFixNeeded && currentGPSFixType < GPS_FIX_TYPE_3D_FIX ) {
//				//if(GlobalManager.ISDEBUG) Tools.writeLogs(" gpsFixType < " + GPS_FIX_TYPE_3D_FIX + " (" + this.getDroneModel().getGpsFixType().name() + ")");
//				isAllOK = false;
//			}
//
//			if(this.isDGPSFixNeeded && currentGPSFixType < GPS_FIX_TYPE_DGPS ) {
//				//if(GlobalManager.ISDEBUG) Tools.writeLogs(" gpsFixType < " + GPS_FIX_TYPE_DGPS + " (" + this.getDroneModel().getGpsFixType().name() + ")");
//				isAllOK = false;
//			}
//
//			if(this.isRTKFloatNeeded && currentGPSFixType < GPS_FIX_TYPE_RTK_FLOAT ) {
//				//if(GlobalManager.ISDEBUG) Tools.writeLogs(" gpsFixType < " + GPS_FIX_TYPE_RTK_FLOAT + " (" + this.getDroneModel().getGpsFixType().name() + ")");
//				isAllOK = false;
//			}
//
//			if(this.isRTKFixNeeded && currentGPSFixType < GPS_FIX_TYPE_RTK_FIXED ) {
//				//if(GlobalManager.ISDEBUG) Tools.writeLogs(" gpsFixType < " + GPS_FIX_TYPE_RTK_FIXED + " (" + this.getDroneModel().getGpsFixType().name() + ")");
//				isAllOK = false;
//			}
//
//			this.setFinished( isAllOK );
//
//		}
//	}
//
//	@Override
//	public void onCommandAckReceived() {
//		// TODO Auto-generated method stub
//	}

}

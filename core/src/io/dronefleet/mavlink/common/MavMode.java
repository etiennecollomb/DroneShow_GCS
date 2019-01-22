package io.dronefleet.mavlink.common;

import io.dronefleet.mavlink.annotations.MavlinkEntryInfo;
import io.dronefleet.mavlink.annotations.MavlinkEnum;

/**
 * These defines are predefined OR-combined mode flags. There is no need to use values from this 
 * enum, but it simplifies the use of the mode flags. Note that manual input is enabled in all modes 
 * as a safety override. 
 */
@MavlinkEnum
public enum MavMode {
	
	//RAJOUT Etienne pour le mode GUIDED
	@MavlinkEntryInfo(1)
    MAV_MODE_CUSTOM,
	
	
	
    /**
     * System is not ready to fly, booting, calibrating, etc. No flag is set. 
     */
    @MavlinkEntryInfo(0)
    MAV_MODE_PREFLIGHT,

    /**
     * System is allowed to be active, under assisted RC control. 
     */
    @MavlinkEntryInfo(80)
    MAV_MODE_STABILIZE_DISARMED,

    /**
     * System is allowed to be active, under assisted RC control. 
     */
    @MavlinkEntryInfo(208)
    MAV_MODE_STABILIZE_ARMED,

    /**
     * System is allowed to be active, under manual (RC) control, no stabilization 
     */
    @MavlinkEntryInfo(64)
    MAV_MODE_MANUAL_DISARMED,

    /**
     * System is allowed to be active, under manual (RC) control, no stabilization 
     */
    @MavlinkEntryInfo(192)
    MAV_MODE_MANUAL_ARMED,

    /**
     * System is allowed to be active, under autonomous control, manual setpoint 
     */
    @MavlinkEntryInfo(88)
    MAV_MODE_GUIDED_DISARMED,

    /**
     * System is allowed to be active, under autonomous control, manual setpoint 
     */
    @MavlinkEntryInfo(216)
    MAV_MODE_GUIDED_ARMED,

    /**
     * System is allowed to be active, under autonomous control and navigation (the trajectory is 
     * decided onboard and not pre-programmed by waypoints) 
     */
    @MavlinkEntryInfo(92)
    MAV_MODE_AUTO_DISARMED,

    /**
     * System is allowed to be active, under autonomous control and navigation (the trajectory is 
     * decided onboard and not pre-programmed by waypoints) 
     */
    @MavlinkEntryInfo(220)
    MAV_MODE_AUTO_ARMED,

    /**
     * UNDEFINED mode. This solely depends on the autopilot - use with caution, intended for 
     * developers only. 
     */
    @MavlinkEntryInfo(66)
    MAV_MODE_TEST_DISARMED,

    /**
     * UNDEFINED mode. This solely depends on the autopilot - use with caution, intended for 
     * developers only. 
     */
    @MavlinkEntryInfo(194)
    MAV_MODE_TEST_ARMED
}

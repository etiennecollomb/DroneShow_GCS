package io.dronefleet.mavlink.ardupilotmega;

import io.dronefleet.mavlink.annotations.MavlinkEntryInfo;
import io.dronefleet.mavlink.annotations.MavlinkEnum;

/**
 *  
 */
@MavlinkEnum
public enum GoproProtuneExposure {
    /**
     * -5.0 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(0)
    GOPRO_PROTUNE_EXPOSURE_NEG_5_0,

    /**
     * -4.5 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(1)
    GOPRO_PROTUNE_EXPOSURE_NEG_4_5,

    /**
     * -4.0 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(2)
    GOPRO_PROTUNE_EXPOSURE_NEG_4_0,

    /**
     * -3.5 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(3)
    GOPRO_PROTUNE_EXPOSURE_NEG_3_5,

    /**
     * -3.0 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(4)
    GOPRO_PROTUNE_EXPOSURE_NEG_3_0,

    /**
     * -2.5 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(5)
    GOPRO_PROTUNE_EXPOSURE_NEG_2_5,

    /**
     * -2.0 EV 
     */
    @MavlinkEntryInfo(6)
    GOPRO_PROTUNE_EXPOSURE_NEG_2_0,

    /**
     * -1.5 EV 
     */
    @MavlinkEntryInfo(7)
    GOPRO_PROTUNE_EXPOSURE_NEG_1_5,

    /**
     * -1.0 EV 
     */
    @MavlinkEntryInfo(8)
    GOPRO_PROTUNE_EXPOSURE_NEG_1_0,

    /**
     * -0.5 EV 
     */
    @MavlinkEntryInfo(9)
    GOPRO_PROTUNE_EXPOSURE_NEG_0_5,

    /**
     * 0.0 EV 
     */
    @MavlinkEntryInfo(10)
    GOPRO_PROTUNE_EXPOSURE_ZERO,

    /**
     * +0.5 EV 
     */
    @MavlinkEntryInfo(11)
    GOPRO_PROTUNE_EXPOSURE_POS_0_5,

    /**
     * +1.0 EV 
     */
    @MavlinkEntryInfo(12)
    GOPRO_PROTUNE_EXPOSURE_POS_1_0,

    /**
     * +1.5 EV 
     */
    @MavlinkEntryInfo(13)
    GOPRO_PROTUNE_EXPOSURE_POS_1_5,

    /**
     * +2.0 EV 
     */
    @MavlinkEntryInfo(14)
    GOPRO_PROTUNE_EXPOSURE_POS_2_0,

    /**
     * +2.5 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(15)
    GOPRO_PROTUNE_EXPOSURE_POS_2_5,

    /**
     * +3.0 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(16)
    GOPRO_PROTUNE_EXPOSURE_POS_3_0,

    /**
     * +3.5 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(17)
    GOPRO_PROTUNE_EXPOSURE_POS_3_5,

    /**
     * +4.0 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(18)
    GOPRO_PROTUNE_EXPOSURE_POS_4_0,

    /**
     * +4.5 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(19)
    GOPRO_PROTUNE_EXPOSURE_POS_4_5,

    /**
     * +5.0 EV (Hero 3+ Only) 
     */
    @MavlinkEntryInfo(20)
    GOPRO_PROTUNE_EXPOSURE_POS_5_0
}

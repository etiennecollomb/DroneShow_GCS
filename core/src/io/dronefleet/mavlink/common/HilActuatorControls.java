package io.dronefleet.mavlink.common;

import io.dronefleet.mavlink.annotations.MavlinkFieldInfo;
import io.dronefleet.mavlink.annotations.MavlinkMessageBuilder;
import io.dronefleet.mavlink.annotations.MavlinkMessageInfo;
import io.dronefleet.mavlink.util.EnumValue;
import java.lang.Enum;
import java.lang.Float;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Sent from autopilot to simulation. Hardware in the loop control outputs (replacement for 
 * {@link io.dronefleet.mavlink.common.HilControls HIL_CONTROLS}) 
 */
@MavlinkMessageInfo(
        id = 93,
        crc = 47,
        description = "Sent from autopilot to simulation. Hardware in the loop control outputs (replacement for HIL_CONTROLS)"
)
public final class HilActuatorControls {
    private final BigInteger timeUsec;

    private final List<Float> controls;

    private final EnumValue<MavMode> mode;

    private final BigInteger flags;

    private HilActuatorControls(BigInteger timeUsec, List<Float> controls, EnumValue<MavMode> mode,
            BigInteger flags) {
        this.timeUsec = timeUsec;
        this.controls = controls;
        this.mode = mode;
        this.flags = flags;
    }

    /**
     * Returns a builder instance for this message.
     */
    @MavlinkMessageBuilder
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Timestamp (microseconds since UNIX epoch or microseconds since system boot) 
     */
    @MavlinkFieldInfo(
            position = 1,
            unitSize = 8,
            description = "Timestamp (microseconds since UNIX epoch or microseconds since system boot)"
    )
    public final BigInteger timeUsec() {
        return this.timeUsec;
    }

    /**
     * Control outputs -1 .. 1. Channel assignment depends on the simulated hardware. 
     */
    @MavlinkFieldInfo(
            position = 2,
            unitSize = 4,
            arraySize = 16,
            description = "Control outputs -1 .. 1. Channel assignment depends on the simulated hardware."
    )
    public final List<Float> controls() {
        return this.controls;
    }

    /**
     * System mode ({@link io.dronefleet.mavlink.common.MavMode MAV_MODE}), includes arming state. 
     */
    @MavlinkFieldInfo(
            position = 3,
            unitSize = 1,
            enumType = MavMode.class,
            description = "System mode (MAV_MODE), includes arming state."
    )
    public final EnumValue<MavMode> mode() {
        return this.mode;
    }

    /**
     * Flags as bitfield, reserved for future use. 
     */
    @MavlinkFieldInfo(
            position = 4,
            unitSize = 8,
            description = "Flags as bitfield, reserved for future use."
    )
    public final BigInteger flags() {
        return this.flags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(o.getClass())) return false;
        HilActuatorControls other = (HilActuatorControls)o;
        if (!Objects.deepEquals(timeUsec, other.timeUsec)) return false;
        if (!Objects.deepEquals(controls, other.controls)) return false;
        if (!Objects.deepEquals(mode, other.mode)) return false;
        if (!Objects.deepEquals(flags, other.flags)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + Objects.hashCode(timeUsec);
        result = 31 * result + Objects.hashCode(controls);
        result = 31 * result + Objects.hashCode(mode);
        result = 31 * result + Objects.hashCode(flags);
        return result;
    }

    @Override
    public String toString() {
        return "HilActuatorControls{timeUsec=" + timeUsec
                 + ", controls=" + controls
                 + ", mode=" + mode
                 + ", flags=" + flags + "}";
    }

    public static final class Builder {
        private BigInteger timeUsec;

        private List<Float> controls;

        private EnumValue<MavMode> mode;

        private BigInteger flags;

        /**
         * Timestamp (microseconds since UNIX epoch or microseconds since system boot) 
         */
        @MavlinkFieldInfo(
                position = 1,
                unitSize = 8,
                description = "Timestamp (microseconds since UNIX epoch or microseconds since system boot)"
        )
        public final Builder timeUsec(BigInteger timeUsec) {
            this.timeUsec = timeUsec;
            return this;
        }

        /**
         * Control outputs -1 .. 1. Channel assignment depends on the simulated hardware. 
         */
        @MavlinkFieldInfo(
                position = 2,
                unitSize = 4,
                arraySize = 16,
                description = "Control outputs -1 .. 1. Channel assignment depends on the simulated hardware."
        )
        public final Builder controls(List<Float> controls) {
            this.controls = controls;
            return this;
        }

        /**
         * System mode ({@link io.dronefleet.mavlink.common.MavMode MAV_MODE}), includes arming state. 
         */
        @MavlinkFieldInfo(
                position = 3,
                unitSize = 1,
                enumType = MavMode.class,
                description = "System mode (MAV_MODE), includes arming state."
        )
        public final Builder mode(EnumValue<MavMode> mode) {
            this.mode = mode;
            return this;
        }

        /**
         * System mode ({@link io.dronefleet.mavlink.common.MavMode MAV_MODE}), includes arming state. 
         */
        public final Builder mode(MavMode entry) {
            return mode(EnumValue.of(entry));
        }

        /**
         * System mode ({@link io.dronefleet.mavlink.common.MavMode MAV_MODE}), includes arming state. 
         */
        public final Builder mode(Enum... flags) {
            return mode(EnumValue.create(flags));
        }

        /**
         * System mode ({@link io.dronefleet.mavlink.common.MavMode MAV_MODE}), includes arming state. 
         */
        public final Builder mode(Collection<Enum> flags) {
            return mode(EnumValue.create(flags));
        }

        /**
         * Flags as bitfield, reserved for future use. 
         */
        @MavlinkFieldInfo(
                position = 4,
                unitSize = 8,
                description = "Flags as bitfield, reserved for future use."
        )
        public final Builder flags(BigInteger flags) {
            this.flags = flags;
            return this;
        }

        public final HilActuatorControls build() {
            return new HilActuatorControls(timeUsec, controls, mode, flags);
        }
    }
}

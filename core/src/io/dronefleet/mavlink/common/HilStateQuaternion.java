package io.dronefleet.mavlink.common;

import io.dronefleet.mavlink.annotations.MavlinkFieldInfo;
import io.dronefleet.mavlink.annotations.MavlinkMessageBuilder;
import io.dronefleet.mavlink.annotations.MavlinkMessageInfo;
import java.lang.Float;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * Sent from simulation to autopilot, avoids in contrast to {@link io.dronefleet.mavlink.common.HilState HIL_STATE} singularities. This 
 * packet is useful for high throughput applications such as hardware in the loop simulations. 
 */
@MavlinkMessageInfo(
        id = 115,
        crc = 4,
        description = "Sent from simulation to autopilot, avoids in contrast to HIL_STATE singularities. This packet is useful for high throughput applications such as hardware in the loop simulations."
)
public final class HilStateQuaternion {
    private final BigInteger timeUsec;

    private final List<Float> attitudeQuaternion;

    private final float rollspeed;

    private final float pitchspeed;

    private final float yawspeed;

    private final int lat;

    private final int lon;

    private final int alt;

    private final int vx;

    private final int vy;

    private final int vz;

    private final int indAirspeed;

    private final int trueAirspeed;

    private final int xacc;

    private final int yacc;

    private final int zacc;

    private HilStateQuaternion(BigInteger timeUsec, List<Float> attitudeQuaternion, float rollspeed,
            float pitchspeed, float yawspeed, int lat, int lon, int alt, int vx, int vy, int vz,
            int indAirspeed, int trueAirspeed, int xacc, int yacc, int zacc) {
        this.timeUsec = timeUsec;
        this.attitudeQuaternion = attitudeQuaternion;
        this.rollspeed = rollspeed;
        this.pitchspeed = pitchspeed;
        this.yawspeed = yawspeed;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.indAirspeed = indAirspeed;
        this.trueAirspeed = trueAirspeed;
        this.xacc = xacc;
        this.yacc = yacc;
        this.zacc = zacc;
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
     * Vehicle attitude expressed as normalized quaternion in w, x, y, z order (with 1 0 0 0 being the 
     * null-rotation) 
     */
    @MavlinkFieldInfo(
            position = 2,
            unitSize = 4,
            arraySize = 4,
            description = "Vehicle attitude expressed as normalized quaternion in w, x, y, z order (with 1 0 0 0 being the null-rotation)"
    )
    public final List<Float> attitudeQuaternion() {
        return this.attitudeQuaternion;
    }

    /**
     * Body frame roll / phi angular speed (rad/s) 
     */
    @MavlinkFieldInfo(
            position = 3,
            unitSize = 4,
            description = "Body frame roll / phi angular speed (rad/s)"
    )
    public final float rollspeed() {
        return this.rollspeed;
    }

    /**
     * Body frame pitch / theta angular speed (rad/s) 
     */
    @MavlinkFieldInfo(
            position = 4,
            unitSize = 4,
            description = "Body frame pitch / theta angular speed (rad/s)"
    )
    public final float pitchspeed() {
        return this.pitchspeed;
    }

    /**
     * Body frame yaw / psi angular speed (rad/s) 
     */
    @MavlinkFieldInfo(
            position = 5,
            unitSize = 4,
            description = "Body frame yaw / psi angular speed (rad/s)"
    )
    public final float yawspeed() {
        return this.yawspeed;
    }

    /**
     * Latitude, expressed as degrees * 1E7 
     */
    @MavlinkFieldInfo(
            position = 6,
            unitSize = 4,
            signed = true,
            description = "Latitude, expressed as degrees * 1E7"
    )
    public final int lat() {
        return this.lat;
    }

    /**
     * Longitude, expressed as degrees * 1E7 
     */
    @MavlinkFieldInfo(
            position = 7,
            unitSize = 4,
            signed = true,
            description = "Longitude, expressed as degrees * 1E7"
    )
    public final int lon() {
        return this.lon;
    }

    /**
     * Altitude in meters, expressed as * 1000 (millimeters) 
     */
    @MavlinkFieldInfo(
            position = 8,
            unitSize = 4,
            signed = true,
            description = "Altitude in meters, expressed as * 1000 (millimeters)"
    )
    public final int alt() {
        return this.alt;
    }

    /**
     * Ground X Speed (Latitude), expressed as cm/s 
     */
    @MavlinkFieldInfo(
            position = 9,
            unitSize = 2,
            signed = true,
            description = "Ground X Speed (Latitude), expressed as cm/s"
    )
    public final int vx() {
        return this.vx;
    }

    /**
     * Ground Y Speed (Longitude), expressed as cm/s 
     */
    @MavlinkFieldInfo(
            position = 10,
            unitSize = 2,
            signed = true,
            description = "Ground Y Speed (Longitude), expressed as cm/s"
    )
    public final int vy() {
        return this.vy;
    }

    /**
     * Ground Z Speed (Altitude), expressed as cm/s 
     */
    @MavlinkFieldInfo(
            position = 11,
            unitSize = 2,
            signed = true,
            description = "Ground Z Speed (Altitude), expressed as cm/s"
    )
    public final int vz() {
        return this.vz;
    }

    /**
     * Indicated airspeed, expressed as cm/s 
     */
    @MavlinkFieldInfo(
            position = 12,
            unitSize = 2,
            description = "Indicated airspeed, expressed as cm/s"
    )
    public final int indAirspeed() {
        return this.indAirspeed;
    }

    /**
     * True airspeed, expressed as cm/s 
     */
    @MavlinkFieldInfo(
            position = 13,
            unitSize = 2,
            description = "True airspeed, expressed as cm/s"
    )
    public final int trueAirspeed() {
        return this.trueAirspeed;
    }

    /**
     * X acceleration (mg) 
     */
    @MavlinkFieldInfo(
            position = 14,
            unitSize = 2,
            signed = true,
            description = "X acceleration (mg)"
    )
    public final int xacc() {
        return this.xacc;
    }

    /**
     * Y acceleration (mg) 
     */
    @MavlinkFieldInfo(
            position = 15,
            unitSize = 2,
            signed = true,
            description = "Y acceleration (mg)"
    )
    public final int yacc() {
        return this.yacc;
    }

    /**
     * Z acceleration (mg) 
     */
    @MavlinkFieldInfo(
            position = 16,
            unitSize = 2,
            signed = true,
            description = "Z acceleration (mg)"
    )
    public final int zacc() {
        return this.zacc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(o.getClass())) return false;
        HilStateQuaternion other = (HilStateQuaternion)o;
        if (!Objects.deepEquals(timeUsec, other.timeUsec)) return false;
        if (!Objects.deepEquals(attitudeQuaternion, other.attitudeQuaternion)) return false;
        if (!Objects.deepEquals(rollspeed, other.rollspeed)) return false;
        if (!Objects.deepEquals(pitchspeed, other.pitchspeed)) return false;
        if (!Objects.deepEquals(yawspeed, other.yawspeed)) return false;
        if (!Objects.deepEquals(lat, other.lat)) return false;
        if (!Objects.deepEquals(lon, other.lon)) return false;
        if (!Objects.deepEquals(alt, other.alt)) return false;
        if (!Objects.deepEquals(vx, other.vx)) return false;
        if (!Objects.deepEquals(vy, other.vy)) return false;
        if (!Objects.deepEquals(vz, other.vz)) return false;
        if (!Objects.deepEquals(indAirspeed, other.indAirspeed)) return false;
        if (!Objects.deepEquals(trueAirspeed, other.trueAirspeed)) return false;
        if (!Objects.deepEquals(xacc, other.xacc)) return false;
        if (!Objects.deepEquals(yacc, other.yacc)) return false;
        if (!Objects.deepEquals(zacc, other.zacc)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + Objects.hashCode(timeUsec);
        result = 31 * result + Objects.hashCode(attitudeQuaternion);
        result = 31 * result + Objects.hashCode(rollspeed);
        result = 31 * result + Objects.hashCode(pitchspeed);
        result = 31 * result + Objects.hashCode(yawspeed);
        result = 31 * result + Objects.hashCode(lat);
        result = 31 * result + Objects.hashCode(lon);
        result = 31 * result + Objects.hashCode(alt);
        result = 31 * result + Objects.hashCode(vx);
        result = 31 * result + Objects.hashCode(vy);
        result = 31 * result + Objects.hashCode(vz);
        result = 31 * result + Objects.hashCode(indAirspeed);
        result = 31 * result + Objects.hashCode(trueAirspeed);
        result = 31 * result + Objects.hashCode(xacc);
        result = 31 * result + Objects.hashCode(yacc);
        result = 31 * result + Objects.hashCode(zacc);
        return result;
    }

    @Override
    public String toString() {
        return "HilStateQuaternion{timeUsec=" + timeUsec
                 + ", attitudeQuaternion=" + attitudeQuaternion
                 + ", rollspeed=" + rollspeed
                 + ", pitchspeed=" + pitchspeed
                 + ", yawspeed=" + yawspeed
                 + ", lat=" + lat
                 + ", lon=" + lon
                 + ", alt=" + alt
                 + ", vx=" + vx
                 + ", vy=" + vy
                 + ", vz=" + vz
                 + ", indAirspeed=" + indAirspeed
                 + ", trueAirspeed=" + trueAirspeed
                 + ", xacc=" + xacc
                 + ", yacc=" + yacc
                 + ", zacc=" + zacc + "}";
    }

    public static final class Builder {
        private BigInteger timeUsec;

        private List<Float> attitudeQuaternion;

        private float rollspeed;

        private float pitchspeed;

        private float yawspeed;

        private int lat;

        private int lon;

        private int alt;

        private int vx;

        private int vy;

        private int vz;

        private int indAirspeed;

        private int trueAirspeed;

        private int xacc;

        private int yacc;

        private int zacc;

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
         * Vehicle attitude expressed as normalized quaternion in w, x, y, z order (with 1 0 0 0 being the 
         * null-rotation) 
         */
        @MavlinkFieldInfo(
                position = 2,
                unitSize = 4,
                arraySize = 4,
                description = "Vehicle attitude expressed as normalized quaternion in w, x, y, z order (with 1 0 0 0 being the null-rotation)"
        )
        public final Builder attitudeQuaternion(List<Float> attitudeQuaternion) {
            this.attitudeQuaternion = attitudeQuaternion;
            return this;
        }

        /**
         * Body frame roll / phi angular speed (rad/s) 
         */
        @MavlinkFieldInfo(
                position = 3,
                unitSize = 4,
                description = "Body frame roll / phi angular speed (rad/s)"
        )
        public final Builder rollspeed(float rollspeed) {
            this.rollspeed = rollspeed;
            return this;
        }

        /**
         * Body frame pitch / theta angular speed (rad/s) 
         */
        @MavlinkFieldInfo(
                position = 4,
                unitSize = 4,
                description = "Body frame pitch / theta angular speed (rad/s)"
        )
        public final Builder pitchspeed(float pitchspeed) {
            this.pitchspeed = pitchspeed;
            return this;
        }

        /**
         * Body frame yaw / psi angular speed (rad/s) 
         */
        @MavlinkFieldInfo(
                position = 5,
                unitSize = 4,
                description = "Body frame yaw / psi angular speed (rad/s)"
        )
        public final Builder yawspeed(float yawspeed) {
            this.yawspeed = yawspeed;
            return this;
        }

        /**
         * Latitude, expressed as degrees * 1E7 
         */
        @MavlinkFieldInfo(
                position = 6,
                unitSize = 4,
                signed = true,
                description = "Latitude, expressed as degrees * 1E7"
        )
        public final Builder lat(int lat) {
            this.lat = lat;
            return this;
        }

        /**
         * Longitude, expressed as degrees * 1E7 
         */
        @MavlinkFieldInfo(
                position = 7,
                unitSize = 4,
                signed = true,
                description = "Longitude, expressed as degrees * 1E7"
        )
        public final Builder lon(int lon) {
            this.lon = lon;
            return this;
        }

        /**
         * Altitude in meters, expressed as * 1000 (millimeters) 
         */
        @MavlinkFieldInfo(
                position = 8,
                unitSize = 4,
                signed = true,
                description = "Altitude in meters, expressed as * 1000 (millimeters)"
        )
        public final Builder alt(int alt) {
            this.alt = alt;
            return this;
        }

        /**
         * Ground X Speed (Latitude), expressed as cm/s 
         */
        @MavlinkFieldInfo(
                position = 9,
                unitSize = 2,
                signed = true,
                description = "Ground X Speed (Latitude), expressed as cm/s"
        )
        public final Builder vx(int vx) {
            this.vx = vx;
            return this;
        }

        /**
         * Ground Y Speed (Longitude), expressed as cm/s 
         */
        @MavlinkFieldInfo(
                position = 10,
                unitSize = 2,
                signed = true,
                description = "Ground Y Speed (Longitude), expressed as cm/s"
        )
        public final Builder vy(int vy) {
            this.vy = vy;
            return this;
        }

        /**
         * Ground Z Speed (Altitude), expressed as cm/s 
         */
        @MavlinkFieldInfo(
                position = 11,
                unitSize = 2,
                signed = true,
                description = "Ground Z Speed (Altitude), expressed as cm/s"
        )
        public final Builder vz(int vz) {
            this.vz = vz;
            return this;
        }

        /**
         * Indicated airspeed, expressed as cm/s 
         */
        @MavlinkFieldInfo(
                position = 12,
                unitSize = 2,
                description = "Indicated airspeed, expressed as cm/s"
        )
        public final Builder indAirspeed(int indAirspeed) {
            this.indAirspeed = indAirspeed;
            return this;
        }

        /**
         * True airspeed, expressed as cm/s 
         */
        @MavlinkFieldInfo(
                position = 13,
                unitSize = 2,
                description = "True airspeed, expressed as cm/s"
        )
        public final Builder trueAirspeed(int trueAirspeed) {
            this.trueAirspeed = trueAirspeed;
            return this;
        }

        /**
         * X acceleration (mg) 
         */
        @MavlinkFieldInfo(
                position = 14,
                unitSize = 2,
                signed = true,
                description = "X acceleration (mg)"
        )
        public final Builder xacc(int xacc) {
            this.xacc = xacc;
            return this;
        }

        /**
         * Y acceleration (mg) 
         */
        @MavlinkFieldInfo(
                position = 15,
                unitSize = 2,
                signed = true,
                description = "Y acceleration (mg)"
        )
        public final Builder yacc(int yacc) {
            this.yacc = yacc;
            return this;
        }

        /**
         * Z acceleration (mg) 
         */
        @MavlinkFieldInfo(
                position = 16,
                unitSize = 2,
                signed = true,
                description = "Z acceleration (mg)"
        )
        public final Builder zacc(int zacc) {
            this.zacc = zacc;
            return this;
        }

        public final HilStateQuaternion build() {
            return new HilStateQuaternion(timeUsec, attitudeQuaternion, rollspeed, pitchspeed, yawspeed, lat, lon, alt, vx, vy, vz, indAirspeed, trueAirspeed, xacc, yacc, zacc);
        }
    }
}

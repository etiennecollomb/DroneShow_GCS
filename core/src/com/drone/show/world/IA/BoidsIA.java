package com.drone.show.world.IA;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.drone.show.world.model.DroneModel;
import com.drone.show.world.model.WorldModel;

public class BoidsIA {
	
	
//	/** WORKING CODE
//	 * mais non utilis� poru le moment
//	 */
//	
//	WorldModel worldModel;
//	long lastUpdateTime;
//	
//	public BoidsIA(WorldModel worldModel_) {
//		worldModel = worldModel_;
//	}
//
//	final float separation = 1f; //en m
////	final float speed = 50f; //en m/s
//	final float distanceMaxNeighbor = 3; //la distance max des neightbors doit etre ponderee a 3
//
//	public void onUpdate(float deltaTime) {
//
//		// check if we need to update Boid
//		/*
//		if (TimeUtils.nanoTime() - lastUpdateTime > 1000000000) {
//			System.out.println((TimeUtils.nanoTime() - lastUpdateTime));
//		} else {
//			return;
//		}
//		*/
//		
//		DroneModel drone;
//
//		/** IA */
//		ArrayList<Vector3> neighborsDistanceVectors;
//		Vector3 averageDistanceVector;
//		Vector3 separationVector;
//		Vector3 moveVector;
//		
//		for (Map.Entry<Integer, DroneModel> entry : worldModel.getDrones().entrySet()) {
//
//			drone = entry.getValue();
//			moveVector = new Vector3(0,0,0);
//
//			neighborsDistanceVectors = this.getNeighborDistanceVectors(drone);
//			averageDistanceVector = Tools.averageVector(neighborsDistanceVectors);
//
//			/** separation */
//			separationVector = new Vector3(averageDistanceVector).scl(-separation);
//
//			/** new position, average? of all rules (in real world, drone just move with the velocity...) */
//			separationVector.scl(speed); //speed
//			moveVector.add(separationVector);
//			
//			/** set new position */
//			moveVector.add( drone.getCurrentPosition() );
//			
//			/** check fixed limit (like ground) */
//			this.checkFixedLimits(moveVector);
//			
//			drone.setTargetPosition( moveVector );
//
//		}
//		lastUpdateTime = TimeUtils.nanoTime();
//	}
//
//
//	/** Neighbors Distance Vectors */
//	private void checkFixedLimits(Vector3 vector3_){
//		
//		//Ground
//		if(vector3_.z < 0)
//			vector3_.z = 0;
//	}
//
//	
//	
//	/** Neighbors Distance Vectors */
//	private ArrayList<Vector3> getNeighborDistanceVectors(Drone drone){
//
//		ArrayList<Vector3> neighborsPositionVectors = new ArrayList<Vector3>();
//
//		DroneModel neighborDrone;
//		Vector3 distanceVector;
//		
//		for (Map.Entry<String, DroneModel> entry : worldModel.getDrones().entrySet()) {
//
//			neighborDrone = entry.getValue();
//			//is in the neighborhood?
//			float distance = drone.getCurrentPosition().dst( neighborDrone.getCurrentPosition() );
//			if( distance <= Drone.NEIGHBORHOOD_RADIUS) {
//				distanceVector = new Vector3( neighborDrone.getCurrentPosition() ).sub( drone.getCurrentPosition() );
//				
//				//ponderation avec la distance : plus c est loin moins ca influence
//				distance = distance / Drone.NEIGHBORHOOD_RADIUS * this.distanceMaxNeighbor;
//				distanceVector.scl((float) (1f/(Math.exp(distance))));
//				
//				neighborsPositionVectors.add( distanceVector );
//			}
//		}
//
//		return neighborsPositionVectors;
//	}
//
//	/*
//	@Override
//	public void run() {
//		while (true) {
//			try {
//				onUpdate();
//				Thread.sleep(1);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	*/


}

package com.drone.show.world.IA;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.drone.show.generic.Tools;
import com.drone.show.world.model.DroneModel;
import com.drone.show.world.model.WorldModel;
import com.drone.show.world.ui.WorldViewer;
import com.drone.show.world.ui.widget.DroneWidget;

public class AvoidDronesTools {


	public static final float NEIGHBORHOOD_DISTANCE = 5f; //en m
	final static float separationForce = 4; //force de repulsion (== ou >= speed of DroneModel?)
	final static float distanceMaxNeighbor = 3f; //la distance max des neightbors doit etre ponderee a 3



	public static void updateVelocity(DroneModel droneModel) {

		/** IA */
		ArrayList<Vector3> neighborsDistanceVectors;
		Vector3 averageDistanceVector;
		Vector3 velocityVector;


		velocityVector = new Vector3(0,0,0);

		neighborsDistanceVectors = getNeighborDistanceVectors(droneModel);
		
		if(neighborsDistanceVectors.size() > 0) {
			averageDistanceVector = Tools.averageVector(neighborsDistanceVectors);
			/** separation */
			velocityVector = new Vector3(averageDistanceVector).nor().scl(-separationForce);
		}
		

		droneModel.currentVelocity = droneModel.currentVelocity.add(velocityVector);
		droneModel.currentVelocity.nor().scl(droneModel.speed);
	}


	/** Neighbors Distance Vectors */
	private void checkFixedLimits(Vector3 vector3_){

		//Ground
		if(vector3_.z < 0)
			vector3_.z = 0;
	}


	
	/** Neighbors Distance Vectors */
	private static ArrayList<Vector3> getNeighborDistanceVectors(DroneModel droneModel){

		ArrayList<Vector3> neighborsPositionVectors = new ArrayList<Vector3>();

		DroneModel neighborDrone;
		Vector3 distanceVector;



		for (Map.Entry<Integer, DroneModel> entry : WorldViewer.worldModel.getDrones().entrySet()) {

			neighborDrone = entry.getValue();

			if(droneModel.id != neighborDrone.id){

				float distance = droneModel.currentPosition.dst( neighborDrone.currentPosition );

				//On a 2 drone qui se percute?
				if(distance<DroneWidget.DRONE_SIZE) {
					droneModel.ledColor.set(1f,  0f,  0f,  1f);
					neighborDrone.ledColor.set(1f,  0f,  0f,  1f);
				}

				//is in the neighborhood?
				if( distance <= AvoidDronesTools.NEIGHBORHOOD_DISTANCE) {
					distanceVector = new Vector3( neighborDrone.currentPosition ).sub( droneModel.currentPosition );

					/** ponderation avec la distance */
					//plus c est loin moins ca influence
					float coefficient = distance / AvoidDronesTools.NEIGHBORHOOD_DISTANCE * distanceMaxNeighbor; //value between [0,distanceMaxNeighbor]
					coefficient = (float) (1f/(Math.exp(coefficient))); //value between [1, 1/exp(distanceMaxNeighbor)]
					//coefficient = (float) (1f/coefficient); 
					
					distanceVector.scl(coefficient);
					neighborsPositionVectors.add( distanceVector );
				}
			}

		}

		return neighborsPositionVectors;
	}


}

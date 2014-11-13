package ma.greenlightgame.physics;

import java.util.LinkedList;

import ma.greenlightgame.entityclasses.EntityA;
import ma.greenlightgame.entityclasses.EntityC;

public class Physics {
	
	public static boolean Collision(EntityA enta, LinkedList<EntityC> entc){
		
		for(int i = 0; i < entc.size(); i ++){
			
			if(enta.getBounds().intersects(entc.get(i).getBounds())){
				return true;
			}
		}
		return false;
	}
}

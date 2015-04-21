package hades.oldschool.lawcrafter.tools;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

public class CameraHandler{
	private static final String BANK = "Bank booth";
	
	public static boolean isFacing(RSTile dest){
		double angle = Camera.getCameraAngle();
		double angleTemp = Camera.getTileAngle(dest);
		return angleTemp - 10 <= angle && angleTemp + 10 >= angle;
	}
	
	public static boolean faceBank(){
		final double angle = Camera.getCameraAngle();
		RSObject objects[] = Objects.findNearest(5, BANK);
		if(objects == null || objects.length == 0)
			return false;
		RSTile bankTile = objects[0].getPosition();
		final double angleTemp = Camera.getTileAngle(bankTile);
		if(angleTemp - 10 <= angle && angleTemp + 10 >= angle)
			return true;
		Camera.turnToTile(bankTile);
		return Timing.waitCondition(new Condition(){
			public boolean active(){
				return angleTemp - 10 <= angle && angleTemp + 10 >= angle;
			}
		}, General.random(3000, 4000));
	}
	
	public static void faceTile(RSTile dest){
		final double angle = Camera.getCameraAngle();
		final double angleTemp = Camera.getTileAngle(dest);
		if(angleTemp - 30 <= angle && angleTemp + 30 >= angle)
			return;
		Camera.turnToTile(dest);
	}
}
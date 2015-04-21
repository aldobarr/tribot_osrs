package hades.oldschool.lawcrafter.tools;

import org.tribot.api2007.WorldHopper;
import org.tribot.script.interfaces.MessageListening07;

public class ServerMessageListener implements MessageListening07{
	private static final String MEMBER_ERROR = "You need to be on the members' servers to use this feature.";
	
	public void clanMessageReceived(String name, String message){}
	
	public void duelRequestReceived(String name, String message){}

	public void personalMessageReceived(String name, String message){}

	public void playerMessageReceived(String name, String message){}

	public void serverMessageReceived(String message){
		if(message.toLowerCase().contains(MEMBER_ERROR.toLowerCase()))
			WorldHopper.changeWorld(WorldHopper.getRandomWorld(true));
	}

	public void tradeRequestReceived(String name){}
}
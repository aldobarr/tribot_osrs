package hades.oldschool;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

@ScriptManifest(authors = { "hadesflames" }, category = "Tools", name = "hades Tile Creater", version = 1.0, description = "Creates an RSTile path based on your own minimap interactions in game.", gameMode = 1)
public class TileCreator extends Script{
	public void run(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(300, 150);
		JTextArea textArea = new JTextArea();
		frame.add(new JScrollPane(textArea));
		Timing.waitCondition(new Condition(){
			public boolean active(){
				General.sleep(75, 125);
				return Login.getLoginState() == Login.STATE.INGAME;
			}
		}, General.random(20000, 25000));
		boolean run = true;
		boolean started = false;
		Map<RSTile, RSTile> tiles = new HashMap<RSTile, RSTile>();
		while(run){
			General.sleep(50);
			
			if(!started){
				RSTile dest = Game.getDestination();
				if(dest != null){
					started = true;
					tiles.put(dest, dest);
				}
			}else{
				RSTile dest = Game.getDestination();
				if(dest != null){
					if(!tiles.containsKey(dest))
						tiles.put(dest, dest);
				}else
					run = false;
			}
		}
		String out = "{";
		Collection<RSTile> tileValues = tiles.values();
		for(RSTile tile : tileValues)
			out += "new RSTile(" + tile.getX() + ", " + tile.getY() + ", " + tile.getPlane() + ", RSTile.TYPES." + tile.getType().toString() + "), ";
		out = out.substring(0, out.length() - 2) + "};";
		textArea.setText(out);
		frame.setVisible(true);
	}
}
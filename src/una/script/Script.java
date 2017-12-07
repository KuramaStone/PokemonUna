package una.script;

import una.entity.NPC;
import una.toolbox.Tools;

public class Script {
	
	private int scriptID;
	
	private Command[] commands;
	
	public Script(int id) {
		scriptID = id;
		commands = Tools.loadScript(id);
	}
	
	public void play(NPC npc) {
		
		
		
	}

}

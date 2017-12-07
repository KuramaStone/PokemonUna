package una.script;

public class Command {
	
	private CommandType type;
	private int[] array;
	private String text;
	
	public Command(int id, int[] array) {
		type = CommandType.fromID(id);
		this.array = array;
	}
	
	public Command(int id, String text) {
		type = CommandType.fromID(id);
		this.text = text;
	}
	
	public CommandType getType() {
		return type;
	}

	public int[] getArray() {
		return array;
	}

	public String getText() {
		return text;
	}

	public static enum CommandType {
		MOVE(0), TURN(1), GIVEPOKE(2), GIVEI(3), SHOWT(4);
		
		private int id;
		
		CommandType(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
		
		public boolean givesText() {
			return id == SHOWT.getID();
		}
		
		public static CommandType fromName(String name) {
			for(CommandType type : values()) {
				if(type.toString().equalsIgnoreCase(name)) {
					return type;
				}
			}
			
			return null;
		}
		
		public static CommandType fromID(int id) {
			for(CommandType type : values()) {
				if(type.getID() == id)
					return type;
			}
			return null;
		}
	}

}

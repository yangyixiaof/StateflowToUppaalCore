package StateflowStructure;

public class Event {
	
	private State container;
	private String name;
	private String scope;
	private String trigger;
	private int id = -1;
	
	public Event(String name,String scope,String trigger,State container) {
		this.setName(name);
		this.setScope(scope);
		this.setTrigger(trigger);
		this.setContainer(container);
	}

	public State getContainer() {
		return container;
	}

	public void setContainer(State container) {
		this.container = container;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}

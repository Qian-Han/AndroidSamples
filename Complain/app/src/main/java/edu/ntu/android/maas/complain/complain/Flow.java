package edu.ntu.android.maas.complain.complain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Flow implements Iterator<Flow.Function>, Iterable<Flow.Function>{

	private List<Function> _functions = new ArrayList<Function>();
	private int visited = 0;
	
	/**
	 * An list storing the sequence of the flows
	 * @param flows
	 */
	public Flow(List<String> flows){
		parse(flows.toArray(new String[flows.size()]));
	}
	
	/**
	 * An array storing the sequence of the flows
	 * @param flows
	 */
	public Flow(String[] flows){
		parse(flows);
	}
	
	/**
	 * The flow string could be as: ACTIVITY::POINTCUT_ONCREATE::NONE->ACTIVITY::POINTCUT_ONSTART::NONE
	 * @param flowString
	 */
	public Flow(String flowString){
		String[] arr = flowString.split("->");
		parse(arr);
	}	
	
	private void parse(String[] flows){
		for (String a : flows){
			if (isValidFunction(a)){
				_functions.add(new Function(a));
			}
		}		
	}
	public boolean isValidFunction(String sig){
		String[] arr = sig.split("::");
		return arr.length >= 3;
	}
	
	public void addFunction(Function func){
		this._functions.add(func);
	}
	
	public static class Function{
		String component;
		int componentId = -1;
		

		String pointcut;
		String operation;
		String attachment;		//It is used for ContentObserver, to note which Uri is attached.
		
		public Function(String sig){
			String[] coms = sig.split("::", 3);
			if (coms.length == 3){

				if (coms[0].indexOf('<') < coms[0].indexOf('>') && coms[0].indexOf('<') > 0){
					this.attachment = coms[0].substring(coms[0].indexOf('<')+1, coms[0].indexOf('>'));
					coms[0] = coms[0].substring(0, coms[0].indexOf('<'));
				}
				
				if (coms[0].indexOf('[')<coms[0].indexOf(']') && coms[0].indexOf('[') > 0){
					this.componentId = Integer.parseInt(coms[0].substring(coms[0].indexOf('[')+1, coms[0].indexOf(']')));
					coms[0] = coms[0].substring(0, coms[0].indexOf('['));
				}
				
				this.component = coms[0];
				
				this.pointcut = coms[1];
				this.operation = coms[2];
			}
		}
		public Function(String component, int componentId,String pointcut, String operation){
			this.component = component;
			this.componentId = componentId;
			this.pointcut = pointcut;
			this.operation = operation;
		}
		public static Function parse(String sig){
			return new Function(sig);
		}
		
		public int getComponentId() {
			return componentId;
		}
		public void setComponentId(int componentId) {
			this.componentId = componentId;
		}
		
		public String getComponent(){
			return component;
		}
		
		/**
		 * Get the full name of component, it is like component[componentId]\lt;attachment\gt;
		 * @return
		 */
		public String getFullComponent(){
			StringBuilder sb = new StringBuilder(component);
			if (componentId != -1)
				sb.append("["+componentId+"]");
			if (attachment != null)
				sb.append("<"+attachment+">");
			return sb.toString();
		}

		public String getPointcut() {
			return pointcut;
		}

		public void setPointcut(String pointcut) {
			this.pointcut = pointcut;
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public String getAttachment() {
			return attachment;
		}
		
		public void setAttachment(String attachment) {
			this.attachment = attachment;
		}
		
		public void setComponent(String component) {
			this.component = component;
		}		
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder(component);
			if (componentId != -1)
				sb.append("["+componentId+"]");
			if (attachment != null)
				sb.append("<"+attachment+">");
			sb.append("::"+pointcut+"::"+operation);
			return sb.toString();
		}
	}

	@Override
	public boolean hasNext() {
		return visited < _functions.size();
	}

	@Override
	public Function next() {
		if (!hasNext()) throw new NoSuchElementException();
		return _functions.get(visited++);
	}

	@Override
	public void remove() {
		_functions.remove(visited);
	}

	@Override
	public Iterator<Function> iterator() {
		visited = 0;
		return this;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Function f : _functions){
			if (!first)	sb.append("->");
			sb.append(f.toString());
			first = false;
		}
		return sb.toString();
	}
}
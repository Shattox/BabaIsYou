package fr.uge.BabaIsYou.Element;

import java.util.ArrayList;

/**
 * Class for elements.
 */
public class Element {
	private String name;
	private final Type type;
	private ArrayList<Status> lstStatus;

	/**
	 * Constructor for Element.
	 * @param name
	 * 		name of the element.
	 * @param type
	 * 		type of the element (enum).
	 * @param status
	 * 		status of the element (enum).
	 */
	public Element(String name, Type type, Status status) {
		this.name = name;
		this.type = type;
		this.lstStatus = new ArrayList<Status>();
		lstStatus.add(status);
	}
	
	/**
	 * This function set a new status in the ArrayList of Status.
	 * @param newStatue
	 * 		the new Status to set in the ArrayList of Status.
	 */
	public void setStatus(Status newStatue) {
		if(!lstStatus.contains(newStatue))
			lstStatus.add(newStatue);
	}

	/**
	 * Reset the ArrayList of Status and add firstStatue which repersents the
	 * new first Status of the ArrayList of Status.
	 * @param firstStatue
	 * 		the statue to add in the ArrayList of Status.
	 */
	public void resetStatus(Status firstStatue) {
		lstStatus = new ArrayList<Status>();
		lstStatus.add(firstStatue);
	}

	/**
	 * Set newName to name
	 * @param newName
	 * 		the name to set.
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Getter for the name of the Element.
	 * @return
	 * 		name of the element.
	 */
	public String name() {
		return name;
	}

	/**
	 * Getter for the type of the Element.
	 * @return
	 * 		type of the element.
	 */
	public Type type() {
		return type;
	}

	/**
	 * Getter for the ArrayList of Status of the Element.
	 * @return
	 * 		list of status of the element.
	 */
	public ArrayList<Status> status() {
		return lstStatus;
	}
}
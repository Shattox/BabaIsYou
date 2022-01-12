package fr.uge.BabaIsYou.Draw;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import fr.uge.BabaIsYou.Element.*;
import fr.uge.BabaIsYou.Read.Position;

/**
 * Class for rules.
 */
public class Rule {
	private ArrayList<ArrayList<Element>> rule;

	/**
	 * Constructor of Rule which inialises the ArrayList of Element "rule".
	 */
	public Rule() {
		this.rule = new ArrayList<ArrayList<Element>>();
	}

	/**
	 * Return the ArrayList of Element rule.
	 * @return
	 * 		the rule ArrayList.
	 */
	public ArrayList<ArrayList<Element>> list() {
		return rule;
	}
	
	/**
	 * Return the ArrayList of items coming from the right or the bottom according to the ind index.
	 * @param b
	 * 		the board of the game.
	 * @param pos
	 * 		the position of element
	 * @param ind
	 * 		if ind = 1 then so he's going to add three element coming from the right else from the down.
	 * @return
	 * 		the ArrayList of Element.
	 */
	public ArrayList<Element> fillListElem(Board b, Position pos, int ind) {
		var lst_elem = new ArrayList<Element>();
		
		if(ind == 1) {
			for(int i = 0; i < 3; i++)
				lst_elem.add(b.Map().get(new Position(pos.x() + i, pos.y())).get(0));
		}
		else {
			for(int i = 0; i < 3; i++)
				lst_elem.add(b.Map().get(new Position(pos.x(), pos.y() + i)).get(0));
		}
		return lst_elem;
	}

	/**
	 * Creates automatically the rules of the game according to the given board.
	 * @param b
	 * 		the board of the game.
	 */
	public void buildRules(Board b) {
		var tmp = new ArrayList<ArrayList<Element>>();
		
		for(var pos: b.Map().keySet()) {
			if(b.Map().get(pos) != null && b.Map().get(pos).get(0).type().equals(Type.Name)) {
				if(b.Map().get(new Position(pos.x() + 1, pos.y())) != null && b.Map().get(new Position(pos.x() + 2, pos.y())) != null) {
					if(b.Map().get(new Position(pos.x() + 1, pos.y())).get(0).type().equals(Type.Op) && ((b.Map().get(new Position(pos.x() + 2, pos.y())).get(0).type().equals(Type.Prop)) || b.Map().get(new Position(pos.x() + 2, pos.y())).get(0).type().equals(Type.Name)) )
						tmp.add(fillListElem(b, pos, 1));
				}
				if(b.Map().get(new Position(pos.x(), pos.y() + 1)) != null && b.Map().get(new Position(pos.x(), pos.y() + 2)) != null) {
					if(b.Map().get(new Position(pos.x(), pos.y() + 1)).get(0).type().equals(Type.Op) && ((b.Map().get(new Position(pos.x(), pos.y() + 2)).get(0).type().equals(Type.Prop)) || b.Map().get(new Position(pos.x(), pos.y() + 2)).get(0).type().equals(Type.Name)) )
						tmp.add(fillListElem(b, pos, 2));
				}
			}
		}
		rule = tmp;
	}

	/**
	 * Checks if the element has the status given in the parameter.
	 * @param b
	 * 		the board of the game.
	 * @param x
	 * 		position x of the element.
	 * @param y
	 * 		position y of the element.
	 * @param nameStatus
	 * 		the name of status you want to checks.
	 * @return
	 * 		true if it's the same otherwise false.
	 */
	public static boolean verifStatus(Board b, int x, int y, Status nameStatus) {
		if(b.Map().get(new Position(x, y)) != null) {
			for(var status: b.Map().get(new Position(x, y)).get(0).status()) {
				if(status.equals(nameStatus))
					return true;	
			}
		}
		return false;
	}

	/**
	 * Checks if the player can melt and wants to go on a hot element.
	 * @param b
	 * 		the board of the game.
	 * @param p
	 * 		the player(s) of the game.
	 * @param pos
	 * 		the position of other element.
	 * @param x
	 * 		position x.
	 * @param y
	 * 		position y.
	 * @return
	 * 		true if all condition are satisfied otherwise false.
	 */
	public static boolean Hot(Board b, Player p, Position pos, int x, int y) {
		for(int i = 0; i < p.Map().get(pos).status().size(); i++) {
			if(b.Map().get(new Position(x, y)) != null) {
				for(var status: b.Map().get(new Position(x, y)).get(0).status())  {
					for(var statusP: b.Map().get(pos).get(0).status() ) {
						if(status.equals(Status.Hot) && statusP.equals(Status.Melt))
							return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the element has the given position can be push.
	 * @param b
	 * 		the board of the game.
	 * @param pos
	 * 		the position of element.
	 * @return
	 * 		true if it's possible otherwise false.
	 */
	private static boolean verifPush(Board b, Position pos) {
		for(var status: b.Map().get(pos).get(0).status()) {
			if(status.equals(Status.Push))
				return true;
		}
		return false;
	}

	/**
	 * Check if it is possible to push an element to the given position.
	 * @param b
	 * 		the board of the game.
	 * @param pos
	 * 		the position of element.
	 * @return 
	 * 		true if it's possible otherwise false.
	 */
	private static boolean canPush(Board b, int x, int y) {
		if(b.Map().get(new Position(x, y)) == null)
			return true;
		for(var status: b.Map().get(new Position(x, y)).get(0).status()) {
			if(status.equals(Status.Stop))
				return false;
		}
		return true;
	}

	/**
	 * Push several or only one element depending on the player's movements.
	 * @param b
	 * 		the board of the game.
	 * @param p
	 * 		the player(s) of the game.
	 * @return 
	 * 		return the number of element pushed by the player.
	 */
	public static int Push(Board b, Player p) {
		for(var pos: b.Map().keySet()) {
			for(var posP: p.Map().keySet()) {
				if(b.Map().get(pos) != null && verifPush(b, pos)) {
					var listPosP = Position.listPos(posP, 1);
					
					for(int i = 0; i < 4; i++) {
						if(p.keypresses()[i] == 1 && listPosP.get(i).x() == pos.x() && listPosP.get(i).y() == pos.y()) {
							var tmp = b.NumberElem(pos.x(), pos.y(), i);
							var listPos = Position.listPos(pos, tmp);
							if(canPush(b, listPos.get(i).x(), listPos.get(i).y())) {
								b.pushElem(pos, tmp, i);
								return tmp;
							} else { b.Map().get(new Position(pos.x(), pos.y())).get(0).setStatus(Status.Stop); }
						}
					}
				}
			}
		}
		return 0;
	}

    /**
	 * This function check if one of the position of the player is on an element
	 * with Win property.
	 * @param b
	 * 		the board of the game.
	 * @param p
	 * 		the player(s) of the game.
	 * @return : 
	 * 		if one element which represents the player is on a element with Win property return true, else return false.
	 */
	public static boolean Win(Board b, Player p) {
		for(var pos: b.Map().keySet()) {
			for(var posP: p.Map().keySet()) {
				for(int i = 0; i < b.Map().get(pos).size(); i++) {
					if(b.Map().get(pos) != null) {
						for(var status: b.Map().get(pos).get(i).status()) {
							if(status.equals(Status.Win) && posP.equals(pos) )
								return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Returns the ArrayList of Element, contained in the board.
	 * @param b
	 * 		the board of the game.
	 * @return
	 * 		the ArrayList of Element.
	 */
	private LinkedHashMap<Position, ArrayList<Element>> getList(Board b) {
		var tmp = new LinkedHashMap<Position, ArrayList<Element>>();

		for(var pos: b.Map().keySet()) {
			for(int i = 0; i < b.Map().get(pos).size(); i++) {
				if(b.Map().get(pos) != null && b.Map().get(pos).get(i).name().charAt(b.Map().get(pos).get(i).name().length() - 1) == 'T')
					tmp.put(pos, b.Map().get(pos));
			}
		}
		return tmp;
	}

	/**
	 * Updates the text elements of the board by changing their status to Push.
	 * @param b
	 * 		the board of the game.
	 */
	private void updateStatusText(Board b) {
		var tmp = new LinkedHashMap<Position, ArrayList<Element>>();
		
		tmp = getList(b);
		
		for(var pos: tmp.keySet()) {
			for(int i = 0; i < tmp.get(pos).size(); i++) {
				tmp.get(pos).get(i).setStatus(Status.Push);
			}
		}
	}

	/**
	 * Updates the elements (non text) of the board by changing their status according to the rules active.
	 * @param b
	 * 		the board of the game.
	 * @param rule
	 * 		the ArrayList rule.
	 */
	private void update(Board b, ArrayList<Element> rule) {
		var tmp = new LinkedHashMap<Position, ArrayList<Element>>();
		
		tmp = b.findWithNameElem(rule.get(0).name().substring(0, rule.get(0).name().length() - 1));
		
		for(var pos: tmp.keySet()) {
			for(int i = 0; i < tmp.get(pos).size(); i++) {
				if(rule.get(2).type().equals(Type.Name))
					tmp.get(pos).get(i).setName(rule.get(2).name().substring(0, rule.get(2).name().length() - 1));
				else
					tmp.get(pos).get(i).setStatus(Status.valueOf(rule.get(2).name().substring(0, rule.get(2).name().length() - 1)));
			}
		}
	}
	
	/**
	 * Apply different rules on the elements of the game.
	 * @param b
	 * 		the board of the game.
	 */
	public void applyRules(Board b) {
		updateStatusText(b);
		for(var r: rule) {
			if(r.get(2) != null)
				update(b, r);
		}
	}
	
	/**
	 * This function return the closest position of element with "Tp" property
	 * of the portal at the position PosP. To make this we compare the distance between
	 * every position of element with Tp property and keep the shorter distance.
	 * @param b
	 * 		the board of the game.
	 * @param posP
	 * 		the position of the portal and also of the player.
	 * @return 
	 * 		the position of the closest portal of the portal at the position posP.
	 */
	private static Position findPortal(Board b, Position posP) {
		var tmp = new Position(100, 100);

		for(var pos: b.Map().keySet()) {
			if(verifStatus(b, pos.x(), pos.y(), Status.Tp) && !posP.equals(pos) && Position.distance(posP, pos) < Position.distance(posP, tmp)) {
				tmp = pos;
			}
		}
		return tmp;
	}

	/**
	 * This function check if a player is on a element with Tp property.
	 * If it's true this function call the previous one to find the closest element 
	 * with Tp property and also call PortalChoice which allows the player to chose his
	 * output.	
	 * @param b
	 * 		the board of the game.
	 * @param p
	 * 		the player(s) of the game.
	 */
	public static void Portal(Board b, Player p) {
		for(var pos: p.Map().keySet()) {
			if(b.Map().get(pos) != null && verifStatus(b, pos.x(), pos.y(), Status.Tp)) {
				var tmp = findPortal(b, pos);
				b.PortalChoice(b, p, pos, tmp);
			}
		}
	}
}
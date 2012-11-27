package c02dt.sudoku.solver;

import java.util.Iterator;
import jess.Fact;
import jess.JessEvent;
import jess.JessListener;
import jess.Rete;
import jess.Value;

public class RuleBased extends Solver implements JessListener {
	
	private Rete engine;
	
	public RuleBased() {
		try {
			engine = new Rete();
			engine.executeCommand("(batch " + System.getProperty("user.dir") + "/c02dt/sudoku/solver/sr.clp )");
			engine.addJessListener(this);
			engine.setEventMask(engine.getEventMask() | JessEvent.FACT);
			
		} catch(jess.JessException e) {
			System.err.println(e + " (caught during solver instanciation)");
		}
	}
	
	public Boolean solve() {
		try {
			for(int r = 0; r < 9; r++) {
				for(int c = 0; c < 9; c++) {
					Double b = (Math.floor(r/3)*3) + (Math.floor(c/3));
					
					if(puzzle.getClue(r, c) > 0) {
						Fact clue = new Fact("Big", engine);
						clue.setSlotValue("r", new Value(r));
						clue.setSlotValue("c", new Value(c));
						clue.setSlotValue("b", new Value(b.intValue()));
						clue.setSlotValue("v", new Value(puzzle.getClue(r, c).intValue()));
						engine.assertFact(clue);
					} else {
						for (int v = 1; v < 10; v++) {
							Fact cell = new Fact("Small", engine);
							cell.setSlotValue("r", new Value(r));
							cell.setSlotValue("c", new Value(c));
							cell.setSlotValue("b", new Value(b.intValue()));
							cell.setSlotValue("v", new Value(v));
							engine.assertFact(cell);
						}
					}
				}
			}
			
			//engine.executeCommand("(watch facts)");
			//engine.executeCommand("(watch rules)");
			engine.run();
			//this.printFacts(engine);
			
		} catch(jess.JessException e) {
			System.err.println(e + " (caught during a solve)");
			return false;
		}
		
		return true;
	}
	
	public void eventHappened(JessEvent je) {
		try {
			if(je.getObject().getClass() == Fact.class) {
				Fact actuator = (Fact) je.getObject();
				String type = actuator.getName();
				Integer r = (Integer) actuator.getSlotValue("r").externalAddressValue(engine.getGlobalContext());
				Integer c = (Integer) actuator.getSlotValue("c").externalAddressValue(engine.getGlobalContext());
				Integer v = (Integer) actuator.getSlotValue("v").externalAddressValue(engine.getGlobalContext());
				Integer b = (Integer) actuator.getSlotValue("b").externalAddressValue(engine.getGlobalContext());
				Integer f = actuator.getFactId();
				
				if(je.getType() == JessEvent.FACT) {
					// Update local puzzle grid to match facts
					if(type.equalsIgnoreCase("MAIN::Big")) {
						puzzle.setValue(r, c, v);
					}
					
					//System.out.print("==> ");
					/*
					 } else if(je.getType() == (JessEvent.FACT + JessEvent.REMOVED)) {
					 System.out.print("<== ");
					 }
					 
					 System.out.println(type + "\t" + r + "x" + c + "x" + b + ": " + v + "\t (f-" + f + ")");
					 */
				}
			}
		} catch(Exception e) {
			System.err.println(e);
		}
	}
	
	public void printFacts(Rete engine) {
		Iterator iter = engine.listFacts();
		try {
			while(iter.hasNext()) {
				Fact aFact = (Fact) iter.next();
				//System.out.println(aFact.getName() + ":\t");
				//System.out.print(aFact.getSlotValue("r").javaObjectValue(engine.getGlobalContext()) + " x ");
				//System.out.print(aFact.getSlotValue("c").javaObjectValue(engine.getGlobalContext()) + " set to ");
				//System.out.println(aFact.getSlotValue("v").javaObjectValue(engine.getGlobalContext()));
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
package burlap.oomdp.singleagent;

import burlap.oomdp.core.AbstractGroundedAction;
import burlap.oomdp.core.AbstractObjectParameterizedGroundedAction;
import burlap.oomdp.core.TransitionProbability;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.EnvironmentOutcome;

import java.util.List;

/**
 * A {@link burlap.oomdp.singleagent.GroundedAction} is a high-level implementation of a {@link burlap.oomdp.core.AbstractGroundedAction}
 * that is closely associated with single-agent {@link burlap.oomdp.singleagent.Action} definitions. The role of
 * a {@link burlap.oomdp.singleagent.GroundedAction} is to provide a reference to its corresponding {@link burlap.oomdp.singleagent.Action}
 * definition and also provide parameter assignments with which its {@link burlap.oomdp.singleagent.Action} should be applied.
 * The set of possible {@link burlap.oomdp.singleagent.GroundedAction} instances specifying the different possible parameter assignments
 * will be generated by the associated
 * {@link burlap.oomdp.singleagent.Action#getAllApplicableGroundedActions(burlap.oomdp.core.states.State)} method. See
 * the {@link burlap.oomdp.singleagent.Action} class documentation for more information on implementing parameterized
 * {@link burlap.oomdp.singleagent.Action} definitions.
 * <br/><br/>
 * This high-level class can be used directly, without subclassing, for any parameter-less {@link burlap.oomdp.singleagent.Action} definitions
 * and should be subclassed for any parameterized {@link burlap.oomdp.singleagent.Action} definitions (with the
 * subclass instances returned by the associated {@link burlap.oomdp.singleagent.Action#getAllApplicableGroundedActions(burlap.oomdp.core.states.State)}
 * and {@link Action#getAssociatedGroundedAction()} method). The {@link burlap.oomdp.singleagent.GroundedAction} subclass can store the
 * parameter assignment data any way you like.
 * <br/><br/>
 * If you plan on
 * making an {@link burlap.oomdp.singleagent.Action} definition with OO-MDP object parameters, you can use the existing
 * {@link burlap.oomdp.singleagent.ObjectParameterizedAction}, which is associated with the {@link burlap.oomdp.singleagent.GroundedAction}
 * subclass {@link burlap.oomdp.singleagent.ObjectParameterizedAction.ObjectParameterizedGroundedAction}.
 * <br/><br/>
 * When you subclass {@link burlap.oomdp.singleagent.GroundedAction} to provide parameter information, you should override
 * the following methods:<br/>
 * {@link #copy()}<br/>
 * {@link #initParamsWithStringRep(String[])}<br/>
 * {@link #getParametersAsString()}<br/>
 * The {@link #copy()} method should be override to return a new instance of your subclass with all parameter assignment information
 * copied over. The {@link #initParamsWithStringRep(String[])} should be overridden to allow a String array specification
 * of parameters to be provided to initialize them. Overriding this method is not critical, but is useful for serialization purposes.
 * The {@link #getParametersAsString()} similarly should returns a String array representing the String value of each parameter.
 * As with the {@link #initParamsWithStringRep(String[])} method, overriding {@link #getParametersAsString()} is not critical,
 * but is useful for serialization and similar processes.
 * <br/><br/>
 * In addition to specifying parameter assignment information for a {@link burlap.oomdp.singleagent.Action} definition,
 * this class also provides a number of useful shortcut methods for evaluating the {@link burlap.oomdp.singleagent.GroundedAction}.
 * Specifically, see the<br/>
 * {@link #executeIn(burlap.oomdp.core.states.State)}<br/>
 * {@link #executeIn(burlap.oomdp.singleagent.environment.Environment)}<br/>
 * {@link #getTransitions(burlap.oomdp.core.states.State)} and<br/>
 * {@link #applicableInState(burlap.oomdp.core.states.State)} <br/>
 * methods, which call the associated {@link burlap.oomdp.singleagent.Action} methods providing this instance
 * as the set of parameters to use. Note that the {@link #getTransitions(burlap.oomdp.core.states.State)} method
 * will throw a runtime exception if the associated {@link burlap.oomdp.singleagent.Action} definition does
 * not implement the {@link burlap.oomdp.singleagent.FullActionModel} interface.
 *

 * @author James MacGlashan
 *
 */
public class GroundedAction implements AbstractGroundedAction{

	/**
	 * The action object for this grounded action
	 */
	public Action action;


	/**
	 * Initializes with the {@link burlap.oomdp.singleagent.Action} definition with which this {@link burlap.oomdp.singleagent.GroundedAction}
	 * is associated.
	 * @param action the associated {@link burlap.oomdp.singleagent.Action} definition.
	 */
	public GroundedAction(Action action){
		this.action = action;
	}

	
	/**
	 * Returns the action name for this grounded action.
	 * @return the action name for this grounded action.
	 */
	public String actionName(){
		return this.action.getName();
	}

	@Override
	public boolean isParameterized() {
		return this.action.isParameterized();
	}

	@Override
	public void initParamsWithStringRep(String[] params) {
		//nothing to do
	}

	@Override
	public String[] getParametersAsString() {
		return new String[0];
	}

	@Override
	public String toString(){
		return this.actionName();
	}

	public boolean applicableInState(State s){
		return this.action.applicableInState(s, this);
	}

	@Override
	public AbstractGroundedAction copy() {

		return new GroundedAction(action);
	}


	/**
	 * Executes this grounded action in the specified {@link burlap.oomdp.singleagent.environment.Environment}.
	 * @param env the {@link burlap.oomdp.singleagent.environment.Environment} in which the action is to be executed.
	 * @return an {@link burlap.oomdp.singleagent.environment.EnvironmentOutcome} specifying the outcome of this action.
	 */
	public EnvironmentOutcome executeIn(Environment env){
		return this.action.performInEnvironment(env, this);
	}

	/**
	 * Executes the grounded action on a given state
	 * @param s the state on which to execute the action
	 * @return The state after the action has been executed
	 */
	public State executeIn(State s){
		return action.performAction(s, this);
	}


	/**
	 * Returns the full set of possible state transitions when this {@link burlap.oomdp.singleagent.GroundedAction} is applied in
	 * the given state. If the underlying {@link burlap.oomdp.singleagent.Action} definition does not implement
	 * {@link burlap.oomdp.singleagent.FullActionModel}, then a runtime exception will be thrown instead.
	 * @param s the source state from which the transitions should be computed.
	 * @return a {@link java.util.List} of {@link burlap.oomdp.core.TransitionProbability} objects specifying all state transitions from the input state that have non-zero probability.
	 */
	public List<TransitionProbability> getTransitions(State s){
		if(!(this.action instanceof FullActionModel)){
			throw new RuntimeException("GroundedAction cannot return the full state transitions because action " + this.actionName() + " does " +
					"not implement the FullActionModel interface.");
		}
		return ((FullActionModel)this.action).getTransitions(s, this);
	}


	/**
	 * A helper method that handles action translate in case this {@link burlap.oomdp.singleagent.GroundedAction} implements
	 * {@link burlap.oomdp.core.AbstractObjectParameterizedGroundedAction}. Works by calling the
	 * {@link burlap.oomdp.core.AbstractObjectParameterizedGroundedAction.Helper#translateParameters(burlap.oomdp.core.AbstractGroundedAction, burlap.oomdp.core.states.State, burlap.oomdp.core.states.State)}
	 * method with this object as the action to translate.
	 * If this is a {@link burlap.oomdp.core.AbstractObjectParameterizedGroundedAction}, then a new {@link burlap.oomdp.singleagent.GroundedAction} with its object parameters mapped to the object names sin the target state
	 * is returned.
	 * @param source the source state in which this {@link burlap.oomdp.singleagent.GroundedAction}'s parameters were bound.
	 * @param target the target state in which the returned {@link burlap.oomdp.singleagent.GroundedAction} will have its parameters bound.
	 * @return If this is a {@link burlap.oomdp.core.AbstractObjectParameterizedGroundedAction}, then a new {@link burlap.oomdp.singleagent.GroundedAction} with its object parameters mapped to the object names sin the target state
	 */
	public GroundedAction translateParameters(State source, State target){
		return (GroundedAction)AbstractObjectParameterizedGroundedAction.Helper.translateParameters(this, source, target);
	}
	
	@Override
	public int hashCode(){
		return this.action.getName().hashCode();
	}
	
	
	@Override
	public boolean equals(Object other){

		if(this == other){
			return true;
		}

		if(!(other instanceof GroundedAction)){
			return false;
		}

		GroundedAction go = (GroundedAction)other;

		return this.actionName().equals(((GroundedAction) other).actionName());

	}






	
}

package ch.qos.logback.core.filter;

import ch.qos.logback.core.boolex.EvaluationException;
import ch.qos.logback.core.boolex.EventEvaluator;

public class EvaluatorFilter extends AbstractEvalutatorFilter {

  EventEvaluator evaluator;
  
  public void start() {
    if(evaluator != null) {
      super.start();
    } else {
      addError("No evaluator set for filter "+this.getName());
    }
  }
  
  public EventEvaluator getEvaluator() {
    return evaluator;
  }

                 
  public void setEvaluator(EventEvaluator evaluator) {
    this.evaluator = evaluator;
  }

  public int decide(Object event) {
    // let us not throw an exception
    // see also bug #17.
    if(!isStarted()) {
      return NEUTRAL;
    }
    try {
      if (evaluator.evaluate(event)) {
        return on_match;
      } else {
        return onMismatch;
      }
    } catch (EvaluationException e) {
      addError("Evaluator "+evaluator.getName()+" threw an exception", e);
      return NEUTRAL;
    }

  }

}

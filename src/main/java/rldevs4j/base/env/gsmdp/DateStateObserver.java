package rldevs4j.base.env.gsmdp;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.nd4j.linalg.api.ndarray.INDArray;
import rldevs4j.utils.DateUtils;

/*     
 * Component responsible for maintaining the state of the environment, 
 * including a global environment date value (dd/MM HH:mm), and update it based on the 
 * arrival of exogenous events $e_i$ or actions $a_i$ executed by the agent.
 * @author Ezequiel Beccaría 
 */
public class DateStateObserver extends StateObserver{
    private Date currentDate;
    
    public DateStateObserver(
            Date initSimDate,
            Behavior behavior, 
            boolean debug) {
        super(behavior, debug);
        currentDate = initSimDate;
    }
    
    @Override
    protected void updateStateDate(INDArray state, Double e){
        DateUtils.add(currentDate, e.intValue(), DateUtils.TimeUnit.MINUTES);
        
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Cordoba"));
        cal.setTime(currentDate);
        
        state.putScalar(16, cal.get(Calendar.DAY_OF_MONTH));
        state.putScalar(17, cal.get(Calendar.MONTH));
        state.putScalar(18, cal.get(Calendar.HOUR_OF_DAY));
        state.putScalar(19, cal.get(Calendar.MINUTE));
    }
}

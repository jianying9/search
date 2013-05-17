package com.search.timer;

import javax.ejb.Local;

/**
 *
 * @author aladdin
 */
@Local
public interface SearchTimerSessionBeanLocal {
    
    public void updateEmployee();
    
    public void updateTagTotal();
}

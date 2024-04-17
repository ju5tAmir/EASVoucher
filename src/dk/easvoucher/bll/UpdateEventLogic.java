package dk.easvoucher.bll;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.dal.UpdateEventDAO;
import dk.easvoucher.exeptions.ExceptionHandler;

public class UpdateEventLogic {

    private final UpdateEventDAO dao = new UpdateEventDAO();

    public void updateEvent(Event event) throws ExceptionHandler {
        dao.updateEvent(event);
    }
}

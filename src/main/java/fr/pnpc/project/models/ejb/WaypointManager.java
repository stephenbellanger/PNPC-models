package fr.pnpc.project.models.ejb;

import fr.pnpc.project.models.model.Waypoint;
import fr.pnpc.project.models.dao.CrudService;
import fr.pnpc.project.models.exceptions.NotValidException;
import fr.pnpc.project.models.exceptions.NullObjectException;
import fr.pnpc.project.models.util.ValidatorManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by stephen on 10/11/17.
 */

@Stateless
public class WaypointManager extends ValidatorManager<Waypoint>{

    @EJB
    CrudService<Waypoint> serviceManager;

    public WaypointManager(){
        super();
    }

    public Waypoint create(Waypoint waypoint) throws NullObjectException, NotValidException {
        if (waypoint == null) {
            throw new NullObjectException(NullObjectException.defaultMessage);
        }
        Set<ConstraintViolation<Waypoint>> constraintViolations = constraintViolations(waypoint);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(ucv -> errors.add(ucv.getMessage()));
            throw new NotValidException(errors);
        }

        return serviceManager.create(waypoint);
    }

    public List getAll(){
        return serviceManager.findAll(Waypoint.class);
    }

    public Waypoint getById(int id){
        return serviceManager.find(Waypoint.class, id);
    }

    public void delete(int id){
        serviceManager.delete(Waypoint.class, id);
    }

    public Waypoint update(Waypoint newWaypoint) throws NullObjectException, NotValidException {
        if(newWaypoint == null){
            throw new NullObjectException(NullObjectException.defaultMessage);
        }
        Set<ConstraintViolation<Waypoint>> constraintViolations = constraintViolations(newWaypoint);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(ucv -> errors.add(ucv.getMessage()));
            throw new NotValidException(errors);
        }

        return serviceManager.update(newWaypoint);
    }

}
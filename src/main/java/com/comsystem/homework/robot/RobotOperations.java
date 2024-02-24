package com.comsystem.homework.robot;


import com.comsystem.homework.exceptions.EmptyDaysException;
import com.comsystem.homework.exceptions.EmptyStonesException;
import com.comsystem.homework.model.RobotAction;
import com.comsystem.homework.model.RobotPlan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RobotOperations {

    public RobotOperations() {

    }

    /**
     * An algorithm that converts a number of days into an action plan.
     * @param days the number of days that the robot can work
     * @return The action plan <em>must maximize</em> the number of stones that the robot will dig. In other words, this
     *         algorithm must try to achieve the highest value of numberOfStones possible for the
     *         number of provided days. The value of numberOfDays is equal to the input
     *         days parameter
     * @see RobotPlan
     */
    public RobotPlan excavateStonesForDays(int days) {

        List<RobotAction> robotActions = new ArrayList<>();

        RobotPlan robot;
        int stones = 0;
        int clones = 0;
        int currentDayStones;
        // TODO
        if(days != 0){

            if(days==1){

                stones = 1;
            }else{

                for(int i=1;i<=days;i++){

                    if(i % 2 ==0){

                        if(i > 2){

                            stones+=clones;
                        }

                        clones++;
                    } else{

                        /*
                         * The first iteration, the clones will be 0 and only one stone will be mined. Every
                         * next time the stones from clones will be added.
                         */
                        currentDayStones = 1 + clones;
                        stones += currentDayStones;
                    }
                }
            }
            robotActions = getRobotActions(days);

            robot = new RobotPlan(days,stones,robotActions);

            return robot;

        }else {
            throw new EmptyDaysException("Given days are empty");
        }
    }

    /**
     * An algorithm that converts a number of stones into an action plan. Essentially this algorithm is the inverse of
     * {@link #excavateStonesForDays(int)}.
     * @param numberOfStones the number of stones the robot has to collect
     * @return The action plan <em>must minimize</em> the number of days necessary for the robot to dig the
     *         provided number of stones. In other words, this algorithm must try to achieve the lowest value of numberOfDays
     *         possible for the number of provided stones. The value of numberOfStones
     *          is equal to the numberOfStones parameter
     * @see RobotPlan
     */
    public RobotPlan daysRequiredToCollectStones(int numberOfStones) {
        List<RobotAction> robotActions;

        RobotPlan robot;

        int days = 0;

        int stonesQuantity = numberOfStones;

        int clones = 0;

        if(numberOfStones != 0) {

            while (stonesQuantity > 0) {
                days++;

                if (days % 2 == 0) {

                    clones++;

                    if (days > 2) {

                        stonesQuantity = stonesQuantity - clones;
                    }
                } else {

                    stonesQuantity = stonesQuantity - clones - 1;
                }
            }

            robotActions = getRobotActions(days);

            robot = new RobotPlan(days, numberOfStones, robotActions);
            return robot;

        }else{
            throw new EmptyStonesException("Given stones are empty");
        }
    }

    private List<RobotAction> getRobotActions(int days) {
        List<RobotAction> robotActions = new ArrayList<>();

        int clones = 0;

        for(int i = 1; i<= days; i++){

            if(i % 2 == 0){

                clones++;

                robotActions.add(RobotAction.CLONE);

                if(clones > 1){

                    int clonesQuantity = clones - 1;

                    while(clonesQuantity > 0){

                        robotActions.add(RobotAction.DIG);
                        clonesQuantity--;
                    }
                }
            }else{

                int excavateActions = clones + 1;

                while (excavateActions>0){

                    robotActions.add(RobotAction.DIG);
                    excavateActions--;
                }

            }
        }
        return robotActions;
    }

}

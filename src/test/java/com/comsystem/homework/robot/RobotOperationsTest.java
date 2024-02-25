package com.comsystem.homework.robot;

import com.comsystem.homework.exceptions.EmptyDaysException;
import com.comsystem.homework.exceptions.EmptyStonesException;
import com.comsystem.homework.model.RobotAction;
import com.comsystem.homework.model.RobotPlan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RobotOperationsTest {

    @Mock
    private RobotOperations robotOperations;
    private List<RobotAction> actionList;
    private RobotPlan robotPlan;

    @BeforeEach
    void setup() {

        this.actionList = generateActionsLib();
        this.robotPlan = new RobotPlan(6,9,this.actionList);
    }

    @AfterEach
    void clearAll(){
        this.actionList.clear();
    }

    @Test
    public void shouldReturnRobotPlanByGivenDaysSuccessfully(){
        Mockito.when(robotOperations.excavateStonesForDays(6)).thenReturn(robotPlan);

        RobotPlan actualRobotPlan = robotOperations.excavateStonesForDays(6);

        Assertions.assertEquals(actualRobotPlan.numberOfStones(),robotPlan.numberOfStones());

        List<RobotAction> actualRobotPlanActions = actualRobotPlan.robotActions();

        Assertions.assertEquals(actualRobotPlanActions.size(),this.actionList.size());

        boolean isRobotActionsEqual = actualRobotPlanActions.equals(this.actionList);

        Assertions.assertTrue(isRobotActionsEqual);

    }

    @Test
    public void shouldReturnRobotPlanByGivenStonesSuccessfully(){
        Mockito.when(robotOperations.daysRequiredToCollectStones(9)).thenReturn(robotPlan);

        RobotPlan actualRobotPlan = robotOperations.daysRequiredToCollectStones(9);

        Assertions.assertEquals(actualRobotPlan.numberOfDays(),robotPlan.numberOfDays());

        List<RobotAction> actualRobotPlanActions = actualRobotPlan.robotActions();

        Assertions.assertEquals(actualRobotPlanActions.size(),this.actionList.size());

        boolean isRobotActionsEqual = actualRobotPlanActions.equals(this.actionList);

        Assertions.assertTrue(isRobotActionsEqual);
    }

    @Test
    public void shouldReturnEmptyDaysExceptionWhenDaysIsNull(){
        Mockito.when(robotOperations.excavateStonesForDays(0)).thenThrow(new EmptyDaysException("Given days are empty"));

        Assertions.assertThrows(EmptyDaysException.class,() -> {
            robotOperations.excavateStonesForDays(0);
        });
    }

    @Test
    public void shouldReturnEmptyStonesExceptionWhenStonesIsNull(){
        Mockito.when(robotOperations.daysRequiredToCollectStones(0)).thenThrow(new EmptyStonesException("Given stones are empty"));

        Assertions.assertThrows(EmptyStonesException.class,() -> {
            robotOperations.daysRequiredToCollectStones(0);
        });
    }


    private List<RobotAction> generateActionsLib() {

        this.actionList = new ArrayList<>();
        this.actionList.add(RobotAction.DIG);

        this.actionList.add(RobotAction.CLONE);

        this.actionList.add(RobotAction.DIG);
        this.actionList.add(RobotAction.DIG);

        this.actionList.add(RobotAction.CLONE);
        this.actionList.add(RobotAction.DIG);

        this.actionList.add(RobotAction.DIG);
        this.actionList.add(RobotAction.DIG);
        this.actionList.add(RobotAction.DIG);

        this.actionList.add(RobotAction.CLONE);
        this.actionList.add(RobotAction.DIG);
        this.actionList.add(RobotAction.DIG);

        return this.actionList;
    }
}

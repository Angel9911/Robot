package com.comsystem.homework.robot;

import com.comsystem.homework.model.RobotAction;
import com.comsystem.homework.model.RobotPlan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RobotOperationsTest {

    private RobotOperations robotOperations;
    private List<RobotAction> actionList;
    private RobotPlan robotPlan;

    @BeforeEach
    void setup(RobotOperations robotOperations) {
        this.robotOperations = robotOperations;

        this.actionList = generateActionsLib();
        this.robotPlan = new RobotPlan(6,9,this.actionList);
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

    }

    @Test
    public void shouldReturnEmptyStonesExceptionWhenStonesIsNull(){

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

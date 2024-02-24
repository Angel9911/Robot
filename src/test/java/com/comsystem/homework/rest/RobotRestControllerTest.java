package com.comsystem.homework.rest;

import com.comsystem.homework.model.RobotAction;
import com.comsystem.homework.model.RobotPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ComponentScan(basePackages = "com.comsystem.homework.config")
public class RobotRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private List<RobotAction> actionList;

    private RobotPlan robotPlan;

    @BeforeEach
    void setup(){

        actionList = generateActionsLib();
        robotPlan = new RobotPlan(6,9,actionList);
    }


    @Test
    public void shouldReturnRobotPlanByGivenDays() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/robot/operation/excavation")
                        .param("numberOfDays","6")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("numberOfDays").value(robotPlan.numberOfDays()))
                        .andExpect(jsonPath("numberOfStones").value(robotPlan.numberOfStones()));
                        //.andExpect()
    }

    @Test
    public void shouldReturnRobotPlanByGivenStones() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/robot/operation/approximation")
                        .param("approximation","9")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("numberOfDays").value(robotPlan.numberOfDays()))
                        .andExpect(jsonPath("numberOfStones").value(robotPlan.numberOfStones()));
                        //.andExpect()
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

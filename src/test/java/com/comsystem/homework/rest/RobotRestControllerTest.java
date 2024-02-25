package com.comsystem.homework.rest;

import com.comsystem.homework.model.RobotAction;
import com.comsystem.homework.model.RobotPlan;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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

    @AfterEach
    void clearAll(){
        this.actionList.clear();
    }


    @Test
    public void shouldReturnRobotPlanByGivenDays() throws Exception {

        List<String> actualRobotActionString = convertEnumActionToString();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/robot/operation/excavation")
                        .param("numberOfDays","6")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("numberOfDays").value(robotPlan.numberOfDays()))
                        .andExpect(jsonPath("numberOfStones").value(robotPlan.numberOfStones()))
                        .andExpect(jsonPath("robotActions", hasSize(robotPlan.robotActions().size())))
                        .andExpect(jsonPath("robotActions").value(actualRobotActionString));
    }

    @Test
    public void shouldReturnRobotPlanByGivenStones() throws Exception {

        List<String> actualRobotActionString = convertEnumActionToString();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/robot/operation/approximation")
                        .param("numberOfStones","9")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("numberOfDays").value(robotPlan.numberOfDays()))
                        .andExpect(jsonPath("numberOfStones").value(robotPlan.numberOfStones()))
                        .andExpect(jsonPath("robotActions", hasSize(robotPlan.robotActions().size())))
                        .andExpect(jsonPath("robotActions").value(actualRobotActionString));
    }

    @Test
    public void shouldReturnBadRequestByGivenEmptyDays() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/robot/operation/excavation")
                        .param("numberOfDays", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestBadGivenEmptyStones() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/robot/operation/approximation")
                .param("numberOfStones", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private List<String> convertEnumActionToString() {

        return this.actionList.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
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

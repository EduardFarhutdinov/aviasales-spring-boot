package com.aviasales;

import com.aviasales.controller.ControllerTickets;
import com.aviasales.service.ClientService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControllerTicketTest {

    @MockBean
    private ClientService service;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ControllerTickets controllerTickets;


    @Test
    public void test() throws Exception {
        assertThat(controllerTickets).isNotNull();

    }
    @Test
    public void get_Ticket_ById_JSON_And_Expect_Status_IsOk_() throws Exception {
        mockMvc.perform(get("/tickets/ticket/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

    }

    @Test
    public void should_Return404_When_TicketNotFound() throws Exception {
      String error =  mockMvc.perform(get("/tickets/ticket/11"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException().getMessage();
        assertEquals(error,"could not find ticket '11'.");

    }


    @Test
    public void should_get_all_unreserved_tickets() throws Exception{
        mockMvc.perform(get("/tickets/list/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].buy",is(false)))
                .andExpect(jsonPath("$[0].companyName",is("Аэрофлот")))
                .andExpect(jsonPath("$[0].cost",is(23500.0)));
    }

    @Test
    public void should_get_all_unreserved_tickets_between_cost() throws Exception{
        mockMvc.perform(get("/tickets/filter/cost/20000.0/30000.0"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)));
    }
}

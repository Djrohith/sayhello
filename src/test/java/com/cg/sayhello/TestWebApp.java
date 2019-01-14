package com.cg.sayhello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class TestWebApp extends SayhelloApplicationTests {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Before
  public void setup() {

    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  public void testEmployee() throws Exception {

    this.mockMvc.perform(get("/employee")).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$.name").value("rohit"))
        .andExpect(jsonPath("$.designation").value("manager")).andExpect(jsonPath("$.empId").value("1"))
        .andExpect(jsonPath("$.salary").value(300000));

  }

}

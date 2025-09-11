package com.hulkhiretech.payments.controller.pojo;

import java.util.Map;

import lombok.Data;

@Data
public class StripeEventV2
{
	private String id;
    private String object;
    private String type;
    private Long created;
    private Data data;
	
    public static class Data
    {
        private Map<String, Object> object; // keep it generic JSON
        public Map<String, Object> getObject() { return object; }
        public void setObject(Map<String, Object> object) { this.object = object; }
    }
}

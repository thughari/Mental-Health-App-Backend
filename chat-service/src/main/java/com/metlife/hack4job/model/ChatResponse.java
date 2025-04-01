package com.metlife.hack4job.model;

import com.fasterxml.jackson.annotation.JsonProperty;  // Import Jackson annotation
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatResponse {

    @JsonProperty("model")
    private String model;

    @JsonProperty("created_at")
    private String created_at;

    @JsonProperty("response")
    private String response;

    @JsonProperty("done")
    private boolean done; // Changed to boolean

}
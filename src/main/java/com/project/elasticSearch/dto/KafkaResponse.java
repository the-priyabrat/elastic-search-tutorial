package com.project.elasticSearch.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class KafkaResponse {
    private String message;
    private String targetedScreen;
    private String sentTime;
    private String authoredBy;
}

package com.banco.demogorest.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoRestUserResponse implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String status;
}

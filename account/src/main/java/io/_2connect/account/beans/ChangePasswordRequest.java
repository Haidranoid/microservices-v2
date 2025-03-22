package io._2connect.account.beans;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {
    private Integer accountId;
    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}

package com.gl.ceir.config.model.app;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


public class UserGroupMembershipId implements Serializable {

    private Long userId;

    private Long groupId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserGroupMembershipId{");
        sb.append("userId=").append(userId);
        sb.append(", groupId=").append(groupId);
        sb.append('}');
        return sb.toString();
    }
}
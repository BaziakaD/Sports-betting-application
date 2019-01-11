package com.training.sportbetting.domain.user;

import com.training.sportbetting.domain.usergroup.UserGroup;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class Admin extends User {

    private List<UserGroup> userGroups;

    public Admin(String email, String password, List<UserGroup> userGroups) {
        super(email, password);
        this.userGroups = userGroups;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(userGroups, admin.userGroups)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(userGroups)
                .toHashCode();
    }
}

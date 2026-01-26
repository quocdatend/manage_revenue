package com.hongoquocdat.manage_revenue.utils;

import androidx.room.TypeConverter;

import com.hongoquocdat.manage_revenue.data.entity.Role;

public class RoleConverter {

    @TypeConverter
    public static String fromRole(Role role) {
        return role == null ? null : role.name();
    }

    @TypeConverter
    public static Role toRole(String role) {
        return role == null ? null : Role.valueOf(role);
    }
}

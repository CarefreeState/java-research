package com.macaku.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macaku.domain.po.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-11
 * Time: 19:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private SysUser user;

    private List<String> permissions;

    @JSONField(serialize = false)//代表，由不通过fastjson序列化，用系统redis其他的方式存入redis
    private List<GrantedAuthority> authorities;//com.alibaba.fastjson.JSONException: autoType is not support.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(Objects.isNull(this.authorities)) {
            // 将 permissions 的权限信息封装成 SimpleGrantedAuthority 象，并且也是集合
            this.authorities = this.permissions.stream()
                    .parallel()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public LoginUser(SysUser user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }
}

package com.utp.integradorspringboot.models;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioRolId implements Serializable {
    private Integer usuarioId;
    private Integer rolId;

    public UsuarioRolId() {}
    public UsuarioRolId(Integer usuarioId, Integer rolId) {
        this.usuarioId = usuarioId;
        this.rolId = rolId;
    }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public Integer getRolId() { return rolId; }
    public void setRolId(Integer rolId) { this.rolId = rolId; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioRolId that = (UsuarioRolId) o;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(rolId, that.rolId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, rolId);
    }
} 
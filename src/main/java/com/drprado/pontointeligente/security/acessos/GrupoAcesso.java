package com.drprado.pontointeligente.security.acessos;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GrupoAcesso {
    private List<GrantedAuthority> permissions;

    private void addPermission(GrantedAuthority permission){
        permissions.add(permission);
    }

    public List<GrantedAuthority> getPermissions(){
        return permissions.stream().collect(Collectors.toList());
    }

    private GrupoAcesso() {
        permissions = new ArrayList<>();
    }

    public static GrupoAcesso acessoComum(){
        GrupoAcesso grupo = new GrupoAcesso();
        grupo.addPermission(Permissao.VIEWER);
        return grupo;
    }

    public static GrupoAcesso acessoAnalista(){
        GrupoAcesso grupo = new GrupoAcesso();
        grupo.addPermission(Permissao.VIEWER);
        grupo.addPermission(Permissao.MANTENER);
        return grupo;
    }

    public static GrupoAcesso acessoGerente(){
        GrupoAcesso grupo = new GrupoAcesso();
        grupo.addPermission(Permissao.VIEWER);
        grupo.addPermission(Permissao.MANTENER);
        grupo.addPermission(Permissao.ADMIN);
        return grupo;
    }

    public static GrupoAcesso acessoProprietario(){
        GrupoAcesso grupo = new GrupoAcesso();
        grupo.addPermission(Permissao.VIEWER);
        grupo.addPermission(Permissao.MANTENER);
        grupo.addPermission(Permissao.ADMIN);
        grupo.addPermission(Permissao.OWNER);
        return grupo;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.integrador.bean;

/**
 *
 * @author Usuario
 */
public class MailSetting {
    private String host;
    private String port;
    private String user;
    private String password;

    public MailSetting() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.host);
        sb.append(",");
        sb.append(this.port);
        sb.append(",");
        sb.append(this.user);
        sb.append("]");
        return sb.toString(); 
    }
    
}

package org.eclipse.tracecompass.incubator.internal.kernel.analysts.core.analysis;

class SocketParams {
    public Integer      cxt_sequence_num    = 0;
    public Integer      fd                  = 0;
    public Integer      type                = 0;
    public Integer      cpu                 = 0;
    public Socket      socket;
    public Connect     connect;
    public Bind        bind;
    public Listen      listen;
    public Accept      accept;
    public Setsockopt  setsockopt;
    public Getsockopt  getsockopt;
    public Sendmsg     sendmsg;
    public Recvmsg     recvmsg;
    public Boolean     isActive             = true;

}

class Socket{
    private Integer family;
    private Integer type;
    private Integer protocol;

    public Socket(Integer _familiy,Integer _type,Integer _protocole) {
        this.family = _familiy;
        this.type = _type;
        this.protocol = _protocole;
    }

    public Integer getType() {
        return this.type;
    }

    public Integer getFamily() {
        return this.family;
    }

    public Integer getProtocol() {
        return this.protocol;
    }
}

class Connect{
    private Integer fd;
    private Integer family;
    private Integer dbport;

    public Connect(Integer _fd,Integer _familiy,Integer _dbport) {
        this.fd = _fd;
        this.dbport = _dbport;
        this.family = _familiy;
    }

    public Integer getFamily() {
        return this.family;
    }

    public Integer getFd() {
        return this.fd;
    }

    public Integer getDbport() {
        return this.dbport;
    }
}

class Bind{
    private Integer fd;
    private Long    umyaddr;
    private Integer addrlen;

    public Bind(Integer _fd, Long _umyaddr, Integer _addrlen) {
        this.fd = _fd;
        this.umyaddr = _umyaddr;
        this.addrlen = _addrlen;
    }

    public Integer getFd() {
        return this.fd;
    }

    public Integer getAddrlen() {
        return this.addrlen;
    }

    public Long getUmyaddr() {
        return this.umyaddr;
    }
}

class Listen{
    private Integer fd;
    private Integer backlog;

    public Listen(Integer _fd, Integer _backlog) {
        this.fd = _fd;
        this.backlog = _backlog;

    }
    public Integer getFd() {
        return this.fd;
    }

    public Integer getBacklog() {
        return this.backlog;
    }

}

class Accept{
    private Integer fd;
    private Integer upeer_addrlen;

    public Accept(Integer _fd,Integer _upeer_addrlen ) {
        this.fd = _fd;
        this.upeer_addrlen = _upeer_addrlen;
    }

    public Integer getFd() {
        return this.fd;
    }

    public Integer getUpeer_addrlen() {
        return this.upeer_addrlen;
    }
}

class Setsockopt{
    private Integer fd;
    private Integer level;
    private Integer optname;
    private Long    optval;
    private Integer optlen;


    public Setsockopt(Integer _fd, Integer _level, Integer _optname, Long _optval,Integer _optlen ) {
        this.fd = _fd;
        this.level = _level;
        this.optlen = _optlen;
        this.optval = _optval;
        this.optname = _optname;
    }

    public Integer getLevel() {
        return this.level;
    }

    public Long getOptval() {
        return this.optval;
    }

    public Integer getOptname() {
        return this.optname;
    }

    public Integer getOptlen() {
        return this.optlen;
    }

    public Integer getFd() {
        return this.fd;
    }
}

class Getsockopt{
    private Integer fd;
    public Integer level;
    public Integer optname;
    public Long    optlen;

    public Getsockopt(Integer _fd, Integer _level, Integer _optname, Long _optlen ) {
        this.fd = _fd;
        this.level = _level;
        this.optlen = _optlen;
        this.optname = _optname;
    }

    public Integer getFd() {
        return this.fd;
    }

    public Integer getOptname() {
        return this.optname;
    }

    public Long getOptlen() {
        return this.optlen;
    }

    public Integer getLevel() {
        return this.level;
    }

}

class Sendmsg{
    public Integer fd;
    public Integer message;

    public Sendmsg(Integer _fd,Integer _message) {
        this.fd = _fd;
        this.message = _message;
    }

    public Integer getFd() {
        return this.fd;
    }

    public Integer getMessage() {
        return this.message;
    }
}

class Recvmsg{

}

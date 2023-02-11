package help.lixin.xxl.job.service;

import help.lixin.xxl.job.mediator.CookieMediator;


public abstract class AbstractService {
    private CookieMediator cookieMediator;

    public AbstractService(CookieMediator cookieMediator) {
        this.cookieMediator = cookieMediator;
    }

    public CookieMediator getCookieMediator() {
        return this.cookieMediator;
    }
}

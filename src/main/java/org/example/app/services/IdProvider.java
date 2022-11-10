package org.example.app.services;
import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.InitializingBean;

public class IdProvider implements InitializingBean {
    Logger logger = Logger.getLogger(IdProvider.class);
    public String provideId(Book book) {
        return this.hashCode() + "_" + book.hashCode();
    }

    private void initIdProvider() {
        logger.info("provider INIT");
    }

    private void destroyIdProvider() {
        logger.info("provider DESTROY");
    }

    private void defaultInit() {
        logger.info("default INIT in provider");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in provider");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}

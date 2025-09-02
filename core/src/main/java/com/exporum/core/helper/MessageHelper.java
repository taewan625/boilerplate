package com.exporum.core.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */
@Component
@RequiredArgsConstructor
public class MessageHelper {

    private final MessageSource messageSource;

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }


    public String getMessage(String code) {
        try{
            return messageSource.getMessage(code,null, getLocale());
        }catch (NoSuchMessageException e){
            return getMessage("message.not.found");
        }
    }



}

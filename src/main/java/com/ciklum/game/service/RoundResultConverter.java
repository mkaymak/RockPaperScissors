package com.ciklum.game.service;

import com.ciklum.game.model.RoundResult;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;

@Component
public abstract class RoundResultConverter implements Converter<String, RoundResult> {
    @Override
    public RoundResult convert(String value) {
        return RoundResult.of(Integer.valueOf(value));
    }
}

package com.template.baseapp.application.presenter;

import com.template.baseapp.domain.boundaries.service.IProductPresenter;
import com.template.baseapp.domain.dtos.BaseProductResponseDto;
import com.template.baseapp.domain.exception.ProductCustomException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ProductPresenter implements IProductPresenter {

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public BaseProductResponseDto createSuccessResponse(BaseProductResponseDto responseDto) {
        Date dateTime = new Date(Long.parseLong(responseDto.getCreatedAt()));
        responseDto.setCreatedAt(formatter.format(dateTime));
        return responseDto;
    }

    @Override
    public BaseProductResponseDto createFailedResponse(ProductCustomException ex) throws ProductCustomException {
        throw ex;
    }
}

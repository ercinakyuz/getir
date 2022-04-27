package com.getir.framework.domain.model.aggregate.builder;


import com.getir.framework.data.entity.AbstractEntity;
import com.getir.framework.domain.model.aggregate.dto.AbstractLoadAggregateDto;

public abstract class AbstractAggregateBuilder {

    protected <TLoadDto extends AbstractLoadAggregateDto> TLoadDto loadAbstractProperties(final TLoadDto loadDto, final AbstractEntity abstractEntity){
        loadDto.setCreatedAt(abstractEntity.getCreatedAt());
        loadDto.setCreatedBy(abstractEntity.getCreatedBy());
        loadDto.setModifiedAt(abstractEntity.getModifiedAt());
        loadDto.setModifiedBy(abstractEntity.getModifiedBy());
        return loadDto;
    }
}

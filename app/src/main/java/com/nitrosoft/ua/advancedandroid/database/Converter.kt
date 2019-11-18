package com.nitrosoft.ua.advancedandroid.database

interface Converter<E, D> {

    fun mapFromEntity(entity: E): D

    fun mapToEntity(model: D): E
}
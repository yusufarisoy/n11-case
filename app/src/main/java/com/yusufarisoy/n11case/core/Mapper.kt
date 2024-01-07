package com.yusufarisoy.n11case.core

interface Mapper<in Input, out Output> {

    fun map(input: Input): Output
}

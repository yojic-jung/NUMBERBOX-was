package com.kamcci.modules.system.construction.sample

import com.kamcci.modules.system.construction.di.config.CustomBeanAnnotationProperty

annotation class CustomBean

annotation class CustomPrimary

annotation class CustomQualifier(val value: String)

@CustomQualifier("aliases")
@CustomPrimary
@CustomBean
class TestBean

class NonAnnotatedClass

object DiTestFixture {
    fun getCustomAnnotationProperty() = CustomBeanAnnotationProperty(
        "com.kamcci.modules.system.construction.sample.CustomBean",
        "com.kamcci.modules.system.construction.sample",
        "singleton",
        "com.kamcci.modules.system.construction.sample.CustomPrimary",
        "com.kamcci.modules.system.construction.sample.CustomQualifier"
    )

    fun getNonAnnotationProperty() = CustomBeanAnnotationProperty(
        "com.kamcci.modules.system.construction.sample.NonAnnotatedClass",
        "com.kamcci.modules.system.construction.sample",
        "singleton",
        "com.kamcci.modules.system.construction.sample.CustomPrimary",
        "com.kamcci.modules.system.construction.sample.CustomQualifier"
    )
}


package com.kamcci.modules.system.construction.dummy

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
        "com.kamcci.modules.system.construction.dummy.CustomBean",
        "com.kamcci.modules.system.construction.dummy",
        "singleton",
        "com.kamcci.modules.system.construction.dummy.CustomPrimary",
        "com.kamcci.modules.system.construction.dummy.CustomQualifier"
    )

    fun getNonAnnotationProperty() = CustomBeanAnnotationProperty(
        "com.kamcci.modules.system.construction.dummy.NonAnnotatedClass",
        "com.kamcci.modules.system.construction.dummy",
        "singleton",
        "com.kamcci.modules.system.construction.dummy.CustomPrimary",
        "com.kamcci.modules.system.construction.dummy.CustomQualifier"
    )
}


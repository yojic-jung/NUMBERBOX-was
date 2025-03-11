package com.kamcci.modules.system.construction.sample

import com.kamcci.numberbox.app.domain.system.construction.Aliases
import com.kamcci.numberbox.app.domain.system.construction.Priority
import com.kamcci.numberbox.app.domain.system.construction.UseCase

annotation class CustomQualifier(val value: String)

@Aliases("aliases")
@Priority
@UseCase
class CustomPrimaryBean

@UseCase
class CustomHasNotOptionBean

class NonAnnotatedClass

